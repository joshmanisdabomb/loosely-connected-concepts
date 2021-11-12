package com.joshmanisdabomb.lcc.data.factory.asset

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider.ModelFactory
import com.joshmanisdabomb.lcc.extensions.IdentifierHelper
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.data.client.model.*
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

class ModelProvider<T>(val idh: IdentifierHelper<T>) {
    
    private fun loc(entry: T) = idh.loc(entry)

    fun particle(texture: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.PARTICLE.upload(i(t) ?: loc(t), Texture.particle(tx), d.models) }

    fun cube(texture: (entry: T) -> Identifier? = { null }, textureParticle: (entry: T) -> Identifier? = { null }, textureUp: (entry: T) -> Identifier? = { null }, textureDown: (entry: T) -> Identifier? = { null }, textureNorth: (entry: T) -> Identifier? = { null }, textureSouth: (entry: T) -> Identifier? = { null }, textureEast: (entry: T) -> Identifier? = { null }, textureWest: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.CUBE.upload(i(t) ?: loc(t), Texture().put(TextureKey.UP, textureUp(t) ?: tx.suffix("up")).put(TextureKey.DOWN, textureDown(t) ?: tx.suffix("down")).put(TextureKey.NORTH, textureNorth(t) ?: tx.suffix("north")).put(TextureKey.SOUTH, textureSouth(t) ?: tx.suffix("south")).put(TextureKey.EAST, textureEast(t) ?: tx.suffix("east")).put(TextureKey.WEST, textureWest(t) ?: tx.suffix("west")).put(TextureKey.PARTICLE, textureParticle(t) ?: tx), d.models) }

    fun cubeAll(texture: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.CUBE_ALL.upload(i(t) ?: loc(t), Texture.all(tx), d.models) }

    fun cubeMirrored(texture: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.CUBE_MIRRORED_ALL.upload(i(t) ?: loc(t), Texture.all(tx), d.models) }

    fun cubeBottomTop(texture: (entry: T) -> Identifier? = { null }, textureTop: (entry: T) -> Identifier? = { null }, textureSide: (entry: T) -> Identifier? = { null }, textureBottom: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.CUBE_BOTTOM_TOP.upload(i(t) ?: loc(t), Texture().put(TextureKey.TOP, textureTop(t) ?: tx.suffix("top")).put(TextureKey.SIDE, textureSide(t) ?: tx.suffix("side")).put(TextureKey.BOTTOM, textureBottom(t) ?: tx.suffix("bottom")), d.models) }

    fun orientable(texture: (entry: T) -> Identifier? = { null }, textureTop: (entry: T) -> Identifier? = { null }, textureSide: (entry: T) -> Identifier? = { null }, textureFront: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.ORIENTABLE.upload(i(t) ?: loc(t), Texture().put(TextureKey.TOP, textureTop(t) ?: tx.suffix("top")).put(TextureKey.SIDE, textureSide(t) ?: tx.suffix("side")).put(TextureKey.FRONT, textureFront(t) ?: tx.suffix("front")), d.models) }

    fun orientableBottom(texture: (entry: T) -> Identifier? = { null }, textureTop: (entry: T) -> Identifier? = { null }, textureSide: (entry: T) -> Identifier? = { null }, textureBottom: (entry: T) -> Identifier? = { null }, textureFront: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.ORIENTABLE_WITH_BOTTOM.upload(i(t) ?: loc(t), Texture().put(TextureKey.TOP, textureTop(t) ?: tx.suffix("top")).put(TextureKey.SIDE, textureSide(t) ?: tx.suffix("side")).put(TextureKey.BOTTOM, textureBottom(t) ?: tx.suffix("bottom")).put(TextureKey.FRONT, textureFront(t) ?: tx.suffix("front")), d.models) }

    fun orientableVertical(texture: (entry: T) -> Identifier? = { null }, textureSide: (entry: T) -> Identifier? = { null }, textureFront: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.ORIENTABLE_VERTICAL.upload(i(t) ?: loc(t), Texture().put(TextureKey.SIDE, textureSide(t) ?: tx.suffix("side")).put(TextureKey.FRONT, textureFront(t) ?: tx.suffix("front")), d.models) }

