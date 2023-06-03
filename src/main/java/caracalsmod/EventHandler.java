package caracalsmod;

import caracalsmod.entity.EntityCaracal;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent event) {
        if (event.getEntity() instanceof EntityCreeper creeper) {
            creeper.tasks.addTask(3, new EntityAIAvoidEntity(creeper, EntityCaracal.class, 8.0F, 1.0D, 1.2D));
        }
    }
}
