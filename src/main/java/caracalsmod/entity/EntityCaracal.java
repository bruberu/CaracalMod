package caracalsmod.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Biomes;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EntityCaracal extends EntityOcelot {
    private int earFlopLeftTime = 0;
    private int earFlopRightTime = 0;

    public static Biome[] BIOMES = {Biomes.SWAMPLAND, Biomes.BEACH, Biomes.COLD_BEACH, Biomes.FOREST, Biomes.PLAINS, Biomes.ROOFED_FOREST, Biomes.EXTREME_HILLS};
    public EntityCaracal(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.world.isRemote) {
            if (this.rand.nextInt(100) == 0 && this.earFlopLeftTime == 0) {
                this.earFlopLeftTime = 8;
            }
            if (this.rand.nextInt(100) == 0 && this.earFlopRightTime == 0) {
                this.earFlopRightTime = 8;
            }
            if (this.earFlopLeftTime > 0) {
                this.earFlopLeftTime--;
            }
            if (this.earFlopRightTime > 0) {
                this.earFlopRightTime--;
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        if (this.isTamed())
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        }
        else
        {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        }
    }

    public float getEarFlopAngle(float partialTicks, boolean isLeft) {
        int timer = isLeft ? this.earFlopLeftTime : this.earFlopRightTime;
        if (timer == 0)
            return 0.0F;
        double realTimer = ((double) timer - partialTicks) * (Math.PI / 8);
        return (float) Math.pow(Math.sin(realTimer), 3);
    }
}
