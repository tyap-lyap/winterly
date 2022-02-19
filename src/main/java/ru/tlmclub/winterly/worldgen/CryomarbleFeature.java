package ru.tlmclub.winterly.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import ru.tlmclub.winterly.block.IcicleBlock;
import ru.tlmclub.winterly.registry.WinterlyBlocks;

public class CryomarbleFeature extends Feature<DefaultFeatureConfig> {

    public CryomarbleFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        for(int i = -20; i < 20; i++) {
            BlockPos newOrigin = new BlockPos(origin.getX(), i, origin.getZ());
            if(world.getBlockState(newOrigin).isAir()) {
                origin = newOrigin;
                break;
            }
        }
        if(context.getRandom().nextInt(16) != 0) return false;

        int spawned = 0;

        for(int x = -3; x < 3; x++) {
            for(int z = -3; z < 3; z++) {
                for(int y = -3; y < 3; y++) {
                    int xPos = origin.getX() + x;
                    int zPos = origin.getZ() + z;
                    int yPos = origin.getY() + y;
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    if(context.getRandom().nextInt(Math.abs(x) + Math.abs(y) + Math.abs(z) + 1) == 0) {
                        if(world.isAir(pos) && isStone(world.getBlockState(pos.down()))) {
                            if(spawned < 3) {
                                world.setBlockState(pos, WinterlyBlocks.RAW_CRYOMARBLE_SHARD.getDefaultState().with(IcicleBlock.FACING, Direction.UP), Block.NOTIFY_ALL);
                                spawned++;
                            }else return true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
