package winterly.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import winterly.Winterly;
import winterly.registry.CommonWinterlyBlocks;

public class UndergroundIcicleFeature extends Feature<NoneFeatureConfiguration> {

    public UndergroundIcicleFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext context) {
        if(!Winterly.config.generateUndergroundIcicles) return false;
        boolean generated = false;
        int randomY = context.random().nextInt(100) - 50;

        for (int x = 0; x < 10; x++) {
            for (int z = 0; z < 10; z++) {
                for (int y = 0; y < 10; y++) {
                    int xPos = context.origin().getX() + x;
                    int zPos = context.origin().getZ() + z;
                    int yPos = randomY + y;
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    WorldGenLevel world = context.level();
                    if(context.random().nextInt(4) == 0) {
                        if(world.isEmptyBlock(pos)) {
                            if(isStone(world.getBlockState(pos.above()))) {
                                world.setBlock(pos, CommonWinterlyBlocks.ICICLE.defaultBlockState(), Block.UPDATE_ALL);
                                generated = true;
                            }
                        }
                    }
                }
            }
        }
        return generated;
    }
}
