package caracalsmod.entity;

import caracalsmod.Tags;
import caracalsmod.client.ModelCaracal;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCaracal extends RenderLiving<EntityCaracal> {
    private static final ResourceLocation CARACAL_TEXTURES = new ResourceLocation(Tags.MODID, "textures/entity/caracal.png");

    public RenderCaracal(RenderManager manager) {
        super(manager, new ModelCaracal(), 0.4F);
    }

    protected ResourceLocation getEntityTexture(EntityCaracal entity) {
        return CARACAL_TEXTURES;
    }

}
