package winterly.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import winterly.worldgen.CryomarbleFeature;
import winterly.worldgen.UndergroundIcicleFeature;

import java.util.List;

import static winterly.Winterly.id;

public class WinterlyFeatures {
    public static final Feature<DefaultFeatureConfig> UNDERGROUND_ICICLE_FEATURE = new UndergroundIcicleFeature();
    public static final ConfiguredFeature<?, ?> UNDERGROUND_ICICLE_FEATURE_CONFIG = new ConfiguredFeature<>(UNDERGROUND_ICICLE_FEATURE, new DefaultFeatureConfig());
    public static final PlacedFeature UNDERGROUND_ICICLE_FEATURE_PLACED = new PlacedFeature(RegistryEntry.of(UNDERGROUND_ICICLE_FEATURE_CONFIG), List.of(PlacedFeatures.BOTTOM_TO_120_RANGE));

    public static final Feature<DefaultFeatureConfig> CRYOMARBLE_FEATURE = new CryomarbleFeature();
    public static final ConfiguredFeature<?, ?> CRYOMARBLE_FEATURE_CONFIG = new ConfiguredFeature<>(CRYOMARBLE_FEATURE, new DefaultFeatureConfig());
    public static final PlacedFeature CRYOMARBLE_FEATURE_PLACED = new PlacedFeature(RegistryEntry.of(CRYOMARBLE_FEATURE_CONFIG), List.of(PlacedFeatures.BOTTOM_TO_120_RANGE));

    public static void init() {
        Registry.register(Registry.FEATURE, id("underground_icicle_feature"), UNDERGROUND_ICICLE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id("underground_icicle_feature"), UNDERGROUND_ICICLE_FEATURE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, id("underground_icicle_feature"), UNDERGROUND_ICICLE_FEATURE_PLACED);

        BiomeModifications.addFeature(ctx -> {
            var entry = ctx.getBiomeRegistryEntry();
            var coldTag = TagKey.of(Registry.BIOME_KEY, new Identifier("c", "climate_cold"));
            return !entry.isIn(BiomeTags.IS_NETHER) && !entry.isIn(BiomeTags.IS_END) && entry.isIn(coldTag);
        },
        GenerationStep.Feature.UNDERGROUND_DECORATION,
        BuiltinRegistries.PLACED_FEATURE.getKey(UNDERGROUND_ICICLE_FEATURE_PLACED).orElseThrow()
        );

        Registry.register(Registry.FEATURE, id("cryomarble_feature"), CRYOMARBLE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id("cryomarble_feature"), CRYOMARBLE_FEATURE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, id("cryomarble_feature"), CRYOMARBLE_FEATURE_PLACED);

        BiomeModifications.addFeature(ctx -> {
            var entry = ctx.getBiomeRegistryEntry();
            return !entry.isIn(BiomeTags.IS_NETHER) && !entry.isIn(BiomeTags.IS_END);
        },
        GenerationStep.Feature.UNDERGROUND_DECORATION,
        BuiltinRegistries.PLACED_FEATURE.getKey(CRYOMARBLE_FEATURE_PLACED).orElseThrow()
        );
    }
}
