package winterly.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import winterly.Winterly;
import winterly.worldgen.CryomarbleFeature;
import winterly.worldgen.UndergroundIcicleFeature;

import static winterly.Winterly.id;

public class WinterlyFeatures {
	public static final Feature<DefaultFeatureConfig> UNDERGROUND_ICICLE_FEATURE = new UndergroundIcicleFeature();
	public static final RegistryKey<ConfiguredFeature<?,?>> UNDERGROUND_ICICLE_CONFIG = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Winterly.id("underground_icicle"));
	public static final RegistryKey<PlacedFeature> UNDERGROUND_ICICLE_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Winterly.id("underground_icicle"));


	public static final Feature<DefaultFeatureConfig> CRYOMARBLE_FEATURE = new CryomarbleFeature();
	public static final RegistryKey<ConfiguredFeature<?,?>> CRYOMARBLE_CONFIG = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Winterly.id("cryomarble"));
	public static final RegistryKey<PlacedFeature> CRYOMARBLE_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Winterly.id("cryomarble"));

    public static void init() {
        Registry.register(Registries.FEATURE, id("underground_icicle"), UNDERGROUND_ICICLE_FEATURE);
		Registry.register(Registries.FEATURE, id("cryomarble"), CRYOMARBLE_FEATURE);

		BiomeModifications.create(Winterly.id("features"))
				.add(ModificationPhase.ADDITIONS, ctx -> {
					var entry = ctx.getBiomeRegistryEntry();
					var coldTag = TagKey.of(RegistryKeys.BIOME, new Identifier("c", "climate_cold"));
					return !entry.isIn(BiomeTags.IS_NETHER) && !entry.isIn(BiomeTags.IS_END) && entry.isIn(coldTag);
				}, ctx -> {
					ctx.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, UNDERGROUND_ICICLE_PLACED);
				})
				.add(ModificationPhase.ADDITIONS, ctx -> {
					var entry = ctx.getBiomeRegistryEntry();
					return !entry.isIn(BiomeTags.IS_NETHER) && !entry.isIn(BiomeTags.IS_END);
				}, ctx -> {
					ctx.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, CRYOMARBLE_PLACED);
				});
    }
}
