package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.block.BombBoardBlock
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.world.feature.SapphireAltarStructureFeature
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Properties
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.World
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class MinesweeperAltarChallenge : AltarChallenge() {

    override fun generateOptions(random: Random, nbt: NbtCompound): NbtCompound {
        val a = random.nextInt(6).plus(3).times(2).plus(1)
        val b = random.nextInt(5).plus(4).times(2).plus(1)
        nbt.putInt("Width", max(a, b))
        nbt.putInt("Depth", min(a, b))
        nbt.putInt("Mines", (a*b).times(random.nextDouble().times(0.26).plus(0.2)).roundToInt())
        return nbt
    }

    override fun generate(world: StructureWorldAccess, piece: SapphireAltarStructureFeature.Piece, yOffset: Int, boundingBox: BlockBox, options: NbtCompound, random: Random) {
        val width = options.getInt("Width")
        val depth = options.getInt("Depth")
        for (i in 0 until width) {
            for (j in 0 until depth) {
                piece.addBlock(world, LCCBlocks.bomb_board_block.defaultState.with(Properties.AXIS, Direction.Axis.Y).with(BombBoardBlock.mine_state, 0), i+1, yOffset, j+4, boundingBox)
            }
        }
    }

    override fun start(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity, player: PlayerEntity): Boolean {
        val options = be.options
        val facing = state[Properties.HORIZONTAL_FACING].opposite

        val width = options.getInt("Width")
        //val w = width.minus(1).div(2)
        val height = options.getInt("Depth")
        val bombs = options.getInt("Mines")

        var flag = false
        foreachPlane(be.pos.offset(facing, 2).down(), facing, width+2, height+2) { p, x, y ->
            if (flag) return@foreachPlane
            val state2 = world.getBlockState(p)
            if (x == 0 || x == width+1 || y == 0 || y == height+1) {
                if (!state2.isOf(LCCBlocks.sapphire_altar_brick)) {
                    println(1); println(p); flag = true
                }
            } else {
                if (!state2.isOf(LCCBlocks.bomb_board_block) || state2[Properties.AXIS] != Direction.Axis.Y) {
                    println(2); println(p); flag = true
                }
            }
        }
        if (flag) {
            player.sendMessage(TranslatableText("block.lcc.sapphire_altar.minesweeper.malformed"), true)
            return false
        }

        val board = generateBoard(width, height, bombs, world.random)

        val pos2 = be.pos.offset(facing, 3).down()
        val bbstate = LCCBlocks.bomb_board_block.defaultState.with(Properties.AXIS, Direction.Axis.Y)
        foreachPlane(pos2, facing, width, height) { p, x, y -> world.setBlockState(p, bbstate.with(BombBoardBlock.mine_state, board[x][y].transformInt(BombBoardBlock.mine, BombBoardBlock.empty)), 18) }
        val pos3 = pos2.offset(facing, height.div(2))
        world.setBlockState(pos3, bbstate.with(BombBoardBlock.mine_state, LCCBlocks.bomb_board_block.getAdjacentMines(world, bbstate, pos3)))
        return true
    }

    fun generateBoard(width: Int, height: Int, bombs: Int, random: Random): List<List<Boolean>> {
        val board = MutableList(width) { MutableList(height) { false } }
        for (i in 0 until bombs) {
            for (attempt in 0 until 40) {
                val x = random.nextInt(width)
                val y = random.nextInt(height)

                if (board[x][y]) continue

                val xd = abs(x - width.div(2))
                val yd = abs(y - height.div(2))
                if (xd <= 1 && yd <= 1) continue
                if (xd <= 4 && yd <= 4 && random.nextInt(10-(xd+yd)) != 0) continue

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

}