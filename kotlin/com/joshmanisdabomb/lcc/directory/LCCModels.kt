package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RoadBlock
import com.joshmanisdabomb.lcc.block.model.ClassicCryingObsidianModel
import com.joshmanisdabomb.lcc.block.model.ConnectedTextureModel
import com.joshmanisdabomb.lcc.block.model.LCCModel
import com.joshmanisdabomb.lcc.block.model.RoadModel
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.util.Identifier

object LCCModels : ThingDirectory<LCCModel, (String) -> String>(), ModelResourceProvider {

    val test_block_5 by create { ConnectedTextureModel("test_block_5") }
    
    val road_full by createMap(*RoadBlock.Companion.RoadMarkings.values(), propertySupplier = { rm -> { "block/road_full${rm.suffix()}" } }) { rm, name, properties -> RoadModel(rm, false, 1f) }
    val road_path by createMap(*RoadBlock.Companion.RoadMarkings.values(), propertySupplier = { rm -> { "block/road_path${rm.suffix()}" } }) { rm, name, properties -> RoadModel(rm, false, 0.9375f) }
    val road_half by createMap(*RoadBlock.Companion.RoadMarkings.values(), propertySupplier = { rm -> { "block/road_half${rm.suffix()}" } }) { rm, name, properties -> RoadModel(rm, false, 0.4375f) }
    val road_full_inner by createMap(*RoadBlock.Companion.RoadMarkings.values(), propertySupplier = { rm -> { "block/road_full_inner${rm.suffix()}" } }) { rm, name, properties -> RoadModel(rm, true, 1f) }
    val road_path_inner by createMap(*RoadBlock.Companion.RoadMarkings.values(), propertySupplier = { rm -> { "block/road_path_inner${rm.suffix()}" } }) { rm, name, properties -> RoadModel(rm, true, 0.9375f) }
    val road_half_inner by createMap(*RoadBlock.Companion.RoadMarkings.values(), propertySupplier = { rm -> { "block/road_half_inner${rm.suffix()}" } }) { rm, name, properties -> RoadModel(rm, true, 0.4375f) }

    val classic_crying_obsidian by create { ClassicCryingObsidianModel() }

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