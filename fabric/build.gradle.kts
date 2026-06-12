import net.darkhax.curseforgegradle.TaskPublishCurseForge

plugins {
    id("multiloader-loader")
    id("net.fabricmc.fabric-loom")
    id("com.modrinth.minotaur")
    id("net.darkhax.curseforgegradle")
}

val minecraft_version: String by project
val fabric_loader_version: String by project
val fabric_version: String by project
val mod_id: String by project
val mod_name: String by project

val releaseType = providers.gradleProperty("release_type").orElse("release")
val releaseDist = providers.gradleProperty("release_dist").orElse("both").map { value ->
    when (value.trim().lowercase()) {
        "client", "server", "both" -> value.trim().lowercase()
        else -> "both"
    }
}
val releaseChangelog = providers.gradleProperty("release_changelog").orElse("No changelog was specified.")
val publishChangelog = providers.provider {
    "[dist=${releaseDist.get()}]\n\n${releaseChangelog.get()}"
}
val modrinthProjectId = providers.gradleProperty("modrinth_project_fabric").orElse(providers.gradleProperty("modrinth_project"))
val curseforgeProjectId = providers.gradleProperty("curseforge_project_fabric").orElse(providers.gradleProperty("curseforge_project"))
val publishArtifact = provider {
    tasks.findByName("remapJar") ?: tasks.named("jar").get()
}

// Optional POM dependency whitelist for Maven publication.
// Example:
// extra["mavenDependencyWhitelist"] = listOf(
//     "group.id",
//     "artifact-id",
//     "group.id:artifact-id",
// )
extra["mavenDependencyWhitelist"] = emptyList<String>()

repositories {
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    implementation("net.fabricmc:fabric-loader:$fabric_loader_version")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")

    implementation("curse.maven:jei-238222:8108850")
    implementation("cc.sighs.oelib:OELib-fabric-26.1:0.3.0-SNAPSHOT")
    compileOnly("maven.modrinth:trinkets-updated:6o5TsjlI")
    implementation("com.terraformersmc:modmenu:18.0.0-alpha.8")
}

loom {
    val aw = project(":common").file("src/main/resources/${mod_id}.classtweaker")
    if (aw.exists()) {
        accessWidenerPath.set(aw)
    }
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("runs/client")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("runs/server")
        }
    }
}

// Implement mcgradleconventions loader attribute
val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements", "includeInternal", "modCompileClasspath").forEach { variant ->
    configurations.named(variant) {
        attributes {
            attribute(loaderAttribute, "fabric")
        }
    }
}
sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant ->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "fabric")
            }
        }
    }
}

modrinth {
    token.set(providers.environmentVariable("MODRINTH_TOKEN"))
    projectId.set(modrinthProjectId)
    versionNumber.set(provider { "${project.version}+fabric" })
    versionName.set(provider { "$mod_name ${project.version} (Fabric) [${releaseDist.get()}]" })
    versionType.set(releaseType)
    changelog.set(publishChangelog)
    uploadFile.set(publishArtifact)
    gameVersions.add(minecraft_version)
    loaders.add("fabric")
    detectLoaders.set(false)
}

tasks.named("modrinth") {
    onlyIf {
        !providers.environmentVariable("MODRINTH_TOKEN").orNull.isNullOrBlank() &&
            !modrinthProjectId.orNull.isNullOrBlank()
    }
}

tasks.register<TaskPublishCurseForge>("publishCurseForge") {
    group = "publishing"
    description = "Publish Fabric artifact to CurseForge."
    apiToken = providers.environmentVariable("CURSEFORGE_TOKEN").orNull

    val resolvedProjectId = curseforgeProjectId.orNull
    if (!resolvedProjectId.isNullOrBlank()) {
        val mainFile = upload(resolvedProjectId, publishArtifact.get())
        mainFile.releaseType = releaseType.get()
        mainFile.changelog = publishChangelog.get()
        mainFile.changelogType = "markdown"
        mainFile.addGameVersion(minecraft_version)
        mainFile.addModLoader("Fabric")
        when (releaseDist.get()) {
            "client" -> mainFile.addEnvironment("Client")
            "server" -> mainFile.addEnvironment("Server")
            else -> mainFile.addEnvironment("Client", "Server")
        }
    }

    onlyIf {
        !providers.environmentVariable("CURSEFORGE_TOKEN").orNull.isNullOrBlank() &&
            !curseforgeProjectId.orNull.isNullOrBlank()
    }
}

tasks.register("publishToPlatformServices") {
    group = "publishing"
    description = "Publish Fabric artifact to Modrinth and CurseForge."
    dependsOn("modrinth", "publishCurseForge")
}
