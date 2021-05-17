package com.joshmanisdabomb.lcc.abstracts.types

import net.minecraft.block.MapColor

enum class WoodType(val mapColor: MapColor) {

    OAK(MapColor.OAK_TAN),
    SPRUCE(MapColor.SPRUCE_BROWN),
    BIRCH(MapColor.PALE_YELLOW),
    JUNGLE(MapColor.DIRT_BROWN),
    ACACIA(MapColor.ORANGE),
    DARK_OAK(MapColor.BROWN),
    RUBBER(MapColor.TERRACOTTA_WHITE),
    CRIMSON(MapColor.DULL_PINK),
    WARPED(MapColor.DARK_AQUA);

}