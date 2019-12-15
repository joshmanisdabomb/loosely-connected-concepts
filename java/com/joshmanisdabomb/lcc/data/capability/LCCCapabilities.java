package com.joshmanisdabomb.lcc.data.capability;

import net.minecraftforge.common.capabilities.CapabilityManager;

public abstract class LCCCapabilities {

    public static void init() {
        CapabilityManager.INSTANCE.register(GauntletCapability.class, new GauntletCapability.Storage(), GauntletCapability::new);
        CapabilityManager.INSTANCE.register(HeartsCapability.class, new HeartsCapability.Storage(), HeartsCapability::new);
        CapabilityManager.INSTANCE.register(SpreaderCapability.class, new SpreaderCapability.Storage(), SpreaderCapability::new);
        CapabilityManager.INSTANCE.register(CryingObsidianCapability.class, new CryingObsidianCapability.Storage(), CryingObsidianCapability::new);
        CapabilityManager.INSTANCE.register(ItemStorageCapability.class, new ItemStorageCapability.Storage(), ItemStorageCapability::new);
    }

}
