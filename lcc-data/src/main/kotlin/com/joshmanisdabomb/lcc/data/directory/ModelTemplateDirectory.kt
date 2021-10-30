package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import net.minecraft.data.client.model.Model
import net.minecraft.data.client.model.TextureKey
import net.minecraft.util.Identifier
import java.util.*

abstract class ModelTemplateDirectory : BasicDirectory<Model, Unit>() {

    abstract val data: DataAccessor

    fun initialiser(input: Model, context: DirectoryContext<Unit>, parameters: Unit) = input.also { if (context.tags.size >= 2) data.models[Identifier(data.modid, "${context.tags[0]}/${context.name}")] = { data.parser.parse(context.tags[1]) } }

    fun referenceEntry(parent: Identifier, vararg keys: TextureKey) = entry(::initialiser) { Model(Optional.of(parent), Optional.empty(), *keys) }
    fun templateEntry(folder: String, json: String, vararg keys: TextureKey) = entry(::initialiser) { Model(Optional.of(Identifier(data.modid, "${tags[0]}/$name")), Optional.empty(), *keys) }.addTags(folder, json)

    override fun defaultProperties(name: String) = Unit

}