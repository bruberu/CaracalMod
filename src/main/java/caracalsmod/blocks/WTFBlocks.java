package caracalsmod.blocks;

import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class WTFBlocks {
    public static final List<BlockBase> BLOCKS = new ArrayList<>();

    public static BlockBase CHISELED_SANDSTONE;
    public static BlockBase CHISELED_RED_SANDSTONE;
    public static BlockBase CHISELED_STONE;

    public static void init() {
        CHISELED_STONE = new BlockBase("chiseled_stone", Material.ROCK);
        CHISELED_RED_SANDSTONE = new BlockBase("chiseled_red_sandstone", Material.ROCK);
        CHISELED_SANDSTONE = new BlockBase("chiseled_sandstone", Material.ROCK);
    }
}
