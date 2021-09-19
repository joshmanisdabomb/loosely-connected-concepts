package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Stainable
import net.minecraft.block.StainedGlassPaneBlock

class StainedShatteredPaneBlock(unbroken: StainedGlassPaneBlock, settings: Settings) : ShatteredPaneBlock(unbroken, settings), Stainable {

    private val color = unbroken.color

    override fun getColor() = color

}