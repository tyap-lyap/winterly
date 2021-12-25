package ru.tlmclub.winterly.mixin.common;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.extension.DecoratedMob;
import ru.tlmclub.winterly.util.HolidayUtils;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonExtension extends AbstractSkeletonEntity implements DecoratedMob {
    private static final TrackedData<Boolean> winterly$DECORATED = DataTracker.registerData(SkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> winterly$INDEX = DataTracker.registerData(SkeletonEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected SkeletonExtension(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean winterly$decorated() {
        return getDataTracker().get(winterly$DECORATED);
    }

    @Override
    public int winterly$getIndex() {
        return getDataTracker().get(winterly$INDEX);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    void initData(CallbackInfo ci) {
        getDataTracker().startTracking(winterly$DECORATED, false);
        getDataTracker().startTracking(winterly$INDEX, 0);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    void write(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("WinterlyDecorated", getDataTracker().get(winterly$DECORATED));
        nbt.putInt("WinterlyIndex", getDataTracker().get(winterly$INDEX));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    void read(NbtCompound nbt, CallbackInfo ci) {
        getDataTracker().set(winterly$DECORATED, nbt.getBoolean("WinterlyDecorated"));
        getDataTracker().set(winterly$INDEX, nbt.getInt("WinterlyIndex"));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt){
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        if(!spawnReason.equals(SpawnReason.SPAWNER) && !spawnReason.equals(SpawnReason.CHUNK_GENERATION)) {
            if(WinterlyMod.CONFIG.getBoolean("enabled", "decorations_on_mobs") && HolidayUtils.isWinter() || !WinterlyMod.CONFIG.getBoolean("only_in_winter", "decorations_on_mobs")) {
                int chance = WinterlyMod.CONFIG.getInteger("chance_percentage", "decorations_on_mobs");
                if(chance > 0 && Math.random() < (double)chance / 100) {
                    getDataTracker().set(winterly$DECORATED, true);
                    getDataTracker().set(winterly$INDEX, world.getRandom().nextInt(5));
                }
            }
        }
        return entityData;
    }
}
