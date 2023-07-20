package caracalsmod;

import caracalsmod.blocks.WTFBlocks;
import caracalsmod.client.CaracalSoundEvents;
import caracalsmod.entity.EntityRegistration;
import net.minecraft.block.Block;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.12.2]")
public class WorldClassTrustworthyFloppas {
    public static final CreativeTabs WTF_TAB = new CreativeTabs(Tags.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WTFBlocks.CHISELED_SANDSTONE);
        }
    };
    public static final Logger LOGGER = LogManager.getLogger(Tags.MODID);

    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc. (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        // register to the event bus so that we can listen to events
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("I am " + Tags.MODNAME + " + at version " + Tags.VERSION);
        if (event.getSide().isClient()) {
            EntityRegistration.registerRenders();
        }
        CaracalSoundEvents.registerSounds();
        WTFBlocks.init();
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));

        return itemBlock;
    }
    @SubscribeEvent
    public void registerItems(@NotNull RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(createItemBlock(WTFBlocks.CHISELED_SANDSTONE, ItemBlock::new));
    }
    @SubscribeEvent
    public void registerBlocks(@NotNull RegistryEvent.Register<Block> event) {
        event.getRegistry().register(WTFBlocks.CHISELED_SANDSTONE);
    }

    @SubscribeEvent
    public void registerRecipes(@NotNull RegistryEvent.Register<IRecipe> event) {
        GameRegistry.addShapedRecipe(new ResourceLocation("caracalsmod:chiseled_sandstone_recipe"),
                new ResourceLocation("caracalsmod"),
                new ItemStack(WTFBlocks.CHISELED_SANDSTONE, 1),
                "ss ",
                "   ",
                "   ",
                's', new ItemStack(Blocks.STONE_SLAB, 1, 1));
    }

    @EventHandler
    // load "Do your mod setup. Build whatever data structures you care about." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
    }
}
