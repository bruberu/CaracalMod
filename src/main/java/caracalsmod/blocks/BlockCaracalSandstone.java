package caracalsmod.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCaracalSandstone extends BlockBasic {
    /**
     * @param name     Block id (for internal use)
     * @param material Material the block behaves like
     */
    protected BlockCaracalSandstone(String name, Material material) {
        super(name, material);
        setHarvestLevel("pickaxe", 0);
        setHardness(0.8f);
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.SAND;
    }
}
