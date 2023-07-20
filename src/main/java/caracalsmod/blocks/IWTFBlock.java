package caracalsmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public interface IWTFBlock {
    default void register() {
        WTFBlocks.BLOCKS.add(this);
    }

    ResourceLocation getRegistryName();
    Block getBlock();
}
