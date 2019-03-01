package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.data.capability.CapabilityEventHandler;
import com.joshmanisdabomb.lcc.data.capability.CapabilityGauntlet;
import com.joshmanisdabomb.lcc.data.capability.LCCCapabilities;
import com.joshmanisdabomb.lcc.event.GeneralEventHandler;
import com.joshmanisdabomb.lcc.event.InputEventHandler;
import com.joshmanisdabomb.lcc.event.OverlayEventHandler;
import com.joshmanisdabomb.lcc.event.RenderEventHandler;
import com.joshmanisdabomb.lcc.gen.BiomeBasedGenerator;
import com.joshmanisdabomb.lcc.registry.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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

    private void setup(final FMLCommonSetupEvent e) {
        LCCGroup.LCCGroupSort.sortItems();

        BiomeBasedGenerator.init();

        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());

        LCCCapabilities.init();
        MinecraftForge.EVENT_BUS.register(new CapabilityEventHandler());
    }

    private void onClientSetup(final FMLClientSetupEvent e) {
    	MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
        MinecraftForge.EVENT_BUS.register(new OverlayEventHandler());
        MinecraftForge.EVENT_BUS.register(new InputEventHandler());
    }

    private void enqueueIMC(final InterModEnqueueEvent e) {
        
    }

    private void processIMC(final InterModProcessEvent e) {
        
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent e) {

    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<Block> e) {
        	LCCBlocks.init(e);
        	e.getRegistry().registerAll(LCCBlocks.all.toArray(new Block[0]));
        }
        
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> e) {
        	LCCItems.init(e);
        	e.getRegistry().registerAll(LCCBlocks.allItem.toArray(new ItemBlock[0]));
        	e.getRegistry().registerAll(LCCItems.all.toArray(new Item[0]));
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> e) {
            LCCEntities.init(e);
            e.getRegistry().registerAll(LCCEntities.all.toArray(new EntityType<?>[0]));
        }

        @SubscribeEvent
        public static void onPotionRegistry(final RegistryEvent.Register<Potion> e) {
            LCCPotions.init(e);
            e.getRegistry().registerAll(LCCPotions.all.toArray(new Potion[0]));
        }
    }
}
