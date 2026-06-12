package ru.pinkgoosik.winterly.item.tool;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class CryomarblePickaxeItem extends Item {
	public CryomarblePickaxeItem(ToolMaterial material, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
		super(material.applyToolProperties(properties, BlockTags.MINEABLE_WITH_PICKAXE, attackDamageModifier, attackSpeedModifier, 0.0F));
	}

	@Override
	public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 0));
		super.hurtEnemy(stack, target, attacker);
	}
}
