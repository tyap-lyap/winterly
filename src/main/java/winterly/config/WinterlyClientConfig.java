package winterly.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import winterly.Winterly;

@Environment(EnvType.CLIENT)
public class WinterlyClientConfig {

	public static Screen buildScreen(Screen parent) {
		ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("title"));
		configBuilder.setSavingRunnable(() -> AutoConfig.getConfigHolder(WinterlyClothConfig.class).save());
		ConfigCategory general = configBuilder.getOrCreateCategory(text("general"));
		ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
		WinterlyClientConfig.setupEntries(general, entryBuilder);
		return configBuilder.build();
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		var config = Winterly.config;

		category.addEntry(builder.startIntField(text("option.max_gift_box_capacity"), config.maxGiftBoxCapacity)
				.setDefaultValue(3)
				.setSaveConsumer(newValue -> config.maxGiftBoxCapacity = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.update_checker_enabled"), config.updateCheckerEnabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.updateCheckerEnabled = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.generate_underground_icicles"), config.generateUndergroundIcicles)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.generateUndergroundIcicles = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.generate_cryomarble"), config.generateCryomarble)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.generateCryomarble = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.generate_frozen_grass"), config.generateFrozenGrass)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.generateFrozenGrass = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.generate_frozen_flowers"), config.generateFrozenFlowers)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.generateFrozenFlowers = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.mob_decorations.enabled"), config.mobDecorations.enabled)
				.setDefaultValue(false)
				.setSaveConsumer(newValue -> config.mobDecorations.enabled = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.mob_decorations.only_in_winter"), config.mobDecorations.onlyInWinter)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.mobDecorations.onlyInWinter = newValue)
				.build());

		category.addEntry(builder.startIntSlider(text("option.mob_decorations.chance"), config.mobDecorations.chance, 0, 100)
				.setDefaultValue(15)
				.setSaveConsumer(newValue -> config.mobDecorations.chance = newValue)
				.build());
	}

	private static Text text(String key) {
		return Text.translatable("config.winterly." + key);
	}
}
