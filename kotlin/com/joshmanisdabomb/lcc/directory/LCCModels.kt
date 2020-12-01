package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.model.ConnectedTextureModel
import com.joshmanisdabomb.lcc.block.model.LCCModel
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.render.model.UnbakedModel
import net.minecraft.util.Identifier

object LCCModels : ThingDirectory<LCCModel, (String) -> String>(), ModelResourceProvider {

    val test_block_5 by create { ConnectedTextureModel("test_block_5") }

    val models by lazy { all.mapKeys { (k, _) -> allProperties[k]!!(k) } }

    override fun init(predicate: (name: String, properties: (String) -> String) -> Boolean) {
        super.init(predicate)
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { LCCModels }
    }

    override fun getDefaultProperty() = { path: String -> "block/$path" }

    override fun loadModelResource(id: Identifier, context: ModelProviderContext): UnbakedModel? {
        if (id.namespace == LCC.modid && models.containsKey(id.path)) return models[id.path]!!
        return null
    }

}