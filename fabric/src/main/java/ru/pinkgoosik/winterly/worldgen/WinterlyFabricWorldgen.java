package ru.pinkgoosik.winterly.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import ru.pinkgoosik.winterly.Winterly;

public final class WinterlyFabricWorldgen {
	private static final ResourceKey<PlacedFeature> CRYOMARBLE = ResourceKey.create(Registries.PLACED_FEATURE, Winterly.id("cryomarble"));
	private static final ResourceKey<PlacedFeature> UNDERGROUND_ICICLE = ResourceKey.create(Registries.PLACED_FEATURE, Winterly.id("underground_icicle"));

	private WinterlyFabricWorldgen() {
	}

	public static void register() {
		BiomeModifications.addFeature(
			BiomeSelectors.foundInOverworld().and(context -> !context.hasPlacedFeature(CRYOMARBLE)),
			GenerationStep.Decoration.UNDERGROUND_DECORATION,
			CRYOMARBLE
		);
		BiomeModifications.addFeature(
			BiomeSelectors.foundInOverworld().and(context -> !context.hasPlacedFeature(UNDERGROUND_ICICLE)),
			GenerationStep.Decoration.UNDERGROUND_DECORATION,
			UNDERGROUND_ICICLE
		);
	}
}
