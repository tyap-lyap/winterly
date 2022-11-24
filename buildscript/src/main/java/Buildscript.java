import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;
import io.github.coolcrabs.brachyura.decompiler.fernflower.FernflowerDecompiler;
import io.github.coolcrabs.brachyura.fabric.*;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import io.github.coolcrabs.brachyura.quilt.QuiltMaven;
import io.github.coolcrabs.brachyura.processing.ProcessorChain;
import net.fabricmc.mappingio.tree.MappingTree;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Buildscript extends SimpleFabricProject {

	@Override
	public String getModId() {
		return Properties.MOD_ID;
	}

	@Override
	public String getVersion() {
		return Properties.MOD_VERSION;
	}

	@Override
	public VersionMeta createMcVersion() {
		return Minecraft.getVersion(Properties.MINECRAFT);
	}

	@Override
	public MappingTree createMappings() {
		return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn(Properties.YARN_MAPPINGS)).tree;
	}

	@Override
	public FabricLoader getLoader() {
		return new FabricLoader(FabricMaven.URL, FabricMaven.loader(Properties.FABRIC_LOADER));
	}

	@Override
	public void getModDependencies(ModDependencyCollector d) {
		addFabricModules(d);

		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "cloth-config-fabric", "8.2.88"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "basic-math", "0.6.1"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "modmenu", "4.0.6"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);

		d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-api", "5.0.2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-base", "5.0.2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		jij(d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-world", "5.0.2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE));
		d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-entity", "5.0.2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "trinkets", "3.4.1"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);

		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "emi", "0.4.2+1.19"), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "lazydfu", Properties.LAZY_DFU), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "ferrite-core", "5.0.0-fabric"), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "starlight", "1.1.1+1.19"), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "lithium", "mc1.19.2-0.10.2"), ModDependencyFlag.RUNTIME);

		// Compatibility
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "immersive-weathering", "1.2.3-1.19.2"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "moonlight", "ND0jV6Ba"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://maven.wispforest.io", new MavenId("io.wispforest", "owo-lib", "0.9.0+1.19"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
	}

	public static void addFabricModules(ModDependencyCollector d) {
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-api", Properties.FABRIC_API), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		try {
			String temp = "https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/%version%/fabric-api-%version%.pom";
			String pom = temp.replaceAll("%version%", Properties.FABRIC_API);
			URL url = new URL(pom);
			URLConnection request = url.openConnection();
			request.connect();
			InputStreamReader isReader = new InputStreamReader(request.getInputStream());
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(isReader);

			model.getDependencies().forEach(dependency -> {
				var id = dependency.getArtifactId();
				var ver = dependency.getVersion();
				d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", id, ver), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
			});

		}
		catch(Exception e) {
			System.out.println("Failed to add fabric modules due to an exception:\n" + e);
		}
	}

	@Override
	public int getJavaVersion() {
		return 17;
	}

	@Override
	public BrachyuraDecompiler decompiler() {
		return new FernflowerDecompiler(Maven.getMavenJarDep(QuiltMaven.URL, new MavenId("org.quiltmc", "quiltflower", Properties.QUILTFLOWER)));
	}

	@Override
	public ProcessorChain resourcesProcessingChain() {
		return new ProcessorChain(super.resourcesProcessingChain(), new FmjVersionFixer(this));
	}
}
