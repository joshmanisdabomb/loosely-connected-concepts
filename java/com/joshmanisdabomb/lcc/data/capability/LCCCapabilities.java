package com.joshmanisdabomb.lcc.data.capability;

import net.minecraftforge.common.capabilities.CapabilityManager;

public abstract class LCCCapabilities {

    public static void init() {
        CapabilityManager.INSTANCE.register(GauntletCapability.CIGauntlet.class, new GauntletCapability(), GauntletCapability.CGauntlet::new);
        CapabilityManager.INSTANCE.register(HeartsCapability.CIHearts.class, new HeartsCapability(), HeartsCapability.CHearts::new);
    }

}
