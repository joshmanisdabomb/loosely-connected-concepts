package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RoadBlock
import com.joshmanisdabomb.lcc.block.model.ConnectedTextureModel
import com.joshmanisdabomb.lcc.block.model.LCCModel
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.client.util.math.Vector3f
import net.minecraft.util.Identifier

object LCCModels : ThingDirectory<LCCModel, (String) -> String>(), ModelResourceProvider {

    val test_block_5 by create { ConnectedTextureModel("test_block_5") }
    val road_full by createMap(*RoadBlock.Companion.RoadMarkings.values().filter { it != RoadBlock.Companion.RoadMarkings.NONE }.toTypedArray(), propertySupplier = { rm -> { "block/road_full_${rm.asString()}" } }) { rm, name, properties -> ConnectedTextureModel("road", connector = LCCBlocks.road::connector, innerSeams = false, pos2 = Vector3f(1f, 1f, 1f)) { top("road_top") } }
    val road_path by createMap(*RoadBlock.Companion.RoadMarkings.values().filter { it != RoadBlock.Companion.RoadMarkings.NONE }.toTypedArray(), propertySupplier = { rm -> { "block/road_path_${rm.asString()}" } }) { rm, name, properties -> ConnectedTextureModel("road", connector = LCCBlocks.road::connector, innerSeams = false, pos2 = Vector3f(1f, 0.9375f, 1f)) { top("road_top") } }
    val road_half by createMap(*RoadBlock.Companion.RoadMarkings.values().filter { it != RoadBlock.Companion.RoadMarkings.NONE }.toTypedArray(), propertySupplier = { rm -> { "block/road_half_${rm.asString()}" } }) { rm, name, properties -> ConnectedTextureModel("road", connector = LCCBlocks.road::connector, innerSeams = false, pos2 = Vector3f(1f, 0.4375f, 1f)) { top("road_top") } }
    val road_full_inner by createMap(*RoadBlock.Companion.RoadMarkings.values().filter { it != RoadBlock.Companion.RoadMarkings.NONE }.toTypedArray(), propertySupplier = { rm -> { "block/road_full_inner_${rm.asString()}" } }) { rm, name, properties -> ConnectedTextureModel("road", connector = { world, state, pos, other, otherPos, path -> LCCBlocks.road.connector(world, state, pos, other, otherPos, path, true) }, innerSeams = false, borderSize = 7, pos2 = Vector3f(1f, 1f, 1f)) { top("road_marked") } }
    val road_path_inner by createMap(*RoadBlock.Companion.RoadMarkings.values().filter { it != RoadBlock.Companion.RoadMarkings.NONE }.toTypedArray(), propertySupplier = { rm -> { "block/road_path_inner_${rm.asString()}" } }) { rm, name, properties -> ConnectedTextureModel("road", connector = { world, state, pos, other, otherPos, path -> LCCBlocks.road.connector(world, state, pos, other, otherPos, path, true) }, innerSeams = false, borderSize = 7, pos2 = Vector3f(1f, 0.9375f, 1f)) { top("road_marked") } }
    val road_half_inner by createMap(*RoadBlock.Companion.RoadMarkings.values().filter { it != RoadBlock.Companion.RoadMarkings.NONE }.toTypedArray(), propertySupplier = { rm -> { "block/road_half_inner_${rm.asString()}" } }) { rm, name, properties -> ConnectedTextureModel("road", connector = { world, state, pos, other, otherPos, path -> LCCBlocks.road.connector(world, state, pos, other, otherPos, path, true) }, innerSeams = false, borderSize = 7, pos2 = Vector3f(1f, 0.4375f, 1f)) { top("road_marked") } }

    val models by lazy { all.mapKeys { (k, _) -> allProperties[k]!!(k) } }

    override fun init(predicate: (name: String, properties: (String) -> String) -> Boolean) {
        super.init(predicate)
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { LCCModels }
    }

    override fun getDefaultProperty() = { path: String -> "block/$path" }

    override fun loadModelResource(id: Identifier, context: ModelProviderContext): UnbakedModel? {
        if (id.namespace == LCC.modid && models.containsKey(id.path)) {
            return models[id.path]!!
        }
        return null
    }

}