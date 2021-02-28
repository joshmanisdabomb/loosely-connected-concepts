package com.joshmanisdabomb.lcc.data.factory.asset

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.directory.LCCModelTextureKeys
import com.joshmanisdabomb.lcc.data.directory.LCCModelTemplates
import net.minecraft.data.client.model.Models
import net.minecraft.data.client.model.SimpleModelSupplier
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.util.Identifier

interface AssetFactory<T> {

    val defaultFolder: String

    fun loc(name: String, modid: String, folder: String = defaultFolder, path: (name: String) -> String = { it }) = Identifier(modid, folder + "/" + path(name))

    fun loc(resource: Identifier, folder: String = defaultFolder, path: (name: String) -> String = { it }) = loc(resource.path, resource.namespace, folder, path)

    fun loc(obj: T, folder: String = defaultFolder, path: (name: String) -> String = { it }) = loc(registry(obj), folder, path)

    fun suffix(id: Identifier, suffix: String?, glue: String = "_") = Identifier(id.namespace, suffix(id.path, suffix, glue))

    fun suffix(path: String, suffix: String?, glue: String = "_") = "${path}${if (suffix != null) "$glue$suffix" else ""}"

    fun assetPath(obj: T) = registry(obj).path

    fun registry(obj: T): Identifier

    fun modelParticle(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry)) = Models.PARTICLE.upload(model, Texture.particle(texture), data.modelStates::addModel)

    fun modelCube(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), textureParticle: Identifier = texture, textureUp: Identifier = suffix(texture, "up"), textureDown: Identifier = suffix(texture, "down"), textureNorth: Identifier = suffix(texture, "north"), textureSouth: Identifier = suffix(texture, "south"), textureEast: Identifier = suffix(texture, "east"), textureWest: Identifier = suffix(texture, "west")) = Models.CUBE.upload(model, Texture().put(TextureKey.UP, textureUp).put(TextureKey.DOWN, textureDown).put(TextureKey.NORTH, textureNorth).put(TextureKey.SOUTH, textureSouth).put(TextureKey.EAST, textureEast).put(TextureKey.WEST, textureWest).put(TextureKey.PARTICLE, textureParticle), data.modelStates::addModel)

    fun modelCubeAll(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry)) = Models.CUBE_ALL.upload(model, Texture.all(texture), data.modelStates::addModel)

    fun modelCubeMirrored(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry)) = Models.CUBE_MIRRORED_ALL.upload(model, Texture.all(texture), data.modelStates::addModel)

    fun modelCubeBottomTop(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), textureTop: Identifier = suffix(texture, "top"), textureSide: Identifier = suffix(texture, "side"), textureBottom: Identifier = suffix(texture, "bottom")) = Models.CUBE_BOTTOM_TOP.upload(model, Texture().put(TextureKey.TOP, textureTop).put(TextureKey.SIDE, textureSide).put(TextureKey.BOTTOM, textureBottom), data.modelStates::addModel)

    fun modelOrientable(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), textureTop: Identifier = suffix(texture, "top"), textureSide: Identifier = suffix(texture, "side"), textureFront: Identifier = suffix(texture, "front")) = Models.ORIENTABLE.upload(model, Texture().put(TextureKey.TOP, textureTop).put(TextureKey.SIDE, textureSide).put(TextureKey.FRONT, textureFront), data.modelStates::addModel)

    fun modelOrientableBottom(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), textureTop: Identifier = suffix(texture, "top"), textureSide: Identifier = suffix(texture, "side"), textureBottom: Identifier = suffix(texture, "bottom"), textureFront: Identifier = suffix(texture, "front")) = Models.ORIENTABLE_WITH_BOTTOM.upload(model, Texture().put(TextureKey.TOP, textureTop).put(TextureKey.SIDE, textureSide).put(TextureKey.BOTTOM, textureBottom).put(TextureKey.FRONT, textureFront), data.modelStates::addModel)

    fun modelOrientableVertical(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), textureSide: Identifier = suffix(texture, "side"), textureFront: Identifier = suffix(texture, "front")) = Models.ORIENTABLE_VERTICAL.upload(model, Texture().put(TextureKey.SIDE, textureSide).put(TextureKey.FRONT, textureFront), data.modelStates::addModel)

    fun modelPillar(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), textureSide: Identifier = suffix(texture, "side"), textureEnd: Identifier = suffix(texture, "end")) = Models.CUBE_COLUMN.upload(model, Texture().put(TextureKey.SIDE, textureSide).put(TextureKey.END, textureEnd), data.modelStates::addModel)

    fun modelPillarHorizontal(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), textureSide: Identifier = suffix(texture, "side"), textureEnd: Identifier = suffix(texture, "end")) = Models.CUBE_COLUMN_HORIZONTAL.upload(model, Texture().put(TextureKey.SIDE, textureSide).put(TextureKey.END, textureEnd), data.modelStates::addModel)

    fun modelCross(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry)) = Models.CROSS.upload(model, Texture.cross(texture), data.modelStates::addModel)

    fun modelPottedCross(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry)) = Models.FLOWER_POT_CROSS.upload(model, Texture.plant(texture), data.modelStates::addModel)

    fun modelGenerated(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry)) = Models.GENERATED.upload(model, Texture.layer0(texture), data.modelStates::addModel)

    fun modelGenerated1(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry), texture1: Identifier = texture) = LCCModelTemplates.generated1.upload(model, Texture().put(TextureKey.LAYER0, texture).put(LCCModelTextureKeys.layer1, texture1), data.modelStates::addModel)

    fun modelHandheld(data: DataAccessor, entry: T, model: Identifier = loc(entry), texture: Identifier = loc(entry)) = Models.HANDHELD.upload(model, Texture.layer0(texture), data.modelStates::addModel)

    fun modelParent(data: DataAccessor, entry: T, parent: Identifier, model: Identifier = loc(entry)) = data.modelStates.addModel(model, SimpleModelSupplier(parent))

    fun modelBuiltin(data: DataAccessor, entry: T, model: Identifier = loc(entry)) = modelParent(data, entry, Identifier("minecraft", "builtin/entity"), model)

}
