package ru.pinkgoosik.winterly.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record DecorationData(boolean decorated, int index) {
	public static final DecorationData DEFAULT = new DecorationData(false, 0);

	public static final MapCodec<DecorationData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.BOOL.optionalFieldOf("decorated", false).forGetter(DecorationData::decorated),
		Codec.INT.optionalFieldOf("index", 0).forGetter(DecorationData::index)
	).apply(instance, DecorationData::new));
	public static final Codec<DecorationData> CODEC = MAP_CODEC.codec();

	public static final StreamCodec<RegistryFriendlyByteBuf, DecorationData> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.BOOL,
		DecorationData::decorated,
		ByteBufCodecs.VAR_INT,
		DecorationData::index,
		DecorationData::new
	);

	public static DecorationData decorated(int index) {
		return new DecorationData(true, index);
	}
}
