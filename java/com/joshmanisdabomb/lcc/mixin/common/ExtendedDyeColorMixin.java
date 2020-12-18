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

    @Shadow
    public abstract float[] getColorComponents();

    @Shadow
    public abstract int getFireworkColor();

    @Shadow
    @Final
    public int signColor;

    @Shadow
    public abstract MapColor getMapColor();

    private int lcc_intColor = -1;

    @Override
    public int getLcc_color() {
        if (lcc_intColor == -1) {
            float[] cols = getColorComponents();
            int r = (int)(cols[0] * 255);
            int g = (int)(cols[0] * 255);
            int b = (int)(cols[0] * 255);
            lcc_intColor = b + (g << 8) + (r << 16);
        }
        return lcc_intColor;
    }

    @NotNull
    @Override
    public float[] getLcc_colorComponents() {
        return getColorComponents();
    }

    @Override
    public int getLcc_signColor() {
        return signColor;
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