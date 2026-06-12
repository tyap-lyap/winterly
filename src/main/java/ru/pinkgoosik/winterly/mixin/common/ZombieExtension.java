package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.extension.DecoratedMob;
import ru.pinkgoosik.winterly.util.HolidayUtils;

@Mixin(Zombie.class)
public abstract class ZombieExtension extends Monster implements DecoratedMob {
	private static final EntityDataAccessor<Boolean> winterly$DECORATED = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> winterly$INDEX = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.INT);

	protected ZombieExtension(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public boolean winterly$isDecorated() {
		return getEntityData().get(winterly$DECORATED);
	}

	@Override
	public int winterly$getIndex() {
		return getEntityData().get(winterly$INDEX);
	}

	@Override
	public void winterly$setDecoration(int index) {
		getEntityData().set(winterly$DECORATED, true);
		getEntityData().set(winterly$INDEX, index);
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	void initData(CallbackInfo ci) {
		this.getEntityData().define(winterly$DECORATED, false);
		this.getEntityData().define(winterly$INDEX, 0);
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	void write(CompoundTag nbt, CallbackInfo ci) {
		nbt.putBoolean("WinterlyDecorated", getEntityData().get(winterly$DECORATED));
		nbt.putInt("WinterlyIndex", getEntityData().get(winterly$INDEX));
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	void read(CompoundTag nbt, CallbackInfo ci) {
		getEntityData().set(winterly$DECORATED, nbt.getBoolean("WinterlyDecorated"));
		getEntityData().set(winterly$INDEX, nbt.getInt("WinterlyIndex"));
	}

	@Inject(method = "finalizeSpawn", at = @At("RETURN"))
	void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CompoundTag tag, CallbackInfoReturnable<SpawnGroupData> cir) {
		if (!spawnType.equals(MobSpawnType.SPAWNER) && !spawnType.equals(MobSpawnType.CHUNK_GENERATION) && !isBaby()) {
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
