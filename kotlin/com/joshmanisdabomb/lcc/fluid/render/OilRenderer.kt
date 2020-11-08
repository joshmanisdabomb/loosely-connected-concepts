package com.joshmanisdabomb.lcc.fluid.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.OilBlock
import com.joshmanisdabomb.lcc.block.OilBlock.Companion.GEYSER
import com.joshmanisdabomb.lcc.directory.LCCFluids
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.FluidState
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView

class OilRenderer : FluidRenderer(LCCFluids.oil_still, LCCFluids.oil_flowing) {

    val geyser_sprites = arrayOfNulls<Sprite?>(2)
    val geyser_texture = LCC.id("block/oil_geyser")

    override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
        super.registerSprites(sprite, registry)
        registry.register(geyser_texture)
    }

    override fun apply(resourceManager: ResourceManager) {
        super.apply(resourceManager)
        with(MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)) {
            geyser_sprites[0] = apply(still_texture)
            geyser_sprites[1] = apply(geyser_texture)
        }
    }

    override fun getFluidSprites(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Array<Sprite> = (if (view?.getBlockState(pos)?.get(GEYSER) == true) geyser_sprites else sprites).requireNoNulls()

}