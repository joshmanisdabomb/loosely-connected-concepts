package com.joshmanisdabomb.lcc.directory.component

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.AdvancedDirectory
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import dev.onyxstudios.cca.api.v3.item.ItemComponent
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object LCCItemComponents : AdvancedDirectory<(ItemStack) -> ItemComponent, ComponentKey<out ItemComponent>, Pair<Class<out ItemComponent>, (Item) -> Boolean>, ItemComponentFactoryRegistry>(), ItemComponentInitializer {

    fun <C : ItemComponent> initialiser(input: (ItemStack) -> C, context: DirectoryContext<Pair<Class<out ItemComponent>, (Item) -> Boolean>>, parameters: ItemComponentFactoryRegistry): ComponentKey<C> {
        val key = ComponentRegistryV3.INSTANCE.getOrCreate(LCC.id(context.name), context.properties.first) as ComponentKey<C>
        parameters.register(context.properties.second, key, input)
        return key
    }

    override fun registerItemComponentFactories(registry: ItemComponentFactoryRegistry) {
        LCCItemComponents.init(registry)
    }

    override fun defaultProperties(name: String) = error("No default parameters available for $name. Please specify a component class and item predicate.")
    override fun defaultContext() = error("No default context available. Please pass the item component registry.")

}