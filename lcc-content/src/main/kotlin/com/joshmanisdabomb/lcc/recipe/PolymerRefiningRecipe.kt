package com.joshmanisdabomb.lcc.recipe

import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRecipeSerializers
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.stack
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import com.joshmanisdabomb.lcc.item.PlasticItem
import net.minecraft.block.Block
import net.minecraft.item.DyeItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.world.World
import java.util.*

class PolymerRefiningRecipe(_id: Identifier) : RefiningSpecialRecipe(_id) {

    private val rand = Random()

    override val blocks = arrayOf<Block>(LCCBlocks.refiner, LCCBlocks.composite_processor)
    override val lang = "container.lcc.refining.recipe.polymerization"
    override val icon = 9
    override val state = RefiningBlock.RefiningProcess.TREATING

    override fun getEnergyPerTick() = LooseEnergy.fromCoals(6f).div(getSpeed())

    override fun getSpeed() = 500
    override fun getSpeedGainPerTick() = 0.012f
    override fun getMaxSpeedGainPerTick() = 400f

    override fun generate(consumed: List<ItemStack>, inventory: RefiningInventory, random: Random) = listOf(generatePlastic(consumed.any { it.isOf(Items.BONE_MEAL) }, *consumed.mapNotNull { (it.item as? DyeItem)?.color as? LCCExtendedDyeColor }.toTypedArray()))

    override fun generateMaximum(inventory: RefiningInventory): List<ItemStack> {
        val colors = mutableListOf<LCCExtendedDyeColor>()
        for (j in 0 until inventory.width*inventory.height) {
            colors += (inventory[j].item as? DyeItem)?.color as? LCCExtendedDyeColor ?: continue
        }
        return isPlasticiserFlexible(inventory)?.run { listOf(generatePlastic(this, *colors.toTypedArray())) } ?: emptyList()
    }

    private fun isPlasticiserFlexible(inventory: RefiningInventory): Boolean? {
        for (j in 0 until inventory.width*inventory.height) {
            val stack = inventory[j]
            if (stack.isOf(Items.BONE_MEAL)) return true
            else if (stack.isOf(Items.QUARTZ)) return false
        }
        return null
    }

    private fun generatePlastic(flexible: Boolean, vararg colors: LCCExtendedDyeColor) = flexible.transform(LCCItems.flexible_plastic, LCCItems.rigid_plastic).stack(3) {
        if (colors.isNotEmpty()) getOrCreateSubTag("display").putInt("color", PlasticItem.getColorBlend(*colors))
    }

    override fun matches(inv: RefiningInventory, world: World): Boolean {
        val stackMap = getInputStackMap(inv) { it >= 2 } ?: return false

        if (stackMap.any { !it.isOf(LCCItems.refined_oil_bucket) && !it.isOf(Items.QUARTZ) && !it.isOf(Items.BONE_MEAL) && it.item !is DyeItem }) return false
        if (stackMap.any { it.isOf(Items.QUARTZ) } && stackMap.any { it.isOf(Items.BONE_MEAL) }) return false
        stackMap.firstOrNull { it.isOf(LCCItems.refined_oil_bucket) && it.count >= 1 } ?: return false
        stackMap.firstOrNull { (it.isOf(Items.QUARTZ) || it.isOf(Items.BONE_MEAL)) && it.count >= 1 } ?: return false
        return true
    }

    override fun input(inv: RefiningInventory): List<ItemStack>? {
        var oil: ItemStack? = null
        var plasticiser: ItemStack? = null
        val dyes = mutableListOf<ItemStack>()
        for (j in 0 until inv.width*inv.height) {
            val stack = inv[j]
            if (stack.isEmpty) continue
            if (stack.isOf(LCCItems.refined_oil_bucket)) {
                oil = stack
                continue
            }
            if (stack.isOf(Items.QUARTZ) || stack.isOf(Items.BONE_MEAL)) {
                if (plasticiser != null && stack.item != plasticiser.item) return null
                plasticiser = stack
                continue
            }
            if (stack.item is DyeItem) {
                dyes.add(stack)
                continue
            }
        }
        if (oil == null || plasticiser == null) return null
        return listOf(oil, plasticiser, *dyes.toTypedArray()).map { it.split(1) }
    }

    override fun craft(inventory: RefiningInventory) = isPlasticiserFlexible(inventory)?.run { generatePlastic(this) } ?: ItemStack.EMPTY

    override fun fits(width: Int, height: Int) = width * height > 2

    override fun getSerializer() = LCCRecipeSerializers.polymerization

}