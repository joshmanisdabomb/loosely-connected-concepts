package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RoadBlock
import com.joshmanisdabomb.lcc.block.model.ClassicCryingObsidianModel
import com.joshmanisdabomb.lcc.block.model.ConnectedTextureModel
import com.joshmanisdabomb.lcc.block.model.RoadModel
import com.joshmanisdabomb.lcc.lib.block.model.LCCModel
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3f

object LCCModels : BasicDirectory<LCCModel, Unit>(), ModelResourceProvider {

    val test_block_5 by entry(::initialiser) { ConnectedTextureModel(id) }

    val road_full by entryMap(::initialiser, *RoadBlock.Companion.RoadMarkings.values()) { RoadModel(it, false, 1f) }
        .setInstanceNameSupplier { name, key -> "road_full${key.suffix()}" }
    val road_path by entryMap(::initialiser, *RoadBlock.Companion.RoadMarkings.values()) { RoadModel(it, false, 0.9375f) }
        .setInstanceNameSupplier { name, key -> "road_path${key.suffix()}" }
    val road_half by entryMap(::initialiser, *RoadBlock.Companion.RoadMarkings.values()) { RoadModel(it, false, 0.4375f) }
        .setInstanceNameSupplier { name, key -> "road_half${key.suffix()}" }
    val road_full_inner by entryMap(::initialiser, *RoadBlock.Companion.RoadMarkings.values()) { RoadModel(it, true, 1f) }
        .setInstanceNameSupplier { name, key -> "road_full_inner${key.suffix()}" }
    val road_path_inner by entryMap(::initialiser, *RoadBlock.Companion.RoadMarkings.values()) { RoadModel(it, true, 0.9375f) }
        .setInstanceNameSupplier { name, key -> "road_path_inner${key.suffix()}" }
    val road_half_inner by entryMap(::initialiser, *RoadBlock.Companion.RoadMarkings.values()) { RoadModel(it, true, 0.4375f) }
        .setInstanceNameSupplier { name, key -> "road_half_inner${key.suffix()}" }

    val solar_panel by entry(::initialiser) { ConnectedTextureModel(LCC.id("solar_panel"), { _, state, _, other, _, _ -> other.isOf(state.block) }, 2, pos2 = Vec3f(1f, 5/16f, 1f)) { this.exclude(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, replacement = LCC.id("block/solar_panel_side")).exclude(Direction.DOWN, replacement = LCC.id("block/solar_panel_bottom")) } }

    val classic_crying_obsidian by entry(::initialiser) { ClassicCryingObsidianModel() }

    val polished_fortstone by entry(::initialiser) { ConnectedTextureModel(id) }

    private val models by lazy { entries.map { (k, v) -> Identifier(v.context.id.namespace, "${v.tags.firstOrNull() ?: "block"}/${v.context.id.path}") to v.entry }.toMap() }

    private fun <M : LCCModel> initialiser(input: M, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out LCCModel, out LCCModel>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { LCCModels }
    }

    override fun loadModelResource(id: Identifier, context: ModelProviderContext): UnbakedModel? {
        if (models.containsKey(id)) {
            return models[id]!!
        }
        return null
    }

}