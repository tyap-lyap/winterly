package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;
import ru.pinkgoosik.winterly.data.DecorationData;
import ru.pinkgoosik.winterly.extension.DecoratedMob;
import ru.pinkgoosik.winterly.util.HolidayUtils;

@Mixin(Zombie.class)
public abstract class ZombieExtension extends Monster implements DecoratedMob {
	protected ZombieExtension(EntityType<? extends Monster> type, Level level) {
		super(type, level);
	}

	@Override
	public boolean winterly$isDecorated() {
		return WinterlyPlatformHolder.get().getDecorationData(this).decorated();
	}

	@Override
	public int winterly$getIndex() {
		return WinterlyPlatformHolder.get().getDecorationData(this).index();
	}

	@Override
	public void winterly$setDecoration(int index) {
		WinterlyPlatformHolder.get().setDecorationData(this, DecorationData.decorated(index));
	}

	@Override
	public void winterly$clearDecoration() {
		WinterlyPlatformHolder.get().setDecorationData(this, DecorationData.DEFAULT);
	}

	@Inject(method = "finalizeSpawn", at = @At("RETURN"))
	void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData groupData, CallbackInfoReturnable<SpawnGroupData> cir) {
		if (!spawnReason.equals(EntitySpawnReason.SPAWNER) && !spawnReason.equals(EntitySpawnReason.CHUNK_GENERATION) && !isBaby()) {
			if (Winterly.config().mobDecorations().enabled() && HolidayUtils.isWinterHolidays() || !Winterly.config().mobDecorations().onlyInWinter()) {
				if (!this.level().dimension().equals(Level.NETHER)) {
					int chance = Winterly.config().mobDecorations().chance();
					if (chance > 0 && Math.random() < (double) chance / 100) {
						this.winterly$setDecoration(level.getRandom().nextInt(11));
					}
				}
			}
		}
	}
}
