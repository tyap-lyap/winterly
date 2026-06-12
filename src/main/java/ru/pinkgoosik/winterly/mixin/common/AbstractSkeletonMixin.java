package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.extension.DecoratedMob;
import ru.pinkgoosik.winterly.util.HolidayUtils;

@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin extends Monster implements RangedAttackMob {

	protected AbstractSkeletonMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "finalizeSpawn", at = @At("RETURN"))
	void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CompoundTag tag, CallbackInfoReturnable<SpawnGroupData> cir) {

		if ((Object) this instanceof Skeleton skeleton && skeleton instanceof DecoratedMob decorated && !spawnType.equals(MobSpawnType.SPAWNER) && !spawnType.equals(MobSpawnType.CHUNK_GENERATION)) {
			if (Winterly.config().mobDecorations().enabled() && HolidayUtils.isWinterHolidays() || !Winterly.config().mobDecorations().onlyInWinter()) {
				if (!this.level().dimension().equals(Level.NETHER)) {
					int chance = Winterly.config().mobDecorations().chance();
					if (chance > 0 && Math.random() < (double) chance / 100) {
						decorated.winterly$setDecoration(level.getRandom().nextInt(11));
					}
				}
			}
		}
	}
}
