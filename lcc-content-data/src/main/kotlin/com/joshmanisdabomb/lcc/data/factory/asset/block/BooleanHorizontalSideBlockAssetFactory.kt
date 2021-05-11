package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import com.joshmanisdabomb.lcc.extensions.booleanProperty
import net.minecraft.block.Block
import net.minecraft.data.client.model.*
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

class BooleanHorizontalSideBlockAssetFactory(val on: Identifier, val off: Identifier, val end: Identifier, val particle: Identifier = off) : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val base = LCCModelTemplates.template_face_up_down.upload(idh.loc(entry), Texture.texture(end).put(TextureKey.PARTICLE, particle), data.modelStates::addModel)
        val off = map.mapValues { (k, v) -> v.upload(idh.locSuffix(entry, "_${k.asString()}"), Texture.texture(off), data.modelStates::addModel) }
        val on = map.mapValues { (k, v) -> v.upload(idh.locSuffix(entry, "_${k.asString()}_on"), Texture.texture(on), data.modelStates::addModel) }
        stateMultipart(data, entry) {
            with(BlockStateVariant.create().put(VariantSettings.MODEL, base))
            map.keys.forEach {
                with(When.create().set(it.booleanProperty, false), BlockStateVariant().put(VariantSettings.MODEL, off[it]))
                with(When.create().set(it.booleanProperty, true), BlockStateVariant().put(VariantSettings.MODEL, on[it]))
            }
        }
    }

    companion object {
        val map = mapOf(Direction.NORTH to LCCModelTemplates.template_face_north, Direction.EAST to LCCModelTemplates.template_face_east, Direction.SOUTH to LCCModelTemplates.template_face_south, Direction.WEST to LCCModelTemplates.template_face_west)
    }

}
