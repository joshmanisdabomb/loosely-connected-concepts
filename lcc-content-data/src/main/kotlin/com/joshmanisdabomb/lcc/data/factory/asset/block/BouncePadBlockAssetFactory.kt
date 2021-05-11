package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.BouncePadBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object BouncePadBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val textures = Texture().put(LCCModelTextureKeys.t0, idh.locSuffix(entry, "base_h")).put(LCCModelTextureKeys.t1, idh.locSuffix(entry, "inner")).put(LCCModelTextureKeys.t2, idh.locSuffix(entry, "setting")).put(LCCModelTextureKeys.t3, idh.locSuffix(entry, "base_v")).put(TextureKey.PARTICLE, idh.locSuffix(entry, "base_v"))
        val bounce_pad = LCCModelTemplates.template_bounce_pad.upload(idh.loc(entry), textures, data.modelStates::addModel)
        val bounce_pad_settings = listOf(
            LCCModelTemplates.template_bounce_pad_0.upload(idh.locSuffix(entry, "0"), textures, data.modelStates::addModel),
            LCCModelTemplates.template_bounce_pad_1.upload(idh.locSuffix(entry, "1"), textures, data.modelStates::addModel),
            LCCModelTemplates.template_bounce_pad_2.upload(idh.locSuffix(entry, "2"), textures, data.modelStates::addModel),
            LCCModelTemplates.template_bounce_pad_3.upload(idh.locSuffix(entry, "3"), textures, data.modelStates::addModel),
            LCCModelTemplates.template_bounce_pad_4.upload(idh.locSuffix(entry, "4"), textures, data.modelStates::addModel)
        )

        stateMultipart(data, entry) {
            Direction.values().forEach {
                with(When.create().set(Properties.FACING, it), BlockStateVariant.create().put(VariantSettings.MODEL, bounce_pad).apply(ModelProvider.directionalRotation(it)))
                BouncePadBlock.SETTING.values.forEach { i ->
                    with(When.create().set(Properties.FACING, it).set(BouncePadBlock.SETTING, i), BlockStateVariant.create().put(VariantSettings.MODEL, bounce_pad_settings[i]).apply(ModelProvider.directionalRotation(it)))
                }
            }
        }
    }

}