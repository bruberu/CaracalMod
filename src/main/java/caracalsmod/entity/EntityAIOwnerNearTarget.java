package caracalsmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class EntityAIOwnerNearTarget extends EntityAITarget {
    EntityTameable tameable;
    protected float maxDistance;


    public EntityAIOwnerNearTarget(EntityTameable creature, float maxDistance) {
        super(creature, true);
        this.tameable = creature;
        this.unseenMemoryTicks = 100;
        this.maxDistance = maxDistance;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (!this.tameable.isTamed() || this.target != null || this.tameable.getOwner() != null)
            return false;
        EntityLiving closest = getClosestHostileWithinAABB(this.tameable.getOwner().getEntityBoundingBox().grow(this.maxDistance, 3.0D, (double) this.maxDistance), this.tameable.getOwner());
        if (closest != null && this.tameable.getOwner().getDistanceSq(closest) < this.maxDistance * this.maxDistance) {
            this.target = closest;
            return true;
        }
        return false;
    }

    public void startExecuting() {
        if (!tameable.world.isRemote)
            this.tameable.setSitting(false);
        this.taskOwner.setAttackTarget(this.target);
        super.startExecuting();
    }
    public EntityLiving getClosestHostileWithinAABB(AxisAlignedBB aabb, Entity hostileTo) {
        List<Entity> entities = hostileTo.world.getEntitiesInAABBexcluding(hostileTo, aabb, (entity) -> {
            if (entity instanceof EntityLiving living) {
                return living.getAttackTarget() == hostileTo && this.isSuitableTarget(living, false);
            }
            return false;
        });
        return (EntityLiving) entities.stream().max((e1, e2) -> Math.toIntExact(Math.round(
                        e2.getDistanceSq(hostileTo) - e1.getDistanceSq(hostileTo))))
                .orElse(null);
    }
}
