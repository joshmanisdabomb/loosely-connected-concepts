package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    @Redirect(method = "getFirstMatch", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;findFirst()Ljava/util/Optional;"))
    private <C extends Inventory, T extends Recipe<C>> Optional<T> sortRecipesForFirst(Stream<T> stream, RecipeType<T> type, C inventory, World world) {
        return stream.max(Comparator.comparingInt(recipe -> {
            ItemStack stack = recipe.getOutput();
            if (stack == null) return 0;
            Item item = stack.getItem();
            if (!(item instanceof LCCItemTrait)) return 0;
            return ((LCCItemTrait) item).lcc_recipeOutputPriority(stack, (RecipeManager)(Object)this, type, inventory, world);
        }));
    }

}
