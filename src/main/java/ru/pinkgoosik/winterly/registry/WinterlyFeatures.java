package ru.pinkgoosik.winterly.registry;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.worldgen.CryomarbleFeature;
import ru.pinkgoosik.winterly.worldgen.UndergroundIcicleFeature;

public class WinterlyFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Winterly.MOD_ID);

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> UNDERGROUND_ICICLE = FEATURES.register("underground_icicle", UndergroundIcicleFeature::new);
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> CRYOMARBLE = FEATURES.register("cryomarble", CryomarbleFeature::new);
}
