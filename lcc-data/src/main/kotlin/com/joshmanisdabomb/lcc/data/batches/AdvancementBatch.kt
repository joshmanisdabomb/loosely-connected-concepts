package com.joshmanisdabomb.lcc.data.batches

import net.minecraft.advancement.Advancement
import net.minecraft.util.Identifier

class AdvancementBatch {

    private val map = mutableMapOf<Identifier, Advancement.Builder>()
    private val builds = mutableMapOf<Identifier, Advancement>()

    fun add(builder: Advancement.Builder, id: Identifier) : Advancement {
        map[id] = builder

        val build = builder.build(id)
        builds[id] = build
        return build
    }

    fun getAdvancement(name: String) = builds.toList().firstOrNull { (k2, v2) -> name == k2.path }?.second

    fun getAdvancements() = builds.toMap()

}