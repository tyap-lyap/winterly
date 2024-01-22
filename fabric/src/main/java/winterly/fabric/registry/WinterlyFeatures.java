package winterly.fabric.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import winterly.Winterly;
import winterly.worldgen.CryomarbleFeature;
import winterly.worldgen.UndergroundIcicleFeature;

import static winterly.Winterly.id;

public class WinterlyFeatures {
	public static final Feature<NoneFeatureConfiguration> UNDERGROUND_ICICLE_FEATURE = new UndergroundIcicleFeature();
	public static final ResourceKey<ConfiguredFeature<?,?>> UNDERGROUND_ICICLE_CONFIG = ResourceKey.create(Registries.CONFIGURED_FEATURE, Winterly.id("underground_icicle"));
	public static final ResourceKey<PlacedFeature> UNDERGROUND_ICICLE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, Winterly.id("underground_icicle"));


	public static final Feature<NoneFeatureConfiguration> CRYOMARBLE_FEATURE = new CryomarbleFeature();
	public static final ResourceKey<ConfiguredFeature<?,?>> CRYOMARBLE_CONFIG = ResourceKey.create(Registries.CONFIGURED_FEATURE, Winterly.id("cryomarble"));
	public static final ResourceKey<PlacedFeature> CRYOMARBLE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, Winterly.id("cryomarble"));

	public static void init() {
		Registry.register(BuiltInRegistries.FEATURE, id("underground_icicle"), UNDERGROUND_ICICLE_FEATURE);
		Registry.register(BuiltInRegistries.FEATURE, id("cryomarble"), CRYOMARBLE_FEATURE);

		BiomeModifications.create(Winterly.id("features"))
				.add(ModificationPhase.ADDITIONS, ctx -> {
					var entry = ctx.getBiomeRegistryEntry();
					var coldTag = TagKey.create(Registries.BIOME, new ResourceLocation("c", "climate_cold"));
					return !entry.is(BiomeTags.IS_NETHER) && !entry.is(BiomeTags.IS_END) && entry.is(coldTag);
				}, ctx -> {
					ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, UNDERGROUND_ICICLE_PLACED);
				})
				.add(ModificationPhase.ADDITIONS, ctx -> {
					var entry = ctx.getBiomeRegistryEntry();
					return !entry.is(BiomeTags.IS_NETHER) && !entry.is(BiomeTags.IS_END);
				}, ctx -> {
					ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CRYOMARBLE_PLACED);
				});
	}
}
