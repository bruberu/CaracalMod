package caracalsmod.entity;

import caracalsmod.WTFConfig;
import caracalsmod.client.CaracalSoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.ArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeBeach;
import net.minecraft.world.biome.BiomeStoneBeach;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityCaracal extends EntityTameable {

    private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
    /**
     * The tempt AI task for this mob, used to prevent taming while it is fleeing.
     */
    private EntityAITempt aiTempt;

    private int earFlopLeftTime = 0;
    private int earFlopRightTime = 0;
    private static final DataParameter<Boolean> IS_BLUE = EntityDataManager.<Boolean>createKey(EntityCaracal.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_FLOPPING_RIGHT = EntityDataManager.<Boolean>createKey(EntityCaracal.class, DataSerializers.BOOLEAN);

    //Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.MUTATED_SAVANNA, Biomes.MUTATED_SAVANNA_ROCK, Biomes.MUTATED_JUNGLE_EDGE
    public static Biome[] COMMON_BIOMES;
    //Biomes.FOREST, Biomes.JUNGLE_EDGE
    public static Biome[] RARE_BIOMES;

    public static final float BIOME_AVG_TEMP = 1.05F;
    public static final float BIOME_AVG_RAIN = 0.3F;
    public static final float COMMON_TEMP_RAIN_RADIUS = 0.4F;
    public static final float RARE_TEMP_RAIN_RADIUS = 0.55F;



    public static Set<Item> TAME_ITEMS = new ObjectOpenHashSet<>(new Item[]{Items.FISH, Items.CHICKEN, Items.RABBIT});

    public static void initBiomes() {
        ArrayList<Biome> commonBiomes = new ArrayList<>();
        ArrayList<Biome> rareBiomes = new ArrayList<>();
        for (Biome biome : Biome.REGISTRY) {
            if (biome instanceof BiomeBeach || biome instanceof BiomeStoneBeach) continue;
            float defaultTemp = biome.getDefaultTemperature();
            float defaultRain = biome.getRainfall();
            float variance = (float) Math.sqrt(Math.pow(BIOME_AVG_TEMP - defaultTemp, 2) + Math.pow((BIOME_AVG_RAIN - defaultRain), 2)) - 0.01F;
            if (COMMON_TEMP_RAIN_RADIUS < variance && variance <= RARE_TEMP_RAIN_RADIUS) {
                rareBiomes.add(biome);
            } else if (variance <= COMMON_TEMP_RAIN_RADIUS) {
                commonBiomes.add(biome);
            }
        }

        COMMON_BIOMES = commonBiomes.toArray(new Biome[0]);
        RARE_BIOMES = rareBiomes.toArray(new Biome[0]);
    }

    public EntityCaracal(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 0.7F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_BLUE, Boolean.FALSE);
        this.dataManager.register(IS_FLOPPING_RIGHT, Boolean.FALSE);
    }

    protected void initEntityAI() {
        this.aiSit = new EntityAISit(this);
        this.aiTempt = new EntityAITempt(this, 0.6D, true, TAME_ITEMS);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(3, new EntityAIOcelotAttack(this));
        this.tasks.addTask(4, this.aiSit);
        this.tasks.addTask(5, this.aiTempt);
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.tasks.addTask(7, new EntityAICaracalSit(this, 0.8D));
        this.tasks.addTask(8, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.8D, 1.0000001E-5F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(4, new EntityAIOwnerNearTarget(this, 10.0F));
        this.targetTasks.addTask(5, new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
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
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.world.getWorldTime() % 300 == 0) {
            this.heal(0.5F);
        }
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);

        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        }
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        }
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(WTFConfig.caracalAttackDamage);
    }

    public float getEarFlopAngle(float partialTicks, boolean isLeft) {
        int timer = isLeft ? this.earFlopLeftTime : this.earFlopRightTime;
        if (timer == 0)
            return 0.0F;
        double realTimer = ((double) timer - partialTicks) * (Math.PI / 8);
        return (float) Math.pow(Math.sin(realTimer), 3);
    }

    public EntityCaracal createChild(EntityAgeable ageable) {
        EntityCaracal caracal = new EntityCaracal(this.world);

        if (this.isTamed()) {
            caracal.setOwnerId(this.getOwnerId());
            caracal.setTamed(true);
            caracal.setIsBlue(this.isBlue());
        }

        return caracal;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("isBlue", isBlue());
        compound.setBoolean("isFloppingRight", isFloppingRight());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setIsBlue(compound.getBoolean("isBlue"));
        setIsFloppingRight(compound.getBoolean("isFloppingRight"));
    }

    @javax.annotation.Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        if (((this.posX * this.posY) % 500) == 17)
            setIsBlue(true);

        return livingdata;
    }

    public boolean isBlue() {
        return this.dataManager.get(IS_BLUE);
    }

    public void setIsBlue(boolean isBlue) {
        this.dataManager.set(IS_BLUE, isBlue);
    }

    public boolean isFloppingRight() {
        return this.dataManager.get(IS_FLOPPING_RIGHT);
    }

    public void setIsFloppingRight(boolean isFloppingRight) {
        this.dataManager.set(IS_FLOPPING_RIGHT, isFloppingRight);
    }


    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof EntityCaracal caracal)) {
            return false;
        } else {
            if (!caracal.isTamed()) {
                return false;
            } else {
                return this.isInLove() && caracal.isInLove();
            }
        }
    }

    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        } else {
            return this.isTamed() ? I18n.translateToLocal("entity.tamed_caracal.name") : super.getName();
        }
    }

    protected void setupTamedAI() {
        if (this.avoidEntity == null) {
            this.avoidEntity = new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D);
        }

        this.tasks.removeTask(this.avoidEntity);

        if (!this.isTamed()) {
            this.tasks.addTask(4, this.avoidEntity);
        }
    }

    public void updateAITasks() {
        if (this.getMoveHelper().isUpdating()) {
            double d0 = this.getMoveHelper().getSpeed();

            if (d0 == 0.6D) {
                this.setSneaking(true);
                this.setSprinting(false);
            } else if (d0 == 1.33D) {
                this.setSneaking(false);
                this.setSprinting(true);
            } else {
                this.setSneaking(false);
                this.setSprinting(false);
            }
        } else {
            this.setSneaking(false);
            this.setSprinting(false);
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        if (this.isTamed()) {
            if (this.isInLove()) {
                return CaracalSoundEvents.CARACAL_PURR;
            } else {
                if (this.getAttackTarget() != null) {
                    return this.rand.nextInt(4) == 0 ? CaracalSoundEvents.CARACAL_HISS : CaracalSoundEvents.CARACAL_GROWL;
                }
                return this.rand.nextInt(4) == 0 ? CaracalSoundEvents.CARACAL_PURR : CaracalSoundEvents.CARACAL_MEOW;
            }
        } else {
            return this.rand.nextInt(4) == 0 ? CaracalSoundEvents.CARACAL_MEOW : null;
        }
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return this.rand.nextInt(4) == 0 ? CaracalSoundEvents.CARACAL_HISS : CaracalSoundEvents.CARACAL_GROWL;
    }

    protected SoundEvent getDeathSound() {
        return CaracalSoundEvents.CARACAL_HISS;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else {
            if (!WTFConfig.friendlyFire && this.isTamed() && source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase living) {
                if (this.isOwner(living) || (this.getOwner() != null && living.isOnSameTeam(this.getOwner()))) {
                    return false; // No friendly fire!
                }
            }
            if (this.aiSit != null) {
                this.aiSit.setSitting(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed()) {
            if (this.isOwner(player)) {
                if (!this.isBreedingItem(itemstack)) {
                    if (!this.world.isRemote) { // Can't combine with the previous line
                        this.aiSit.setSitting(!this.isSitting());
                        this.setIsFloppingRight(this.world.rand.nextBoolean());
                    }
                } else {
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    if (!this.world.isRemote) {
                        this.heal(2.0F);
                    }
                }
            }
        } else if ((this.aiTempt == null || !this.aiTempt.isRunning()) && TAME_ITEMS.contains(itemstack.getItem()) && player.getDistanceSq(this) < 9.0D) {
            if (!player.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.playTameEffect(true);
                    this.aiSit.setSitting(true);
                    this.setIsFloppingRight(this.world.rand.nextBoolean());
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TAME_ITEMS.contains(stack.getItem());
    }
}