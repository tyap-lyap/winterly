package winterly.config;

public class WinterlyConfig {
    public MobDecorationsConfig mobDecorations = new MobDecorationsConfig();

    public static class MobDecorationsConfig {
        public boolean enabled = true;
        public boolean onlyInWinter = true;
        public int chance = 5;
    }

}
