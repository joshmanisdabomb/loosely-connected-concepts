package com.joshmanisdabomb.lcc.block

import net.minecraft.block.BlockState
import net.minecraft.block.PressurePlateBlock
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.block.DoorBlock as VanillaDoorBlock
import net.minecraft.block.PaneBlock as VanillaPaneBlock
import net.minecraft.block.PressurePlateBlock as VanillaPressurePlateBlock
import net.minecraft.block.SaplingBlock as VanillaSaplingBlock
import net.minecraft.block.StairsBlock as VanillaStairsBlock
import net.minecraft.block.TrapdoorBlock as VanillaTrapdoorBlock
import net.minecraft.block.WoodenButtonBlock as VanillaWoodenButtonBlock

class StairsBlock(copy: BlockState, settings: Settings) : VanillaStairsBlock(copy, settings)
class DoorBlock(settings: Settings) : VanillaDoorBlock(settings)
class PressurePlateBlock(rule: PressurePlateBlock.ActivationRule, settings: Settings) : VanillaPressurePlateBlock(rule, settings)
class WoodenButtonBlock(settings: Settings) : VanillaWoodenButtonBlock(settings)
class TrapdoorBlock(settings: Settings) : VanillaTrapdoorBlock(settings)
class SaplingBlock(generator: SaplingGenerator, settings: Settings) : VanillaSaplingBlock(generator, settings)
class PaneBlock(settings: Settings) : VanillaPaneBlock(settings)