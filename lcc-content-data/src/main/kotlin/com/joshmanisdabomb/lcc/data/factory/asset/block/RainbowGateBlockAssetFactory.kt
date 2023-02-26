package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.RainbowGateBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import net.minecraft.block.Block
import net.minecraft.data.client.*

object RainbowGateBlockAssetFactory : BlockAssetFactory {

    val symbols = arrayOf(
        LCCModelTemplates.template_rainbow_gate_symbol_1,
        LCCModelTemplates.template_rainbow_gate_symbol_2,
        LCCModelTemplates.template_rainbow_gate_symbol_3,
        LCCModelTemplates.template_rainbow_gate_symbol_4,
        LCCModelTemplates.template_rainbow_gate_symbol_5,
        LCCModelTemplates.template_rainbow_gate_symbol_6,
        LCCModelTemplates.template_rainbow_gate_symbol_7,
        LCCModelTemplates.template_rainbow_gate_symbol_8
    )

    override fun apply(data: DataAccessor, entry: Block) {
        val base = LCCModelTemplates.template_rainbow_gate.upload(idh.loc(entry), TextureMap.texture(entry).put(TextureKey.END, idh.locSuffix(entry, "end")), data.models)
        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, base))
            symbols.forEachIndexed { k, v ->
                with(When.create().set(RainbowGateBlock.symbol, k.plus(1)), BlockStateVariant().put(VariantSettings.MODEL, v.upload(idh.locSuffix(entry, k.plus(1).toString()), TextureMap.texture(idh.locSuffix(entry, "symbols")), data.models)))
            }
        }
    }

}
