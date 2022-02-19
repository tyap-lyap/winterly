package ru.tlmclub.winterly.item.tool;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class CryomarbleSwordItem extends SwordItem {

    public CryomarbleSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(entity instanceof LivingEntity livingEntity) {
            if(livingEntity.getMainHandStack().isOf(this) && isOnSnow(livingEntity, world)) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 0));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 100, 1));
            }
        }
    }

    private static boolean isOnSnow(LivingEntity entity, World world) {
        BlockState state = world.getBlockState(entity.getBlockPos().down());
        return state.isOf(Blocks.SNOW_BLOCK) || state.isOf(Blocks.POWDER_SNOW)
                || state.isOf(Blocks.ICE) || state.isOf(Blocks.PACKED_ICE)
                || state.isOf(Blocks.BLUE_ICE);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0));
        return super.postHit(stack, target, attacker);
    }
}
