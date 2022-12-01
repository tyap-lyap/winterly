package winterly.config;

public class WinterlyConfig {
	public int maxGiftBoxCapacity = 3;
	public boolean updateCheckerEnabled = true;
	public boolean generateUndergroundIcicles = true;
	public boolean generateCryomarble = true;
	public boolean generateFrozenGrass = true;
	public boolean generateFrozenFlowers = true;
    public MobDecorationsConfig mobDecorations = new MobDecorationsConfig();

	public int getGiftBoxCapacity() {
		return Math.max(1, maxGiftBoxCapacity);
	}

    public static class MobDecorationsConfig {
        public boolean enabled = false;
        public boolean onlyInWinter = true;
        public int chance = 15;
    }

}
