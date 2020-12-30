package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class ParticleBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        stateOne(data, entry) { if (texture != null) modelParticle(data, entry, texture = texture) else modelParticle(data, entry) }
    }

    companion object : ParticleBlockAssetFactory()

}