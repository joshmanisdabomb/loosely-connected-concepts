package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.event.RenderEventHandler;
import com.joshmanisdabomb.lcc.gen.BiomeBasedGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LCC.MODID)
public class LCC
{
	private static final Logger LOGGER = LogManager.getLogger();
    
	public static final String MODID = "lcc";
	public static final LCCGroup itemGroup = new LCCGroup();

    public LCC() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LCCGroup.LCCGroupSort.sortItems();
        BiomeBasedGenerator.init();
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
    	MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        
    }

    private void processIMC(final InterModProcessEvent event) {
        
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        	LCCBlocks.init(blockRegistryEvent);
        	blockRegistryEvent.getRegistry().registerAll(LCCBlocks.all.toArray(new Block[0]));
        }
        
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
        	LCCItems.init(itemRegistryEvent);
        	itemRegistryEvent.getRegistry().registerAll(LCCBlocks.allItem.toArray(new ItemBlock[0]));
        	itemRegistryEvent.getRegistry().registerAll(LCCItems.all.toArray(new Item[0]));
        }

        @SubscribeEvent
        public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> entityRegistryEvent) {
            LCCEntities.init(entityRegistryEvent);
            entityRegistryEvent.getRegistry().registerAll(LCCEntities.all.toArray(new EntityType<?>[0]));
        }
    }
}
