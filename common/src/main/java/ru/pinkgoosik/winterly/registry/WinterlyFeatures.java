package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.worldgen.CryomarbleFeature;
import ru.pinkgoosik.winterly.worldgen.UndergroundIcicleFeature;

public class WinterlyFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Winterly.MOD_ID);

	public static final RegisterSupplier<Feature<NoneFeatureConfiguration>> UNDERGROUND_ICICLE = FEATURES.register("underground_icicle", UndergroundIcicleFeature::new);
	public static final RegisterSupplier<Feature<NoneFeatureConfiguration>> CRYOMARBLE = FEATURES.register("cryomarble", CryomarbleFeature::new);
}
