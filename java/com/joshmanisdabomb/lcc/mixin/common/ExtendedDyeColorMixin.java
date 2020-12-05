package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.concepts.color.LCCExtendedDyeColor;
import net.minecraft.block.MapColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DyeColor.class)
public abstract class ExtendedDyeColorMixin implements LCCExtendedDyeColor {

    @Shadow @Final
    private int color;

    @Shadow
    public abstract float[] getColorComponents();

    @Shadow
    public abstract int getFireworkColor();

    @Shadow
    public abstract int getSignColor();

    @Shadow
    public abstract MapColor getMapColor();

    @Override
    public int getLcc_color() {
        return color;
    }

    @NotNull
    @Override
    public float[] getLcc_colorComponents() {
        return getColorComponents();
    }

    @Override
    public int getLcc_signColor() {
        return getSignColor();
    }

    @Override
    public int getLcc_fireworkColor() {
        return getFireworkColor();
    }

    @NotNull
    @Override
    public MapColor getLcc_mapColor() {
        return getMapColor();
    }

    @NotNull
    @Override
    public Item getLcc_dye() {
        return DyeItem.byColor((DyeColor)(Object)this);
    }

}