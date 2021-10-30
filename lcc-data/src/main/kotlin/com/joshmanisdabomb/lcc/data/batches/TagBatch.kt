package com.joshmanisdabomb.lcc.data.batches

import com.google.common.collect.HashBasedTable
import com.joshmanisdabomb.lcc.mixin.data.common.TagBuilderAccessor
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.registry.Registry

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

    fun block(tag: Tag<Block>) = block((tag as Tag.Identified<Block>).id)
    fun item(tag: Tag<Item>) = item((tag as Tag.Identified<Item>).id)
    fun entity(tag: Tag<EntityType<*>>) = entity((tag as Tag.Identified<EntityType<*>>).id)
    fun fluid(tag: Tag<Fluid>) = fluid((tag as Tag.Identified<Fluid>).id)

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

        fun getId(entry: T) = registry.getKey(convert(entry)).get().value
        protected abstract fun convert(entry: T): U

        override fun asString() = registry.key.value.path.lowercase()
    }

    class TagBuilder<T, U>(val type: TagType<T, U>, val id: Identifier) : Tag.Builder() {

        var sorter = Comparator.comparing<Tag.TrackedEntry, Boolean> { it.entry is Tag.TagEntry || it.entry is Tag.OptionalTagEntry }.thenComparing(Comparator.comparing { it.entry.toString().replace("?", "") })

        fun attachId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { add(Tag.ObjectEntry(it), "lcc-data") }
            return this
        }

        fun optionalId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { add(Tag.OptionalObjectEntry(it), "lcc-data") }
            return this
        }

        fun attachTagId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { add(Tag.TagEntry(it), "lcc-data") }
            return this
        }

        fun optionalTagId(vararg items: Identifier): TagBuilder<T, U> {
            items.forEach { add(Tag.OptionalTagEntry(it), "lcc-data") }
            return this
        }

        fun attach(vararg items: T) = attachId(*items.map(type::getId).toTypedArray())

        fun optional(vararg items: T) = optionalId(*items.map(type::getId).toTypedArray())

        fun attachTag(vararg tags: Tag.Identified<U>) = attachTagId(*tags.map { it.id }.toTypedArray())

        fun optionalTag(vararg tags: Tag.Identified<U>) = optionalTagId(*tags.map { it.id }.toTypedArray())

        fun attachTag(vararg tags: TagBuilder<*, U>) = attachTagId(*tags.map { it.id }.toTypedArray())

        fun optionalTag(vararg tags: TagBuilder<*, U>) = optionalTagId(*tags.map { it.id }.toTypedArray())

        fun applySort(comparator: Comparator<Tag.TrackedEntry>): TagBuilder<T, U> {
            sorter = comparator
            return this
        }

    }

}