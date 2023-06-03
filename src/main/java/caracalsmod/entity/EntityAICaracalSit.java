package caracalsmod.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAICaracalSit extends EntityAIMoveToBlock {
    private final EntityCaracal caracal;

    public EntityAICaracalSit(EntityCaracal creature, double speedIn) {
        super(creature, speedIn, 8);
        this.caracal = creature;
    }

    public boolean shouldExecute()
    {
        return this.caracal.isTamed() && !this.caracal.isSitting() && super.shouldExecute();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.caracal.getAISit().setSitting(false);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
        this.caracal.setSitting(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
        this.caracal.getAISit().setSitting(false);

        if (!this.getIsAboveDestination())
        {
            this.caracal.setSitting(false);
        }
        else if (!this.caracal.isSitting())
        {
            this.caracal.setSitting(true);
        }
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        if (!worldIn.isAirBlock(pos.up()))
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.CHEST)
            {
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityChest && ((TileEntityChest)tileentity).numPlayersUsing < 1)
                {
                    return true;
                }
            }
            else
            {
                if (block == Blocks.LIT_FURNACE)
                {
                    return true;
                }

                if (block == Blocks.BED && iblockstate.getValue(BlockBed.PART) != BlockBed.EnumPartType.HEAD)
                {
                    return true;
                }
            }

            return false;
        }
    }

}
