package com.joshmanisdabomb.lcc.mixin.base.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedItem;
import com.joshmanisdabomb.lcc.event.DamageEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class ImplLivingMixin extends Entity {

    private final HashMap<BlockPos, BlockState> lcc_onEntitySingleJumpOff_positions = new HashMap<>();

    public ImplLivingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("HEAD"), cancellable = true)
    public void swing(Hand hand, boolean sendToAll, CallbackInfo info) {
        ItemStack stack = ((LivingEntity)(Object)this).getStackInHand(hand);
        if (!stack.isEmpty() && ((LCCExtendedItem)stack.getItem()).lcc_onEntitySwing(stack, ((LivingEntity)(Object)this))) {
            info.cancel();
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", shift = At.Shift.BEFORE), ordinal = 0)
    private float setDamageAmount(float amount, DamageSource source) {
        Float f = DamageEntityCallback.EVENT.invoker().modifyDamage((LivingEntity)(Object)this, source, amount, amount);
        return f == null ? -1 : Math.max(f, 0);
    }

    @Inject(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"), cancellable = true)
    private void cancelDamage(DamageSource source, float amount, CallbackInfo info) {
        if (amount == -999) info.cancel();
    }

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;jump()V", at = @At("HEAD"), cancellable = true)
    public void singleBlockJump(CallbackInfo info) {
        Box box = this.getBoundingBox();
        BlockPos pos1 = new BlockPos(box.minX + 0.001D, box.minY - 0.01D, box.minZ + 0.001D);
        BlockPos pos2 = new BlockPos(box.maxX - 0.001D, box.minY + 0.01D, box.maxZ - 0.001D);
        BlockPos.Mutable bp = new BlockPos.Mutable();
        if (this.world.isRegionLoaded(pos1, pos2)) {
            lcc_onEntitySingleJumpOff_positions.clear();
            for(int i = pos1.getX(); i <= pos2.getX(); ++i) {
                for(int j = pos1.getY(); j <= pos2.getY(); ++j) {
                    for(int k = pos1.getZ(); k <= pos2.getZ(); ++k) {
                        bp.set(i, j, k);
                        lcc_onEntitySingleJumpOff_positions.put(bp.toImmutable(), this.world.getBlockState(bp)); }
                }
            }

            if (!lcc_onEntitySingleJumpOff_positions.isEmpty()) {
                try {
                    Set<Block> uniqueValues = new HashSet<>();
                    for (BlockState blockState : lcc_onEntitySingleJumpOff_positions.values()) {
                        Block block = blockState.getBlock();
                        if (block instanceof LCCExtendedBlock && uniqueValues.add(block)) {
                            if (((LCCExtendedBlock) block).lcc_onEntitySingleJumpOff(world, lcc_onEntitySingleJumpOff_positions.entrySet().stream().filter(e -> e.getValue().isOf(block)).map(Map.Entry::getKey).toArray(BlockPos[]::new), lcc_onEntitySingleJumpOff_positions.values().stream().filter(state -> state.isOf(block)).toArray(BlockState[]::new), (LivingEntity)(Object)this)) {
                                info.cancel();
                                return;
                            }
                        }
                    }
                } catch (Throwable var12) {
                    CrashReport crashReport = CrashReport.create(var12, "LCC: Single colliding entity with blocks");
                    throw new CrashException(crashReport);
                }
            }
        }
    }

}
