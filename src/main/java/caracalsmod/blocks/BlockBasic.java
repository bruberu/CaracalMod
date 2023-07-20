package caracalsmod.blocks;

import caracalsmod.Tags;
import caracalsmod.WorldClassTrustworthyFloppas;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

import java.util.Optional;

public class BlockBasic extends Block implements IWTFBlock {
    /**
     * @param name Block id (for internal use)
     * @param material Material the block behaves like
     */
    protected BlockBasic(String name, Material material) {
        super(material);
        setRegistryName(name);
        setTranslationKey(Tags.MODID + "." + name);
        setCreativeTab(WorldClassTrustworthyFloppas.WTF_TAB);
        register();
    }

    public Optional<Item> getItemBlock() {
        ResourceLocation registryName = getRegistryName();
        return (registryName != null) ?
                Optional.of(new ItemBlock(this).setRegistryName(registryName)) :
                Optional.empty();
    }

    public Block getBlock() {
        return this;
    }
}
