package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.Identifier

open class StaticFireBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val t = texture ?: idh.loc(entry)
        val floors = (0..1).map { Models.TEMPLATE_FIRE_FLOOR.upload(idh.locSuffix(entry, "floor$it"), Texture().put(TextureKey.FIRE, t.suffix(it.toString())), data.models) }
        val sides = (0..1).map { Models.TEMPLATE_FIRE_SIDE.upload(idh.locSuffix(entry, "side$it"), Texture().put(TextureKey.FIRE, t.suffix(it.toString())), data.models) }
        val sideAlts = (0..1).map { Models.TEMPLATE_FIRE_SIDE_ALT.upload(idh.locSuffix(entry, "side_alt$it"), Texture().put(TextureKey.FIRE, t.suffix(it.toString())), data.models) }
        val sidesAll = sides + sideAlts
        stateMultipart(data, entry) {
            with(floors.map { BlockStateVariant.create().put(VariantSettings.MODEL, it) })
            VariantSettings.Rotation.values().forEach { r -> with(sidesAll.map { BlockStateVariant.create().put(VariantSettings.MODEL, it).put(VariantSettings.Y, r) }) }
        }
    }

    companion object : StaticFireBlockAssetFactory()

}