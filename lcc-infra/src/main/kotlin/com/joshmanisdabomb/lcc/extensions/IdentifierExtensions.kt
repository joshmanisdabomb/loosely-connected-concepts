package com.joshmanisdabomb.lcc.extensions

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

fun Identifier.modify(pathMod: (name: String) -> String) = IdentifierHelper.modify(this, pathMod)
fun Identifier.prefix(prefix: String?, glue: String = "_", glueWhen: (prefix: String?) -> Boolean = IdentifierHelper.Companion::defaultGlueWhen) = IdentifierHelper.idPrefix(this, prefix, glue, glueWhen)
fun Identifier.suffix(suffix: String?, glue: String = "_", glueWhen: (suffix: String?) -> Boolean = IdentifierHelper.Companion::defaultGlueWhen) = IdentifierHelper.idSuffix(this, suffix, glue, glueWhen)

val Block.identifier get() = Registry.BLOCK.getId(this)
val Item.identifier get() = Registry.ITEM.getId(this)

fun Block.identifierLoc(folder: String? = IdentifierHelper.BlockIdentifierHelper.defaultFolder, pathMod: (name: String) -> String = { it }) = IdentifierHelper.BlockIdentifierHelper.loc(this, folder, pathMod)
fun Block.identifierLocPrefix(prefix: String?, folder: String? = IdentifierHelper.BlockIdentifierHelper.defaultFolder, glue: String = "_", glueWhen: (prefix: String?) -> Boolean = IdentifierHelper.Companion::defaultGlueWhen, pathMod: (name: String) -> String = { it }) = IdentifierHelper.BlockIdentifierHelper.locPrefix(this, prefix, folder, glue, glueWhen, pathMod)
fun Block.identifierLocSuffix(suffix: String?, folder: String? = IdentifierHelper.BlockIdentifierHelper.defaultFolder, glue: String = "_", glueWhen: (suffix: String?) -> Boolean = IdentifierHelper.Companion::defaultGlueWhen, pathMod: (name: String) -> String = { it }) = IdentifierHelper.BlockIdentifierHelper.locSuffix(this, suffix, folder, glue, glueWhen, pathMod)

fun Item.identifierLoc(folder: String? = IdentifierHelper.ItemIdentifierHelper.defaultFolder, pathMod: (name: String) -> String = { it }) = IdentifierHelper.ItemIdentifierHelper.loc(this, folder, pathMod)
fun Item.identifierLocPrefix(prefix: String?, folder: String? = IdentifierHelper.ItemIdentifierHelper.defaultFolder, glue: String = "_", glueWhen: (prefix: String?) -> Boolean = IdentifierHelper.Companion::defaultGlueWhen, pathMod: (name: String) -> String = { it }) = IdentifierHelper.ItemIdentifierHelper.locPrefix(this, prefix, folder, glue, glueWhen, pathMod)
fun Item.identifierLocSuffix(suffix: String?, folder: String? = IdentifierHelper.ItemIdentifierHelper.defaultFolder, glue: String = "_", glueWhen: (suffix: String?) -> Boolean = IdentifierHelper.Companion::defaultGlueWhen, pathMod: (name: String) -> String = { it }) = IdentifierHelper.ItemIdentifierHelper.locSuffix(this, suffix, folder, glue, glueWhen, pathMod)

abstract class IdentifierHelper<T>(val defaultFolder: String?) {

    abstract fun registry(obj: T): Identifier

    fun loc(obj: T, folder: String? = this.defaultFolder, pathMod: (name: String) -> String = { it }) = modify(prefix(registry(obj), folder, "/"), pathMod)

    fun prefix(id: Identifier, prefix: String?, glue: String = "_", glueWhen: (prefix: String?) -> Boolean = ::defaultGlueWhen) = idPrefix(id, prefix, glue, glueWhen)

    fun suffix(id: Identifier, suffix: String?, glue: String = "_", glueWhen: (suffix: String?) -> Boolean = ::defaultGlueWhen) = idSuffix(id, suffix, glue, glueWhen)

    fun locPrefix(obj: T, prefix: String?, folder: String? = this.defaultFolder, glue: String = "_", glueWhen: (prefix: String?) -> Boolean = ::defaultGlueWhen, pathMod: (name: String) -> String = { it }) = loc(obj, folder) { stringPrefix(pathMod(it), prefix, glue, glueWhen) }

    fun locSuffix(obj: T, suffix: String?, folder: String? = this.defaultFolder, glue: String = "_", glueWhen: (suffix: String?) -> Boolean = ::defaultGlueWhen, pathMod: (name: String) -> String = { it }) = loc(obj, folder) { stringSuffix(pathMod(it), suffix, glue, glueWhen) }

    fun assetPath(obj: T) = registry(obj).path

    object BlockIdentifierHelper : IdentifierHelper<Block>("block") {
        override fun registry(obj: Block) = obj.identifier
    }

    object ItemIdentifierHelper : IdentifierHelper<Item>("item") {
        override fun registry(obj: Item) = obj.identifier
    }

    companion object {
        fun createWithDefaultNamespace(id: String, namespace: String) = if (!id.contains(':')) Identifier(namespace, id) else Identifier(id)

        fun create(path: String, namespace: String, pathMod: (name: String) -> String = { it }) = Identifier(namespace, pathMod(path))

        fun modify(resource: Identifier, pathMod: (name: String) -> String) = create(resource.path, resource.namespace, pathMod)

        fun stringPrefix(path: String, prefix: String?, glue: String = "_", glueWhen: (prefix: String?) -> Boolean = ::defaultGlueWhen) = "${if (glueWhen(prefix)) "$prefix$glue" else ""}$path"

        fun stringSuffix(path: String, suffix: String?, glue: String = "_", glueWhen: (suffix: String?) -> Boolean = ::defaultGlueWhen) = "$path${if (glueWhen(suffix)) "$glue$suffix" else ""}"

        fun idPrefix(id: Identifier, prefix: String?, glue: String = "_", glueWhen: (prefix: String?) -> Boolean = ::defaultGlueWhen) = Identifier(id.namespace, stringPrefix(id.path, prefix, glue, glueWhen))

        fun idSuffix(id: Identifier, suffix: String?, glue: String = "_", glueWhen: (suffix: String?) -> Boolean = ::defaultGlueWhen) = Identifier(id.namespace, stringSuffix(id.path, suffix, glue, glueWhen))

        internal fun defaultGlueWhen(str: String?) = str != null
    }

}