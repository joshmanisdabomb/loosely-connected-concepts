package com.joshmanisdabomb.lcc.data.directory

import com.google.common.collect.Sets
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.advancement.NuclearExplosionCriterion
import com.joshmanisdabomb.lcc.advancement.RaceCriterion
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.directory.*
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.ImpossibleCriterion
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.advancement.criterion.LocationArrivalCriterion
import net.minecraft.advancement.criterion.PlacedBlockCriterion
import net.minecraft.data.DataCache
import net.minecraft.data.DataProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.LocationPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.tag.Tag
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.io.IOException

object LCCAdvancementData : AdvancedDirectory<Advancement.Task, Advancement, Unit, Unit>(), DataProvider {

    //Main Progression
    val main_root by entry(::initialiser) {
        Advancement.Task.create()
            .display(LCCBlocks.test_block, this, toast = false, chat = false)
            .has(LCCItems.ruby).has(LCCItems.topaz_shard).has(Items.EMERALD).has(Items.DIAMOND).has(LCCItems.sapphire).has(Items.AMETHYST_SHARD)
            .criteriaMerger(CriterionMerger.OR)
            .translation("Loosely Connected Concepts", "Main progression line of Loosely Connected Concepts", "en_us", this)
    }.addTags("main", "root")

        val spawner_table by entry(::initialiser) {
            Advancement.Task.create()
                .parent(main_root)
                .display(LCCBlocks.test_block, this)
                .criterion("place", PlacedBlockCriterion.Conditions.block(LCCBlocks.spawner_table))
                .criteriaMerger(CriterionMerger.OR)
                .translation("Next Level Crafting", "Convert a mob spawner into a crafting station", "en_us", this)
        }.addTags("main")
            val simulation_fabric by entry(::initialiser) { 
                Advancement.Task.create()
                    .parent(spawner_table)
                    .display(LCCItems.simulation_fabric, this)
                    .has(LCCItems.simulation_fabric)
                    .translation("Enter the Matrix", "Craft the Simulation Fabric in an Arcane Table", "en_us", this)
            }.addTags("main")
                val ruby by entry(::initialiser) {
                    Advancement.Task.create()
                        .parent(simulation_fabric)
                        .display(LCCItems.ruby, this)
                        .has(LCCItems.ruby).has(LCCBlocks.ruby_ore).has(LCCBlocks.ruby_block)
                        .criteriaMerger(CriterionMerger.OR)
                        .translation("What Could Have Been", "Obtain rubies by throwing emeralds into the time rift", "en_us", this)
                }.addTags("main")
                //IDEA complete nether reactor
            val rainbow_portal by entry(::initialiser) { emptyAdvancement(spawner_table, this) }.addTags("main") //into the rainbowverse

        val topaz by entry(::initialiser) {
            Advancement.Task.create()
                .parent(main_root)
                .display(LCCItems.topaz_shard, this)
                .has(LCCItems.topaz_shard)
                .translation("Reskinned Amethyst", "Collect topaz from a volcanic geode near a lava lake", "en_us", this)
        }.addTags("main")

        val refiner by entry(::initialiser) {
            Advancement.Task.create()
                .parent(main_root)
                .display(LCCBlocks.refiner, this)
                .has(LCCBlocks.refiner)
                .translation("A Refined Palate", "The industrial revolution and its consequences have been a disaster for the human race", "en_us", this)
        }.addTags("main")
            val uranium by entry(::initialiser) {
                Advancement.Task.create()
                    .parent(refiner)
                    .display(LCCItems.enriched_uranium, this)
                    .has(LCCTags.enriched_uranium, "has_enriched_uranium")
                    .translation("Enrichment Activities", "Refine the uranium into a different shade of green", "en_us", this)
            }.addTags("main")
                val nuke by entry(::initialiser) {
                    Advancement.Task.create()
                        .parent(uranium)
                        .display(LCCBlocks.atomic_bomb, this)
                        .criterion("detonate", NuclearExplosionCriterion.create(NumberRange.IntRange.ANY))
                        .translation("The World is the Problem", "Detonate an atomic bomb", "en_us", this)
                }.addTags("main")
                    val nuke_first by entry(::initialiser) {
                        Advancement.Task.create()
                            .parent(nuke)
                            .display(LCCBlocks.atomic_bomb, this, frame = AdvancementFrame.CHALLENGE, hidden = true)
                            .criterion("race", RaceCriterion.create(nuke.id))
                            .rewards(AdvancementRewards.Builder.experience(500))
                            .translation("Nuclear Arms Race", "Be the first person on the server to detonate an atomic bomb", "en_us", this)
                    }.addTags("main")
                    val winter_survive by entry(::initialiser) { emptyAdvancement(nuke, this) }.addTags("main") //the struggle was real, return to winter level 0 after being at winter level 5

