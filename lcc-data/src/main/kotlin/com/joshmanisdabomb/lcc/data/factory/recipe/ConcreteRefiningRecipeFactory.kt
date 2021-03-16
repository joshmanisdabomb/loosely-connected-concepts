package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.json.recipe.RefiningShapelessRecipeJsonFactory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.identifier
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ConcreteRefiningRecipeFactory : RecipeFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        DyeColor.values().forEach { d ->
            val dye = Registry.ITEM.getOrEmpty(Identifier("minecraft", d.asString().plus("_dye"))).orElse(null) ?: return@forEach
            val concrete = Registry.BLOCK.getOrEmpty(Identifier("minecraft", d.asString().plus("_concrete_powder"))).orElse(null) ?: return@forEach

            RefiningShapelessRecipeJsonFactory()
                .addInput(dye)
                .addInput(Blocks.SAND, 4)
                .addInput(Blocks.GRAVEL, 4)
                .addInput(LCCBlocks.pumice, 4)
                .addOutput(concrete, 16)
                .with(LCCBlocks.refiner, LCCBlocks.composite_processor)
                .meta("container.lcc.refining.recipe.pozzolanic_mixing", 0, RefiningBlock.RefiningProcess.MIXING)
                .speed(1600, 0.04f, 400f)
                .energyPerOperation(LooseEnergy.fromCoals(1.5f))
                .apply { hasCriterionInterface(this, LCCBlocks.refiner) }
                .apply { offerInterface(this, data, suffix(concrete.identifier.run { LCC.id(path) }, "from_refiner")) }
        }
        //TODO alternate colour concretes
    }

}
