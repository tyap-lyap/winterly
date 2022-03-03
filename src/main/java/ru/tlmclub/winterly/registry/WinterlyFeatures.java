package ru.tlmclub.winterly.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import ru.tlmclub.winterly.worldgen.CryomarbleFeature;
import ru.tlmclub.winterly.worldgen.UndergroundIcicleFeature;

import java.util.List;

import static ru.tlmclub.winterly.WinterlyMod.locate;

@SuppressWarnings("deprecation")
public class WinterlyFeatures {
    public static final Feature<DefaultFeatureConfig> UNDERGROUND_ICICLE_FEATURE = new UndergroundIcicleFeature();
    public static final ConfiguredFeature<?, ?> UNDERGROUND_ICICLE_FEATURE_CONFIG = new ConfiguredFeature<>(UNDERGROUND_ICICLE_FEATURE, new DefaultFeatureConfig());
    public static final PlacedFeature UNDERGROUND_ICICLE_FEATURE_PLACED = new PlacedFeature(RegistryEntry.of(UNDERGROUND_ICICLE_FEATURE_CONFIG), List.of(PlacedFeatures.BOTTOM_TO_120_RANGE));

    public static final Feature<DefaultFeatureConfig> CRYOMARBLE_FEATURE = new CryomarbleFeature();
    public static final ConfiguredFeature<?, ?> CRYOMARBLE_FEATURE_CONFIG = new ConfiguredFeature<>(CRYOMARBLE_FEATURE, new DefaultFeatureConfig());
    public static final PlacedFeature CRYOMARBLE_FEATURE_PLACED = new PlacedFeature(RegistryEntry.of(CRYOMARBLE_FEATURE_CONFIG), List.of(PlacedFeatures.BOTTOM_TO_120_RANGE));

    public static void init() {
        Registry.register(Registry.FEATURE, locate("underground_icicle_feature"), UNDERGROUND_ICICLE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, locate("underground_icicle_feature"), UNDERGROUND_ICICLE_FEATURE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, locate("underground_icicle_feature"), UNDERGROUND_ICICLE_FEATURE_PLACED);

        BiomeModifications.addFeature(ctx -> {
                    Biome.Category category = Biome.getCategory(ctx.getBiomeRegistryEntry());
                    return !category.equals(Biome.Category.NETHER) && !category.equals(Biome.Category.THEEND)
                            && category.equals(Biome.Category.ICY);
                },
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                BuiltinRegistries.PLACED_FEATURE.getKey(UNDERGROUND_ICICLE_FEATURE_PLACED).orElseThrow()
        );

        Registry.register(Registry.FEATURE, locate("cryomarble_feature"), CRYOMARBLE_FEATURE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, locate("cryomarble_feature"), CRYOMARBLE_FEATURE_CONFIG);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, locate("cryomarble_feature"), CRYOMARBLE_FEATURE_PLACED);

        BiomeModifications.addFeature(ctx -> {
                    Biome.Category category = Biome.getCategory(ctx.getBiomeRegistryEntry());
                    return !category.equals(Biome.Category.NETHER) && !category.equals(Biome.Category.THEEND);
                },
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                BuiltinRegistries.PLACED_FEATURE.getKey(CRYOMARBLE_FEATURE_PLACED).orElseThrow()
        );
    }
}