    fun pillar(texture: (entry: T) -> Identifier? = { null }, textureSide: (entry: T) -> Identifier? = { null }, textureEnd: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.CUBE_COLUMN.upload(i(t) ?: loc(t), Texture().put(TextureKey.SIDE, textureSide(t) ?: tx.suffix("side")).put(TextureKey.END, textureEnd(t) ?: tx.suffix("end")), d.models) }

    fun pillarHorizontal(texture: (entry: T) -> Identifier? = { null }, textureSide: (entry: T) -> Identifier? = { null }, textureEnd: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.CUBE_COLUMN_HORIZONTAL.upload(i(t) ?: loc(t), Texture().put(TextureKey.SIDE, textureSide(t) ?: tx.suffix("side")).put(TextureKey.END, textureEnd(t) ?: tx.suffix("end")), d.models) }

    fun cross(texture: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.CROSS.upload(i(t) ?: loc(t), Texture.cross(tx), d.models) }

    fun pottedCross(texture: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.FLOWER_POT_CROSS.upload(i(t) ?: loc(t), Texture.plant(tx), d.models) }

    fun generated(texture: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.GENERATED.upload(i(t) ?: loc(t), Texture.layer0(tx), d.models) }

    fun handheld(texture: (entry: T) -> Identifier? = { null }) =
        ModelFactory<T> { d, t, i -> val tx = texture(t) ?: loc(t); Models.HANDHELD.upload(i(t) ?: loc(t), Texture.layer0(tx), d.models) }

    fun parent(parent: Identifier) =
        ModelFactory<T> { d, t, i -> (i(t) ?: loc(t)).also { d.models.accept(it, SimpleModelSupplier(parent)) } }

    fun builtin(model: (entry: T) -> Identifier = ::loc) = parent(Identifier("minecraft", "builtin/entity"))

    companion object {
        val block = ModelProvider(IdentifierHelper.BlockIdentifierHelper)
        val item = ModelProvider(IdentifierHelper.ItemIdentifierHelper)

        private val hr: Map<Direction, Map<Direction, BlockStateVariant.() -> Unit>> = mapOf(
            Direction.NORTH to mapOf(
                Direction.NORTH to {},
                Direction.EAST to { put(VariantSettings.Y, VariantSettings.Rotation.R90) },
                Direction.SOUTH to { put(VariantSettings.Y, VariantSettings.Rotation.R180) },
                Direction.WEST to { put(VariantSettings.Y, VariantSettings.Rotation.R270) },
            )
        )

        private val dr: Map<Direction, Map<Direction, BlockStateVariant.() -> Unit>> = mapOf(
            Direction.UP to mapOf(
                Direction.UP to {},
                Direction.NORTH to { put(VariantSettings.X, VariantSettings.Rotation.R90) },
                Direction.EAST to { put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90) },
                Direction.SOUTH to { put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180) },
                Direction.WEST to { put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270) },
                Direction.DOWN to { put(VariantSettings.X, VariantSettings.Rotation.R180) }
            ),
            Direction.NORTH to mapOf(
                Direction.UP to { put(VariantSettings.X, VariantSettings.Rotation.R270) },
                Direction.NORTH to {  },
                Direction.EAST to { put(VariantSettings.Y, VariantSettings.Rotation.R90) },
                Direction.SOUTH to { put(VariantSettings.Y, VariantSettings.Rotation.R180) },
                Direction.WEST to { put(VariantSettings.Y, VariantSettings.Rotation.R270) },
                Direction.DOWN to { put(VariantSettings.X, VariantSettings.Rotation.R90) }
            )
        )

        fun horizontalRotation(direction: Direction, default: Direction = Direction.NORTH) = hr[default]!![direction]!!
        fun directionalRotation(direction: Direction, default: Direction = Direction.UP) = dr[default]!![direction]!!
    }

    fun interface ModelFactory<T> {
        fun create(data: DataAccessor, entry: T, location: (entry: T) -> Identifier?) : Identifier

        fun create(data: DataAccessor, entry: T) = create(data, entry, { null })
    }

}