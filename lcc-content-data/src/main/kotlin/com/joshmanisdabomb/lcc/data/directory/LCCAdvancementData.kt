package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.advancement.*
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.directory.*
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.advancement.AdvancementRewards
import net.minecraft.advancement.CriterionMerger
import net.minecraft.advancement.criterion.*
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items
import net.minecraft.predicate.DamagePredicate
import net.minecraft.predicate.NbtPredicate
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.entity.DamageSourcePredicate
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LocationPredicate
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.tag.Tag
import net.minecraft.text.TranslatableText
import net.minecraft.util.registry.Registry

object LCCAdvancementData : AdvancedDirectory<Advancement.Task, Advancement, Unit, Unit>() {

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
                .display(LCCBlocks.spawner_table, this)
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
                val nether_reactor by entry(::initialiser) {
                    Advancement.Task.create()
                        .parent(simulation_fabric)
                        .display(LCCBlocks.nether_reactor, this, frame = AdvancementFrame.CHALLENGE)
                        .criterion("challenge", NetherReactorChallengeCriterion.create())
                        .criteriaMerger(CriterionMerger.OR)
                        .rewards(AdvancementRewards.Builder.experience(500))
                        .translation("Never Say Nether", "Survive the nether reactor and player-kill all spawned pigmen without leaving the nether spire", "en_us", this)
                }.addTags("main")
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
                val nuclear by entry(::initialiser) {
                    Advancement.Task.create()
                        .parent(uranium)
                        .display(LCCBlocks.nuclear_generator, this, frame = AdvancementFrame.GOAL)
                        .criterion("activate", NuclearGeneratorCriterion.create())
                        .translation("Unlimited Power!", "Activate a nuclear generator", "en_us", this)
                }.addTags("main")

        val salt by entry(::initialiser) {
            Advancement.Task.create()
                .parent(main_root)
                .display(LCCItems.salt, this)
                .criterion("salted", PlayerHurtEntityCriterion.Conditions.create(DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.create().projectile(true).directEntity(EntityPredicate.Builder.create().type(LCCEntities.salt)))))
                .criteriaMerger(CriterionMerger.OR)
                .translation("Pocket Salt", "Reach into your pocket and quickly blind something", "en_us", this)
        }.addTags("main")

        val rubber by entry(::initialiser) {
            Advancement.Task.create()
                .parent(main_root)
                .display(LCCItems.flexible_rubber, this)
                .has(LCCItems.latex_bottle).has(LCCItems.flexible_rubber)
                .criteriaMerger(CriterionMerger.OR)
                .translation("Tap That", "Extract latex or dry rubber from a tapped rubber tree", "en_us", this)
        }.addTags("main")
            val hazmat by entry(::initialiser) {
                Advancement.Task.create()
                    .parent(rubber)
                    .display(LCCItems.hazmat_chestplate, this)
                    .criterion("depleted", ContainedArmorDepletionCriterion.create(ItemPredicate(null, LCCItems.hazmat_chestplate, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY)))
                    .criteriaMerger(CriterionMerger.OR)
                    .translation("Safety First", "Protect yourself with an oxygenated hazmat suit", "en_us", this)
            }.addTags("main")

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
        return this.display(item, TranslatableText("advancements.lcc.${context.tags[0]}.$name.title"), TranslatableText("advancements.lcc.${context.tags[0]}.$name.description"), if (context.tags.size < 2) null else LCC.id("textures/gui/advancements/backgrounds/${context.tags[0]}.png"), frame, toast, chat, hidden)
    }

    private fun Advancement.Task.translation(title: String, description: String, locale: String, context: DirectoryContext<Unit>): Advancement.Task {
        val name = context.tags.getOrNull(1) ?: context.name
        LCCData.lang[locale]!!["advancements.lcc.${context.tags[0]}.$name.title"] = title
        LCCData.lang[locale]!!["advancements.lcc.${context.tags[0]}.$name.description"] = description
        return this
    }

    private fun Advancement.Task.has(item: ItemConvertible) = this.criterion(Registry.ITEM.getId(item.asItem()).path, InventoryChangedCriterion.Conditions.items(item))
    private fun Advancement.Task.has(tag: Tag<Item>, name: String = "has_"+Registry.ITEM.getId(tag.values().first()).path) = this.criterion(name, InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tag).build()))

    private fun emptyAdvancement(parent: Advancement?, context: DirectoryContext<Unit>) = Advancement.Task.create().apply { parent?.let { parent(it) } }.display(Items.AIR, context).criterion("nope", ImpossibleCriterion.Conditions())

    fun initialiser(input: Advancement.Task, context: DirectoryContext<Unit>, parameters: Unit): Advancement {
        val name = "${context.tags[0]}/${context.tags.getOrNull(1) ?: context.name}"
        return input.build(LCC.id(name)).also { LCCData.advancements.list[name] = it }
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}