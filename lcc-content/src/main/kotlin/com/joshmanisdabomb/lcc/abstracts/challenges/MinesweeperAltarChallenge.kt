package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.block.BombBoardBlock
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.extensions.addString
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.world.feature.structure.SapphireAltarStructure
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtString
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.World
import kotlin.math.*

class MinesweeperAltarChallenge : AltarChallenge() {

    override fun initialData(random: Random, nbt: NbtCompound): NbtCompound {
        val a = random.nextInt(5).plus(5).times(2).plus(1)
        val b = random.nextInt(4).plus(6).times(2).plus(1)
        nbt.putInt("Width", max(a, b))
        nbt.putInt("Depth", min(a, b))
        return nbt
    }

    override fun generate(world: StructureWorldAccess, piece: SapphireAltarStructure.Piece, yOffset: Int, boundingBox: BlockBox, data: NbtCompound, random: Random) {
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
        val depth = data.getInt("Depth")

        if (!verifyAltar(world, facing, pos, width, depth)) {
            player.sendMessage(Text.translatable("block.lcc.sapphire_altar.minesweeper.malformed"), true)
            return false
        }

        var attempts = 0
        var board: List<List<Boolean>>
        do {
            val bombs = (width * depth).toDouble().pow(0.7).times(world.random.nextDouble().times(0.7).plus(0.8)).roundToInt()
            board = generateBoard(width, depth, bombs, world.random)
            attempts++
            println("Attempt $attempts at generating bomb board @ $pos")
        } while (attempts < 200 && !solveBoard(board))
        if (attempts >= 200) {
            player.sendMessage(Text.translatable("block.lcc.sapphire_altar.minesweeper.unsolvable"), true)
            return false
        }

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
            be.challengers = players.filter { it.uuid == player.uuid || (it.world.dimension == world.dimension && range.intersects(it.boundingBox)) }.map { it.uuid }.toMutableList()
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
            for (attempt in 0 until 60) {
                val x = random.nextInt(width)
                val y = random.nextInt(height)

                if (board[x][y]) continue

                val xd = abs(x - width.div(2))
                val yd = abs(y - height.div(2))
                if (xd <= 1 && yd <= 1) continue
                if (xd <= 3 && yd <= 3 && random.nextInt(5-(xd+yd).div(2)) != 0) continue

                board[x][y] = true
                break
            }
        }
        return board
    }

    fun solveBoard(board: List<List<Boolean>>): Boolean {
        val width = board.size
        val height = board.first().size
        val board = board.map { it.map { it.transformInt(-2, -1).toByte() }.toMutableList() }.toMutableList()

        //reveal middle tile
        if (!solveBoardClick(board, width.div(2), height.div(2))) return false

        //run routine
        var ideas: Int
        do {
            ideas = 0

            var ideas3x3: Int
            do {
                ideas3x3 = solveBoard3x3Flag(board)
                if (ideas3x3 > 0 && !solveBoard3x3Reveal(board)) {
                    return false
                }
            } while (ideas3x3 > 0)
            ideas += ideas3x3

            /*var ideasTank: Int
            do {
                ideasTank = 0
                val borders = solveBoardTankBorders(board)
                solveBoardDebug(board, 9)
            } while (false)
            ideas += ideasTank*/

            if (solveBoardFinished(board)) {
                return true
            }
        } while (ideas > 0)
        return false
    }

    fun solveBoardClick(board: MutableList<MutableList<Byte>>, x: Int, y: Int): Boolean {
        val value = board[x][y]
        if (value == (-2).toByte() || value == (-4).toByte()) {
            return false
        } else if (value == (-1).toByte() || value == (-3).toByte()) {
            val adjacent = solveBoardAdjacent(board, x, y, -2, -4)
            board[x][y] = adjacent
            if (adjacent == 0.toByte()) {
                for (i in -1..1) {
                    for (j in -1..1) {
                        if (i == 0 && j == 0) continue
                        if (x+i !in board.indices) continue
                        if (y+j !in board.first().indices) continue
                        solveBoardClick(board, x+i, y+j)
                    }
                }
            }
        }
        return true
    }

    fun solveBoardFlag(board: MutableList<MutableList<Byte>>, x: Int, y: Int): Boolean {
        val value = board[x][y]
        if (value !in -2..-1) return false
        board[x][y] = value.minus(2).toByte()
        return true
    }

    fun solveBoardAdjacent(board: List<List<Byte>>, x: Int, y: Int, vararg valid: Byte): Byte {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                if (valid.any { board.getOrNull(x+i)?.getOrNull(y+j) == it }) count++
            }
        }
        return count.toByte()
    }

    fun solveBoard3x3Flag(board: MutableList<MutableList<Byte>>): Int {
        var count = 0
        for (x in board.indices) {
            for (y in board[x].indices) {
                val value = board[x][y]
                if (value <= 0) continue
                if (value == solveBoardAdjacent(board, x, y, -1, -2, -3, -4)) {
                    for (i in -1..1) {
                        for (j in -1..1) {
                            if (i == 0 && j == 0) continue
                            if (x+i !in board.indices) continue
                            if (y+j !in board.first().indices) continue
                            if (solveBoardFlag(board, x+i, y+j)) count++
                        }
                    }
                }
            }
        }
        return count
    }

    fun solveBoard3x3Reveal(board: MutableList<MutableList<Byte>>): Boolean {
        for (x in board.indices) {
            for (y in board[x].indices) {
                val value = board[x][y]
                if (value <= 0) continue
                if (value == solveBoardAdjacent(board, x, y, -3, -4)) {
                    for (i in -1..1) {
                        for (j in -1..1) {
                            if (i == 0 && j == 0) continue
                            if (x+i !in board.indices) continue
                            if (y+j !in board.first().indices) continue
                            if (board[x+i][y+j] < -2) continue
                            if (!solveBoardClick(board, x+i, y+j)) return false
                        }
                    }
                }
            }
        }
        return true
    }

    /*fun solveBoardTankBorders(board: List<List<Byte>>): List<List<Pair<Int, Int>>> {
        val unsplit = mutableListOf<Pair<Int, Int>>()
        for (x in board.indices) {
            for (y in board[x].indices) {
                val value = board[x][y]
                if (value in -2..-1 && solveBoardAdjacent(board, x, y, 0, 1, 2, 3, 4, 5, 6, 7, 8) > 0) {
                    unsplit.add(Pair(x, y))
                }
            }
        }
        val regions = mutableListOf<MutableList<Pair<Int, Int>>>()
        val reverse = mutableMapOf<Pair<Int, Int>, Int>()
        for (t in unsplit) {
            val region = reverse[t] ?: regions.size
            reverse[t] = region
            for (i in -1..1) {
                for (j in -1..1) {
                    if (i == 0 && j == 0) continue
                    val t2 = Pair(t.first + i, t.second + j)
                    if (unsplit.contains(t2)) {
                        if (region >= regions.size) {
                            regions.add(mutableListOf())
                        }
                        reverse[t2] = region
                        regions[region].add(t2)
                    }
                }
            }
        }
        for (x in board.indices) {
            for (y in board[x].indices) {
                val t = Pair(x, y)
                if (!reverse.containsKey(t)) continue
                (board[x] as MutableList)[y] = reverse[t]?.plus(10)?.toByte() ?: continue
            }
        }
        return regions
    }*/

    fun solveBoardFinished(board: List<List<Byte>>) = !board.any { it.any { it in -2..-1 } }

    /*private fun solveBoardDebug(board: List<List<Byte>>, stage: Int) {
        println("Board at Stage $stage")
        for (l in board.transpose().asReversed()) {
            println(l.joinToString(separator = "") {
                when (it.toInt()) {
                    -4 -> "[F]"
                    -3 -> "[!]"
                    -2 -> "[*]"
                    -1 -> "[-]"
                    0 -> "[ ]"
                    else -> "[$it]"
                }
            })
        }
    }*/

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