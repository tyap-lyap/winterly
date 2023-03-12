import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;
import io.github.coolcrabs.brachyura.decompiler.fernflower.FernflowerDecompiler;
import io.github.coolcrabs.brachyura.fabric.*;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
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
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag.RUNTIME;
import static io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag.COMPILE;

public class Buildscript extends SimpleFabricProject {
	public Properties properties = null;

	@Override
	public String getModId() {
		return this.getProperties().getProperty("name");
	}

	@Override
	public String getVersion() {
		return this.getProperties().getProperty("modVersion");
	}

	@Override
	public VersionMeta createMcVersion() {
		return Minecraft.getVersion(this.getProperties().getProperty("minecraftVersion"));
	}

	@Override
	public MappingTree createMappings() {
		return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn(this.getProperties().getProperty("yarnMappings"))).tree;
	}

	@Override
	public FabricLoader getLoader() {
		return new FabricLoader(FabricMaven.URL, FabricMaven.loader(this.getProperties().getProperty("fabricLoader")));
	}

	@Override
	public void getModDependencies(ModDependencyCollector d) {
		addFabricModules(d);

		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "cloth-config-fabric", this.getProperties().getProperty("clothConfig")), RUNTIME, COMPILE);
		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "basic-math", "0.6.1"), RUNTIME, COMPILE);

		String cardinalComponents = this.getProperties().getProperty("cardinalComponents");
		d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-api", cardinalComponents), RUNTIME, COMPILE);
		d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-base", cardinalComponents), RUNTIME, COMPILE);
		d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-entity", cardinalComponents), RUNTIME, COMPILE);
		jij(d.addMaven("https://ladysnake.jfrog.io/artifactory/mods", new MavenId("dev.onyxstudios.cardinal-components-api", "cardinal-components-world", cardinalComponents), RUNTIME, COMPILE));
		d.addMaven("https://maven.terraformersmc.com", new MavenId("dev.emi", "trinkets", this.getProperties().getProperty("trinkets")), RUNTIME, COMPILE);

		// Runtime Mods
		d.addMaven("https://api.modrinth.com/maven", new MavenId("maven.modrinth", "emi", "0.7.0+1.19.3"), RUNTIME);

		// Compatibility
		d.addMaven("https://maven.terraformersmc.com", new MavenId("com.terraformersmc", "modmenu", "5.1.0-beta.4"), RUNTIME, COMPILE);
		//d.addMaven("https://api.modrinth.com/maven", new MavenId("maven.modrinth", "immersive-weathering", "1.2.3-1.19.2"), RUNTIME, COMPILE);
		//d.addMaven("https://api.modrinth.com/maven", new MavenId("maven.modrinth", "moonlight", "ND0jV6Ba"), RUNTIME, COMPILE);
		d.addMaven("https://maven.wispforest.io", new MavenId("io.wispforest", "owo-lib", "0.10.2+1.19.3"), RUNTIME, COMPILE);
		//d.addMaven("https://api.modrinth.com/maven", new MavenId("maven.modrinth", "fabric-seasons", "1.4-BETA+1.19"), RUNTIME, COMPILE);
	}

	public void addFabricModules(ModDependencyCollector d) {
		String fabricApi = this.getProperties().getProperty("fabricApi");
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-api", fabricApi), RUNTIME, COMPILE);
		try {
			String temp = "https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/%version%/fabric-api-%version%.pom";
			String pom = temp.replaceAll("%version%", fabricApi);
			URL url = new URL(pom);
			URLConnection request = url.openConnection();
			request.connect();
			InputStreamReader isReader = new InputStreamReader(request.getInputStream());
			MavenXpp3Reader reader = new MavenXpp3Reader();
			Model model = reader.read(isReader);

			model.getDependencies().forEach(dependency -> {
				var id = dependency.getArtifactId();
				var ver = dependency.getVersion();
				d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", id, ver), RUNTIME, COMPILE);
			});

		}
		catch(Exception e) {
			System.out.println("Failed to add fabric modules due to an exception:\n" + e);
		}
	}

	public Properties getProperties() {
		if(this.properties == null) {
			try (InputStream input = new FileInputStream(getProjectDir().resolve("buildscript").resolve("buildscript.properties").toFile())) {
				Properties prop = new Properties();
				prop.load(input);
				this.properties = prop;
			}
			catch (Exception e) {
				System.out.println("Failed to read buildscript.properties due to an exception:\n" + e);
				this.properties = new Properties();
			}
		}
		return this.properties;
	}

	@Override
	public int getJavaVersion() {
		return 17;
	}

	@Override
	public BrachyuraDecompiler decompiler() {
		return new FernflowerDecompiler(Maven.getMavenJarDep(QuiltMaven.URL, new MavenId("org.quiltmc", "quiltflower", this.getProperties().getProperty("quiltflower"))));
	}

	@Override
	public ProcessorChain resourcesProcessingChain() {
		return new ProcessorChain(super.resourcesProcessingChain(), new FmjVersionFixer(this));
	}
}
