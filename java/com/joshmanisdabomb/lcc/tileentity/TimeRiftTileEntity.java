package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.recipe.TimeRiftRecipe;
import com.joshmanisdabomb.lcc.registry.LCCRecipes;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class TimeRiftTileEntity extends TileEntity implements ITickableTileEntity {

    public static final double RADIUS = 5.0F;

    public float size = 1.0F;
    public float lastSize = size;

    private List<TimeRiftRecipe> recipes;

    public TimeRiftTileEntity() {
        super(LCCTileEntities.time_rift);
    }

    @Override
    public void tick() {
        this.lastSize = this.size;
        this.size = Math.max(1.0F, this.size * 0.94F);

        this.recipes = world.getRecipeManager().getRecipes(LCCRecipes.TIME_RIFT, null, world);

        List<ItemEntity> entities = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).grow(5));
        for (ItemEntity e : entities) {
            TimeRiftRecipe recipe = this.getRecipe(e.getItem());
            if (recipe == null) continue;
            TimeRiftRecipe recipe2 = this.getRecipe(recipe.getRecipeOutput().copy());

            double distsq = Math.sqrt(e.getPositionVec().squareDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
            double s = ((RADIUS*RADIUS)-distsq) * 0.012F;
            double speed = s * s * (recipe2 == null ? 1 : MathHelper.clamp((e.ticksExisted - 50) * 0.09, 0, 1));

            if ((recipe2 == null || e.ticksExisted > 70) && distsq < 0.9) {
                this.receiveItem(e, recipe);
                this.size = 3F;
            } else if (distsq <= RADIUS * RADIUS) {
                this.size = (float)Math.max(1 + (speed * 4F), this.size);
                e.setMotion(e.getMotion().add(e.getPositionVec().subtract(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).normalize().mul(-speed, -speed, -speed)));
            }
        }
    }

    private void receiveItem(ItemEntity e, TimeRiftRecipe recipe) {
        e.getItem().shrink(1);
        if (e.getItem().isEmpty()) e.remove();

        ItemEntity result = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, recipe.getRecipeOutput().copy());
        result.setMotion(result.getMotion().mul(2.2F, 2.2F, 2.2F));
        result.setDefaultPickupDelay();
        world.addEntity(result);
    }

    private TimeRiftRecipe getRecipe(ItemStack is) {
        for (TimeRiftRecipe r : this.recipes) {
            if (r.matches(is)) return r;
        }
        return null;
    }

}
