package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.container.EntityDataContainer
import com.joshmanisdabomb.lcc.data.factory.loot.entity.ClassicEntityLootFactory
import com.joshmanisdabomb.lcc.data.factory.loot.entity.ConsumerEntityLootFactory
import com.joshmanisdabomb.lcc.data.factory.loot.entity.FunctionalEntityLootFactory
import com.joshmanisdabomb.lcc.data.factory.tag.EntityTagFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BasicTranslationFactory
import com.joshmanisdabomb.lcc.data.factory.translation.BritishTranslationFactory
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.tags.LCCEntityTags
import net.minecraft.item.Items
import net.minecraft.loot.condition.KilledByPlayerLootCondition
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition
import net.minecraft.loot.function.LootingEnchantLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.UniformLootNumberProvider

object LCCEntityData : BasicDirectory<EntityDataContainer, Unit>() {

    val pocket_zombie_pigman by entry(::initialiser) { data().defaultLang().add(ClassicEntityLootFactory(Items.GOLD_NUGGET)) }

    val baby_skeleton by entry(::initialiser) { data().defaultLang().add(FunctionalEntityLootFactory(mapOf(Items.ARROW to null, Items.BONE to null))).add(EntityTagFactory(LCCEntityTags.wasteland_defense)) }
    val wasp by entry(::initialiser) { data().defaultLang().add(FunctionalEntityLootFactory(mapOf(
        LCCItems.stinger to {
            apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
            conditionally(KilledByPlayerLootCondition.builder())
            conditionally(RandomChanceWithLootingLootCondition.builder(0.3F, 0.1F))
        }
    ))).add(EntityTagFactory(LCCEntityTags.wasteland_offense)).add(EntityTagFactory(LCCEntityTags.wasteland_defense)) }
    val consumer by entry(::initialiser) { data().defaultLang().add(ConsumerEntityLootFactory).add(EntityTagFactory(LCCEntityTags.wasteland_offense)).add(EntityTagFactory(LCCEntityTags.wasteland_defense)).add(EntityTagFactory(LCCEntityTags.salt_weakness)) }
    val disciple by entry(::initialiser) { data().defaultLang().add(FunctionalEntityLootFactory(mapOf(LCCItems.enhancing_pyre_alpha to null))).add(EntityTagFactory(LCCEntityTags.wasteland_offense)).add(EntityTagFactory(LCCEntityTags.wasteland_defense)) }
    val psycho_pig by entry(::initialiser) { data().defaultLang().add(FunctionalEntityLootFactory(mapOf(Items.PORKCHOP to null))).add(EntityTagFactory(LCCEntityTags.wasteland_offense)).add(EntityTagFactory(LCCEntityTags.wasteland_defense)) }
    val woodlouse by entry(::initialiser) { data().defaultLang().add(FunctionalEntityLootFactory(mapOf(LCCItems.woodlouse_shell to null))).add(EntityTagFactory(LCCEntityTags.wasteland_defense)) }
    val rotwitch by entry(::initialiser) { data().defaultLang().add(FunctionalEntityLootFactory(mapOf(
        LCCItems.fly_eggs to {
            apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
            apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
        }
    ))).add(EntityTagFactory(LCCEntityTags.wasteland_offense)).add(EntityTagFactory(LCCEntityTags.wasteland_defense)) }

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