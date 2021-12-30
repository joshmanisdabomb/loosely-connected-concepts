package com.joshmanisdabomb.lcc.abstracts.computing.session

import com.joshmanisdabomb.lcc.block.entity.TerminalBlockEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.World
import java.util.*

interface ComputingSessionViewContext {

    fun getWorldFromContext(): World

    fun sendControlEvent(event: ControlEvent, builder: (packet: PacketByteBuf) -> Unit)

    fun handleControlEvent(packet: PacketByteBuf, player: ServerPlayerEntity)

    fun getViewToken(): UUID

    fun generateViewToken()

    enum class ControlEvent {
        CLOSE {
            override fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit {
                return { session, view, player -> session.onClose(player, view) }
            }
        },
        KEY_DOWN {
            override fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit {
                val keycode = packet.readShort().toInt()
                return { session, view, player -> session.keyPressed(player, view, keycode) }
            }
        },
        KEY_UP {
            override fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit {
                val keycode = packet.readShort().toInt()
                return { session, view, player -> session.keyReleased(player, view, keycode) }
            }
        },
        MOUSE_DOWN {
            override fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit {
                val mx = packet.readDouble()
                val my = packet.readDouble()
                val button = packet.readByte().toInt()
                return { session, view, player -> session.mouseClicked(player, view, mx, my, button) }
            }
        },
        MOUSE_UP {
            override fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit {
                val mx = packet.readDouble()
                val my = packet.readDouble()
                val button = packet.readByte().toInt()
                return { session, view, player -> session.mouseReleased(player, view, mx, my, button) }
            }
        },
        CHAR {
            override fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit {
                val char = packet.readChar()
                return { session, view, player -> session.charTyped(player, view, char) }
            }
        };

        abstract fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit
    }

}