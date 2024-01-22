package winterly.mixin.common;

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
import winterly.Winterly;
import winterly.extension.DecoratedMob;
import winterly.util.HolidayUtils;

@Mixin(Zombie.class)
public abstract class ZombieExtension extends Monster implements DecoratedMob {
    private static final EntityDataAccessor<Boolean> winterly$DECORATED = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> winterly$INDEX = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.INT);

    protected ZombieExtension(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean winterly$decorated() {
        return getEntityData().get(winterly$DECORATED);
    }

    @Override
    public int winterly$getIndex() {
        return getEntityData().get(winterly$INDEX);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    void initData(CallbackInfo ci) {
        getEntityData().define(winterly$DECORATED, false);
        getEntityData().define(winterly$INDEX, 0);
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
    void initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, SpawnGroupData entityData, CompoundTag entityNbt, CallbackInfoReturnable<SpawnGroupData> cir) {
        if(!spawnReason.equals(MobSpawnType.SPAWNER) && !spawnReason.equals(MobSpawnType.CHUNK_GENERATION) && !isBaby()) {
            if(Winterly.config.mobDecorations.enabled && HolidayUtils.isWinterHolidays() || !Winterly.config.mobDecorations.onlyInWinter) {
                if(!this.level().dimension().equals(Level.NETHER)) {
                    int chance = Winterly.config.mobDecorations.chance;
                    if(chance > 0 && Math.random() < (double)chance / 100) {
                        getEntityData().set(winterly$DECORATED, true);
                        getEntityData().set(winterly$INDEX, world.getRandom().nextInt(5));
                    }
                }

            }
        }
    }

}
