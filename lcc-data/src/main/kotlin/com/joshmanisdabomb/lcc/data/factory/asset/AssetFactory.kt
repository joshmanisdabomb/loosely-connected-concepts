package com.joshmanisdabomb.lcc.data.factory.asset

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.data.client.model.Models
import net.minecraft.data.client.model.Texture
import net.minecraft.data.client.model.TextureKey
import net.minecraft.util.Identifier

interface AssetFactory<T> {

    val defaultFolder: String

    fun id(name: String, modid: String, folder: String = defaultFolder, path: (name: String) -> String = { it }) = Identifier(modid, folder + "/" + path(name))

    fun suffix(id: Identifier, suffix: String) = Identifier(id.namespace, "${id.path}_$suffix")

    fun assetPath(obj: T) = registry(obj).path

    fun registry(obj: T): Identifier

    fun modelParticle(data: DataAccessor, entry: T, model: Identifier = id(assetPath(entry), data.modid), texture: Identifier = id(assetPath(entry), data.modid)) = Models.PARTICLE.upload(model, Texture.particle(texture), data.modelStates::addModel)

    fun modelCubeAll(data: DataAccessor, entry: T, model: Identifier = id(assetPath(entry), data.modid), texture: Identifier = id(assetPath(entry), data.modid)) = Models.CUBE_ALL.upload(model, Texture.all(texture), data.modelStates::addModel)

    fun modelOrientable(data: DataAccessor, entry: T, model: Identifier = id(assetPath(entry), data.modid), texture: Identifier = id(assetPath(entry), data.modid), textureTop: Identifier = suffix(texture, "top"), textureSide: Identifier = suffix(texture, "side"), textureFront: Identifier = suffix(texture, "front")) = Models.ORIENTABLE.upload(model, Texture().put(TextureKey.TOP, textureTop).put(TextureKey.SIDE, textureSide).put(TextureKey.FRONT, textureFront), data.modelStates::addModel)

    fun modelOrientableVertical(data: DataAccessor, entry: T, model: Identifier = id(assetPath(entry), data.modid), texture: Identifier = id(assetPath(entry), data.modid), textureSide: Identifier = suffix(texture, "side"), textureFront: Identifier = suffix(texture, "front")) = Models.ORIENTABLE_VERTICAL.upload(model, Texture().put(TextureKey.SIDE, textureSide).put(TextureKey.FRONT, textureFront), data.modelStates::addModel)

    fun modelPillar(data: DataAccessor, entry: T, model: Identifier = id(assetPath(entry), data.modid), texture: Identifier = id(assetPath(entry), data.modid), textureSide: Identifier = suffix(texture, "side"), textureEnd: Identifier = suffix(texture, "end")) = Models.CUBE_COLUMN.upload(model, Texture().put(TextureKey.SIDE, textureSide).put(TextureKey.END, textureEnd), data.modelStates::addModel)

    fun modelPillarHorizontal(data: DataAccessor, entry: T, model: Identifier = id(assetPath(entry), data.modid), texture: Identifier = id(assetPath(entry), data.modid), textureSide: Identifier = suffix(texture, "side"), textureEnd: Identifier = suffix(texture, "end")) = Models.CUBE_COLUMN_HORIZONTAL.upload(model, Texture().put(TextureKey.SIDE, textureSide).put(TextureKey.END, textureEnd), data.modelStates::addModel)

}
