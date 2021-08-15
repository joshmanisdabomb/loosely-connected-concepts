package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.block.BombBoardBlock
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.world.feature.SapphireAltarStructureFeature
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.Direction
import net.minecraft.world.StructureWorldAccess
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class MinesweeperAltarChallenge : AltarChallenge() {

    override fun generateOptions(random: Random, nbt: NbtCompound): NbtCompound {
        val a = random.nextInt(6).plus(3).times(2).plus(1)
        val b = random.nextInt(10).plus(8)
        nbt.putInt("Width", max(a, b))
        nbt.putInt("Depth", min(a, b))
        nbt.putInt("Mines", (a*b).times(random.nextDouble().times(0.26).plus(0.2)).roundToInt())
        return nbt
    }

    override fun generate(world: StructureWorldAccess, piece: SapphireAltarStructureFeature.Piece, yOffset: Int, boundingBox: BlockBox, options: NbtCompound, random: Random) {
        val width = options.getInt("Width")
        val w = width.minus(1).div(2)
        val height = options.getInt("Depth")
        val bombs = options.getInt("Mines")

        val board = MutableList(width) { MutableList(height) { false } }
        for (i in 0 until bombs) {
            for (attempt in 0 until 40) {
                val x = world.random.nextInt(width)
                val y = world.random.nextInt(height)
                if (board[x][y]) continue

                var adjacent = 0
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j == 0 && k == 0) continue
                        if (x+j < 0 || x+j >= width) continue
                        if (y+k < 0 || y+k >= height) continue
                        if (!board[x+j][y+k]) continue
                        adjacent += 1
                    }
                }

                when (adjacent) {
                    1 -> if (world.random.nextInt(2) != 0) continue
                    2 -> if (world.random.nextInt(3) != 0) continue
                    3 -> if (world.random.nextInt(4) != 0) continue
                    4 -> if (world.random.nextInt(5) != 0) continue
                    5 -> if (world.random.nextInt(6) != 0) continue
                    6 -> if (world.random.nextInt(7) != 0) continue
                    7 -> continue
                    8 -> continue
                }

                board[x][y] = true
                break
            }
        }

        for (i in 0 until width) {
            for (j in 0 until height) {
                piece.addBlock(world, LCCBlocks.bomb_board_block.defaultState.with(Properties.AXIS, Direction.Axis.Y).with(BombBoardBlock.mine_state, board[i][j].transformInt(BombBoardBlock.mine, BombBoardBlock.empty)), i+1, yOffset, j+4, boundingBox)
            }
        }
        //foreachPlane(pos.offset(dir, 3).down(), dir, width, height) { p, x, y -> world.setBlockState(p, , 18) }
    }

}