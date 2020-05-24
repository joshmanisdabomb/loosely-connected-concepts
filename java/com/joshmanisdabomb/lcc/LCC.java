package com.joshmanisdabomb.lcc;

import com.joshmanisdabomb.lcc.capability.CapabilityEvents;
import com.joshmanisdabomb.lcc.capability.LCCCapabilities;
import com.joshmanisdabomb.lcc.event.bus.*;
import com.joshmanisdabomb.lcc.event.mod.ColorEvents;
import com.joshmanisdabomb.lcc.event.mod.DataEvents;
import com.joshmanisdabomb.lcc.event.mod.RegistryEvents;
import com.joshmanisdabomb.lcc.event.mod.ResourceEvents;
import com.joshmanisdabomb.lcc.gen.world.BiomeBasedGenerator;
import com.joshmanisdabomb.lcc.item.group.Creative2Group;
import com.joshmanisdabomb.lcc.item.group.LCCGroup;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.proxy.ClientProxy;
import com.joshmanisdabomb.lcc.proxy.DedicatedServerProxy;
import com.joshmanisdabomb.lcc.proxy.Proxy;
import com.joshmanisdabomb.lcc.registry.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LCC.MODID)
public class LCC
{
	private static final Logger LOGGER = LogManager.getLogger();
    
	public static final String MODID = "lcc";
	public static final LCCGroup itemGroup = new LCCGroup();

	public static final Proxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> DedicatedServerProxy::new);

    public LCC() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);

        LCCSounds.init();
    }

    private void setup(final FMLCommonSetupEvent e) {
        LCCBiomes.initEntries();
        BiomeBasedGenerator.init();

        LCCDimensions.initManagerRegistry();

        MinecraftForge.EVENT_BUS.register(new GeneralEvents());
        MinecraftForge.EVENT_BUS.register(new InterfaceEvents());
        MinecraftForge.EVENT_BUS.register(new TagEvents());

        LCCCapabilities.init();
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());

        LCCPacketHandler.init();
    }

    private void onClientSetup(final FMLClientSetupEvent e) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    	MinecraftForge.EVENT_BUS.register(new RenderEvents());
        MinecraftForge.EVENT_BUS.register(new UIEvents());
        MinecraftForge.EVENT_BUS.register(new InputEvents());

        LCCBlocks.initRenderLayers();
        LCCEntities.initRenderers();
        LCCTileEntities.initRenderers();
        LCCParticles.initFactories();

        LCCScreens.init();

        Creative2Group.GROUPS.forEach(Creative2Group::initSorting);
    }

    private void enqueueIMC(final InterModEnqueueEvent e) {
        
    }

    private void processIMC(final InterModProcessEvent e) {
        
    }
    
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class Registry extends RegistryEvents {}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Resource extends ResourceEvents {}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Color extends ColorEvents {}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class Data extends DataEvents {}

}
