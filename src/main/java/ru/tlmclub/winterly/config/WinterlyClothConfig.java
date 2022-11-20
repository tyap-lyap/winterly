package ru.tlmclub.winterly.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import ru.tlmclub.winterly.Winterly;

@Config(name = "winterly")
public class WinterlyClothConfig extends WinterlyConfig implements ConfigData {

	public static Screen buildScreen(Screen parent) {
		ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(text("title"));
		configBuilder.setSavingRunnable(() -> AutoConfig.getConfigHolder(WinterlyClothConfig.class).save());
		ConfigCategory general = configBuilder.getOrCreateCategory(text("general"));
		ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
		WinterlyClothConfig.setupEntries(general, entryBuilder);
		return configBuilder.build();
	}

    public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
        var config = Winterly.config;
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

	public static WinterlyClothConfig init() {
		AutoConfig.register(WinterlyClothConfig.class, GsonConfigSerializer::new);
		return AutoConfig.getConfigHolder(WinterlyClothConfig.class).getConfig();
	}

}
