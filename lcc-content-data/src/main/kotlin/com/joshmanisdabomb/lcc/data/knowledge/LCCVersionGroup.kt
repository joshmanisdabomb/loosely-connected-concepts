package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.LCC

enum class LCCVersionGroup(val title: String, val shortname: String?, val code: String, val branch: String, val namespace: String, val order: Short, val sources: Boolean, val tags: Boolean) {

    LCC_FABRIC("Loosely Connected Concepts (Fabric)", null, "LooselyConnectedConcepts", "fabric", LCC.modid, 100, true, true),
    LCC_FORGE("Loosely Connected Concepts (Forge)", "Forge", "LooselyConnectedConcepts", "lcc1.15.2forge", LCC.modid.plus("f"), 200, false, false),
    AIMAGG("Aimless Agglomeration", "Aimagg", "AimlessAgglomeration", "aa1.12.2", "aimagg", 300, true, false),
    YAM("Yet Another Mod", "YAM", "YAM", "yam1.7.2", "yam", 400, false, false);

}