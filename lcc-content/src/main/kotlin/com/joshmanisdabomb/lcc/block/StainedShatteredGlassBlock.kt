package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Stainable
import net.minecraft.block.StainedGlassBlock

class StainedShatteredGlassBlock(unbroken: StainedGlassBlock, settings: Settings) : ShatteredGlassBlock(unbroken, settings), Stainable {

    private val color = unbroken.color

    override fun getColor() = color

}