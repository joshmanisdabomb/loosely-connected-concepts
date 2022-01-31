package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.cache.NativeSkinCache
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.FlyEntity
import com.joshmanisdabomb.lcc.entity.model.FlyEntityModel
import com.joshmanisdabomb.lcc.entity.render.feature.FlyHeadFeatureRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import java.util.*

@Environment(EnvType.CLIENT)
class FlyEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<FlyEntity, FlyEntityModel>(ctx, FlyEntityModel(ctx.getPart(LCCModelLayers.fly)), 0.0f) {

    init {
        addFeature(FlyHeadFeatureRenderer(this))
    }

    override fun getTexture(entity: FlyEntity) = LCC.id("textures/entity/fly.png")

    companion object {

        private val flyColors = mutableMapOf<UUID, Int>()

        fun getFlyColor(player: ClientPlayerEntity): Int? {
            val color = flyColors[player.uuid]
            if (color != null) return color

            //TODO lcc infra colors, saturate/brighten this color
            val skin = (MinecraftClient.getInstance().textureManager.getTexture(player.skinTexture) as? NativeSkinCache)?.image ?: return null
            val raw = skin.makePixelArray().map { it and 0xFFFFFF }
            val filter = raw[0]
            val filteredRaw = raw.filter { it != filter }
            val candidates = filteredRaw.map {
                val components = LCCExtendedDyeColor.getComponents(it).map { it.times(16).toInt().times(16).coerceAtMost(255) }
                return@map components[0] + (components[1] shl 8) + (components[2] shl 16)
            }
            val common = candidates.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
            if (common != null) {
                flyColors[player.uuid] = common
                return common
            }
            return null
        }

    }

}