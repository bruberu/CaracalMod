package caracalsmod.entity;

import caracalsmod.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class EntityRegistration {
    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityEntry> event)
    {
        event.getRegistry().register(EntityEntryBuilder.create().entity(EntityCaracal.class)
                .id(new ResourceLocation(Tags.MODID, "caracal"), 0)
                .name("caracal")
                .tracker(80, 3, true)
                .spawn(EnumCreatureType.CREATURE, 2, 1, 3, EntityCaracal.BIOMES)
                .egg(0x3d352f, 0xf0ded1).build());

    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityCaracal.class, RenderCaracal::new);
    }
}
