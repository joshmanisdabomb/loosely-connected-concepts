package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.container.EntityDataContainer
import com.joshmanisdabomb.lcc.data.factory.loot.entity.ClassicEntityLootFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCEntities
import net.minecraft.item.Items

object LCCEntityData : BasicDirectory<EntityDataContainer, Unit>() {

    val pocket_zombie_pigman by entry(::initialiser) { EntityDataContainer().defaultLang().add(ClassicEntityLootFactory(Items.GOLD_NUGGET)) }

    fun initialiser(input: EntityDataContainer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out EntityDataContainer, out EntityDataContainer>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        all.forEach { (k, v) -> v.init(k, LCCEntities[k]) }

        val missing = LCCEntities.all.values.minus(all.values.flatMap { it.affects })
        missing.forEach { val key = LCCEntities[it].name; defaults().init(key, it) }
    }

    fun defaults() = EntityDataContainer().defaultLang()

    private fun EntityDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)

}