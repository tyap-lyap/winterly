package ru.pinkgoosik.winterly.util;

public final class FrostNoise {
	private static final double WORLDGEN_GRASS_THRESHOLD = 0.72;
	private static final double WORLDGEN_FLOWER_THRESHOLD = 0.60;
	private static final double PRECIPITATION_GRASS_THRESHOLD = 0.64;
	private static final double PRECIPITATION_FLOWER_THRESHOLD = 0.52;
	private static final long PRECIPITATION_BUCKET_TICKS = 80L;
	public static boolean shouldFreeze(long seed, int x, int z) {
		return sample(seed, x, z) >= WORLDGEN_GRASS_THRESHOLD;
	}

	public static double threshold(boolean flower, boolean precipitation) {
		if (precipitation) {
			return flower ? PRECIPITATION_FLOWER_THRESHOLD : PRECIPITATION_GRASS_THRESHOLD;
		}
		return flower ? WORLDGEN_FLOWER_THRESHOLD : WORLDGEN_GRASS_THRESHOLD;
	}

	public static double sample(long seed, int x, int z) {
		long mixed = mix(seed, x, z);
		return (double) ((mixed >>> 11) & ((1L << 53) - 1)) / (double) (1L << 53);
	}

	public static double sampleForPrecipitation(long seed, int x, int z, long gameTime) {
		long bucket = Math.max(0L, gameTime / PRECIPITATION_BUCKET_TICKS);
		double areaBias = sample(seed ^ 0xA5A5A5A55A5A5A5AL, x >> 2, z >> 2);
		double timeSample = sample(seed ^ Long.rotateLeft(bucket * 0x9E3779B97F4A7C15L, 23), x, z);
		return areaBias * 0.35 + timeSample * 0.65;
	}

	private static long mix(long seed, int x, int z) {
		long h = seed ^ 0x9E3779B97F4A7C15L;
		h ^= Integer.toUnsignedLong(x) * 0x632BE59BD9B4E019L;
		h = Long.rotateLeft(h, 27);
		h ^= Integer.toUnsignedLong(z) * 0x9E3779B185EBCA87L;
		h = fmix64(h);

		long coarse = seed ^ 0xD1B54A32D192ED03L;
		coarse ^= Integer.toUnsignedLong(x >> 2) * 0x94D049BB133111EBL;
		coarse ^= Integer.toUnsignedLong(z >> 2) * 0x369DEA0F31A53F85L;
		coarse = fmix64(coarse);

		return fmix64(h ^ Long.rotateLeft(coarse, 17));
	}

	private static long fmix64(long value) {
		value ^= value >>> 33;
		value *= 0xff51afd7ed558ccdL;
		value ^= value >>> 33;
		value *= 0xc4ceb9fe1a85ec53L;
		value ^= value >>> 33;
		return value;
	}
}
