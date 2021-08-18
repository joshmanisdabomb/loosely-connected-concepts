package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.block.BombBoardBlock
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.extensions.addString
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.world.feature.SapphireAltarStructureFeature
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.World
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class MinesweeperAltarChallenge : AltarChallenge() {

    override fun initialData(random: Random, nbt: NbtCompound): NbtCompound {
        val a = random.nextInt(5).plus(5).times(2).plus(1)
        val b = random.nextInt(4).plus(6).times(2).plus(1)
        nbt.putInt("Width", max(a, b))
        nbt.putInt("Depth", min(a, b))
        nbt.putInt("Mines", (a*b).times(random.nextDouble().times(0.11).plus(0.16)).roundToInt())
        return nbt
    }

    override fun generate(world: StructureWorldAccess, piece: SapphireAltarStructureFeature.Piece, yOffset: Int, boundingBox: BlockBox, data: NbtCompound, random: Random) {
        val width = data.getInt("Width")
        val depth = data.getInt("Depth")
        for (i in 0 until width) {
            for (j in 0 until depth) {
                piece.addBlock(world, LCCBlocks.bomb_board_block.defaultState.with(Properties.AXIS, Direction.Axis.Y).with(BombBoardBlock.mine_state, 0), i+1, yOffset, j+4, boundingBox)
            }
        }
    }

    override fun start(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity, player: PlayerEntity): Boolean {
        val data = be.data
        val facing = state[Properties.HORIZONTAL_FACING].opposite

        val width = data.getInt("Width")
        //val w = width.minus(1).div(2)
        val depth = data.getInt("Depth")
        val bombs = data.getInt("Mines")

        if (!verifyAltar(world, facing, pos, width, depth)) {
            player.sendMessage(TranslatableText("block.lcc.sapphire_altar.minesweeper.malformed"), true)
            return false
        }

        val board = generateBoard(width, depth, bombs, world.random)

        val pos2 = be.pos.offset(facing, 3).down()
        val bbstate = LCCBlocks.bomb_board_block.defaultState.with(Properties.AXIS, Direction.Axis.Y)
        foreachPlane(pos2, facing, width, depth) { p, x, y -> world.setBlockState(p, bbstate.with(BombBoardBlock.mine_state, board[x][y].transformInt(BombBoardBlock.mine, BombBoardBlock.mystery)), 18) }
        val pos3 = pos2.offset(facing, depth.div(2))
        world.setBlockState(pos3, bbstate.with(BombBoardBlock.mine_state, LCCBlocks.bomb_board_block.getAdjacentMines(world, bbstate, pos3)))

        val nbtBoard = NbtList()
        board.forEach { nbtBoard.addString(it.joinToString("") { it.transform("x", " ") }) }
        data.put("Board", nbtBoard)

        if (!world.isClient) {
            val range = Box(pos).expand(40.0, 40.0, 40.0)
            val players = world.server!!.playerManager.playerList
            be.challengers = players.filter { it.world.dimension == world.dimension && range.intersects(it.boundingBox) }.map { it.uuid }.toMutableList()
        }

        return true
    }

    override fun verify(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState {
        val data = be.data
        val facing = state[Properties.HORIZONTAL_FACING].opposite

        val width = data.getInt("Width")
        val depth = data.getInt("Depth")
        val nbtBoard = data.getList("Board", NBT_STRING)
        val board = nbtBoard.map { (it as? NbtString)?.asString()?.map { it != ' ' } ?: List(width) { false } }

        return when (verifyBoard(world, facing, pos, board, width, depth)) {
            true -> ChallengeState.COMPLETED
            false -> ChallengeState.FAILED
            null -> ChallengeState.ACTIVE
        }
    }

    override fun verifyTick(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState {
        val data = be.data
        val facing = state[Properties.HORIZONTAL_FACING].opposite

        val width = data.getInt("Width")
        val depth = data.getInt("Depth")

        return if (verifyAltar(world, facing, pos, width, depth)) ChallengeState.ACTIVE else ChallengeState.FAILED_AGGRESSIVE
    }

    fun generateBoard(width: Int, height: Int, bombs: Int, random: Random): List<List<Boolean>> {
        val board = MutableList(width) { MutableList(height) { false } }
        for (i in 0 until bombs) {
            for (attempt in 0 until 40) {
                val x = random.nextInt(width)
                val y = random.nextInt(height)

                if (board[x][y]) continue
                if ((x == 0 || x == width-1) && random.nextInt(2) == 0) continue
                if ((y == 0 || y == height-1) && random.nextInt(2) == 0) continue
                if ((x <= 1 || x >= width-2) && random.nextInt(2) == 0) continue
                if ((y <= 1 || y >= height-2) && random.nextInt(2) == 0) continue

                val xd = abs(x - width.div(2))
                val yd = abs(y - height.div(2))
                if (xd <= 1 && yd <= 1) continue
                if (xd <= 3 && yd <= 3 && random.nextInt(5-(xd+yd).div(2)) != 0) continue

                var adjacent = 0
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j == 0 && k == 0) continue
                        if (x + j < 0 || x + j >= width) continue
                        if (y + k < 0 || y + k >= height) continue
                        if (!board[x + j][y + k]) continue
                        adjacent += 1
                    }
                }

                when (adjacent) {
                    1 -> if (random.nextInt(4) == 0) continue
                    2 -> if (random.nextInt(3) == 0) continue
                    3 -> if (random.nextInt(2) == 0) continue
                    4 -> if (random.nextInt(3) != 0) continue
                    5 -> if (random.nextInt(4) != 0) continue
                    6 -> if (random.nextInt(5) != 0) continue
                    7 -> continue
                    8 -> continue
                }

                board[x][y] = true
                break
            }
        }
        return board
    }

    fun verifyAltar(world: World, facing: Direction, origin: BlockPos, width: Int, depth: Int): Boolean {
        var flag = false
        foreachPlane(origin.offset(facing, 2).down(), facing, width+2, depth+2) { p, x, y ->
            if (flag) return@foreachPlane
            val state2 = world.getBlockState(p)
            if (x == 0 || x == width+1 || y == 0 || y == depth+1) {
                if (!state2.isOf(LCCBlocks.sapphire_altar_brick)) {
                    flag = true
                }
            } else {
                if (!state2.isOf(LCCBlocks.bomb_board_block) || state2[Properties.AXIS] != Direction.Axis.Y) {
                    flag = true
                }
            }
        }
        return !flag
    }

    fun verifyBoard(world: World, facing: Direction, origin: BlockPos, board: List<List<Boolean>>, width: Int, depth: Int): Boolean? {
        var flag: Boolean? = true
        foreachPlane(origin.offset(facing, 3).down(), facing, width, depth) { p, x, y ->
            if (flag == false) return@foreachPlane
            val state2 = world.getBlockState(p)
            when (state2[BombBoardBlock.mine_state]) {
                BombBoardBlock.mine -> {
                    if (!board[x][y]) {
                        flag = false
                    }
                }
                BombBoardBlock.mystery -> {
                    if (board[x][y]) {
                        flag = false
                    }
                    else flag = null
                }
                else -> {
                    if (board[x][y]) flag = false
                }
            }
        }
        return flag
    }

    override fun handleState(cstate: ChallengeState, world: ServerWorld, pos: BlockPos, state: BlockState, entity: SapphireAltarBlockEntity): Boolean {
        when (cstate) {
            ChallengeState.COMPLETED -> {
                val challengers = world.server.playerManager.playerList.filter { entity.challengers?.contains(it.uuid) == true }
                challengers.forEach {
                    LCCCriteria.sapphire_altar.trigger(it, this, cstate.getRewards(state))
                }
                entity.challengers = null
            }
        }
        return super.handleState(cstate, world, pos, state, entity)
    }

}