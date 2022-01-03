package com.joshmanisdabomb.lcc.abstracts.computing.session

import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.World
import java.util.*

interface ComputingSessionViewContext {

    fun getSession(): ComputingSession?

    fun getSessionToken(): UUID?

    fun getWorldFromContext(): World

    fun sendControlEvent(event: ControlEvent, builder: (packet: PacketByteBuf) -> Unit)

    fun handleControlEvent(packet: PacketByteBuf, player: ServerPlayerEntity)

    fun getViewToken(): UUID?

    fun generateViewToken()

    fun isWatching(player: ServerPlayerEntity): Boolean {
        val sessionId = getSessionToken() ?: return false
        val viewId = getViewToken() ?: return false
        val handler = player.currentScreenHandler as? ComputingSessionViewContextProvider ?: return false
        val currentView = handler.getView(player, player.getWorld())
        return currentView.getSessionToken() == sessionId && currentView.getViewToken() == viewId
    }

    fun getWatching(server: MinecraftServer) = server.playerManager.playerList.filter { isWatching(it) }

    enum class ControlEvent {
        OPEN {
            override fun getHandler(packet: PacketByteBuf): (session: ComputingSession, view: ComputingSessionViewContext, player: ServerPlayerEntity) -> Unit {
                return { session, view, player -> session.onOpen(player, view) }
            }
        },
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