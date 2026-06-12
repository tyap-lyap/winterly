package ru.pinkgoosik.winterly.client.render;

import net.minecraft.util.context.ContextKey;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.data.DecorationData;

public final class NeoForgeRenderStateKeys {
	public static final ContextKey<DecorationData> MOB_DECORATION = new ContextKey<>(Winterly.id("mob_decoration"));
	public static final ContextKey<Boolean> VISIBLE_HAT = new ContextKey<>(Winterly.id("visible_hat"));
}
