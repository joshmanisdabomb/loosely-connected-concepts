package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.Identifier

open class StaticFireBlockAssetFactory(val texture: Identifier? = null) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val t = texture ?: loc(entry)
        val floors = (0..1).map { Models.TEMPLATE_FIRE_FLOOR.upload(suffix(loc(entry), "floor$it"), Texture().put(TextureKey.FIRE, suffix(t, it.toString())), data.modelStates::addModel) }
        val sides = (0..1).map { Models.TEMPLATE_FIRE_SIDE.upload(suffix(loc(entry), "side$it"), Texture().put(TextureKey.FIRE, suffix(t, it.toString())), data.modelStates::addModel) }
        val sideAlts = (0..1).map { Models.TEMPLATE_FIRE_SIDE_ALT.upload(suffix(loc(entry), "side_alt$it"), Texture().put(TextureKey.FIRE, suffix(t, it.toString())), data.modelStates::addModel) }
        val sidesAll = sides + sideAlts
        stateMultipart(data, entry) {
            with(floors.map { BlockStateVariant.create().put(VariantSettings.MODEL, it) })
            VariantSettings.Rotation.values().forEach { r -> with(sidesAll.map { BlockStateVariant.create().put(VariantSettings.MODEL, it).put(VariantSettings.Y, r) }) }
        }
    }

    companion object : StaticFireBlockAssetFactory()

}