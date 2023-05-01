package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCStructurePieceTrait;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.StructureWeightSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureWeightSampler.class)
public class StructureWeightSamplerMixin {

    @Redirect(method = "method_42694", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/StructurePiece;getBoundingBox()Lnet/minecraft/util/math/BlockBox;"))
    private static BlockBox replaceBox(StructurePiece piece) {
        BlockBox original = piece.getBoundingBox();
        if (piece instanceof LCCStructurePieceTrait) {
            return ((LCCStructurePieceTrait)piece).getAdaptationBoundingBox(original);
        }
        return original;
    }

}
