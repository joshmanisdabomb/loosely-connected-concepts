package com.joshmanisdabomb.lcc.misc;

import com.joshmanisdabomb.lcc.creative2.Creative2GroupKey;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraft.util.IStringSerializable;

import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public interface ExtendedDyeColor extends IStringSerializable, Creative2GroupKey {

    int getColorValue();

    default float[] getComponents(int color) {
        int i = (color & 16711680) >> 16;
        int j = (color & '\uff00') >> 8;
        int k = color & 255;
        return new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
    }

    @Override
    default float[] getSelectionColor() {
        float[] c = getColorValueComponents();
        return new float[]{c[0], c[1], c[2], 1.0F};
    }

    float[] getColorValueComponents();

    int getTextColor();

    int getFireworkColor();

    MaterialColor getMapColor();

    String name();

    @Override
    default String getName() {
        return this.name().toLowerCase();
    }

    enum ClassicDyeColor implements ExtendedDyeColor {

        RED(MaterialColor.TNT, 0xD63030),
        ORANGE(MaterialColor.ADOBE, 0xD78330),
        YELLOW(MaterialColor.GOLD, 0xD7D730),
        LIME(MaterialColor.LIME, 0x84D830),
        GREEN(MaterialColor.EMERALD, 0x30D830),
        TURQUOISE(MaterialColor.GRASS, 0x30D884),
        AQUA(MaterialColor.DIAMOND, 0x30D8D8),
        LIGHT_BLUE(MaterialColor.LIGHT_BLUE, 0x669ED8),
        LAVENDER(MaterialColor.ICE, 0x7575D8),
        PURPLE(MaterialColor.PURPLE, 0x8430D8),
        LIGHT_PURPLE(MaterialColor.MAGENTA, 0xA947D8),
        MAGENTA(MaterialColor.MAGENTA, 0xD730D7),
        HOT_PINK(MaterialColor.PINK, 0xD63083),
        GRAY(MaterialColor.GRAY, 0x707070),
        LIGHT_GRAY(MaterialColor.LIGHT_GRAY, 0x9D9D9D),
        WHITE(MaterialColor.QUARTZ, 0xD4D4D4);

        public final MaterialColor mapColor;
        public final int colorValue;
        private final float[] colorValueComponents;

        ClassicDyeColor(MaterialColor mapColor, int colorValue) {
            this.mapColor = mapColor;
            this.colorValue = colorValue;
            this.colorValueComponents = this.getComponents(colorValue);
        }

        @Override
        public int getColorValue() {
            return this.colorValue;
        }

        @Override
        public float[] getColorValueComponents() {
            return this.colorValueComponents;
        }

        @Override
        public int getTextColor() {
            return this.colorValue; //
        }

        @Override
        public int getFireworkColor() {
            return this.colorValue; //
        }

        @Override
        public MaterialColor getMapColor() {
            return this.mapColor;
        }

        @Override
        public String toString() {
            return this.getName();
        }

    }

    enum AlternateDyeColor implements ExtendedDyeColor {

        CINNABAR(MaterialColor.ORANGE_TERRACOTTA, 0xE12C00),
        MAROON(MaterialColor.NETHERRACK, 0x7C1212),
        BRICK(MaterialColor.RED_TERRACOTTA, 0x7C362B),
        TAN(MaterialColor.WHITE_TERRACOTTA, 0xFFB27F),
        GOLD(MaterialColor.WOOD, 0xB59300),
        LIGHT_GREEN(MaterialColor.EMERALD, 0x00E121),
        MINT(MaterialColor.GRASS, 0x7FFFB8),
        TURQUOISE(MaterialColor.DIAMOND, 0x18E1F0),
        NAVY(MaterialColor.BLUE, 0x122699),
        INDIGO(MaterialColor.BLUE_TERRACOTTA, 0x21007F),
        LAVENDER(MaterialColor.ICE, 0xA17FFF),
        LIGHT_PURPLE(MaterialColor.MAGENTA, 0xAA31DE),
        HOT_PINK(MaterialColor.PINK, 0xDE007C),
        BURGUNDY(MaterialColor.PURPLE_TERRACOTTA, 0x840046),
        ROSE(MaterialColor.PINK_TERRACOTTA, 0xFF7A7A),
        DARK_GRAY(MaterialColor.BLACK_TERRACOTTA, 0x3A3A3A);

        public final MaterialColor mapColor;
        public final int colorValue;
        private final float[] colorValueComponents;

        AlternateDyeColor(MaterialColor mapColor, int colorValue) {
            this.mapColor = mapColor;
            this.colorValue = colorValue;
            this.colorValueComponents = this.getComponents(colorValue);
        }

        @Override
        public int getColorValue() {
            return this.colorValue;
        }

        @Override
        public float[] getColorValueComponents() {
            return this.colorValueComponents;
        }

        @Override
        public int getTextColor() {
            return this.colorValue; //
        }

        @Override
        public int getFireworkColor() {
            return this.colorValue; //
        }

        @Override
        public MaterialColor getMapColor() {
            return this.mapColor;
        }

        @Override
        public String toString() {
            return this.getName();
        }

    }

    enum CompoundDyeColor implements ExtendedDyeColor {

        WHITE(DyeColor.WHITE, ClassicDyeColor.WHITE),
        ORANGE(DyeColor.ORANGE, ClassicDyeColor.ORANGE),
        MAGENTA(DyeColor.MAGENTA, ClassicDyeColor.MAGENTA),
        LIGHT_BLUE(DyeColor.LIGHT_BLUE, ClassicDyeColor.LIGHT_BLUE),
        YELLOW(DyeColor.YELLOW, ClassicDyeColor.YELLOW),
        LIME(DyeColor.LIME, ClassicDyeColor.LIME),
        PINK(DyeColor.PINK, null),
        GRAY(DyeColor.GRAY, null),
        LIGHT_GRAY(DyeColor.LIGHT_GRAY, ClassicDyeColor.LIGHT_GRAY),
        CYAN(DyeColor.CYAN, null),
        PURPLE(DyeColor.PURPLE, ClassicDyeColor.PURPLE),
        BLUE(DyeColor.BLUE, null),
        BROWN(DyeColor.BROWN, null),
        GREEN(DyeColor.GREEN, null),
        RED(DyeColor.RED, ClassicDyeColor.RED),
        BLACK(DyeColor.BLACK, null),
        CINNABAR(AlternateDyeColor.CINNABAR, null),
        MAROON(AlternateDyeColor.MAROON, null),
        BRICK(AlternateDyeColor.BRICK, null),
        TAN(AlternateDyeColor.TAN, null),
        GOLD(AlternateDyeColor.GOLD, null),
        LIGHT_GREEN(AlternateDyeColor.LIGHT_GREEN, ClassicDyeColor.GREEN),
        MINT(AlternateDyeColor.MINT, ClassicDyeColor.AQUA),
        TURQUOISE(AlternateDyeColor.TURQUOISE, ClassicDyeColor.TURQUOISE),
        NAVY(AlternateDyeColor.NAVY, null),
        INDIGO(AlternateDyeColor.INDIGO, null),
        LAVENDER(AlternateDyeColor.LAVENDER, ClassicDyeColor.LAVENDER),
        LIGHT_PURPLE(AlternateDyeColor.LIGHT_PURPLE, ClassicDyeColor.LIGHT_PURPLE),
        HOT_PINK(AlternateDyeColor.HOT_PINK, ClassicDyeColor.HOT_PINK),
        BURGUNDY(AlternateDyeColor.BURGUNDY, null),
        ROSE(AlternateDyeColor.ROSE, null),
        DARK_GRAY(AlternateDyeColor.DARK_GRAY, ClassicDyeColor.GRAY);

        public final IStringSerializable dyeColor;
        public final ClassicDyeColor classic;

        CompoundDyeColor(IStringSerializable color, ClassicDyeColor classic) {
            this.dyeColor = color;
            this.classic = classic;
        }

        private int get(ToIntFunction<DyeColor> from, ToIntFunction<ExtendedDyeColor> fromExtended) {
            return dyeColor instanceof DyeColor ? from.applyAsInt((DyeColor)dyeColor) : fromExtended.applyAsInt((ExtendedDyeColor)dyeColor);
        }

        private <T> T get(Function<DyeColor, T> from, Function<ExtendedDyeColor, T> fromExtended) {
            return dyeColor instanceof DyeColor ? from.apply((DyeColor)dyeColor) : fromExtended.apply((ExtendedDyeColor)dyeColor);
        }

        public int getColorValue() {
            return get(DyeColor::getColorValue, ExtendedDyeColor::getColorValue);
        }

        @Override
        public float[] getColorValueComponents() { return get(DyeColor::getColorComponentValues, ExtendedDyeColor::getColorValueComponents); }

        @Override
        public int getTextColor() {
            return get(DyeColor::getTextColor, ExtendedDyeColor::getTextColor);
        }

        @Override
        public int getFireworkColor() {
            return get(DyeColor::getFireworkColor, ExtendedDyeColor::getFireworkColor);
        }

        @Override
        public MaterialColor getMapColor() {
            return get(DyeColor::getMapColor, ExtendedDyeColor::getMapColor);
        }

        @Override
        public Object getGroupKey(Set<?> groupKeys) {
            if (groupKeys.stream().allMatch(k -> k instanceof DyeColor)) return this.dyeColor;
            return this;
        }

        @Override
        public String toString() {
            return this.getName();
        }

        public static CompoundDyeColor get(DyeColor c) {
            return CompoundDyeColor.values()[c.ordinal()];
        }

        public static CompoundDyeColor get(AlternateDyeColor c) {
            return CompoundDyeColor.values()[c.ordinal() + DyeColor.values().length];
        }

    }

}