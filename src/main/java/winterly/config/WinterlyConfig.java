package winterly.config;

public class WinterlyConfig {
	public boolean generateUndergroundIcicle = true;
    public MobDecorationsConfig mobDecorations = new MobDecorationsConfig();

    public static class MobDecorationsConfig {
        public boolean enabled = true;
        public boolean onlyInWinter = true;
        public int chance = 5;
    }

}
