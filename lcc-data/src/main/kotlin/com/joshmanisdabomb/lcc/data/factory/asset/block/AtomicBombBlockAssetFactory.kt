package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.AtomicBombBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

object AtomicBombBlockAssetFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val id = loc(entry)
        stateVariant(data, entry) {
            coordinate(BlockStateVariantMap.create(AtomicBombBlock.segment, Properties.HORIZONTAL_FACING).register { segment, dir ->
                BlockStateVariant.create().put(VariantSettings.MODEL, when (segment) {
                    AtomicBombBlock.AtomicBombSegment.HEAD -> LCCModelTemplates.template_atomic_bomb_head.upload(suffix(id, "head"), Texture().put(LCCModelTextureKeys.t2, suffix(id, "tail")).put(LCCModelTextureKeys.t5, suffix(id, "head")).put(TextureKey.PARTICLE, suffix(id, "tail")), data.modelStates::addModel)
                    AtomicBombBlock.AtomicBombSegment.TAIL -> LCCModelTemplates.template_atomic_bomb_tail.upload(suffix(id, "tail"), Texture().put(LCCModelTextureKeys.t1, suffix(id, "tail_side")).put(LCCModelTextureKeys.t2, suffix(id, "tail")).put(LCCModelTextureKeys.t3, suffix(id, "fin")).put(LCCModelTextureKeys.t4, suffix(id, "core")).put(TextureKey.PARTICLE, suffix(id, "tail")), data.modelStates::addModel)
                    else -> LCCModelTemplates.template_atomic_bomb_middle.upload(suffix(id, "middle"), Texture().put(LCCModelTextureKeys.t2, suffix(id, "tail")).put(LCCModelTextureKeys.t5, suffix(id, "head")).put(TextureKey.PARTICLE, suffix(id, "tail")), data.modelStates::addModel)
                }).apply(HorizontalBlockAssetFactory.defaultDirections[Direction.NORTH]!![dir]!!)
            })
        }
    }

}
