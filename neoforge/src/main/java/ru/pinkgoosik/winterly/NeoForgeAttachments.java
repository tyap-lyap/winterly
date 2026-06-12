package ru.pinkgoosik.winterly;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import ru.pinkgoosik.winterly.data.ChunkFlowerCache;
import ru.pinkgoosik.winterly.data.DecorationData;

import java.util.function.Supplier;

public class NeoForgeAttachments {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
		DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Winterly.MOD_ID);

	public static final Supplier<AttachmentType<ChunkFlowerCache>> CHUNK_FLOWER_CACHE = ATTACHMENT_TYPES.register(
		"chunk_flower_cache", () -> AttachmentType.builder(() -> new ChunkFlowerCache()).serialize(ChunkFlowerCache.CODEC).build()
	);

	public static final Supplier<AttachmentType<DecorationData>> MOB_DECORATION = ATTACHMENT_TYPES.register(
		"mob_decoration",
		() -> AttachmentType.builder(() -> DecorationData.DEFAULT)
			.serialize(DecorationData.MAP_CODEC)
			.copyOnDeath()
			.sync(DecorationData.STREAM_CODEC)
			.build()
	);
}
