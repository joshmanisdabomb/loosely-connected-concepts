package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.data.capability.CapabilityEvents;
import com.joshmanisdabomb.lcc.data.capability.LCCCapabilities;
import com.joshmanisdabomb.lcc.event.bus.GeneralEvents;
import com.joshmanisdabomb.lcc.event.bus.InputEvents;
import com.joshmanisdabomb.lcc.event.bus.OverlayEvents;
import com.joshmanisdabomb.lcc.event.bus.RenderEvents;
import com.joshmanisdabomb.lcc.event.mod.ColorEvents;
import com.joshmanisdabomb.lcc.event.mod.ModelEvents;
import com.joshmanisdabomb.lcc.event.mod.RegistryEvents;
import com.joshmanisdabomb.lcc.gen.BiomeBasedGenerator;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.proxy.ClientProxy;
import com.joshmanisdabomb.lcc.proxy.Proxy;
import com.joshmanisdabomb.lcc.proxy.ServerProxy;
import com.joshmanisdabomb.lcc.registry.LCCEntities;
import com.joshmanisdabomb.lcc.registry.LCCGroup;
import com.joshmanisdabomb.lcc.registry.LCCScreens;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
        LCCGroup.initSorting();

        BiomeBasedGenerator.init();

        MinecraftForge.EVENT_BUS.register(new GeneralEvents());

        LCCCapabilities.init();
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());

        LCCPacketHandler.init();
    }

    private void onClientSetup(final FMLClientSetupEvent e) {
    	MinecraftForge.EVENT_BUS.register(new RenderEvents());
        MinecraftForge.EVENT_BUS.register(new OverlayEvents());
        MinecraftForge.EVENT_BUS.register(new InputEvents());

        LCCEntities.initRenderers();
        LCCTileEntities.initRenderers();

        LCCScreens.init();
    }

    private void enqueueIMC(final InterModEnqueueEvent e) {
        
    }

    private void processIMC(final InterModProcessEvent e) {
        
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent e) {

    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class Registry extends RegistryEvents {}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Model extends ModelEvents {}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Color extends ColorEvents {}

}
