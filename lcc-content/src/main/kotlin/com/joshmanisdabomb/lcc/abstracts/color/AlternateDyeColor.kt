package com.joshmanisdabomb.lcc.abstracts.color

import net.minecraft.block.MapColor
import net.minecraft.block.MapColor.*

enum class AlternateDyeColor(override val lcc_mapColor: MapColor, override val lcc_color: Int) : LCCExtendedDyeColor {

    CINNABAR(TERRACOTTA_ORANGE, 0xE12C00),
    MAROON(DARK_RED, 0x7C1212),
    BRICK(TERRACOTTA_RED, 0x7C362B),
    TAN(TERRACOTTA_WHITE, 0xFFB27F),
    GOLD(OAK_TAN, 0xB59300),
    LIGHT_GREEN(EMERALD_GREEN, 0x00E121),
    MINT(PALE_GREEN, 0x7FFFB8),
    TURQUOISE(DIAMOND_BLUE, 0x18E1F0),
    NAVY(BLUE, 0x122699),
    INDIGO(TERRACOTTA_BLUE, 0x21007F),
    LAVENDER(PALE_PURPLE, 0xA17FFF),
    LIGHT_PURPLE(MAGENTA, 0xAA31DE),
    HOT_PINK(PINK, 0xDE007C),
    BURGUNDY(TERRACOTTA_PURPLE, 0x840046),
    ROSE(TERRACOTTA_PINK, 0xFF7A7A),
    DARK_GRAY(TERRACOTTA_BLACK, 0x3A3A3A);

    override val lcc_colorComponents = LCCExtendedDyeColor.getComponents(lcc_color)

    override val lcc_fireworkColor = lcc_color
    override val lcc_signColor = lcc_color

    override val lcc_dye = null

}