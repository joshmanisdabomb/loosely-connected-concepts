package com.joshmanisdabomb.lcc.data.capability;

import net.minecraftforge.common.capabilities.CapabilityManager;

public abstract class LCCCapabilities {

    public static void init() {
        CapabilityManager.INSTANCE.register(CapabilityGauntlet.CIGauntlet.class, new CapabilityGauntlet(), CapabilityGauntlet.CGauntlet::new);
        CapabilityManager.INSTANCE.register(CapabilityHearts.CIHearts.class, new CapabilityHearts(), CapabilityHearts.CHearts::new);
    }

}
