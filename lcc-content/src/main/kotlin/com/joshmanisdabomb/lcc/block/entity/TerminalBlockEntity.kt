package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContextProvider
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCPacketsToServer
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyStorage
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.container.TerminalScreenHandler
import com.joshmanisdabomb.lcc.item.PlasticItem
import com.joshmanisdabomb.lcc.network.ComputingNetwork
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import dev.onyxstudios.cca.api.v3.component.ComponentProvider
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.PropertyDelegate
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import java.util.*

class TerminalBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.terminal, pos, state), ExtendedScreenHandlerFactory, WorldEnergyStorage, ComputingSessionViewContext {

    var color: Int = PlasticItem.defaultColor
    var customName: Text? = null

    var session: UUID? = null
    var sessionAccess: UUID? = null

    override var rawEnergy: Float? = 0f
    override val rawEnergyMaximum = LooseEnergy.toStandard(100f)
    private var energy: Float
        get() = rawEnergy ?: 0f
        set(value) { rawEnergy = value }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@TerminalBlockEntity.energyDisplay.first
            1 -> this@TerminalBlockEntity.energyDisplay.second
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@TerminalBlockEntity.energyDisplay.first = value
            1 -> this@TerminalBlockEntity.energyDisplay.second = value
            else -> Unit
        }

        override fun size() = 2
    }
    private val energyDisplay = DecimalTransport(::energy)

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        color = nbt.getInt("Color")
        rawEnergy = nbt.getFloat("Energy")
        session = if (nbt.containsUuid("Session")) nbt.getUuid("Session") else null
        sessionAccess = if (nbt.containsUuid("Access")) nbt.getUuid("Access") else null

        if (nbt.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(nbt.getString("CustomName"))
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)

        nbt.putInt("Color", color)
        rawEnergy?.apply { nbt.putFloat("Energy", this) }
        session?.apply { nbt.putUuid("Session", this) }
        sessionAccess?.apply { nbt.putUuid("Access", this) }

        if (customName != null) nbt.putString("CustomName", Text.Serializer.toJson(customName))
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt() = createNbt()

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): TerminalScreenHandler? {
        val splayer = player as? ServerPlayerEntity ?: return null
        val level = player.world.levelProperties
        val sessions = LCCComponents.computing_sessions.maybeGet(level).orElse(null) ?: return null
        LCCComponents.computing_sessions.syncWith(splayer, level as ComponentProvider, sessions.syncSingle(session) { sessionAccess }) { true }
        return TerminalScreenHandler(syncId, inv, propertyDelegate, pos)
    }

    override fun getDisplayName() = customName ?: Text.translatable("container.lcc.terminal")

    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
        buf.writeBlockPos(pos)
    }

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    private fun findSession(world: World, pos: BlockPos, vararg networks: ComputingNetwork): ComputingSession? {
        for (network in networks) {
            val result = network.discover(world, pos to null)
            val computer = result.nodes["active_computer"]?.firstOrNull()
            val other = computer?.second
            if (other != null) {
                val half = (world.getBlockEntity(computer.first) as? ComputingBlockEntity)?.getHalf(other)
                val module = half?.module as? ComputerComputerModule
                return module?.getSession(half) ?: continue
            }
        }
        return null
    }

    override fun getSession(): ComputingSession? {
        val session = session ?: return null
        val level = world?.levelProperties ?: return null
        val sessions = LCCComponents.computing_sessions.maybeGet(level).orElse(null) ?: return null
        return sessions.getSession(session)
    }

    override fun getSessionToken() = session

    override fun sendControlEvent(event: ComputingSessionViewContext.ControlEvent, builder: (packet: PacketByteBuf) -> Unit) {
        val world = MinecraftClient.getInstance().world?.registryKey ?: return
        ClientPlayNetworking.send(LCCPacketsToServer[LCCPacketsToServer::terminal_control].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeIdentifier(world.value); writeBlockPos(pos); writeByte(event.ordinal); builder(this) })
    }

    override fun handleControlEvent(packet: PacketByteBuf, player: ServerPlayerEntity) {
        val type = ComputingSessionViewContext.ControlEvent.values()[packet.readByte().toInt()]
        val session = this.getSession() ?: return
    }

    override fun getViewToken() = sessionAccess

    override fun generateViewToken() { sessionAccess = UUID.randomUUID() }

    override fun getWorldFromContext() = world!!

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: TerminalBlockEntity) {
            val sworld = world as? ServerWorld ?: return
            val energy = entity.getEnergy(LooseEnergy, WorldEnergyContext(world, pos, null, null)) ?: 0f
            if (entity.session != null || energy > 0f) {
                var session = entity.findSession(world, pos, ComputingNetwork.local, ComputingNetwork.wired)
                if (session != null) {
                    val removed = entity.removeEnergyDirect(2f, LooseEnergy, WorldEnergyContext(world, pos, null, null))
                    entity.markDirty()
                    if (removed < 2f) {
                        session = null
                    }
                }

                if (entity.session != session?.id) {
                    entity.session = session?.id
                    if (session != null) {
                        entity.generateViewToken()
                        entity.getSession()?.syncToAllWatching(sworld.server)
                    }
                    sworld.chunkManager.markForUpdate(pos)
                } else if (session != null) {
                    session.serverTickView(entity)
                }
            }

            EnergyTransaction()
                .include { entity.requestEnergy(WorldEnergyContext(world, pos, null, null), it, LooseEnergy, *Direction.values()) }
                .run(5f)
        }
    }

}
