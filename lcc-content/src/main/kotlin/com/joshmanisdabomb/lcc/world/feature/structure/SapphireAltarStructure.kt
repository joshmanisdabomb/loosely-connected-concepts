package com.joshmanisdabomb.lcc.world.feature.structure

import com.joshmanisdabomb.lcc.abstracts.challenges.AltarChallenge
import com.joshmanisdabomb.lcc.block.SapphireAltarBlock
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.directory.LCCStructureTypes
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import net.minecraft.block.BlockState
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.structure.ShiftableStructurePiece
import net.minecraft.structure.StructureContext
import net.minecraft.structure.StructureTemplateManager
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.Heightmap
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.StructureTerrainAdaptation
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.structure.Structure
import java.util.*
import kotlin.math.max
import kotlin.math.min

class SapphireAltarStructure(config: Config) : Structure(config) {

    class Piece : ShiftableStructurePiece {

        val challenge: AltarChallenge
        val data: NbtCompound
        val rotation: Direction

        constructor(challenge: AltarChallenge, data: NbtCompound, random: Random, pos: BlockPos, width: Int, depth: Int, rot: Direction) : super(LCCStructurePieceTypes.sapphire_altar, pos.x, pos.y, pos.z, width, 40, depth + 3, rot) {
            this.challenge = challenge
            this.data = data
            this.rotation = rot
        }

        constructor(manager: StructureTemplateManager, nbt: NbtCompound) : super(LCCStructurePieceTypes.sapphire_altar, nbt) {
            this.challenge = LCCRegistries.altar_challenges[Identifier(nbt.getString("Challenge"))] ?: error("Invalid altar challenge for structure.")
            this.data = nbt.getCompound("ChallengeOptions")
            this.rotation = Direction.byName(nbt.getString("Rotation")) ?: error("Invalid direction for sapphire altar structure.")
            setOrientation(this.rotation)
        }

        override fun writeNbt(context: StructureContext, nbt: NbtCompound) {
            nbt.putString("Challenge", challenge.id.toString())
            nbt.put("ChallengeOptions", data)
            nbt.putString("Rotation", rotation.getName())
            super.writeNbt(context, nbt)
        }

        override fun generate(world: StructureWorldAccess, accessor: StructureAccessor, gen: ChunkGenerator, random: net.minecraft.util.math.random.Random, chunkBox: BlockBox, chunkPos: ChunkPos, pivot: BlockPos) {
            var y = pivot.y
            for (i in 0 until width) {
                for (j in 0 until depth-3) {
                    val p = this.offsetPos(i, 0, j + 3)
                    y = max(y, world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, p.x, p.z))
                }
            }
            val w = width.div(2).minus(1)
            y = min(y + 2, pivot.y + w - 1) - pivot.y
            for (i in 0 until width) {
                for (j in 0 until depth-3) {
                    addBlock(world, LCCBlocks.sapphire_altar_brick.defaultState, i, y, j + 3, boundingBox)
                    fillDownwards(world, LCCBlocks.sapphire_altar_brick.defaultState, i, y-1, j + 3, boundingBox)
                }
            }
            fill(world, boundingBox, 0, y+1, 3, width-1, y + 100, depth-1)
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    addBlock(world, LCCBlocks.sapphire_altar_brick.defaultState, i + w, y, j, boundingBox)
                    fillDownwards(world, LCCBlocks.sapphire_altar_brick.defaultState, i + w, y-1, j, boundingBox)
                }
            }
            fill(world, boundingBox, w, y+1, 0, w+2, y + 100, 2)
            for (i in 0 until width-w) {
                var blocks = 0
                for (j in 0 until 3) {
                    if (!canReplace(getBlockAt(world, w - i - 1, y - i, j, boundingBox)) || !canReplace(getBlockAt(world, w - i - 1, y + 1 - i, j, boundingBox))) {
                        blocks++
                        continue
                    }
                    addBlock(world, LCCBlocks.sapphire_altar_brick_stairs.defaultState.with(HORIZONTAL_FACING, Direction.EAST), w - i - 1, y - i, j, boundingBox)
                    fillDownwards(world, LCCBlocks.sapphire_altar_brick.defaultState, w - i - 1, y - i - 1, j, boundingBox)
                }
                if (blocks >= 3) break
            }
            for (i in 0 until width-w-3) {
                var blocks = 0
                for (j in 0 until 3) {
                    if (!canReplace(getBlockAt(world, w + i + 3, y - i, j, boundingBox)) || !canReplace(getBlockAt(world, w + i + 3, y + 1 - i, j, boundingBox))) {
                        blocks++
                        continue
                    }
                    addBlock(world, LCCBlocks.sapphire_altar_brick_stairs.defaultState.with(HORIZONTAL_FACING, Direction.WEST), w + i + 3, y - i, j, boundingBox)
                    fillDownwards(world, LCCBlocks.sapphire_altar_brick.defaultState, w + i + 3, y - i - 1, j, boundingBox)
                }
                if (blocks >= 3) break
            }
            challenge.generate(world, this, y, boundingBox, data, random)
            addBlock(world, LCCBlocks.sapphire_altar.defaultState.with(HORIZONTAL_FACING, Direction.SOUTH).with(SapphireAltarBlock.middle, SapphireAltarBlock.SapphireState.NORMAL), w + 1, y + 1, 1, boundingBox)
            (world.getBlockEntity(offsetPos(w + 1, y + 1, 1)) as? SapphireAltarBlockEntity)?.setChallenge(challenge, data)
        }

        public override fun addBlock(world: StructureWorldAccess, block: BlockState, x: Int, i: Int, j: Int, box: BlockBox) {
            super.addBlock(world, block, x, i, j, box)
        }

    }

    override fun getStructurePosition(context: Context): Optional<StructurePosition> {
        var y = context.chunkGenerator.getHeight(context.chunkPos.startX, context.chunkPos.startZ, Heightmap.Type.WORLD_SURFACE_WG, context.world, context.noiseConfig)
        if (y < context.chunkGenerator().seaLevel) y = context.chunkGenerator().seaLevel + 2
        val pos = BlockPos(context.chunkPos.startX, y, context.chunkPos.startZ)
        val rot = Util.getRandom(horizontalDirections, context.random)
        val challenge = LCCRegistries.altar_challenges.getRandom(context.random).orElseThrow().value()
        val data = challenge.initialData(context.random)
        val width = challenge.getAltarWidth(data) ?: 3
        val depth = challenge.getAltarDepth(data) ?: 3
        return Optional.of(StructurePosition(pos) {
            it.addPiece(Piece(challenge, data, context.random, pos, width, depth, rot))
        })
    }

    override fun getTerrainAdaptation() = StructureTerrainAdaptation.BEARD_BOX

    override fun getType() = LCCStructureTypes.sapphire_altar

    companion object {

        val codec = createCodec(::SapphireAltarStructure)

    }

}