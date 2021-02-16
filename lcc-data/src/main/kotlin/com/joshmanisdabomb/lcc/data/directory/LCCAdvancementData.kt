package com.joshmanisdabomb.lcc.data.directory

import com.google.common.collect.Sets
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.advancement.NuclearExplosionCriterion
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.directory.*
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementFrame
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

object LCCAdvancementData : ThingDirectory<Advancement, Pair<String, String?>>(), DataProvider {

    val main_root by createWithNameProperties("main" to "root") { n, p -> Advancement.Task.create().display(LCCBlocks.test_block, p.first, null, toast = false, chat = false).has(LCCItems.ruby).has(LCCItems.topaz_shard).has(Items.EMERALD).has(Items.DIAMOND).has(LCCItems.sapphire).has(Items.AMETHYST_SHARD).criteriaMerger(CriterionMerger.OR).translation("Loosely Connected Concepts", "Main progression line of Loosely Connected Concepts", "en_us", p.first, p.second ?: n).build(n, p) }
        val spawner_table by createWithNameProperties("main" to null) { n, p -> Advancement.Task.create().parent(main_root).display(LCCBlocks.spawner_table, p.first, n).criterion("place", PlacedBlockCriterion.Conditions.block(LCCBlocks.spawner_table)).translation("Next Level Crafting", "Convert a mob spawner into a crafting station", "en_us", p.first, p.second ?: n).build(n, p) }
            val simulation_fabric by createWithNameProperties("main" to null) { n, p -> Advancement.Task.create().parent(spawner_table).display(LCCItems.simulation_fabric, p.first, n).has(LCCItems.simulation_fabric).translation("Enter the Matrix", "Craft the Simulation Fabric in an Arcane Table", "en_us", p.first, p.second ?: n).build(n, p) }
                val ruby by createWithNameProperties("main" to null) { n, p -> Advancement.Task.create().parent(simulation_fabric).display(LCCItems.ruby, p.first, n).has(LCCItems.ruby).has(LCCBlocks.ruby_ore).has(LCCBlocks.ruby_block).criteriaMerger(CriterionMerger.OR).translation("What Could Have Been", "Obtain rubies by throwing emeralds into the time rift", "en_us", p.first, p.second ?: n).build(n, p) }
            val rainbow_portal by createWithNameProperties("main" to null) { n, p -> emptyAdvancement(spawner_table, n, p) } //into the rainbowverse
            //IDEA complete nether reactor

        val topaz by createWithNameProperties("main" to null) { n, p -> Advancement.Task.create().parent(main_root).display(LCCItems.topaz_shard, p.first, n).has(LCCItems.topaz_shard).translation("Reskinned Amethyst", "Collect topaz from a volcanic geode near a lava lake", "en_us", p.first, p.second ?: n).build(n, p) }

        val refiner by createWithNameProperties("main" to null) { n, p -> Advancement.Task.create().parent(main_root).display(LCCBlocks.refiner, p.first, n).has(LCCBlocks.refiner).translation("A Refined Palate", "The industrial revolution and its consequences have been a disaster for the human race", "en_us", p.first, p.second ?: n).build(n, p) }
            val uranium by createWithNameProperties("main" to null) { n, p -> Advancement.Task.create().parent(refiner).display(LCCItems.enriched_uranium, p.first, n).has(LCCTags.enriched_uranium, "has_enriched_uranium").criteriaMerger(CriterionMerger.OR).translation("Enrichment Activities", "Refine the uranium into a different shade of green", "en_us", p.first, p.second ?: n).build(n, p) }
                val nuke by createWithNameProperties("main" to null) { n, p -> Advancement.Task.create().parent(refiner).display(LCCBlocks.atomic_bomb, p.first, n).criterion("detonate", NuclearExplosionCriterion.Conditions.uranium(NumberRange.IntRange.ANY)).criteriaMerger(CriterionMerger.OR).translation("The World is the Problem", "Detonate an atomic bomb", "en_us", p.first, p.second ?: n).build(n, p) }
                    val nuke_first by createWithNameProperties("main" to null/*hidden*/) { n, p -> emptyAdvancement(nuke, n, p) } //nuclear arms race, be the first on a multiplayer server to detonate a nuclear device
                    val winter_survive by createWithNameProperties("main" to null) { n, p -> emptyAdvancement(nuke, n, p) } //the struggle was real, return to winter level 0 after being at winter level 5

