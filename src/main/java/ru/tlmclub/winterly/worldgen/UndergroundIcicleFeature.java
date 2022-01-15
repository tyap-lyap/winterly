package ru.tlmclub.winterly.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import ru.tlmclub.winterly.registry.WinterlyBlocks;

public class UndergroundIcicleFeature extends Feature<DefaultFeatureConfig> {

    public UndergroundIcicleFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext context) {
        boolean generated = false;
        int randomY = context.getRandom().nextInt(100) - 50;

        for (int x = 0; x < 10; x++) {
            for (int z = 0; z < 10; z++) {
                for (int y = 0; y < 10; y++) {
                    int xPos = context.getOrigin().getX() + x;
                    int zPos = context.getOrigin().getZ() + z;
                    int yPos = randomY + y;
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    StructureWorldAccess world = context.getWorld();
                    if(context.getRandom().nextInt(4) == 0) {
                        if(world.isAir(pos)) {
                            if(isStone(world.getBlockState(pos.up()))) {
                                world.setBlockState(pos, WinterlyBlocks.ICICLE.getDefaultState(), Block.NOTIFY_ALL);
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
