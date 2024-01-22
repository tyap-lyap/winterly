package winterly.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import winterly.Winterly;
import winterly.block.IcicleBlock;
import winterly.registry.CommonWinterlyBlocks;

public class CryomarbleFeature extends Feature<NoneFeatureConfiguration> {

    public CryomarbleFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if(!Winterly.config.generateCryomarble) return false;
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();

        for(int i = -20; i < 20; i++) {
            BlockPos newOrigin = new BlockPos(origin.getX(), i, origin.getZ());
            if(world.getBlockState(newOrigin).isAir()) {
                origin = newOrigin;
                break;
            }
        }
        if(context.random().nextInt(16) != 0) return false;

        int spawned = 0;

        for(int x = -3; x < 3; x++) {
            for(int z = -3; z < 3; z++) {
                for(int y = -3; y < 3; y++) {
                    int xPos = origin.getX() + x;
                    int zPos = origin.getZ() + z;
                    int yPos = origin.getY() + y;
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    if(context.random().nextInt(Math.abs(x) + Math.abs(y) + Math.abs(z) + 1) == 0) {
                        if(world.isEmptyBlock(pos) && isStone(world.getBlockState(pos.below()))) {
                            if(spawned < 3) {
                                world.setBlock(pos, CommonWinterlyBlocks.RAW_CRYOMARBLE_SHARD.defaultBlockState().setValue(IcicleBlock.FACING, Direction.UP), Block.UPDATE_ALL);
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
