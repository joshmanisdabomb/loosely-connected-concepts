package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.block.HeartCondenserBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.json.recipe.HeartCondenserRecipeJsonFactory
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.item.HeartContainerItem
import net.minecraft.item.Item

object HeartContainerRecipeFactory : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        if (entry !is HeartContainerItem) return
        HeartCondenserRecipeJsonFactory(entry)
            .input(LCCItems.heart_full[entry.heart]!!, 2)
            .input(LCCItems.heart_half[entry.heart]!!, 1)
            .state(HeartCondenserBlock.HeartState.find(entry.heart.name)!!)
            .apply { hasCriterionInterface(this, LCCItems.enhancing_pyre_beta) }
            .apply { hasCriterionInterface(this, LCCItems.heart_full[entry.heart]!!) }
            .apply { hasCriterionInterface(this, LCCItems.heart_half[entry.heart]!!) }
            .apply { offerInterface(this, data) }
    }

}
