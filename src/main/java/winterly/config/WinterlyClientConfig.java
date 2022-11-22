package winterly.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import winterly.Winterly;

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

		category.addEntry(builder.startBooleanToggle(text("option.generate_underground_icicles"), config.generateUndergroundIcicle)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.generateUndergroundIcicle = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.mob_decorations.enabled"), config.mobDecorations.enabled)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.mobDecorations.enabled = newValue)
				.build());

		category.addEntry(builder.startBooleanToggle(text("option.mob_decorations.only_in_winter"), config.mobDecorations.onlyInWinter)
				.setDefaultValue(true)
				.setSaveConsumer(newValue -> config.mobDecorations.onlyInWinter = newValue)
				.build());

		category.addEntry(builder.startIntSlider(text("option.mob_decorations.chance"), config.mobDecorations.chance, 0, 100)
				.setDefaultValue(5)
				.setSaveConsumer(newValue -> config.mobDecorations.chance = newValue)
				.build());
	}

	private static Text text(String key) {
		return Text.translatable("config.winterly." + key);
	}
}