    val wasteland_root by createWithNameProperties("wasteland" to "root") { n, p -> Advancement.Task.create().display(LCCBlocks.cracked_mud, p.first, null, toast = false, chat = false).criterion("enter", LocationArrivalCriterion.Conditions.create(LocationPredicate.biome(LCCBiomes.getRegistryKey(LCCBiomes.wasteland)))).translation("LCC: Wasteland", "Perambulate into the haze of the wasteland", "en_us", p.first, p.second ?: n).build(n, p) }
        val oil by createWithNameProperties("wasteland" to null) { n, p -> Advancement.Task.create().parent(wasteland_root).display(LCCItems.oil_bucket, p.first, n).has(LCCItems.oil_bucket).translation("Another Mod With Oil", "Daring today aren't we?", "en_us", p.first, p.second ?: n).build(n, p) }
            val asphalt by createWithNameProperties("wasteland" to null) { n, p -> Advancement.Task.create().parent(oil).display(LCCItems.asphalt_bucket, p.first, n).has(LCCItems.asphalt_bucket).translation("Paving the Way", "Refine oil and gravel into paving mixture", "en_us", p.first, p.second ?: n).build(n, p) }
                //IDEA walk 200m in a straight line from point a without leaving road - all roads lead to home / paved with good intentions
        val sapphire by createWithNameProperties("wasteland" to null) { n, p -> Advancement.Task.create().parent(wasteland_root).display(LCCItems.sapphire, p.first, n).has(LCCItems.sapphire).translation("Beauty Amongst the Unsightly", "Find a sapphire", "en_us", p.first, p.second ?: n).build(n, p) }

    private fun Advancement.Task.display(item: ItemConvertible, category: String, id: String? = null, frame: AdvancementFrame = AdvancementFrame.TASK, toast: Boolean = true, chat: Boolean = true, hidden: Boolean = false) = this.display(item, TranslatableText("advancements.lcc.$category.${id ?: "root"}.title"), TranslatableText("advancements.lcc.$category.${id ?: "root"}.description"), if (id != null) null else LCC.id("textures/gui/advancements/backgrounds/$category.png"), frame, toast, chat, hidden)

    private fun Advancement.Task.translation(title: String, description: String, locale: String, category: String, id: String? = null): Advancement.Task {
        LCCData.accessor.lang[locale]!!["advancements.lcc.$category.${id ?: "root"}.title"] = title
        LCCData.accessor.lang[locale]!!["advancements.lcc.$category.${id ?: "root"}.description"] = description
        return this
    }

    private fun Advancement.Task.has(item: ItemConvertible) = this.criterion(Registry.ITEM.getId(item.asItem()).path, InventoryChangedCriterion.Conditions.items(item))
    private fun Advancement.Task.has(tag: Tag<Item>, name: String = "has_"+Registry.ITEM.getId(tag.values().first()).path) = this.criterion(name, InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(tag).build()))

    private fun Advancement.Task.build(path: String, properties: Pair<String, String?>) = build(LCC.id("${properties.first}/${properties.second ?: path}"))

    private fun emptyAdvancement(parent: Advancement?, n: String, p: Pair<String, String?>) = Advancement.Task.create().apply { if (parent != null) parent(parent) }.display(Items.AIR, p.first, n).criterion("nope", ImpossibleCriterion.Conditions()).build(n, p)

    override fun init(predicate: (name: String, properties: Pair<String, String?>) -> Boolean) {
        super.init(predicate)
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
        all.forEach { (k, v) ->
            val properties = allProperties[k]!!
            v.createTask().apply { findParent { if (it.namespace == "lcc") LCCAdvancementData.all.toList().firstOrNull { (k2, v2) -> val p = allProperties[k2] ?: return@firstOrNull false; it.path == "${p.first}/${p.second ?: k2}" }?.second else null } }.build(consumer, LCC.id("${properties.first}/${properties.second ?: k}").toString())
        }
    }

    override fun getName() = LCCData.accessor.modid + " Advancements"

}