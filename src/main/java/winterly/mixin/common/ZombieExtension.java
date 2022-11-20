package winterly.mixin.common;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.Winterly;
import winterly.extension.DecoratedMob;
import winterly.util.HolidayUtils;

@Mixin(ZombieEntity.class)
public abstract class ZombieExtension extends HostileEntity implements DecoratedMob {
    private static final TrackedData<Boolean> winterly$DECORATED = DataTracker.registerData(ZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> winterly$INDEX = DataTracker.registerData(ZombieEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected ZombieExtension(EntityType<? extends HostileEntity> entityType, World world) {
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

    @Inject(method = "initialize", at = @At("RETURN"))
    void initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        if(!spawnReason.equals(SpawnReason.SPAWNER) && !spawnReason.equals(SpawnReason.CHUNK_GENERATION) && !isBaby()) {
            if(Winterly.config.mobDecorations.enabled && HolidayUtils.isWinter() || !Winterly.config.mobDecorations.onlyInWinter) {
                if(!this.world.getRegistryKey().equals(World.NETHER)) {
                    int chance = Winterly.config.mobDecorations.chance;
                    if(chance > 0 && Math.random() < (double)chance / 100) {
                        getDataTracker().set(winterly$DECORATED, true);
                        getDataTracker().set(winterly$INDEX, world.getRandom().nextInt(5));
                    }
                }

            }
        }
    }

}
