package caracalsmod;

import caracalsmod.blocks.WTFBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelRegistry {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        //DMLRegistry.registeredItems.forEach(ModelRegistry::registerModel);
        WTFBlocks.BLOCKS.forEach(block -> registerModel(Item.getItemFromBlock(block.getBlock()), Objects.requireNonNull(block.getRegistryName())));
    }

    private static void registerModel(Item item) {
        ResourceLocation modelLocation;

        // Default model registration
        ResourceLocation registryLocation = item.getRegistryName();
        if (registryLocation == null)
            return;
        modelLocation = registryLocation;

        registerModel(item, modelLocation);
    }

    private static void registerModel(Item item, ResourceLocation location) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(location, "inventory"));
    }
}