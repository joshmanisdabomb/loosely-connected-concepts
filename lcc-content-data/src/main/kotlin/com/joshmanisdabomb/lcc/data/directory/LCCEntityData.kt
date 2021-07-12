package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.container.EntityDataContainer
import com.joshmanisdabomb.lcc.data.factory.loot.entity.ClassicEntityLootFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCEntities
import net.minecraft.item.Items

object LCCEntityData : BasicDirectory<EntityDataContainer, Unit>() {

    val pocket_zombie_pigman by entry(::initialiser) { data().defaultLang().add(ClassicEntityLootFactory(Items.GOLD_NUGGET)) }

    fun initialiser(input: EntityDataContainer, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out EntityDataContainer, out EntityDataContainer>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach { it.entry.init(it.name, LCCEntities.getOrNull(it.name)) }

        val missing = LCCEntities.all.values.minus(initialised.flatMap { it.entry.affects })
        missing.forEach { val key = LCCEntities[it].name; defaults().init(key, it) }
    }

    private fun data() = EntityDataContainer(LCCData)
    private fun defaults() = data().defaultLang()

    private fun EntityDataContainer.defaultLang() = add(BasicTranslationFactory).add(BritishTranslationFactory)

}