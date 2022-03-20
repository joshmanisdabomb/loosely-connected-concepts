package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.LCCHooks;
import com.joshmanisdabomb.lcc.component.PistonCauseComponent;
import com.joshmanisdabomb.lcc.facade.piston.LCCPiston;
import com.joshmanisdabomb.lcc.facade.sign.LCCSign;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlockEntity.class)
public abstract class PistonBlockEntityMixin extends BlockEntity {

    public PistonBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @ModifyVariable(method = "pushEntities", at = @At(value = "STORE"), ordinal = 0)
    private static boolean slimePush(boolean original, World world, BlockPos pos, float f, PistonBlockEntity entity) {
        PistonCauseComponent component = LCCHooks.piston_cause_component_key.getNullable(entity);
        if (component != null) {
            LCCPiston base = component.getBase();
            if (base != null) {
                Boolean slimePush = base.slimePush(entity, world, pos, f);
                if (slimePush != null) {
                    return slimePush;
                }
            }
        }
        return original;
    }

}
