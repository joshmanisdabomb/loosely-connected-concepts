package com.joshmanisdabomb.lcc.misc;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.IStringSerializable;

public abstract class Colors {

    public enum ClassicDyeColor implements IStringSerializable {

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

        public final MaterialColor mapColor;

        ClassicDyeColor(MaterialColor mapColor) {
            this.mapColor = mapColor;
        }

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }

        @Override
        public String toString() {
            return this.getName();
        }

    }

    public enum AlternateDyeColor implements IStringSerializable {

        CINNABAR(MaterialColor.ORANGE_TERRACOTTA),
        MAROON(MaterialColor.NETHERRACK),
        BRICK(MaterialColor.RED_TERRACOTTA),
        TAN(MaterialColor.WHITE_TERRACOTTA),
        GOLD(MaterialColor.WOOD),
        LIGHT_GREEN(MaterialColor.EMERALD),
        MINT(MaterialColor.GRASS),
        TURQUOISE(MaterialColor.DIAMOND),
        NAVY(MaterialColor.BLUE),
        INDIGO(MaterialColor.BLUE_TERRACOTTA),
        LAVENDER(MaterialColor.ICE),
        LIGHT_PURPLE(MaterialColor.MAGENTA),
        HOT_PINK(MaterialColor.PINK),
        BURGUNDY(MaterialColor.PURPLE_TERRACOTTA),
        ROSE(MaterialColor.PINK_TERRACOTTA),
        DARK_GRAY(MaterialColor.BLACK_TERRACOTTA);

        public final MaterialColor mapColor;

        AlternateDyeColor(MaterialColor mapColor) {
            this.mapColor = mapColor;
        }

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }

        @Override
        public String toString() {
            return this.getName();
        }

    }

}