package winterly.fabric.data;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import winterly.Winterly;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;

public class WinterlyComponents implements WorldComponentInitializer {
	public static final ComponentKey<WorldData> WORLD_DATA = ComponentRegistryV3.INSTANCE.getOrCreate(Winterly.id("world_data"), WorldData.class);

	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
		registry.register(WORLD_DATA, WorldData::new);
	}
}
