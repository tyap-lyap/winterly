package ru.tlmclub.winterly.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;
import ru.tlmclub.winterly.Winterly;

@Config(name = "winterly")
public class WinterlyClothConfig extends WinterlyConfig implements ConfigData {

    public static ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(text("title"));

            ConfigCategory general = builder.getOrCreateCategory(text("general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            WinterlyClothConfig.setupEntries(general, entryBuilder);

            return builder.build();
        };
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
                .setDefaultValue(15)
                .setSaveConsumer(newValue -> config.mobDecorations.chance = newValue)
                .build());
    }

    private static Text text(String key) {
        return Text.translatable("config.winterly." + key);
    }

    public static void init() {
        AutoConfig.register(WinterlyClothConfig.class, GsonConfigSerializer::new);
    }

    public static WinterlyClothConfig getConfig() {
        return AutoConfig.getConfigHolder(WinterlyClothConfig.class).getConfig();
    }

}
