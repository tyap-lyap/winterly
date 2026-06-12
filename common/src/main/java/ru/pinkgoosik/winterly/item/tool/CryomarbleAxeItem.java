package ru.pinkgoosik.winterly.item.tool;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class CryomarbleAxeItem extends AxeItem {
	public CryomarbleAxeItem(ToolMaterial material, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
		super(material, attackDamageModifier, attackSpeedModifier, properties);
	}

	@Override
	public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 0));
		super.hurtEnemy(stack, target, attacker);
	}
}
