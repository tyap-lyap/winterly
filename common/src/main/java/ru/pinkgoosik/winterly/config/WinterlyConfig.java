package ru.pinkgoosik.winterly.config;

import cc.sighs.oelib.config.ConfigManager;
import cc.sighs.oelib.config.ConfigSchema;
import cc.sighs.oelib.config.ConfigUnit;
import cc.sighs.oelib.config.codecs.ConfigMetaCodec;
import cc.sighs.oelib.config.field.ConfigField;
import cc.sighs.oelib.config.model.ConfigStorageFormat;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;
import ru.pinkgoosik.winterly.Winterly;

import java.lang.invoke.MethodHandles;

public record WinterlyConfig(
	int maxGiftBoxCapacity,
	boolean updateCheckerEnabled,
	boolean generateUndergroundIcicles,
	boolean generateCryomarble,
	boolean generateFrozenGrass,
	boolean generateFrozenFlowers,
	MobDecorationsConfig mobDecorations
) {
	public static final Permission OP_LEVEL_4 = new Permission.HasCommandLevel(PermissionLevel.OWNERS);
	private static final String FILE_NAME = "winterly";
	public static final ConfigSchema.Definition<WinterlyConfig> DEFINITION = ConfigSchema.defineServer(
		MethodHandles.lookup(),
		Identifier.fromNamespaceAndPath(Winterly.MOD_ID, "option"),
		WinterlyConfig.class,
		meta -> meta
			.directory(Winterly.MOD_ID)
			.fileName(FILE_NAME)
			.format(ConfigStorageFormat.TOML),
		schema -> schema.group(
			ConfigField.intRange("max_gift_box_capacity", 1, 100)
				.comment("Maximum capacity of gift boxes")
				.defaultValue(3)
				.forGetter(WinterlyConfig::maxGiftBoxCapacity),
			ConfigField.bool("update_checker_enabled")
				.comment("Enable update checker")
				.defaultValue(true)
				.forGetter(WinterlyConfig::updateCheckerEnabled),
			ConfigField.bool("generate_underground_icicles")
				.comment("Generate underground icicles")
				.defaultValue(true)
				.forGetter(WinterlyConfig::generateUndergroundIcicles),
			ConfigField.bool("generate_cryomarble")
				.comment("Generate cryomarble ore")
				.defaultValue(true)
				.forGetter(WinterlyConfig::generateCryomarble),
			ConfigField.bool("generate_frozen_grass")
				.comment("Generate frozen grass")
				.defaultValue(true)
				.forGetter(WinterlyConfig::generateFrozenGrass),
			ConfigField.bool("generate_frozen_flowers")
				.comment("Generate frozen flowers")
				.defaultValue(true)
				.forGetter(WinterlyConfig::generateFrozenFlowers),
			ConfigSchema.record("mob_decorations", MobDecorationsConfig.class,
				MobDecorationsConfig.CODEC,
				WinterlyConfig::mobDecorations
			)
		).apply(schema, WinterlyConfig::new)
	);
	public static final ConfigUnit<WinterlyConfig> UNIT = DEFINITION.unit();

	public static void register() {
		ConfigManager.registerServer(UNIT, player -> player.permissions().hasPermission(OP_LEVEL_4));
	}

	public int getGiftBoxCapacity() {
		return Math.max(1, maxGiftBoxCapacity);
	}

	public record MobDecorationsConfig(
		boolean enabled,
		boolean onlyInWinter,
		int chance
	) {
		public static final ConfigMetaCodec<MobDecorationsConfig> CODEC = ConfigSchema.metaCodec(
			MobDecorationsConfig.class,
			nested -> nested.group(
				ConfigField.bool("enabled")
					.comment("Enable mob decorations")
					.defaultValue(true)
					.forGetter(MobDecorationsConfig::enabled),
				ConfigField.bool("only_in_winter")
					.comment("Only decorate mobs during winter holidays")
					.defaultValue(true)
					.forGetter(MobDecorationsConfig::onlyInWinter),
				ConfigField.intRange("chance", 0, 100)
					.comment("Chance of decoration (0-100)")
					.defaultValue(15)
					.forGetter(MobDecorationsConfig::chance)
			).apply(nested, MobDecorationsConfig::new)
		);
	}
}
