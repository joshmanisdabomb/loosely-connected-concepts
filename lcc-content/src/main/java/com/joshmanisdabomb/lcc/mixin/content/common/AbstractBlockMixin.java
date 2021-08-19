package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Inject(method = "calcBlockBreakingDelta", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    public void breakSpeedDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        if (!player.canHarvest(state)) return;
        ItemStack stack = player.getMainHandStack();
        float original = info.getReturnValueF();
        Float newDelta = null;
        for (ToolEffectivity t : ToolEffectivity.values()) {
            if (t.isEffective(state, stack)) {
                if (t.isTool(stack, state, true)) {
                    info.setReturnValue(original);
                    return;
                } else if (newDelta == null) {
                    float hardness = state.getHardness(world, pos);
                    float principal = 1.0f;
                    if (StatusEffectUtil.hasHaste(player)) principal *= 1.0F + (StatusEffectUtil.getHasteAmplifier(player) + 1f) * 0.2F;
                    StatusEffectInstance fatigue = player.getStatusEffect(StatusEffects.MINING_FATIGUE);
                    if (fatigue != null) {
                        switch (fatigue.getAmplifier()) {
                            case 0:
                                principal *= 0.3F;
                                break;
                            case 1:
                                principal *= 0.09F;
                                break;
                            case 2:
                                principal *= 0.0027F;
                                break;
                            default:
                                principal *= 8.1E-4F;
                        }
                    }
                    if (player.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(player)) principal /= 5.0F;
                    if (!player.isOnGround()) principal /= 5.0F;

                    principal /= hardness;
                    principal /= 30f;

                    float rate = original / principal;
                    newDelta = principal * MathHelper.fastInverseCbrt(1/rate) / 2f;
                }
            }
        }
        if (newDelta != null) {
            info.setReturnValue(newDelta);
        }
    }

}