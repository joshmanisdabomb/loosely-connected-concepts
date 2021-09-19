package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties

object SaltBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateMultipart(data, entry) {
            with(getVariantsList(data, entry, 1, LCCModelTemplates.template_salt_1_1, LCCModelTemplates.template_salt_1_2, LCCModelTemplates.template_salt_1_3))
            with(When.create().set(Properties.LEVEL_3, 2), getVariantsList(data, entry, 2, LCCModelTemplates.template_salt_2_1, LCCModelTemplates.template_salt_2_2, LCCModelTemplates.template_salt_2_3))
            with(When.create().set(Properties.LEVEL_3, 3), getVariantsList(data, entry, 3, LCCModelTemplates.template_salt_3_1, LCCModelTemplates.template_salt_3_2))
        }
    }

    private fun getVariantsList(data: DataAccessor, entry: Block, level: Int, vararg variants: Model) = variants.flatMapIndexed { i, m ->
        val model = m.upload(idh.locSuffix(entry, "${level}_${i.plus(1)}"), Texture.texture(LCC.block("salt")), data.modelStates::addModel)
        VariantSettings.Rotation.values().map {
            BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.Y, it)
        }
    }

}
