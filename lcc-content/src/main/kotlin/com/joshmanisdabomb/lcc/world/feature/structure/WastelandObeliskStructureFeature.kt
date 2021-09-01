package com.joshmanisdabomb.lcc.world.feature.structure

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.mojang.serialization.Codec
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructurePieceWithDimensions
import net.minecraft.structure.StructureStart
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.DynamicRegistryManager
import net.minecraft.world.HeightLimitView
import net.minecraft.world.Heightmap
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.source.BiomeSource
import net.minecraft.world.gen.ChunkRandom
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.StructureFeature
import net.minecraft.world.gen.feature.StructureFeature.StructureStartFactory
import java.util.*

class WastelandObeliskStructureFeature(configCodec: Codec<DefaultFeatureConfig>) : StructureFeature<DefaultFeatureConfig>(configCodec) {

    override fun shouldStartAt(chunkGenerator: ChunkGenerator, biomeSource: BiomeSource, worldSeed: Long, random: ChunkRandom, pos: ChunkPos, biome: Biome, chunkPos: ChunkPos, config: DefaultFeatureConfig, world: HeightLimitView): Boolean {
        return biomeSource.getBiomesInArea(pos.centerX, chunkGenerator.seaLevel, pos.centerZ, 48).all { it.generationSettings.hasStructureFeature(this) }
    }

    override fun isUniformDistribution() = false

    override fun getStructureStartFactory(): StructureStartFactory<DefaultFeatureConfig> = StructureStartFactory(::Start)

    class Start(feature: StructureFeature<DefaultFeatureConfig>, pos: ChunkPos, references: Int, seed: Long) : StructureStart<DefaultFeatureConfig>(feature, pos, references, seed) {

        override fun init(registry: DynamicRegistryManager, gen: ChunkGenerator, manager: StructureManager, chunkPos: ChunkPos, biome: Biome, config: DefaultFeatureConfig, world: HeightLimitView) {
            val y = gen.getHeight(chunkPos.startX, chunkPos.startZ, Heightmap.Type.WORLD_SURFACE_WG, world)
            val pos = BlockPos(chunkPos.startX, y-1, chunkPos.startZ)
            method_35462(Piece(random, pos))
        }

    }

    class Piece : StructurePieceWithDimensions {

        constructor(random: Random, pos: BlockPos) : super(LCCStructurePieceTypes.wasteland_obelisk, pos.x, pos.y, pos.z, 5, 4, 5, Direction.NORTH)

        constructor(world: ServerWorld, nbt: NbtCompound) : super(LCCStructurePieceTypes.wasteland_obelisk, nbt) {
            setOrientation(Direction.NORTH)
        }

        override fun writeNbt(world: ServerWorld, nbt: NbtCompound) {
            super.writeNbt(world, nbt)
        }

        override fun generate(world: StructureWorldAccess, structureAccessor: StructureAccessor, chunkGenerator: ChunkGenerator, random: Random, boundingBox: BlockBox, chunkPos: ChunkPos, pos: BlockPos): Boolean {
            var y = pos.y
            fillWithOutline(world, boundingBox, 1, 0, 0, 3, 0, 4, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, false)
            fillWithOutline(world, boundingBox, 0, 0, 1, 0, 0, 3, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, false)
            fillWithOutline(world, boundingBox, 4, 0, 1, 4, 0, 3, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, false)
            fill(world, boundingBox, 0, 1, 0, 4, 3, 4)
            addBlock(world, LCCBlocks.wasteland_obelisk.defaultState.with(Properties.BOTTOM, true), 2, 1, 2, boundingBox)
            addBlock(world, LCCBlocks.wasteland_obelisk.defaultState.with(Properties.BOTTOM, false), 2, 2, 2, boundingBox)
            return true
        }

    }

}