package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.AtomicBombBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties

object AtomicBombBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(AtomicBombBlock.segment, Properties.HORIZONTAL_FACING).register { segment, dir ->
                BlockStateVariant.create().put(
                    VariantSettings.MODEL, when (segment) {
                    AtomicBombBlock.AtomicBombSegment.HEAD -> LCCModelTemplates.template_atomic_bomb_head.upload(idh.locSuffix(entry, "head"), TextureMap().put(LCCModelTextureKeys.t2, idh.locSuffix(entry, "tail")).put(LCCModelTextureKeys.t5, idh.locSuffix(entry, "head")).put(TextureKey.PARTICLE, idh.locSuffix(entry, "tail")), data.models)
                    AtomicBombBlock.AtomicBombSegment.TAIL -> LCCModelTemplates.template_atomic_bomb_tail.upload(idh.locSuffix(entry, "tail"), TextureMap().put(LCCModelTextureKeys.t1, idh.locSuffix(entry, "tail_side")).put(LCCModelTextureKeys.t2, idh.locSuffix(entry, "tail")).put(LCCModelTextureKeys.t3, idh.locSuffix(entry, "fin")).put(LCCModelTextureKeys.t4, idh.locSuffix(entry, "core")).put(TextureKey.PARTICLE, idh.locSuffix(entry, "tail")), data.models)
                    else -> LCCModelTemplates.template_atomic_bomb_middle.upload(idh.locSuffix(entry, "middle"), TextureMap().put(LCCModelTextureKeys.t2, idh.locSuffix(entry, "tail")).put(LCCModelTextureKeys.t5, idh.locSuffix(entry, "head")).put(TextureKey.PARTICLE, idh.locSuffix(entry, "tail")), data.models)
                }).apply(ModelProvider.horizontalRotation(dir))
            })
        }
    }

}