    val wasteland_root by entry(::initialiser) {
        Advancement.Task.create()
            .display(LCCBlocks.cracked_mud, this, toast = false, chat = false)
            .criterion("enter", LocationArrivalCriterion.Conditions.create(LocationPredicate.biome(LCCBiomes.getRegistryKey(LCCBiomes.wasteland))))
            .translation("LCC: Wasteland", "Perambulate into the haze of the wasteland", "en_us", this)
    }.addTags("wasteland", "root")
        val oil by entry(::initialiser) {
            Advancement.Task.create()
                .parent(wasteland_root)
                .display(LCCItems.oil_bucket, this)
                .has(LCCItems.oil_bucket)
                .translation("Another Mod With Oil", "Daring today aren't we?", "en_us", this)
        }.addTags("wasteland")
            val asphalt by entry(::initialiser) {
                Advancement.Task.create()
                    .parent(oil)
                    .display(LCCItems.asphalt_bucket, this)
                    .has(LCCItems.asphalt_bucket)
                    .translation("Paving the Way", "Refine oil and gravel into paving mixture", "en_us", this)
            }.addTags("wasteland")
                //IDEA walk 200m in a straight line from point a without leaving road - all roads lead to home / paved with good intentions
        val sapphire by entry(::initialiser) {
            Advancement.Task.create()
                .parent(wasteland_root)
                .display(LCCItems.sapphire, this)
                .has(LCCItems.sapphire)
                .translation("Beauty Amongst the Unsightly", "Find a sapphire", "en_us", this)
        }.addTags("wasteland")

    private fun Advancement.Task.display(item: ItemConvertible, context: DirectoryContext<Unit>, frame: AdvancementFrame = AdvancementFrame.TASK, toast: Boolean = true, chat: Boolean = true, hidden: Boolean = false): Advancement.Task {
        val name = context.tags.getOrNull(1) ?: context.name
        return this.display(item, TranslatableText("advancements.lcc.${context.tags[0]}.$name.title"), TranslatableText("advancements.lcc.${context.tags[0]}.$name.description"), if (context.tags.size > 1) null else LCC.id("textures/gui/advancements/backgrounds/${context.tags[0]}.png"), frame, toast, chat, hidden)
    }

    private fun Advancement.Task.translation(title: String, description: String, locale: String, context: DirectoryContext<Unit>): Advancement.Task {
        val name = context.tags.getOrNull(1) ?: context.name
        LCCData.accessor.lang[locale]!!["advancements.lcc.${context.tags[0]}.$name.title"] = title
        LCCData.accessor.lang[locale]!!["advancements.lcc.${context.tags[0]}.$name.description"] = description
        return this
    }

    private fun Advancement.Task.has(item: ItemConvertible) = this.criterion(Registry.ITEM.getId(item.asItem()).path, InventoryChangedCriterion.Conditions.items(item))
    private fun Advancement.Task.has(tag: Tag<Item>, name: String = "has_"+Registry.ITEM.getId(tag.values().first()).path) = this.criterion(name, InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tag).build()))

    private fun emptyAdvancement(parent: Advancement?, context: DirectoryContext<Unit>) = Advancement.Task.create().apply { parent?.let { parent(it) } }.display(Items.AIR, context).criterion("nope", ImpossibleCriterion.Conditions())

    fun initialiser(input: Advancement.Task, context: DirectoryContext<Unit>, parameters: Unit) = input.build(LCC.id("${context.tags[0]}/${context.tags.getOrNull(1) ?: context.name}"))

    override fun afterInitAll(initialised: List<DirectoryEntry<out Advancement.Task, out Advancement>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        LCCData.accessor.handler.install(this)
    }

    override fun run(cache: DataCache) {
        val path = LCCData.accessor.handler.output
        val set: MutableSet<Identifier> = Sets.newHashSet()
        val consumer = { a: Advancement ->
            check(set.add(a.id)) { "Duplicate advancement " + a.id }
            val path1 = path.resolve("data/" + a.id.namespace + "/advancements/" + a.id.path + ".json")
            try {
                DataProvider.writeToPath(DataUtils.gson, cache, a.createTask().toJson(), path1)
            } catch (ioexception: IOException) {
                DataUtils.logger.error("Couldn't save advancement {}", path1, ioexception)
            }
        }
        entries.forEach { (k, v) ->
            v.entry.createTask().apply { findParent { if (it.namespace == "lcc") LCCAdvancementData.all.toList().firstOrNull { (k2, v2) -> it.path == "${v.tags[0]}/${v.tags.getOrNull(1) ?: v.name}" }?.second else null } }.build(consumer, LCC.id("${v.tags[0]}/${v.tags.getOrNull(1) ?: v.name}").toString())
        }
    }

    override fun getName() = LCCData.accessor.modid + " Advancements"

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}