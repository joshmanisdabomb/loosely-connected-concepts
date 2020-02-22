package com.joshmanisdabomb.lcc.data.capability;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public abstract class LCCCapabilities {

    public static final HashMap<ResourceLocation, Supplier<LCCCapabilityHelper>> ch_all = new HashMap<>();

    public static void init() {
        register(GauntletCapability.class, new GauntletCapability.Storage(), GauntletCapability::new);
        register(HeartsCapability.class, new HeartsCapability.Storage(), HeartsCapability::new);
        register(SpreaderCapability.class, new SpreaderCapability.Storage(), SpreaderCapability::new);
        register(CryingObsidianCapability.class, new CryingObsidianCapability.Storage(), CryingObsidianCapability::new);
        register(PartitionCapability.class, new PartitionCapability.Storage(), PartitionCapability::new);
        register(NuclearCapability.class, new NuclearCapability.Storage(), NuclearCapability::new);
    }

    public static <T extends LCCCapabilityHelper> void register(Class<T> type, Capability.IStorage<T> storage, Callable<? extends T> factory) {
        CapabilityManager.INSTANCE.register(type, storage, factory);
        Supplier<LCCCapabilityHelper> factory2 = () -> { try { return factory.call(); } catch (Exception e) { e.printStackTrace(); } return null; };
        LCCCapabilityHelper ch = factory2.get();
        ch_all.put(ch.getLocation(), factory2);
    }

}
