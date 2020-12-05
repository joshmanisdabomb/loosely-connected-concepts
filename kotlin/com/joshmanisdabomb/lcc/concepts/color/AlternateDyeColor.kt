package com.joshmanisdabomb.lcc.concepts.color

import net.minecraft.block.MapColor
import net.minecraft.block.MapColor.*

enum class AlternateDyeColor(override val lcc_mapColor: MapColor, override val lcc_color: Int) : LCCExtendedDyeColor {

    CINNABAR(ORANGE_TERRACOTTA, 0xE12C00),
    MAROON(NETHER, 0x7C1212),
    BRICK(RED_TERRACOTTA, 0x7C362B),
    TAN(WHITE_TERRACOTTA, 0xFFB27F),
    GOLD(WOOD, 0xB59300),
    LIGHT_GREEN(EMERALD, 0x00E121),
    MINT(GRASS, 0x7FFFB8),
    TURQUOISE(DIAMOND, 0x18E1F0),
    NAVY(BLUE, 0x122699),
    INDIGO(BLUE_TERRACOTTA, 0x21007F),
    LAVENDER(ICE, 0xA17FFF),
    LIGHT_PURPLE(MAGENTA, 0xAA31DE),
    HOT_PINK(PINK, 0xDE007C),
    BURGUNDY(PURPLE_TERRACOTTA, 0x840046),
    ROSE(PINK_TERRACOTTA, 0xFF7A7A),
    DARK_GRAY(BLACK_TERRACOTTA, 0x3A3A3A);

    override val lcc_colorComponents = LCCExtendedDyeColor.getComponents(lcc_color)

    override val lcc_fireworkColor = lcc_color
    override val lcc_signColor = lcc_color

    override val lcc_dye = null

}