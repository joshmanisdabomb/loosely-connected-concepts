package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.block.DirectionalBlock
import com.joshmanisdabomb.lcc.block.HorizontalBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.item.Item
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.DyeColor
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Direction.*
import net.minecraft.util.registry.Registry

object LCCBlocks : RegistryDirectory<Block, Unit>() {

    override val _registry by lazy { Registry.BLOCK }

    val test_block by create { Block(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_2 by create { HorizontalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_3 by create { DirectionalBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }
    val test_block_4 by create { PillarBlock(Settings.of(Material.SOIL, DyeColor.YELLOW).strength(0.5F).sounds(BlockSoundGroup.SCAFFOLDING)) }

    val cracked_mud by create { Block(FabricBlockSettings.of(Material.STONE, MapColor.WHITE_TERRACOTTA).strength(2.0F, 0.1F).breakByTool(FabricToolTags.PICKAXES, 0).requiresTool().sounds(BlockSoundGroup.STONE)) }

    fun Block.traitHorizontalPlacement(context: ItemPlacementContext, property: DirectionProperty = HorizontalFacingBlock.FACING) = defaultState.with(property, context.playerFacing.opposite)!!

    fun Block.traitDirectionalPlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.playerLookDirection.opposite)!!

    fun Block.traitPillarPlacement(context: ItemPlacementContext, property: EnumProperty<Axis> = PillarBlock.AXIS) = defaultState.with(property, context.side.axis)!!

}