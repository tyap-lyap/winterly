package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.GiftBoxBlock;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.RED_WOOL;

public class WinterlyNeoForgeBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, Winterly.MOD_ID);

	public static final RegisterSupplier<Block> RED_GIFT_BOX = register("red_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> ORANGE_GIFT_BOX = register("orange_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> YELLOW_GIFT_BOX = register("yellow_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> GREEN_GIFT_BOX = register("green_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> CYAN_GIFT_BOX = register("cyan_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> BLUE_GIFT_BOX = register("blue_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> PURPLE_GIFT_BOX = register("purple_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> BLACK_GIFT_BOX = register("black_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));
	public static final RegisterSupplier<Block> WHITE_GIFT_BOX = register("white_gift_box", (_, props) -> new GiftBoxBlock(props), () -> copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY));

	private static <T extends Block> RegisterSupplier<T> register(String name, BiFunction<String, BlockBehaviour.Properties, T> factory, Supplier<BlockBehaviour.Properties> propertiesSupplier) {
		RegisterSupplier<T> obj = BLOCKS.register(name, () -> {
			ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Winterly.MOD_ID, name));
			BlockBehaviour.Properties properties = propertiesSupplier.get().setId(key);
			return factory.apply(name, properties);
		});
		WinterlyNeoForgeItems.registerBlockItem(name, obj);
		return obj;
	}

	static BlockBehaviour.Properties copyOf(Block block) {
		return BlockBehaviour.Properties.ofFullCopy(block);
	}
}
