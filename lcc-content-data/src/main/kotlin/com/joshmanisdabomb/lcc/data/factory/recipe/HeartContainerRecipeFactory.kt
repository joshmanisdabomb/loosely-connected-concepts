package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.item.HeartContainerItem
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory
import net.minecraft.item.Item

object HeartContainerRecipeFactory : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        if (entry !is HeartContainerItem) return
        ShapelessRecipeJsonFactory.create(entry)
            .input(LCCItems.heart_full[entry.heart], 10)
            .input(LCCItems.enhancing_dust_beta)
            .apply { hasCriterionShapeless(this, LCCItems.enhancing_dust_beta) }
            .apply { offerShapeless(this, data, override = LCCRecipeSerializers.spawner_table_shapeless) }
    }

}
