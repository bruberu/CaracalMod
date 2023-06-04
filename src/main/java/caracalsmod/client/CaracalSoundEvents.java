package caracalsmod.client;

import caracalsmod.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CaracalSoundEvents {
    public static SoundEvent CARACAL_PURR;
    public static SoundEvent CARACAL_MEOW;
    public static SoundEvent CARACAL_HISS;
    public static SoundEvent CARACAL_GROWL;

    public static void registerSounds() {
        CARACAL_PURR = registerSound("caracal.purr");
        CARACAL_MEOW = registerSound("caracal.meow");
        CARACAL_HISS = registerSound("caracal.hiss");
        CARACAL_GROWL = registerSound("caracal.growl");
    }

    private static SoundEvent registerSound(String soundNameIn) {
        ResourceLocation location = new ResourceLocation(Tags.MODID, soundNameIn);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
