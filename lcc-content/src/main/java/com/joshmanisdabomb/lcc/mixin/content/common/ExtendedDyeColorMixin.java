package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor;
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
            int g = (int)(cols[1] * 255);
            int b = (int)(cols[2] * 255);
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

    @NotNull
    @Override
    public int getPlasticColor() {
        DyeColor color = (DyeColor)(Object)this;
        if (color == DyeColor.WHITE) return 0xFFFFFF;
        else if (color == DyeColor.ORANGE) return 0xFF7700;
        else if (color == DyeColor.MAGENTA) return 0xFF00FF;
        else if (color == DyeColor.LIGHT_BLUE) return 0x00AAFF;
        else if (color == DyeColor.YELLOW) return 0xFFFF00;
        else if (color == DyeColor.LIME) return 0x00FF00;
        else if (color == DyeColor.PINK) return 0xFF55BB;
        else if (color == DyeColor.GRAY) return 0x777777;
        else if (color == DyeColor.LIGHT_GRAY) return 0xBBBBBB;
        else if (color == DyeColor.CYAN) return 0x00FFFF;
        else if (color == DyeColor.PURPLE) return 0x7700FF;
        else if (color == DyeColor.BLUE) return 0x0000FF;
        else if (color == DyeColor.BROWN) return 0x773300;
        else if (color == DyeColor.GREEN) return 0x007700;
        else if (color == DyeColor.RED) return 0xFF0000;
        else if (color == DyeColor.BLACK) return 0x000000;
        throw new IllegalStateException("Unknown vanilla dye color!");
    }

}