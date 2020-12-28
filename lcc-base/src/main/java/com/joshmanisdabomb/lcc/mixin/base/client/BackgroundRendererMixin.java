package com.joshmanisdabomb.lcc.mixin.base.client;

import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Shadow
    private static float red;
    @Shadow
    private static float green;
    @Shadow
    private static float blue;

    /* FIXME Changing in 1.17
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", shift = At.Shift.BY, by = -4))
    private static void changeFogColor(Camera camera, float partialTicks, ClientWorld world, int i, float f, CallbackInfo info) {
        if (fluidState && this.pos.y < (double)((float)this.blockPos.getY() + fluidState.getHeight(this.area, this.blockPos)) instanceof LCCExtendedFluid) {
            Float[] colors = ((LCCExtendedFluid) submergedFluidState).lcc_fogColor();
            if (colors != null) {
                red = colors[0];
                green = colors[1];
                blue = colors[2];
            }
        }
    }

    @Inject(method = "applyFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"), cancellable = true)
    private static void changeFogDensity(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo info) {
        Object submergedFluidState = camera.getSubmergedFluidState().getFluid();
        if (submergedFluidState instanceof LCCExtendedFluid) {
            Float density = ((LCCExtendedFluid)submergedFluidState).lcc_fogDensity();
            if (density != null) {
                RenderSystem.fogDensity(density);
                info.cancel();
            }
        }
    }*/

}
