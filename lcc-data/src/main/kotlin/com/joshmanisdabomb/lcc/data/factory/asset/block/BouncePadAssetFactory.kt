package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.BouncePadBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.ModelTemplates
import com.joshmanisdabomb.lcc.data.directory.ModelTextureKeys
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object BouncePadAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        val textures = Texture().put(ModelTextureKeys.t0, suffix(id, "base_h")).put(ModelTextureKeys.t1, suffix(id, "inner")).put(ModelTextureKeys.t2, suffix(id, "setting")).put(ModelTextureKeys.t3, suffix(id, "base_v")).put(TextureKey.PARTICLE, suffix(id, "base_v"))
        val bounce_pad = ModelTemplates.template_bounce_pad_block.upload(id, textures, data.modelStates::addModel)
        val bounce_pad_settings = listOf(
            ModelTemplates.template_bounce_pad_0_block.upload(suffix(id, "0"), textures, data.modelStates::addModel),
            ModelTemplates.template_bounce_pad_1_block.upload(suffix(id, "1"), textures, data.modelStates::addModel),
            ModelTemplates.template_bounce_pad_2_block.upload(suffix(id, "2"), textures, data.modelStates::addModel),
            ModelTemplates.template_bounce_pad_3_block.upload(suffix(id, "3"), textures, data.modelStates::addModel),
            ModelTemplates.template_bounce_pad_4_block.upload(suffix(id, "4"), textures, data.modelStates::addModel)
        )

        stateMultipart(data, entry) {
            Direction.values().forEach {
                with(When.create().set(Properties.FACING, it), BlockStateVariant.create().put(VariantSettings.MODEL, bounce_pad).apply(DirectionalBlockAssetFactory.defaultDirections[Direction.UP]!![it]!!))
                BouncePadBlock.SETTING.values.forEach { i ->
                    with(When.create().set(Properties.FACING, it).set(BouncePadBlock.SETTING, i), BlockStateVariant.create().put(VariantSettings.MODEL, bounce_pad_settings[i]).apply(DirectionalBlockAssetFactory.defaultDirections[Direction.UP]!![it]!!))
                }
            }
        }
    }

}