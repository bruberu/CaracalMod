package caracalsmod.blocks;

import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class WTFBlocks {
    public static List<IWTFBlock> BLOCKS = new ArrayList<>();

    public static BlockBasic CHISELED_SANDSTONE;
    public static BlockBasic CHISELED_RED_SANDSTONE;
    public static BlockBasic CHISELED_STONE;

    public static void init() {
        CHISELED_STONE = new BlockBasic("chiseled_stone", Material.ROCK);
        CHISELED_RED_SANDSTONE = new BlockBasic("chiseled_red_sandstone", Material.ROCK);
        CHISELED_SANDSTONE = new BlockBasic("chiseled_sandstone", Material.ROCK);
    }
}
