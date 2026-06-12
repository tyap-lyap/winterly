package ru.pinkgoosik.winterly;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import ru.pinkgoosik.winterly.data.ChunkFlowerCache;
import ru.pinkgoosik.winterly.data.DecorationData;

public class FabricAttachments {
	public static final AttachmentType<ChunkFlowerCache> FLOWER_CACHE = AttachmentRegistry.create(
		Winterly.id("flower_cache"),
		builder -> builder
			.initializer(ChunkFlowerCache::new)
			.persistent(ChunkFlowerCache.CODEC.codec())
	);

	public static final AttachmentType<DecorationData> MOB_DECORATION = AttachmentRegistry.create(
		Winterly.id("mob_decoration"),
		builder -> builder
			.initializer(() -> DecorationData.DEFAULT)
			.persistent(DecorationData.CODEC)
			.copyOnDeath()
			.syncWith(DecorationData.STREAM_CODEC, AttachmentSyncPredicate.all())
	);

	public static void register() {}
}
