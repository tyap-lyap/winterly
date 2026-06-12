package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.winterly.extension.DecoratedMob;

@Mixin(Skeleton.class)
public abstract class SkeletonExtension extends AbstractSkeleton implements DecoratedMob {
	@Unique
    private static final EntityDataAccessor<Boolean> winterly$DECORATED = SynchedEntityData.defineId(Skeleton.class, EntityDataSerializers.BOOLEAN);
	@Unique
    private static final EntityDataAccessor<Integer> winterly$INDEX = SynchedEntityData.defineId(Skeleton.class, EntityDataSerializers.INT);

	protected SkeletonExtension(EntityType<? extends AbstractSkeleton> entityType, Level world) {
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
}
