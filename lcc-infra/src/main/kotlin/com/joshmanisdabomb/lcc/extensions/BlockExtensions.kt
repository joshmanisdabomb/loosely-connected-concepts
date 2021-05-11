package com.joshmanisdabomb.lcc.extensions

import net.minecraft.block.Block
import net.minecraft.block.FacingBlock
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.PillarBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.math.Direction

fun Block.horizontalPlacement(context: ItemPlacementContext, property: DirectionProperty = HorizontalFacingBlock.FACING) = defaultState.with(property, context.playerFacing.opposite)!!
fun Block.horizontalFacePlacement(context: ItemPlacementContext, default: Direction = context.playerFacing.opposite, property: DirectionProperty = HorizontalFacingBlock.FACING) = defaultState.with(property, context.side.let { if (it.axis == Direction.Axis.Y) default else it })!!
fun Block.directionalPlayerPlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.playerLookDirection.opposite)!!
fun Block.directionalFacePlacement(context: ItemPlacementContext, property: DirectionProperty = FacingBlock.FACING) = defaultState.with(property, context.side)!!
fun Block.pillarPlacement(context: ItemPlacementContext, property: EnumProperty<Direction.Axis> = PillarBlock.AXIS) = defaultState.with(property, context.side.axis)!!