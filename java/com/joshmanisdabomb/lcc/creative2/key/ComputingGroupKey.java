package com.joshmanisdabomb.lcc.creative2.key;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.creative2.Creative2GroupKey;
import com.joshmanisdabomb.lcc.item.ComputingItem;
import com.joshmanisdabomb.lcc.item.StorageItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

public class ComputingGroupKey implements Creative2GroupKey {

    public final int levelIndex;
    private final float[] color;

    public ComputingGroupKey(ItemStack is) {
        ComputingItem item = (ComputingItem)is.getItem();
        this.levelIndex = item.getLevelIndex(is);
        ArrayList<StorageInfo.Partition> parts = new StorageInfo(is).getPartitions();
        if (parts.stream().anyMatch(p -> p.type.isOS())) {
            int colorValue = parts.get(0).type.colorValue;
            this.color = new float[]{((colorValue & 0xFF0000) >> 16) / 255F, ((colorValue & 0x00FF00) >> 8) / 255F, (colorValue & 0x0000FF) / 255F, 1.0F};
        } else {
            float percentComputing = !(item instanceof StorageItem) ? (float) this.levelIndex / (item.levels.length - 1) : 0;
            float percentStorage = item instanceof StorageItem ? (float) this.levelIndex / (item.levels.length - 1) : 0;
            this.color = new float[]{0.19607843F + (0.6f * percentComputing), 0.19607843F + (0.4392157f * percentComputing), 0.19607843f + (0.4392157f * percentStorage), 1.0F};
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float[] getSelectionColor() {
        return this.color;
    }

}
