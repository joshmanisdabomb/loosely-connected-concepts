package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.data.capability.CapabilityEvents;
import com.joshmanisdabomb.lcc.data.capability.LCCCapabilities;
import com.joshmanisdabomb.lcc.event.*;
import com.joshmanisdabomb.lcc.gen.BiomeBasedGenerator;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.proxy.ClientProxy;
import com.joshmanisdabomb.lcc.proxy.Proxy;
import com.joshmanisdabomb.lcc.proxy.ServerProxy;
import com.joshmanisdabomb.lcc.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
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

	public static final Proxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

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

        MinecraftForge.EVENT_BUS.register(new GeneralBusEvents());

        LCCCapabilities.init();
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());

        LCCPacketHandler.init();
    }

    private void onClientSetup(final FMLClientSetupEvent e) {
    	MinecraftForge.EVENT_BUS.register(new RenderBusEvents());
        MinecraftForge.EVENT_BUS.register(new OverlayBusEvents());
        MinecraftForge.EVENT_BUS.register(new InputBusEvents());

        LCCEntities.initRenderers();
    }

    private void enqueueIMC(final InterModEnqueueEvent e) {
        
    }

    private void processIMC(final InterModProcessEvent e) {
        
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent e) {

    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class Registry extends RegistryModEvents {}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class Model extends ModelModEvents {}

}
