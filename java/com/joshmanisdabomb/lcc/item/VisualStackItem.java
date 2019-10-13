package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class VisualStackItem extends Item {

    public VisualStackItem(int maxStack, Properties p) {
        super(p);
        this.addPropertyOverride(new ResourceLocation(LCC.MODID, "visual_stack"), (stack, world, entity) -> MathHelper.clamp((stack.getCount() - 1) / ((float)maxStack - 1), 0, 1));
    }

}
