package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;
import ru.pinkgoosik.winterly.data.DecorationData;
import ru.pinkgoosik.winterly.extension.DecoratedMob;

@Mixin(Skeleton.class)
public abstract class SkeletonExtension extends AbstractSkeleton implements DecoratedMob {
	protected SkeletonExtension(EntityType<? extends AbstractSkeleton> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public boolean winterly$isDecorated() {
		return WinterlyPlatformHolder.get().getDecorationData(this).decorated();
	}

	@Override
	public int winterly$getIndex() {
		return WinterlyPlatformHolder.get().getDecorationData(this).index();
	}

	@Override
	public void winterly$setDecoration(int index) {
		WinterlyPlatformHolder.get().setDecorationData(this, DecorationData.decorated(index));
	}

	@Override
	public void winterly$clearDecoration() {
		WinterlyPlatformHolder.get().setDecorationData(this, DecorationData.DEFAULT);
	}
}
