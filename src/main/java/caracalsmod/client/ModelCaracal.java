package caracalsmod.client;

// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import caracalsmod.entity.EntityCaracal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelCaracal extends ModelBase {
    private CaracalState state = CaracalState.IDLE;

    private final ModelRenderer BODY;
    private final ModelRenderer LEGS;
    private final ModelRenderer FRONT_LEFT_LEG;
    private final ModelRenderer FRONT_RIGHT_LEG;

    private final ModelRenderer BACK_RIGHT_LEG;
    private final ModelRenderer BACK_LEFT_LEG;
    private final ModelRenderer HEAD;
    private final ModelRenderer EAR_RIGHT;
    private final ModelRenderer EAR_RIGHT_TIP;
    private final ModelRenderer EAR_RIGHT_ROOT;
    private final ModelRenderer EAR_LEFT;
    private final ModelRenderer EAR_LEFT_TIP;
    private final ModelRenderer EAR_LEFT_ROOT;
    private final ModelRenderer TAIL;
    private final ModelRenderer TAIL_TIP;
    private final ModelRenderer TAIL_ROOT;

    public ModelCaracal() {
        textureWidth = 64;
        textureHeight = 64;

        BODY = new ModelRenderer(this);
        BODY.setRotationPoint(0.0443F, 24.0F, -2.3818F);
        BODY.cubeList.add(new ModelBox(BODY, 0, 0, -3.0443F, -10.0F, -0.6182F, 6, 6, 12, 0.0F, false));
        BODY.cubeList.add(new ModelBox(BODY, 22, 18, -3.0443F, -9.75F, -2.6182F, 6, 5, 2, 0.0F, false));


        LEGS = new ModelRenderer(this);
        LEGS.setRotationPoint(0.0F, 24.0F, 0.0F);


        BACK_RIGHT_LEG = new ModelRenderer(this);
        BACK_RIGHT_LEG.setRotationPoint(2.2F, -5.6692F, 8.0262F);
        LEGS.addChild(BACK_RIGHT_LEG);
        BACK_RIGHT_LEG.cubeList.add(new ModelBox(BACK_RIGHT_LEG, 0, 27, -0.9F, -0.3308F, -0.8691F, 2, 6, 2, 0.0F, false));

        BACK_LEFT_LEG = new ModelRenderer(this);
        BACK_LEFT_LEG.setRotationPoint(-2.2F, -5.6692F, 8.0262F);
        LEGS.addChild(BACK_LEFT_LEG);
        BACK_LEFT_LEG.cubeList.add(new ModelBox(BACK_LEFT_LEG, 8, 27, -1.0F, -0.3308F, -0.8691F, 2, 6, 2, 0.0F, false));

        FRONT_RIGHT_LEG = new ModelRenderer(this);
        FRONT_RIGHT_LEG.setRotationPoint(0.0F, -6.8171F, 0.5324F);
        LEGS.addChild(FRONT_RIGHT_LEG);
        FRONT_RIGHT_LEG.cubeList.add(new ModelBox(FRONT_RIGHT_LEG, 0, 0, 1.2F, -2.1829F, -3.7324F, 2, 9, 2, 0.0F, false));

        FRONT_LEFT_LEG = new ModelRenderer(this);
        FRONT_LEFT_LEG.setRotationPoint(0.0F, -6.8171F, 0.5324F);
        LEGS.addChild(FRONT_LEFT_LEG);
        FRONT_LEFT_LEG.cubeList.add(new ModelBox(FRONT_LEFT_LEG, 20, 25, -3.2F, -2.1829F, -3.7324F, 2, 9, 2, 0.0F, false));

        HEAD = new ModelRenderer(this);
        HEAD.setRotationPoint(0.0F, 24.0F, -1.0F);
        HEAD.cubeList.add(new ModelBox(HEAD, 1, 19, -3.0F, -10.0F, -8.0F, 6, 4, 4, 0.0F, false));
        HEAD.cubeList.add(new ModelBox(HEAD, 24, 7, -1.5F, -8.0F, -9.0F, 3, 2, 1, 0.0F, false));

        EAR_RIGHT = new ModelRenderer(this);
        EAR_RIGHT.setRotationPoint(1.5348F, -9.8735F, -4.6432F);
        HEAD.addChild(EAR_RIGHT);


        EAR_RIGHT_TIP = new ModelRenderer(this);
        EAR_RIGHT_TIP.setRotationPoint(1.6732F, -0.6532F, 0.25F);
        EAR_RIGHT.addChild(EAR_RIGHT_TIP);
        setRotationAngle(EAR_RIGHT_TIP, 0.0F, 0.0F, 0.5672F);
        EAR_RIGHT_TIP.cubeList.add(new ModelBox(EAR_RIGHT_TIP, 19, 19, -0.1156F, -2.9711F, -0.75F, 0, 3, 1, 0.0F, false));

        EAR_RIGHT_ROOT = new ModelRenderer(this);
        EAR_RIGHT_ROOT.setRotationPoint(0.9025F, 0.0343F, 0.0F);
        EAR_RIGHT.addChild(EAR_RIGHT_ROOT);
        setRotationAngle(EAR_RIGHT_ROOT, 0.0F, 0.0F, -0.3927F);
        EAR_RIGHT_ROOT.cubeList.add(new ModelBox(EAR_RIGHT_ROOT, 17, 18, -1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F, false));

        EAR_LEFT = new ModelRenderer(this);
        EAR_LEFT.setRotationPoint(1.5348F, -9.8735F, -4.6432F);
        HEAD.addChild(EAR_LEFT);


        EAR_LEFT_TIP = new ModelRenderer(this);
        EAR_LEFT_TIP.setRotationPoint(-4.7428F, -0.6532F, 0.0F);
        EAR_LEFT.addChild(EAR_LEFT_TIP);
        setRotationAngle(EAR_LEFT_TIP, 0.0F, 0.0F, -0.5672F);
        EAR_LEFT_TIP.cubeList.add(new ModelBox(EAR_LEFT_TIP, 17, 19, 0.1157F, -2.9711F, -0.5F, 0, 3, 1, 0.0F, false));

        EAR_LEFT_ROOT = new ModelRenderer(this);
        EAR_LEFT_ROOT.setRotationPoint(-3.9585F, 0.0637F, 0.0F);
        EAR_LEFT.addChild(EAR_LEFT_ROOT);
        setRotationAngle(EAR_LEFT_ROOT, 0.0F, 0.0F, 0.3927F);
        EAR_LEFT_ROOT.cubeList.add(new ModelBox(EAR_LEFT_ROOT, 6, 0, -1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F, false));

        TAIL = new ModelRenderer(this);
        TAIL.setRotationPoint(0.0F, 15.0F, 9.0F);


        TAIL_TIP = new ModelRenderer(this);
        TAIL_TIP.setRotationPoint(0.0F, 4.8355F, 2.834F);
        TAIL.addChild(TAIL_TIP);
        setRotationAngle(TAIL_TIP, -1.309F, 0.0F, 0.0F);
        TAIL_TIP.cubeList.add(new ModelBox(TAIL_TIP, 27, 3, -0.4557F, -0.6959F, 0.0335F, 1, 1, 3, 0.0F, false));

        TAIL_ROOT = new ModelRenderer(this);
        TAIL_ROOT.setRotationPoint(0.0F, 2.1829F, -8.4676F);
        TAIL.addChild(TAIL_ROOT);
        setRotationAngle(TAIL_ROOT, -0.9599F, 0.0F, 0.0F);
        TAIL_ROOT.cubeList.add(new ModelBox(TAIL_ROOT, 24, 0, -0.4557F, -8.3787F, 2.9247F, 1, 1, 6, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float f1, float f2, float f3, float f4, float scale) {
        EntityCaracal caracal = (EntityCaracal) entity;
        if (caracal.isBlue()) {
            GlStateManager.pushMatrix();
            GlStateManager.color(0, 0, 3.0F);
        }
        if (caracal.isChild()) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 10.0F * scale, 4.0F * scale);
            this.HEAD.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.BODY.render(scale);
            this.LEGS.render(scale);
            this.TAIL.render(scale);
            GlStateManager.popMatrix();
        } else {
            BODY.render(scale);
            LEGS.render(scale);
            HEAD.render(scale);
            TAIL.render(scale);
        }
        if (caracal.isBlue()) {
            GlStateManager.popMatrix();
        }
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase caracal, float limbSwing, float limbSwingAmount, float partialTickTime) {
        EntityCaracal entity = (EntityCaracal) caracal;

        if (entity.isSitting()) {

        } else {
            this.BACK_LEFT_LEG.rotateAngleX = MathHelper.cos(limbSwing) * limbSwingAmount;
            this.BACK_RIGHT_LEG.rotateAngleX = MathHelper.cos(limbSwing + 0.3F) * limbSwingAmount;
            this.FRONT_LEFT_LEG.rotateAngleX = MathHelper.cos(limbSwing + (float) Math.PI + 0.3F) * limbSwingAmount;
            this.FRONT_RIGHT_LEG.rotateAngleX = MathHelper.cos(limbSwing + (float) Math.PI) * limbSwingAmount;


            this.TAIL.rotateAngleX = MathHelper.cos(entity.world.getTotalWorldTime() * 0.2F) * 0.1F;
            this.TAIL_TIP.rotateAngleX = MathHelper.cos(entity.world.getTotalWorldTime() * 0.2F) * 0.1F - 1.309F;
        }

        this.EAR_LEFT.rotateAngleZ = -entity.getEarFlopAngle(partialTickTime, true);
        this.EAR_RIGHT.rotateAngleZ = entity.getEarFlopAngle(partialTickTime, false);

        this.EAR_LEFT_TIP.rotateAngleZ = -entity.getEarFlopAngle(partialTickTime, true) * 0.25F;
        this.EAR_RIGHT_TIP.rotateAngleZ = entity.getEarFlopAngle(partialTickTime, false) * 0.25F;
    }

    private enum CaracalState {
        IDLE,
        WALK,
        SPRINT,
        SITTING
    }
}