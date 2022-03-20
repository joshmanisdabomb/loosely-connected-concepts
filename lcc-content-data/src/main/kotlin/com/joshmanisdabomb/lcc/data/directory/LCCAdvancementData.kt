package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.advancement.*
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.directory.*
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
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
import net.minecraft.predicate.StatePredicate
import net.minecraft.predicate.entity.DamageSourcePredicate
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.LocationPredicate
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.tag.TagKey
import net.minecraft.text.TranslatableText
import net.minecraft.util.registry.Registry

object LCCAdvancementData : AdvancedDirectory<Advancement.Builder, Advancement, Unit, Unit>() {

    //Main Progression
    val main_root by entry(::initialiser) {
        Advancement.Builder.create()
            .display(LCCBlocks.test_block, this, toast = false, chat = false)
            .has(LCCItems.ruby).has(LCCItems.topaz_shard).has(Items.EMERALD).has(Items.DIAMOND).has(LCCItems.sapphire).has(Items.AMETHYST_SHARD)
            .criteriaMerger(CriterionMerger.OR)
            .translation("Loosely Connected Concepts", "Main progression line of Loosely Connected Concepts", "en_us", this)
    }.addTags("main", "root")

    //Main Progression > Arcane Table
    val spawner_table by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(main_root)
            .display(LCCBlocks.spawner_table, this)
            .criterion("place", PlacedBlockCriterion.Conditions.block(LCCBlocks.spawner_table))
            .criteriaMerger(CriterionMerger.OR)
            .translation("Next Level Crafting", "Convert a mob spawner into a crafting station", "en_us", this)
    }.addTags("main")

    //Main Progression > Arcane Table > Simulation Fabric
    val simulation_fabric by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(spawner_table)
            .display(LCCItems.simulation_fabric, this)
            .has(LCCItems.simulation_fabric)
            .translation("Enter the Matrix", "Craft the Simulation Fabric in an Arcane Table", "en_us", this)
    }.addTags("main")

    //Main Progression > Arcane Table > Simulation Fabric > Rubies
    val ruby by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(simulation_fabric)
            .display(LCCItems.ruby, this)
            .has(LCCItems.ruby).has(LCCBlocks.ruby_ore).has(LCCBlocks.ruby_block)
            .criteriaMerger(CriterionMerger.OR)
            .translation("What Could Have Been", "Obtain rubies by throwing emeralds into the time rift", "en_us", this)
    }.addTags("main")

    //Main Progression > Arcane Table > Simulation Fabric > Nether Reactor
    val nether_reactor by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(simulation_fabric)
            .display(LCCBlocks.nether_reactor, this, frame = AdvancementFrame.CHALLENGE)
            .criterion("challenge", NetherReactorChallengeCriterion.create())
            .criteriaMerger(CriterionMerger.OR)
            .rewards(AdvancementRewards.Builder.experience(500))
            .translation("Never Say Nether", "Survive the nether reactor and player-kill all spawned pigmen without leaving the nether spire", "en_us", this)
    }.addTags("main")

    //Main Progression > Arcane Table > Simulation Fabric > Rubies > Rainbow Portal
    val rainbow_portal by entry(::initialiser) { emptyAdvancement(spawner_table, this) }.addTags("main") //into the rainbowverse

    //Main Progression > Topaz
    val topaz by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(main_root)
            .display(LCCItems.topaz_shard, this)
            .has(LCCItems.topaz_shard)
            .translation("Reskinned Amethyst", "Collect topaz from a volcanic geode near a lava lake", "en_us", this)
    }.addTags("main")

    //Main Progression > Refiner
    val refiner by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(main_root)
            .display(LCCBlocks.refiner, this)
            .has(LCCBlocks.refiner)
            .translation("A Refined Palate", "The industrial revolution and its consequences have been a disaster for the human race", "en_us", this)
    }.addTags("main")

    //Main Progression > Refiner > Uranium Enrichment
    val uranium by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(refiner)
            .display(LCCItems.enriched_uranium, this)
            .has(LCCItemTags.enriched_uranium, "has_enriched_uranium")
            .translation("Enrichment Activities", "Refine the uranium into a different shade of green", "en_us", this)
    }.addTags("main")

    //Main Progression > Refiner > Uranium Enrichment > Nuke
    val nuke by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(uranium)
            .display(LCCBlocks.atomic_bomb, this)
            .criterion("detonate", NuclearExplosionCriterion.create(NumberRange.IntRange.ANY))
            .translation("The World is the Problem", "Detonate an atomic bomb", "en_us", this)
    }.addTags("main")

    //Main Progression > Refiner > Uranium Enrichment > Nuke First
    val nuke_first by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(nuke)
            .display(LCCBlocks.atomic_bomb, this, frame = AdvancementFrame.CHALLENGE, hidden = true)
            .criterion("race", RaceCriterion.create(nuke.id))
            .rewards(AdvancementRewards.Builder.experience(500))
            .translation("Nuclear Arms Race", "Be the first person on the server to detonate an atomic bomb", "en_us", this)
    }.addTags("main")

    //Main Progression > Refiner > Uranium Enrichment > Survive Winter
    val winter_survive by entry(::initialiser) { emptyAdvancement(nuke, this) }.addTags("main") //the struggle was real, return to winter level 0 after being at winter level 5

    //Main Progression > Refiner > Uranium Enrichment > Nuclear Power
    val nuclear by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(uranium)
            .display(LCCBlocks.nuclear_generator, this, frame = AdvancementFrame.GOAL)
            .criterion("activate", NuclearGeneratorCriterion.create())
            .translation("Unlimited Power!", "Activate a nuclear generator", "en_us", this)
    }.addTags("main")

    //Main Progression > Throw Salt
    val salt by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(main_root)
            .display(LCCItems.salt, this)
            .criterion("salted", PlayerHurtEntityCriterion.Conditions.create(DamagePredicate.Builder.create().type(DamageSourcePredicate.Builder.create().projectile(true).directEntity(EntityPredicate.Builder.create().type(LCCEntities.salt)))))
            .criteriaMerger(CriterionMerger.OR)
            .translation("Pocket Salt", "Reach into your pocket and quickly blind something", "en_us", this)
    }.addTags("main")

    //Main Progression > Extract Rubber
    val rubber by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(main_root)
            .display(LCCItems.flexible_rubber, this)
            .has(LCCItems.latex_bottle).has(LCCItems.flexible_rubber)
            .criteriaMerger(CriterionMerger.OR)
            .translation("Tap That", "Extract latex or dry rubber from a tapped rubber tree", "en_us", this)
    }.addTags("main")

    //Main Progression > Extract Rubber > Hazmat Suit
    val hazmat by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(rubber)
            .display(LCCItems.hazmat_chestplate, this)
            .criterion("depleted", ContainedArmorDepletionCriterion.create(ItemPredicate(null, setOf(LCCItems.hazmat_chestplate), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY)))
            .criteriaMerger(CriterionMerger.OR)
            .translation("Safety First", "Protect yourself with an oxygenated hazmat suit", "en_us", this)
    }.addTags("main")

    //Wasteland Progression
    val wasteland_root by entry(::initialiser) {
        Advancement.Builder.create()
            .display(LCCBlocks.cracked_mud, this, toast = false, chat = false)
            .criterion("enter", LocationArrivalCriterion.Conditions.create(LocationPredicate.biome(LCCBiomes.getRegistryKey(LCCBiomes.wasteland_barrens))))
            .translation("LCC: Wasteland", "Perambulate into the haze of the wasteland", "en_us", this)
    }.addTags("wasteland", "root")

    //Wasteland Progression > Extract Oil
    val oil by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(wasteland_root)
            .display(LCCItems.oil_bucket, this)
            .has(LCCItems.oil_bucket)
            .translation("Another Mod With Oil", "Daring today aren't we?", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Extract Oil > Asphalt
    val asphalt by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(oil)
            .display(LCCItems.asphalt_bucket, this)
            .has(LCCItems.asphalt_bucket)
            .translation("Paving the Way", "Refine oil into tar and mix with gravel for paving mixture", "en_us", this)
    }.addTags("wasteland")

    //IDEA walk 200m in a straight line from point a without leaving road - all roads lead to home / paved with good intentions

    //Wasteland Progression > Extract Oil > Plastics
    val plastic by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(oil)
            .display(LCCItems.flexible_plastic, this)
            .has(LCCItems.rigid_plastic).has(LCCItems.flexible_plastic)
            .criteriaMerger(CriterionMerger.OR)
            .translation("Life in Plastic", "Refine oil into flexible or rigid plastic", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Extract Oil > Chain Reaction
    val explosive_paste by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(oil)
            .display(LCCBlocks.explosive_paste, this, frame = AdvancementFrame.GOAL)
            .criterion("triggered_paste", ExplosivePasteTriggeredCriterion.create(StatePredicate.ANY, LocationPredicate.ANY))
            .translation("Chain Reaction", "Trigger explosive paste with an explosion (directly caused by you)", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Deader than my Trim
    val deadwood by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(wasteland_root)
            .display(LCCBlocks.deadwood_log, this)
            .has(LCCItemTags.deadwood_logs, "has_deadwood")
            .has(LCCBlocks.deadwood_planks)
            .criteriaMerger(CriterionMerger.OR)
            .translation("Deader than my Trim", "Find the remains of a once living tree", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Deader than my Trim > Hold the Fort
    val fortstone by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(deadwood)
            .display(LCCBlocks.cobbled_fortstone, this)
            .has(LCCBlocks.fortstone).has(LCCBlocks.cobbled_fortstone)
            .criteriaMerger(CriterionMerger.OR)
            .translation("Hold the Fort", "Acquire fortstone from the sharp craggy sub-biome", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Deader than my Trim > Hold the Fort > 1v1 Me Rust
    val rusty_iron by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(fortstone)
            .display(LCCItems.iron_oxide, this)
            .has(LCCBlocks.rusted_iron_blocks.values.last())
            .translation("1v1 Me Rust", "Leave a block of iron submerged in water in the wastelands", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Deader than my Trim > Hold the Fort > 1v1 Me Rust > Challenge Accepted
    val sapphire by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(rusty_iron)
            .display(LCCItems.sapphire, this)
            .criterion("challenge", SapphireAltarCompleteCriterion.create(null, NumberRange.IntRange.ANY))
            .translation("Challenge Accepted", "Beat the challenge posed by a sapphire altar", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Deader than my Trim > Hold the Fort > 1v1 Me Rust > Challenge Accepted > Staking Rewards
    val sapphire_10 by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(sapphire)
            .display(LCCBlocks.sapphire_block, this)
            .criterion("challenge", SapphireAltarCompleteCriterion.create(null, NumberRange.IntRange.atLeast(10)))
            .translation("Staking Rewards", "Receive the maximum gains (10 sapphires) from a sapphire altar", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > A Hearty Meal
    val heart_container by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(wasteland_root)
            .display(LCCItems.heart_container[HeartType.RED]!!, this)
            .criterion("add_red_health", HeartContainerCriterion.create(null, NumberRange.FloatRange.ANY))
            .translation("A Hearty Meal", "Use any heart container to increase your maximum life", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > A Hearty Meal > Healthy Gamer
    val heart_container_max by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(heart_container)
            .display(LCCItems.heart_full[HeartType.RED]!!, this, frame = AdvancementFrame.GOAL)
            .criteriaMerger(CriterionMerger.AND)
            .apply {
                for (i in 1..10) this.criterion("max_red_health_$i", HeartContainerCriterion.create(HeartType.RED, NumberRange.FloatRange.atLeast(i.times(2.0))))
            }
            .translation("Healthy Gamer", "Use 10 red heart containers to achieve a maximum of 20 red hearts", "en_us", this)
    }.addTags("wasteland")

    //IDEA ultimate tank, all 3 health pools +10

    //IDEA you know im super fly - have 20 flies at once

    //Wasteland Progression > Alternative Metal
    val tungsten by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(wasteland_root)
            .display(LCCItems.tungsten_ingot, this)
            .has(LCCItems.tungsten_ingot)
            .translation("Alternative Metal", "Smelt a tungsten ingot", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Alternative Metal > Radar
    val radar by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(tungsten)
            .display(LCCBlocks.radar, this)
            .has(LCCBlocks.radar)
            .translation("Great British Secret Weapon", "And it was here... in these skies... that the Luftwaffe was defeated...", "en_us", this)
    }.addTags("wasteland")

    //Wasteland Progression > Alternative Metal > Radar
    val detector by entry(::initialiser) {
        Advancement.Builder.create()
            .parent(tungsten)
            .display(LCCItems.radiation_detector, this)
            .has(LCCItems.radiation_detector)
            .translation("Finding Uranium for Dummies", "Hardcore gamers use the Radiation effect alone to find uranium", "en_us", this)
    }.addTags("wasteland")

    //IDEA kill a wasteland mob without using any wasteland tools or armor

    //IDEA crowbar only hit stuns, smash glass, salvage something

    private fun Advancement.Builder.display(item: ItemConvertible, context: DirectoryContext<Unit>, frame: AdvancementFrame = AdvancementFrame.TASK, toast: Boolean = true, chat: Boolean = true, hidden: Boolean = false): Advancement.Builder {
        val name = context.tags.getOrNull(1) ?: context.name
        return this.display(item, TranslatableText("advancements.lcc.${context.tags[0]}.$name.title"), TranslatableText("advancements.lcc.${context.tags[0]}.$name.description"), if (context.tags.size < 2) null else LCC.id("textures/gui/advancements/backgrounds/${context.tags[0]}.png"), frame, toast, chat, hidden)
    }

    private fun Advancement.Builder.translation(title: String, description: String, locale: String, context: DirectoryContext<Unit>): Advancement.Builder {
        val name = context.tags.getOrNull(1) ?: context.name
        LCCData.lang[locale, "advancements.lcc.${context.tags[0]}.$name.title"] = title
        LCCData.lang[locale, "advancements.lcc.${context.tags[0]}.$name.description"] = description
        return this
    }

    private fun Advancement.Builder.has(item: ItemConvertible) = this.criterion(Registry.ITEM.getId(item.asItem()).path, InventoryChangedCriterion.Conditions.items(item))
    private fun Advancement.Builder.has(tag: TagKey<Item>, name: String = "has_"+tag.id.path) = this.criterion(name, InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tag).build()))

    private fun emptyAdvancement(parent: Advancement?, context: DirectoryContext<Unit>) = Advancement.Builder.create().apply { parent?.let { parent(it) } }.display(Items.AIR, context).criterion("nope", ImpossibleCriterion.Conditions())

    fun initialiser(input: Advancement.Builder, context: DirectoryContext<Unit>, parameters: Unit): Advancement {
        val name = "${context.tags[0]}/${context.tags.getOrNull(1) ?: context.name}"
        return LCCData.advancements.add(input, context.id)
    }

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}