package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.abstracts.challenges.AltarChallenge
import com.joshmanisdabomb.lcc.block.SapphireAltarBlock
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructurePieceWithDimensions
import net.minecraft.structure.StructureStart
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.DynamicRegistryManager
import net.minecraft.world.HeightLimitView
import net.minecraft.world.Heightmap
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.StructureFeature
import net.minecraft.world.gen.feature.StructureFeature.StructureStartFactory
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.asKotlinRandom

class SapphireAltarStructureFeature(configCodec: Codec<DefaultFeatureConfig>) : StructureFeature<DefaultFeatureConfig>(configCodec) {

    override fun getStructureStartFactory(): StructureStartFactory<DefaultFeatureConfig> = StructureStartFactory(::Start)

    class Start(feature: StructureFeature<DefaultFeatureConfig>, pos: ChunkPos, references: Int, seed: Long) : StructureStart<DefaultFeatureConfig>(feature, pos, references, seed) {

        override fun init(registry: DynamicRegistryManager, gen: ChunkGenerator, manager: StructureManager, chunkPos: ChunkPos, biome: Biome, config: DefaultFeatureConfig, world: HeightLimitView) {
            val y = gen.getHeight(chunkPos.startX, chunkPos.startZ, Heightmap.Type.WORLD_SURFACE_WG, world)
            val pos = BlockPos(chunkPos.startX, y, chunkPos.startZ)
            val rot = horizontalDirections.random(random.asKotlinRandom())
            val challenge = LCCRegistries.altar_challenges.getRandom(random) ?: error("Invalid altar challenge for structure.")
            val data = challenge.initialData(random)
            val width = challenge.getAltarWidth(data) ?: 3
            val depth = challenge.getAltarDepth(data) ?: 3
            method_35462(Piece(challenge, data, random, pos, width, depth, rot))
        }

    }

    class Piece : StructurePieceWithDimensions {

        val challenge: AltarChallenge
        val data: NbtCompound
        val rotation: Direction

        constructor(challenge: AltarChallenge, data: NbtCompound, random: Random, pos: BlockPos, width: Int, depth: Int, rot: Direction) : super(LCCStructurePieceTypes.sapphire_altar, pos.x, pos.y, pos.z, width, 40, depth + 3, rot) {
            this.challenge = challenge
            this.data = data
            this.rotation = rot
        }

        constructor(world: ServerWorld, nbt: NbtCompound) : super(LCCStructurePieceTypes.sapphire_altar, nbt) {
            this.challenge = LCCRegistries.altar_challenges[Identifier(nbt.getString("Challenge"))] ?: error("Invalid altar challenge for structure.")
            this.data = nbt.getCompound("ChallengeOptions")
            this.rotation = Direction.byName(nbt.getString("Rotation")) ?: error("Invalid direction for sapphire altar structure.")
            setOrientation(this.rotation)
        }

        override fun writeNbt(world: ServerWorld, nbt: NbtCompound) {
            nbt.putString("Challenge", challenge.id.toString())
            nbt.put("ChallengeOptions", data)
            nbt.putString("Rotation", rotation.getName())
            super.writeNbt(world, nbt)
        }

        override fun generate(world: StructureWorldAccess, structureAccessor: StructureAccessor, chunkGenerator: ChunkGenerator, random: Random, boundingBox: BlockBox, chunkPos: ChunkPos, pos: BlockPos): Boolean {
            var y = pos.y
            for (i in 0 until width) {
                for (j in 0 until depth-3) {
                    val p = this.offsetPos(i, 0, j + 3)
                    y = max(y, world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, p.x, p.z))
                }
            }
            val w = width.div(2).minus(1)
            y = min(y + 2, pos.y + w - 1) - pos.y
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
            return true
        }

        public override fun addBlock(world: StructureWorldAccess, block: BlockState, x: Int, i: Int, j: Int, box: BlockBox) {
            super.addBlock(world, block, x, i, j, box)
        }

    }

}