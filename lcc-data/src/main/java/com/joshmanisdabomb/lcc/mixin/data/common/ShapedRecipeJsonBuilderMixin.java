package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShapedRecipeJsonBuilder.class)
public class ShapedRecipeJsonBuilderMixin {

    @Redirect(method = "offerTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getGroup()Lnet/minecraft/item/ItemGroup;"))
    public ItemGroup offerNonnullableGroup(Item item) {
        ItemGroup group = item.getGroup();
        return group != null ? group : ItemGroup.MISC;
    }

}