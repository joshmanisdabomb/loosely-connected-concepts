package com.joshmanisdabomb.lcc.directory

import net.minecraft.server.world.ChunkTicketType

object LCCChunkTickets : ThingDirectory<ChunkTicketType<*>, Unit>() {

    val nuclear by create { ChunkTicketType.create<net.minecraft.util.Unit>("nuclear") { _, _ -> 0 } }

}