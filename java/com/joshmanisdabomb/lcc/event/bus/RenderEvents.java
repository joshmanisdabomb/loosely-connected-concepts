package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.block.MultipartBlock;
import com.joshmanisdabomb.lcc.capability.GauntletCapability;
import com.joshmanisdabomb.lcc.entity.NuclearExplosionEntity;
import com.joshmanisdabomb.lcc.entity.render.GauntletPlayerRenderer;
import com.joshmanisdabomb.lcc.functionality.NuclearFunctionality;
import com.joshmanisdabomb.lcc.item.render.GauntletRenderer;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.joshmanisdabomb.lcc.registry.LCCTags;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
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
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderEvents {

    public static final GauntletRenderer GAUNTLET = new GauntletRenderer();
    public static final Lazy<GauntletPlayerRenderer> GAUNTLET_PLAYER = Lazy.of(() -> new GauntletPlayerRenderer(Minecraft.getInstance().getRenderManager(), false));
    public static final Lazy<GauntletPlayerRenderer> GAUNTLET_PLAYER_SLIM = Lazy.of(() -> new GauntletPlayerRenderer(Minecraft.getInstance().getRenderManager(), true));

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre e) {
        if (!(e.getRenderer() instanceof GauntletPlayerRenderer)) {
            e.getPlayer().getCapability(GauntletCapability.Provider.DEFAULT_CAPABILITY).ifPresent(gauntlet -> {
                if (gauntlet.uppercutDuration > 0) {
                    (((AbstractClientPlayerEntity) e.getPlayer()).getSkinType().equals("slim") ? GAUNTLET_PLAYER_SLIM : GAUNTLET_PLAYER).get().render(
                        (AbstractClientPlayerEntity) e.getPlayer(),
                        e.getPlayer().rotationYaw,
                        e.getPartialRenderTick(),
                        e.getMatrixStack(),
                        e.getBuffers(),
                        e.getLight()
                    );
                    e.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public void onHandEvent(RenderHandEvent e) {
        Minecraft mc = Minecraft.getInstance();

        if (e.getHand() == Hand.MAIN_HAND && e.getItemStack().getItem() == LCCItems.gauntlet) {
            e.getMatrixStack().push();

            boolean flag = mc.player.getPrimaryHand() != HandSide.LEFT;

            float f = flag ? 1.0F : -1.0F;
            float f1 = MathHelper.sqrt(e.getSwingProgress());
            float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
            float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
            float f4 = -0.4F * MathHelper.sin(e.getSwingProgress() * (float)Math.PI);
            e.getMatrixStack().translate((double)(f * (f2 + 0.64000005F)), (double)(f3 + -0.6F + e.getEquipProgress() * -0.6F), (double)(f4 + -0.71999997F));
            e.getMatrixStack().rotate(Vector3f.YP.rotationDegrees(f * 45.0F));
            float f5 = MathHelper.sin(e.getSwingProgress() * e.getSwingProgress() * (float)Math.PI);
            float f6 = MathHelper.sin(f1 * (float)Math.PI);
            e.getMatrixStack().rotate(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
            e.getMatrixStack().rotate(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
            mc.getTextureManager().bindTexture(mc.player.getLocationSkin());
            e.getMatrixStack().translate((double)(f * -1.0F), (double)3.6F, 3.5D);
            e.getMatrixStack().rotate(Vector3f.ZP.rotationDegrees(f * 120.0F));
            e.getMatrixStack().rotate(Vector3f.XP.rotationDegrees(200.0F));
            e.getMatrixStack().rotate(Vector3f.YP.rotationDegrees(f * -135.0F));
            e.getMatrixStack().translate((double)(f * 5.6F), 0.0D, 0.0D);

            PlayerRenderer pr = (PlayerRenderer)mc.getRenderManager().<AbstractClientPlayerEntity>getRenderer(mc.player);
            if (flag) pr.renderRightArm(e.getMatrixStack(), e.getBuffers(), e.getLight(), mc.player);
            else pr.renderLeftArm(e.getMatrixStack(), e.getBuffers(), e.getLight(), mc.player);

            e.getMatrixStack().scale(1.0F, -1.0F, -1.0F);
            e.getMatrixStack().translate(f * -0.45f, -0.98f, -0.01f);
            e.getMatrixStack().rotate(Vector3f.XP.rotationDegrees(180.0F));
            e.getMatrixStack().rotate(Vector3f.ZP.rotationDegrees(f * 5.0F));
            e.getMatrixStack().rotate(Vector3f.YP.rotationDegrees(270.0F));

            GAUNTLET.renderHand(e.getItemStack(), e.getMatrixStack(), e.getBuffers(), e.getLight(), OverlayTexture.NO_OVERLAY);

            e.getMatrixStack().pop();

            e.setCanceled(true);
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
            double x = pos.getX() - e.getInfo().getProjectedView().x;
            double y = pos.getY() - e.getInfo().getProjectedView().y;
            double z = pos.getZ() - e.getInfo().getProjectedView().z;
            for (VoxelShape s : ((MultipartBlock)state.getBlock()).getPartsFromTrace(e.getTarget().getHitVec(), state, world, pos)) {
                Matrix4f matrix4f = e.getMatrix().getLast().getMatrix();
                IVertexBuilder buffer = e.getBuffers().getBuffer(RenderType.LINES);
                s.forEachEdge((x1, y1, z1, x2, y2, z2) -> {
                    buffer.pos(matrix4f, (float)(x1 + x), (float)(y1 + y), (float)(z1 + z)).color(0, 0, 0, 0.4F).endVertex();
                    buffer.pos(matrix4f, (float)(x2 + x), (float)(y2 + y), (float)(z2 + z)).color(0, 0, 0, 0.4F).endVertex();
                });
            }
            e.setCanceled(true);
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onFogColors(EntityViewRenderEvent.FogColors e) {
        ActiveRenderInfo r = e.getRenderer().getActiveRenderInfo();
        if (r.getFluidState().isTagged(LCCTags.OIL)) {
            e.setRed(0.02F);
            e.setGreen(0.02F);
            e.setBlue(0.02F);
        }

        float nukeSunset = -1;
        for (Entity entity : Minecraft.getInstance().world.getAllEntities()) {
            if (entity instanceof NuclearExplosionEntity) {
                double radius = NuclearFunctionality.getExplosionRadius(((NuclearExplosionEntity) entity).partialTicks);
                double distance = r.getProjectedView().distanceTo(entity.getPositionVec());
                if (distance < radius + 5) {
                    RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
                    e.setRed(0.98F);
                    e.setGreen(0.92F);
                    e.setBlue(0.39F);
                    return;
                }
                r.getViewVector().normalize();
                float look = (float)Math.pow(r.getViewVector().dot(new Vector3f(entity.getPositionVec().subtract(r.getProjectedView()).normalize())), 4);
                float intensity = ((NuclearExplosionEntity)entity).partialTicks / ((NuclearExplosionEntity) entity).getLifetime();
                float initial = Math.max((15 - ((NuclearExplosionEntity)entity).partialTicks), 0) / 15F;
                nukeSunset = Math.max(look * intensity + initial, Math.max(nukeSunset, 0.0F));
            }
        }
        if (nukeSunset >= 0) {
            e.setRed(0.68F + (0.32F * nukeSunset));
            e.setGreen(0.67F + (0.33F * nukeSunset));
            e.setBlue(0.6F + (0.3F * nukeSunset));
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onFogDensity(EntityViewRenderEvent.FogDensity e) {
        ActiveRenderInfo r = e.getRenderer().getActiveRenderInfo();
        if (r.getFluidState().isTagged(LCCTags.OIL)) {
            RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
            e.setDensity(0.7F);
            e.setCanceled(true);
        }

        float nukeSunset = -1;
        for (Entity entity : Minecraft.getInstance().world.getAllEntities()) {
            if (entity instanceof NuclearExplosionEntity) {
                double radius = NuclearFunctionality.getExplosionRadius(((NuclearExplosionEntity) entity).partialTicks);
                double distance = r.getProjectedView().distanceTo(entity.getPositionVec());
                if (distance < radius + 5) {
                    RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
                    e.setDensity(0.03F * (float)MathHelper.clamp(radius + 5 - distance, 0, 10));
                    e.setCanceled(true);
                    return;
                }
                r.getViewVector().normalize();
                float look = (float)Math.pow(r.getViewVector().dot(new Vector3f(entity.getPositionVec().subtract(r.getProjectedView()).normalize())), 4);
                float intensity = ((NuclearExplosionEntity)entity).partialTicks / ((NuclearExplosionEntity) entity).getLifetime() * 2;
                float initial = Math.max((15 - ((NuclearExplosionEntity)entity).partialTicks), 0) / 15F;
                nukeSunset = Math.max(look * intensity + initial, Math.max(nukeSunset, 0.0F));
            }
        }
        if (nukeSunset >= 0) {
            RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
            e.setDensity(0.004F + (0.02F * nukeSunset));
            e.setCanceled(true);
        }
    }

}