package com.joshmanisdabomb.lcc.misc;

import net.minecraft.block.material.MaterialColor;

public enum ClassicDyeColor {

    RED(MaterialColor.TNT),
    ORANGE(MaterialColor.ADOBE),
    YELLOW(MaterialColor.GOLD),
    LIME(MaterialColor.LIME),
    GREEN(MaterialColor.EMERALD),
    TURQUOISE(MaterialColor.GRASS),
    AQUA(MaterialColor.DIAMOND),
    LIGHT_BLUE(MaterialColor.LIGHT_BLUE),
    LAVENDER(MaterialColor.ICE),
    PURPLE(MaterialColor.PURPLE),
    LIGHT_PURPLE(MaterialColor.MAGENTA),
    MAGENTA(MaterialColor.MAGENTA),
    HOT_PINK(MaterialColor.PINK),
    GRAY(MaterialColor.GRAY),
    LIGHT_GRAY(MaterialColor.LIGHT_GRAY),
    WHITE(MaterialColor.QUARTZ);

    private final MaterialColor map;

    ClassicDyeColor(MaterialColor map) {
        this.map = map;
    }
}