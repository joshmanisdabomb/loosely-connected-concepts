package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.SapphireAltarBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties

object SapphireAltarBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val altar = LCCModelTemplates.template_sapphire_altar.upload(idh.loc(entry), TextureMap().put(TextureKey.FRONT, idh.locSuffix(entry, "front")).put(TextureKey.SIDE, idh.loc(entry)).put(TextureKey.INSIDE, idh.locSuffix(entry, "top")).put(TextureKey.BOTTOM, idh.locSuffix(entry, "gems")).put(TextureKey.PARTICLE, idh.loc(entry)), data.models)
        val key = LCCModelTemplates.template_sapphire_altar_key.upload(idh.locSuffix(entry, "key"), TextureMap.texture(idh.loc(LCCBlocks.rusted_iron_blocks.values.last())), data.models)
        stateMultipart(data, entry) {
            horizontalDirections.forEach {
                with(When.create().set(Properties.HORIZONTAL_FACING, it), BlockStateVariant.create().put(VariantSettings.MODEL, altar).apply(ModelProvider.horizontalRotation(it)))
                SapphireAltarBlock.SapphireState.values().filter { it != SapphireAltarBlock.SapphireState.EMPTY }.forEach { s ->
                    val texture = TextureMap.texture(idh.locSuffix(entry, if (s == SapphireAltarBlock.SapphireState.DULL) "dull" else "gems"))
                    val tl = LCCModelTemplates.template_sapphire_altar_tl.upload(idh.locSuffix(entry, "tl").suffix(if (s == SapphireAltarBlock.SapphireState.DULL) "dull" else null), texture, data.models)
                    val tr = LCCModelTemplates.template_sapphire_altar_tr.upload(idh.locSuffix(entry, "tr").suffix(if (s == SapphireAltarBlock.SapphireState.DULL) "dull" else null), texture, data.models)
                    val middle = LCCModelTemplates.template_sapphire_altar_middle.upload(idh.locSuffix(entry, "middle").suffix(if (s == SapphireAltarBlock.SapphireState.DULL) "dull" else null), texture, data.models)
                    val bl = LCCModelTemplates.template_sapphire_altar_bl.upload(idh.locSuffix(entry, "bl").suffix(if (s == SapphireAltarBlock.SapphireState.DULL) "dull" else null), texture, data.models)
                    val br = LCCModelTemplates.template_sapphire_altar_br.upload(idh.locSuffix(entry, "br").suffix(if (s == SapphireAltarBlock.SapphireState.DULL) "dull" else null), texture, data.models)
                    with(When.create().set(Properties.HORIZONTAL_FACING, it).set(SapphireAltarBlock.tl, s), BlockStateVariant.create().put(VariantSettings.MODEL, tl).apply(ModelProvider.horizontalRotation(it)))
                    with(When.create().set(Properties.HORIZONTAL_FACING, it).set(SapphireAltarBlock.tr, s), BlockStateVariant.create().put(VariantSettings.MODEL, tr).apply(ModelProvider.horizontalRotation(it)))
                    with(When.create().set(Properties.HORIZONTAL_FACING, it).set(SapphireAltarBlock.middle, s), BlockStateVariant.create().put(VariantSettings.MODEL, middle).apply(ModelProvider.horizontalRotation(it)))
                    with(When.create().set(Properties.HORIZONTAL_FACING, it).set(SapphireAltarBlock.bl, s), BlockStateVariant.create().put(VariantSettings.MODEL, bl).apply(ModelProvider.horizontalRotation(it)))
                    with(When.create().set(Properties.HORIZONTAL_FACING, it).set(SapphireAltarBlock.br, s), BlockStateVariant.create().put(VariantSettings.MODEL, br).apply(ModelProvider.horizontalRotation(it)))
                }
                with(When.create().set(Properties.HORIZONTAL_FACING, it).set(SapphireAltarBlock.key, true), BlockStateVariant.create().put(VariantSettings.MODEL, key).apply(ModelProvider.horizontalRotation(it)))
            }
        }
    }

}
