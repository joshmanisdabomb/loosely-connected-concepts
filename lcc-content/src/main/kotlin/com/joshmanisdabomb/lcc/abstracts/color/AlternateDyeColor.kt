package com.joshmanisdabomb.lcc.abstracts.color

import net.minecraft.block.MapColor
import net.minecraft.block.MapColor.*

enum class AlternateDyeColor(override val lcc_mapColor: MapColor, override val lcc_color: Int, override val plasticColor: Int) : LCCExtendedDyeColor {

    CINNABAR(TERRACOTTA_ORANGE, 0xE12C00, 0xFF3C00),
    MAROON(DARK_RED, 0x7C1212, 0x770000),
    BRICK(TERRACOTTA_RED, 0x7C362B, 0xBB6655),
    TAN(TERRACOTTA_WHITE, 0xFFB27F, 0xFFCC88),
    GOLD(OAK_TAN, 0xB59300, 0xCCAA00),
    LIGHT_GREEN(EMERALD_GREEN, 0x00E121, 0x55FF55),
    MINT(PALE_GREEN, 0x7FFFB8, 0x77FFCC),
    TURQUOISE(DIAMOND_BLUE, 0x18E1F0, 0x33CCCC),
    NAVY(BLUE, 0x122699, 0x000077),
    INDIGO(TERRACOTTA_BLUE, 0x21007F, 0x220088),
    LAVENDER(PALE_PURPLE, 0xA17FFF, 0xBB88FF),
    LIGHT_PURPLE(MAGENTA, 0xAA31DE, 0xAA33FF),
    HOT_PINK(PINK, 0xDE007C, 0xDD0077),
    BURGUNDY(TERRACOTTA_PURPLE, 0x840046, 0x770033),
    ROSE(TERRACOTTA_PINK, 0xFF7A7A, 0xFF7777),
    DARK_GRAY(TERRACOTTA_BLACK, 0x3A3A3A, 0x333333);

    override val lcc_colorComponents = LCCExtendedDyeColor.getComponents(lcc_color)

    override val lcc_fireworkColor = lcc_color
    override val lcc_signColor = lcc_color

    override val lcc_dye = null

}