package com.joshmanisdabomb.lcc.concepts.color

import net.minecraft.block.MapColor
import net.minecraft.block.MapColor.*

enum class ClassicDyeColor(override val lcc_mapColor: MapColor, override val lcc_color: Int) : LCCExtendedDyeColor {

    RED(LAVA, 0xD63030),
    ORANGE(MapColor.ORANGE, 0xD78330),
    YELLOW(GOLD, 0xD7D730),
    LIME(MapColor.LIME, 0x84D830),
    GREEN(EMERALD, 0x30D830),
    TURQUOISE(GRASS, 0x30D884),
    AQUA(DIAMOND, 0x30D8D8),
    LIGHT_BLUE(MapColor.LIGHT_BLUE, 0x669ED8),
    LAVENDER(ICE, 0x7575D8),
    PURPLE(MapColor.PURPLE, 0x8430D8),
    LIGHT_PURPLE(MapColor.MAGENTA, 0xA947D8),
    MAGENTA(MapColor.MAGENTA, 0xD730D7),
    HOT_PINK(PINK, 0xD63083),
    GRAY(MapColor.GRAY, 0x707070),
    LIGHT_GRAY(MapColor.LIGHT_GRAY, 0x9D9D9D),
    WHITE(QUARTZ, 0xD4D4D4);

    override val lcc_colorComponents = LCCExtendedDyeColor.getComponents(lcc_color)

    override val lcc_fireworkColor = lcc_color
    override val lcc_signColor = lcc_color

    override val lcc_dye = null

}