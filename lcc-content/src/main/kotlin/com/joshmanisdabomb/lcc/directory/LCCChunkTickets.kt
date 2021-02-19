package com.joshmanisdabomb.lcc.directory

import net.minecraft.server.world.ChunkTicketType

object LCCChunkTickets : BasicDirectory<ChunkTicketType<*>, Unit>() {

    val nuclear by entry(::initialiser) { ChunkTicketType.create<net.minecraft.util.Unit>("nuclear") { _, _ -> 0 } }

    fun <T> initialiser(input: ChunkTicketType<T>, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

}