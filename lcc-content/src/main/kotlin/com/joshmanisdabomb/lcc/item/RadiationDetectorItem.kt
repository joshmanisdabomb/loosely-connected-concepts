package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.TooltipConstants
import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyStorage
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.gui.overlay.RadiationOverlay
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.UnclampedModelPredicateProvider
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import kotlin.math.abs
import kotlin.math.pow

class RadiationDetectorItem(val energy: Float, settings: Settings) : Item(settings), StackEnergyStorage {

    override fun getRawEnergyMaximum(context: StackEnergyContext) = energy

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: StackEnergyContext) = 0f

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand) ?: return TypedActionResult.fail(ItemStack.EMPTY)
        if (!user.isSurvival || this.getEnergy(LooseEnergy, StackEnergyContext(stack)) ?: 0f >= energyPerTick) {
            user.setCurrentHand(hand)
            return TypedActionResult.consume(stack)
        }
        return TypedActionResult.fail(stack)
    }

    override fun usageTick(world: World, user: LivingEntity, stack: ItemStack, remainingUseTicks: Int) {
        if ((user as? PlayerEntity)?.isSurvival == true && removeEnergyDirect(energyPerTick, LooseEnergy, StackEnergyContext(stack)) < energyPerTick) {
            user.clearActiveItem()
        }
        if (world.isClient) {
            if (user != MinecraftClient.getInstance().getCameraEntity()) return
            val bp = BlockPos.Mutable()
            val list = mutableListOf<BlockPos>()
            RadiationOverlay.detected = 0
            for (i in -20..20) {
                for (j in -20..20) {
                    for (k in -20..20) {
                        val state = world.getBlockState(bp.set(user.blockPos, i, j, k))
                        if (state.isIn(LCCTags.radioactive)) {
                            if (maxOf(abs(i), abs(j), abs(k)) > world.random.nextDouble().pow(16).times(21)) continue
                            list.add(bp.toImmutable())
                        }
                    }
                }
            }
            for (bp in list.run { if (list.size > 20) { shuffle(world.random); take(20) } else this }) {
                world.addImportantParticle(LCCParticles.uranium, true, bp.x.plus(0.5), bp.y.plus(0.5), bp.z.plus(0.5), 0.0, 0.0, 0.0)
                RadiationOverlay.detected += 1
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks)
    }

    override fun getUseAction(stack: ItemStack) = UseAction.SPYGLASS

    override fun getMaxUseTime(stack: ItemStack) = 72000

    override fun isItemBarVisible(stack: ItemStack) = true

    override fun getItemBarStep(stack: ItemStack): Int {
        return MathHelper.ceil((getEnergyFill(StackEnergyContext(stack))?.coerceAtMost(1f) ?: 0f).times(13f))
    }

    override fun getItemBarColor(stack: ItemStack): Int {
        val fill = getEnergyFill(StackEnergyContext(stack))?.coerceAtMost(1f) ?: 0f
        return MathHelper.hsvToRgb(fill.times(0.1f).plus(0.05f), fill.times(0.7f).plus(0.3f), fill.times(0.2f).plus(0.8f))
    }

    override fun appendStacks(group: ItemGroup, stacks: DefaultedList<ItemStack>) {
        if (isIn(group)) {
            stacks.add(ItemStack(this))
            stacks.add(ItemStack(this).also { setRawEnergy(StackEnergyContext(it), energy); })
        }
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        with (StackEnergyContext(stack)) {
            tooltip.add(TranslatableText(TooltipConstants.energy, LooseEnergy.display(getEnergy(LooseEnergy, this) ?: 0f), LooseEnergy.display(getMaximumEnergy(LooseEnergy, this) ?: 0f), " ".plus(LooseEnergy.units)).formatted(Formatting.GOLD))
        }
    }

    @Environment(EnvType.CLIENT)
    fun getWinterPredicate() = UnclampedModelPredicateProvider { stack, world, entity, _ ->
        val w = world ?: entity?.world ?: MinecraftClient.getInstance().world ?: return@UnclampedModelPredicateProvider 0f
        val winter = LCCComponents.nuclear.getNullable(w)?.winter
        NuclearUtil.getWinterLevel(winter?.minus((!MinecraftClient.getInstance().isPaused && w.random.nextFloat() > 0.2f.plus(winter.rem(1f).times(0.8f))).transformInt())?.coerceAtLeast(0f) ?: 0f).toFloat()
    }

    companion object {
        const val energyPerTick = 4f
    }

}