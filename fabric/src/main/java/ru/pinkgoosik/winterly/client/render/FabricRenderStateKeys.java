package ru.pinkgoosik.winterly.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import ru.pinkgoosik.winterly.data.DecorationData;

public final class FabricRenderStateKeys {
	// fuck fabric
	public static final RenderStateDataKey<DecorationData> MOB_DECORATION =
		RenderStateDataKey.create(() -> "winterly:mob_decoration");
	public static final RenderStateDataKey<Boolean> VISIBLE_HAT =
		RenderStateDataKey.create(() -> "winterly:visible_hat");
}
