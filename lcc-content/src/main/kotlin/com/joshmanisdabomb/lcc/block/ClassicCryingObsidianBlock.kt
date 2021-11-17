package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.ClassicCryingObsidianBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCPacketsToClient
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.fabricmc.fabric.api.server.PlayerStream
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.RespawnAnchorBlock
import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*

class ClassicCryingObsidianBlock(settings: Settings) : BlockWithEntity(settings), LCCBlockTrait {

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        if (!player.isSurvival || stack.isOf(Items.LAPIS_BLOCK)) {
            val be = world.getBlockEntity(pos) as? ClassicCryingObsidianBlockEntity ?: return ActionResult.SUCCESS
            if (be.isActive(player)) return ActionResult.SUCCESS
            if (player.isSurvival) stack.decrement(1)
            if (!world.isClient) {
                val splayer = player as? ServerPlayerEntity ?: return ActionResult.SUCCESS
                if (splayer.spawnPointDimension != world.registryKey || splayer.spawnPointPosition != pos) {
                    splayer.setSpawnPoint(world.registryKey, pos, 0.0f, false, true)
                    world.playSound(null, pos.x.toDouble() + 0.5, pos.y.toDouble() + 0.5, pos.z.toDouble() + 0.5, LCCSounds.classic_crying_obsidian_set_spawn, SoundCategory.BLOCKS, 1.0f, 2.0f)
                }
            }
            return ActionResult.SUCCESS
        } else {
            return ActionResult.PASS
        }
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = ClassicCryingObsidianBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun lcc_spawnOn(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, yaw: Float, spawnPointSet: Boolean, alive: Boolean): Optional<Vec3d>? {
        run {
            val be = world.getBlockEntity(pos) as? ClassicCryingObsidianBlockEntity ?: return@run
            val playerPos = be.getLocation(player) ?: return@run
            return Optional.of(playerPos)
        }
        return RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos)
    }

    override fun lcc_spawnAfter(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, yaw: Float, spawnPointSet: Boolean, alive: Boolean) {
        player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, Vec3d.ofCenter(pos))
    }

    override fun lcc_spawnSet(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, oldWorld: ServerWorld?, oldState: BlockState?, oldPos: BlockPos?, yaw: Float, spawnPointSet: Boolean, alive: Boolean) {
        (world.getBlockEntity(pos) as? ClassicCryingObsidianBlockEntity)?.register(player, player.pos)
        val p = PlayerStream.watching(world, pos).filter { it == player }.findFirst().orElse(null) ?: return
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(p, LCCPacketsToClient[LCCPacketsToClient::classic_crying_obsidian_update].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeBlockPos(pos); writeBoolean(true) })
    }

    override fun lcc_spawnRemoved(player: ServerPlayerEntity, world: ServerWorld, state: BlockState, pos: BlockPos, newWorld: ServerWorld?, newState: BlockState?, newPos: BlockPos?, yaw: Float, spawnPointSet: Boolean, alive: Boolean) {
        (world.getBlockEntity(pos) as? ClassicCryingObsidianBlockEntity)?.deregister(player)
        val p = PlayerStream.watching(world, pos).filter { it == player }.findFirst().orElse(null) ?: return
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(p, LCCPacketsToClient[LCCPacketsToClient::classic_crying_obsidian_update].first().id, PacketByteBuf(Unpooled.buffer()).apply { writeBlockPos(pos); writeBoolean(false) })
    }

}