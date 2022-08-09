package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.TooltipConstants;
import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor;
import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class ContainedPlayerMixin extends LivingEntity {

    protected ContainedPlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract Iterable<ItemStack> getArmorItems();

    @Shadow
    @Final
    private PlayerAbilities abilities;

    @Inject(at = @At("HEAD"), method = "canConsume", cancellable = true)
    public void blockEating(boolean ignoreHunger, CallbackInfoReturnable<Boolean> info) {
        Iterable<ItemStack> pieces = getArmorItems();
        for (ItemStack piece : pieces) {
            Item item = piece.getItem();
            if (item instanceof ContainedArmor) {
                if (((ContainedArmor)item).blockEating((PlayerEntity)(Object)this, piece, pieces)) {
                    ((PlayerEntity)(Object)this).sendMessage(Text.translatable(TooltipConstants.contained_armor_consume), true);
                    info.setReturnValue(false);
                    info.cancel();
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "addExhaustion")
    public void activity(float exhaustion, CallbackInfo info) {
        if (!this.abilities.invulnerable) {
            Iterable<ItemStack> pieces = getArmorItems();
            for (ItemStack piece : pieces) {
                Item item = piece.getItem();
                if (item instanceof ContainedArmor && item instanceof OxygenStorage) {
                    if (!((ContainedArmor)item).hasFullSuit(piece, pieces)) continue;
                    ((OxygenStorage)item).addOxygen(piece, -exhaustion * 10f);
                }
            }
        }
    }

}
