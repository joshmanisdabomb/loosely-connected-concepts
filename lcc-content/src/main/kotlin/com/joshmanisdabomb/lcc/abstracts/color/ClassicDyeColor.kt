package com.joshmanisdabomb.lcc.abstracts.color

import net.minecraft.block.MapColor
import net.minecraft.block.MapColor.*

enum class ClassicDyeColor(override val lcc_mapColor: MapColor, override val lcc_color: Int, override val plasticColor: Int) : LCCExtendedDyeColor {

    RED(BRIGHT_RED, 0xD63030, 0xFF0000),
    ORANGE(MapColor.ORANGE, 0xD78330, 0xFF7700),
    YELLOW(GOLD, 0xD7D730, 0xFFFF00),
    LIME(MapColor.LIME, 0x84D830, 0x66FF00),
    GREEN(EMERALD_GREEN, 0x30D830, 0x00FF00),
    TURQUOISE(PALE_GREEN, 0x30D884, 0x22FFAA),
    AQUA(DIAMOND_BLUE, 0x30D8D8, 0x00FFFF),
    LIGHT_BLUE(MapColor.LIGHT_BLUE, 0x669ED8, 0x0077FF),
    LAVENDER(PALE_PURPLE, 0x7575D8, 0xBB88FF),
    PURPLE(MapColor.PURPLE, 0x8430D8, 0x7700FF),
    LIGHT_PURPLE(MapColor.MAGENTA, 0xA947D8, 0xAA33FF),
    MAGENTA(MapColor.MAGENTA, 0xD730D7, 0xFF00FF),
    HOT_PINK(PINK, 0xD63083, 0xDD0077),
    GRAY(MapColor.GRAY, 0x707070, 0x777777),
    LIGHT_GRAY(MapColor.LIGHT_GRAY, 0x9D9D9D, 0xBBBBBB),
    WHITE(OFF_WHITE, 0xD4D4D4, 0xFFFFFF);

    override val lcc_colorComponents = LCCExtendedDyeColor.getComponents(lcc_color)

    override val lcc_fireworkColor = lcc_color
    override val lcc_signColor = lcc_color

    override val lcc_dye = null

}