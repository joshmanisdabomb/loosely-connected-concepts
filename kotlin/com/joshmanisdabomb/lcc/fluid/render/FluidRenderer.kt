package com.joshmanisdabomb.lcc.fluid.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCFluids
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.client.MinecraftClient
import net.minecraft.client.texture.Sprite
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.FluidState
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView

@Environment(EnvType.CLIENT)
open class FluidRenderer(val still: FlowableFluid, val flowing: FlowableFluid, val still_texture: Identifier = LCC.id("block/${LCCFluids[still]}"), val flowing_texture: Identifier = LCC.id("block/${LCCFluids[flowing]}"), val color: Int = 0xFFFFFF) : FluidRenderHandler, SimpleSynchronousResourceReloadListener, ClientSpriteRegistryCallback {

    val sprites = arrayOfNulls<Sprite>(2)

    fun register() {
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(this)

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(this)

        FluidRenderHandlerRegistry.INSTANCE.register(still, this)
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, this)
    }

    override fun registerSprites(sprite: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
        registry.register(still_texture)
        registry.register(flowing_texture)
    }

    override fun getFabricId() = Identifier(LCC.modid, "${LCCFluids[still]}_reload_listener")

    override fun apply(resourceManager: ResourceManager) {
        with(MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)) {
            sprites[0] = apply(still_texture)
            sprites[1] = apply(flowing_texture)
        }
    }

    override fun getFluidSprites(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Array<Sprite> = sprites.requireNoNulls()

    override fun getFluidColor(view: BlockRenderView?, pos: BlockPos?, state: FluidState) = color

}