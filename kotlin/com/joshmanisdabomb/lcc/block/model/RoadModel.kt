package com.joshmanisdabomb.lcc.block.model

import com.joshmanisdabomb.lcc.block.RoadBlock
import net.minecraft.client.util.math.Vector3f

class RoadModel(rm: RoadBlock.Companion.RoadMarkings, height: Float) : ConnectedTextureModel("road", pos2 = Vector3f(1f, height, 1f), mapConsumer = { top("road_top") }) {



}