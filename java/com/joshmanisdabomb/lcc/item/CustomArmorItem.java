package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class CustomArmorItem extends ArmorItem {

    public CustomArmorItem(IArmorMaterial material, EquipmentSlotType slot, Item.Properties builder) {
        super(material, slot, builder);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return LCC.MODID + ":textures/models/armor/" + material.getName() + "_layer_" + (slot == EquipmentSlotType.LEGS ? 2 : 1) + (type == null ? "" : ("_" + type)) + ".png";
    }

}
