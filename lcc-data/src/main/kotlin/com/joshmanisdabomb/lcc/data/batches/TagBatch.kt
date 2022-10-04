package com.joshmanisdabomb.lcc.data.batches

import com.google.common.collect.HashBasedTable
import com.joshmanisdabomb.lcc.mixin.data.common.TagBuilderAccessor
import com.joshmanisdabomb.lcc.mixin.data.common.TagEntryAccessor
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.TagBuilder
import net.minecraft.tag.TagEntry
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome

class TagBatch {

    private val table = HashBasedTable.create<TagType<*, *>, Identifier, TagBuilder<*, *>>()

    fun <T, U> tag(type: TagType<T, U>, id: Identifier): TagBuilder<T, U> {
        if (table.contains(type, id)) return table.get(type, id) as TagBuilder<T, U>
        return TagBuilder(type, id).also { table.put(type, id, it) }
    }

    fun block(id: Identifier) = tag(TagType.BLOCK, id)
    fun item(id: Identifier) = tag(TagType.ITEM, id)
    fun entity(id: Identifier) = tag(TagType.ENTITY, id)
    fun fluid(id: Identifier) = tag(TagType.FLUID, id)
    fun biome(id: Identifier) = tag(TagType.BIOME, id)

    fun block(tag: TagKey<Block>) = block(tag.id)
    fun item(tag: TagKey<Item>) = item(tag.id)
    fun entity(tag: TagKey<EntityType<*>>) = entity(tag.id)
    fun fluid(tag: TagKey<Fluid>) = fluid(tag.id)
    fun biome(tag: TagKey<Biome>) = biome(tag.id)

    fun getBuilders() = table.cellSet().onEach { (it.value as TagBuilderAccessor).entries.sortWith(it.value.sorter) }

    sealed class TagType<T, U>(val registry: Registry<U>) : StringIdentifiable {
        object BLOCK : TagType<Block, Block>(Registry.BLOCK) {
            override fun convert(entry: Block) = entry
        }
        object ITEM : TagType<ItemConvertible, Item>(Registry.ITEM) {
            override fun convert(entry: ItemConvertible) = entry.asItem()
        }
        object ENTITY : TagType<EntityType<*>, EntityType<*>>(Registry.ENTITY_TYPE) {
            override fun convert(entry: EntityType<*>) = entry
        }
        object FLUID : TagType<Fluid, Fluid>(Registry.FLUID) {
            override fun convert(entry: Fluid) = entry
        }
        object BIOME : TagType<Biome, Biome>(BuiltinRegistries.BIOME) {
            override fun convert(entry: Biome) = entry

            override fun asString() = registry.key.value.path.lowercase()
        }

        fun getId(entry: T) = registry.getKey(convert(entry)).get().value
        protected abstract fun convert(entry: T): U

        override fun asString() = registry.key.value.path.lowercase().plus("s")
    }

    class TagBuilder<T, U>(val type: TagType<T, U>, val id: Identifier) : net.minecraft.tag.TagBuilder() {

        var sorter = Comparator.comparing<TagEntry, Boolean> { (it as? TagEntryAccessor)?.isTag == true }.thenComparing(Comparator.comparing { it.toString().replace("?", "") })

        fun attachId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { add(it) }
            return this
        }

        fun optionalId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { addOptional(it) }
            return this
        }

        fun attachTagId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { addTag(it) }
            return this
        }

        fun optionalTagId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { addOptionalTag(it) }
            return this
        }

        fun attach(vararg items: T) = attachId(*items.map(type::getId).toTypedArray())

        fun optional(vararg items: T) = optionalId(*items.map(type::getId).toTypedArray())

        fun attachTag(vararg tags: TagKey<U>) = attachTagId(*tags.map { it.id }.toTypedArray())

        fun optionalTag(vararg tags: TagKey<U>) = optionalTagId(*tags.map { it.id }.toTypedArray())

        fun attachTag(vararg tags: TagBuilder<*, U>) = attachTagId(*tags.map { it.id }.toTypedArray())

        fun optionalTag(vararg tags: TagBuilder<*, U>) = optionalTagId(*tags.map { it.id }.toTypedArray())

        fun applySort(comparator: Comparator<TagEntry>): TagBuilder<T, U> {
            sorter = comparator
            return this
        }

    }

}