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
        boolean generated = false;

        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        for(int i = 0; i < 100; i++) {
            if(world.getBlockState(origin.up(i)).isAir()) {
                origin = origin.up(i);
                break;
            }
        }

        for(int x = 0; x < 5; x++) {
            for(int z = 0; z < 5; z++) {
                for(int y = 0; y < 5; y++) {
                    int xPos = origin.getX() + x;
                    int zPos = origin.getZ() + z;
                    int yPos = origin.getY() + y;
                    BlockPos pos = new BlockPos(xPos, yPos, zPos);
                    if(context.getRandom().nextInt(5) == 0) {
                        if(world.isAir(pos)) {
                            if(isStone(world.getBlockState(pos.down()))) {
                                world.setBlockState(pos, WinterlyBlocks.RAW_CRYOMARBLE_SHARD.getDefaultState().with(IcicleBlock.FACING, Direction.UP), Block.NOTIFY_ALL);
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
