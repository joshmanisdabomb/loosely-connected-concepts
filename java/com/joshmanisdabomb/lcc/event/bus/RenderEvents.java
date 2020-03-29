package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.block.MultipartBlock;
import com.joshmanisdabomb.lcc.item.render.GauntletRenderer;
import com.joshmanisdabomb.lcc.registry.LCCFluids;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvents {

    public static final GauntletRenderer GAUNTLET = new GauntletRenderer();

    @SubscribeEvent
    public void onHandEvent(RenderHandEvent e) {
        Minecraft mc = Minecraft.getInstance();

        if (e.getHand() == Hand.MAIN_HAND && e.getItemStack().getItem() == LCCItems.gauntlet) {
            GlStateManager.pushMatrix();
            boolean flag = mc.player.getPrimaryHand() != HandSide.LEFT;
            float f = flag ? 1.0F : -1.0F;
            float f1 = MathHelper.sqrt(e.getSwingProgress());
            float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
            float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
            float f4 = -0.4F * MathHelper.sin(e.getSwingProgress() * (float)Math.PI);
            GlStateManager.translatef(f * (f2 + 0.64000005F), f3 + -0.6F + e.getEquipProgress() * -0.6F, f4 + -0.71999997F);
            GlStateManager.rotatef(f * 45.0F, 0.0F, 1.0F, 0.0F);
            float f5 = MathHelper.sin(e.getSwingProgress() * e.getSwingProgress() * (float)Math.PI);
            float f6 = MathHelper.sin(f1 * (float)Math.PI);
            GlStateManager.rotatef(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);
            AbstractClientPlayerEntity abstractclientplayerentity = mc.player;
            mc.getTextureManager().bindTexture(abstractclientplayerentity.getLocationSkin());
            GlStateManager.translatef(f * -1.0F, 3.6F, 3.5F);
            GlStateManager.rotatef(f * 120.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotatef(200.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(f * -135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translatef(f * 5.6F, 0.0F, 0.0F);

            PlayerRenderer pr = (PlayerRenderer)mc.getRenderManager().<AbstractClientPlayerEntity>getRenderer(mc.player);
            if (flag) pr.renderRightArm(e.getMatrixStack(), e.getBuffers(), e.getLight(), mc.player);
            else pr.renderLeftArm(e.getMatrixStack(), e.getBuffers(), e.getLight(), mc.player);

            GlStateManager.scalef(1.0F, -1.0F, -1.0F);
            GlStateManager.translatef(f * -0.425f, -1, 0);
            GAUNTLET.render(e.getItemStack(), e.getMatrixStack(), e.getBuffers(), e.getLight(), OverlayTexture.NO_OVERLAY);

            e.setCanceled(true);
            GlStateManager.popMatrix();
        }
    }

    @SubscribeEvent
    public void onBlockHighlight(DrawHighlightEvent e) {
        if (e.getTarget().getType() != RayTraceResult.Type.BLOCK) return;

        BlockRayTraceResult target = (BlockRayTraceResult)e.getTarget();
        BlockPos pos = target.getPos();
        ClientWorld world = Minecraft.getInstance().world;
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof MultipartBlock && world.getWorldBorder().contains(pos)) {
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param);
            GlStateManager.lineWidth(Math.max(2.5F, (float)Minecraft.getInstance().getMainWindow().getFramebufferWidth() / 1920.0F * 2.5F));
            GlStateManager.disableTexture();
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(1.0F, 1.0F, 0.999F);
            double offsetX = e.getInfo().getProjectedView().x;
            double offsetY = e.getInfo().getProjectedView().y;
            double offsetZ = e.getInfo().getProjectedView().z;
            for (VoxelShape s : ((MultipartBlock)state.getBlock()).getPartsFromTrace(e.getTarget().getHitVec(), state, world, pos)) {
                WorldRenderer.drawVoxelShapeParts(e.getMatrix(), e.getBuffers().getBuffer(RenderType.LINES), s, pos.getX() - offsetX, pos.getY() - offsetY, pos.getZ() - offsetZ, 0, 0, 0, 0.4F);
            }
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture();
            GlStateManager.disableBlend();

            e.setCanceled(true);
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onFogColors(EntityViewRenderEvent.FogColors e) {
        ActiveRenderInfo r = e.getRenderer().getActiveRenderInfo();
        if (r.getFluidState().isTagged(LCCFluids.OIL)) {
            e.setRed(0.02F);
            e.setGreen(0.02F);
            e.setBlue(0.02F);
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onFogDensity(EntityViewRenderEvent.FogDensity e) {
        ActiveRenderInfo r = e.getRenderer().getActiveRenderInfo();
        if (r.getFluidState().isTagged(LCCFluids.OIL)) {
            GlStateManager.fogMode(GlStateManager.FogMode.EXP2.param);
            e.setDensity(0.7F);
            e.setCanceled(true);
        }
    }

}