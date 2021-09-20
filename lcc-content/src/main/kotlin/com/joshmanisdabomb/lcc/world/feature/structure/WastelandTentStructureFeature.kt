package com.joshmanisdabomb.lcc.world.feature.structure

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.extensions.transform
import com.mojang.serialization.Codec
import net.minecraft.block.Blocks
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.structure.SimpleStructurePiece
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructurePlacementData
import net.minecraft.structure.StructureStart
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor
import net.minecraft.structure.processor.RuleStructureProcessor
import net.minecraft.structure.processor.StructureProcessorRule
import net.minecraft.structure.rule.AlwaysTrueRuleTest
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.structure.rule.RandomBlockMatchRuleTest
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.registry.DynamicRegistryManager
import net.minecraft.world.HeightLimitView
import net.minecraft.world.Heightmap
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.StructureFeature
import net.minecraft.world.gen.feature.StructureFeature.StructureStartFactory
import java.util.*
import java.util.function.Predicate

class WastelandTentStructureFeature(codec: Codec<DefaultFeatureConfig>) : StructureFeature<DefaultFeatureConfig>(codec) {

    override fun getStructureStartFactory(): StructureStartFactory<DefaultFeatureConfig> = StructureStartFactory(::Start)

    class Start(feature: StructureFeature<DefaultFeatureConfig>, pos: ChunkPos, references: Int, seed: Long) : StructureStart<DefaultFeatureConfig>(feature, pos, references, seed) {

        override fun init(registry: DynamicRegistryManager, gen: ChunkGenerator, manager: StructureManager, chunkPos: ChunkPos, config: DefaultFeatureConfig, world: HeightLimitView, predicate: Predicate<Biome>) {
            val y = gen.getHeight(chunkPos.startX, chunkPos.startZ, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, world)
            val pos = BlockPos(chunkPos.startX, y, chunkPos.startZ)
            val rot = BlockRotation.random(random)
            val mirror = BlockMirror.values()[random.nextInt(3)]
            addPiece(Piece(manager, (random.nextInt(3) == 0).transform(template_alt, template), pos, rot, mirror))
            setBoundingBoxFromChildren()
        }

    }

    class Piece : SimpleStructurePiece {

        constructor(manager: StructureManager, template: Identifier, pos: BlockPos, rotation: BlockRotation, mirror: BlockMirror) : super(LCCStructurePieceTypes.wasteland_tent, 0, manager, template, template.toString(), getData(rotation, mirror, template), pos) {

        }

        constructor(world: ServerWorld, nbt: NbtCompound) : super(LCCStructurePieceTypes.wasteland_tent, nbt, world, { getData(BlockRotation.valueOf(nbt.getString("Rot")), BlockMirror.valueOf(nbt.getString("Mir")), it) }) {

        }

        override fun writeNbt(world: ServerWorld, nbt: NbtCompound) {
            super.writeNbt(world, nbt)
            nbt.putString("Rot", placementData.rotation.name)
            nbt.putString("Mir", placementData.mirror.name)
        }

        override fun handleMetadata(metadata: String, pos: BlockPos, world: ServerWorldAccess, random: Random, boundingBox: BlockBox) {
            when (metadata) {
                "Chest" -> {
                    world.setBlockState(pos, Blocks.AIR.defaultState, 3)
                    val blockEntity = world.getBlockEntity(pos.down())
                    if (blockEntity is ChestBlockEntity) {
                        blockEntity.setLootTable(LCC.id("chests/tent"), random.nextLong())
                    }
                }
            }
        }

        override fun generate(world: StructureWorldAccess, accessor: StructureAccessor, gen: ChunkGenerator, random: Random, boundingBox: BlockBox, chunkPos: ChunkPos, pos: BlockPos): Boolean {
            val original = this.pos
            this.pos = this.pos.down(1)
            val bool = super.generate(world, accessor, gen, random, boundingBox, chunkPos, pos)
            this.pos = original
            return bool
        }

        companion object {

            private fun getData(rot: BlockRotation, mirror: BlockMirror, template: Identifier): StructurePlacementData {
                return StructurePlacementData().setRotation(rot).setMirror(mirror).setPosition(BlockPos.ORIGIN).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS).addProcessor(tent_path).addProcessor(tent_floor).addProcessor(tent_wall)
            }

        }

    }

    companion object {

        val template = LCC.id("wasteland_tent")
        val template_alt = LCC.id("wasteland_fallen_tent")

        val tent_path = RuleStructureProcessor(listOf(
            StructureProcessorRule(RandomBlockMatchRuleTest(Blocks.MAGENTA_WOOL, 0.66f), AlwaysTrueRuleTest.INSTANCE, Blocks.DIRT_PATH.defaultState),
            StructureProcessorRule(BlockMatchRuleTest(Blocks.MAGENTA_WOOL), AlwaysTrueRuleTest.INSTANCE, LCCBlocks.cracked_mud.defaultState)
        ))
        val tent_floor = RuleStructureProcessor(listOf(
            StructureProcessorRule(RandomBlockMatchRuleTest(Blocks.LIGHT_BLUE_WOOL, 0.33f), AlwaysTrueRuleTest.INSTANCE, Blocks.DIRT_PATH.defaultState),
            StructureProcessorRule(BlockMatchRuleTest(Blocks.LIGHT_BLUE_WOOL), AlwaysTrueRuleTest.INSTANCE, LCCBlocks.cracked_mud.defaultState)
        ))
        val tent_wall = RuleStructureProcessor(listOf(
            StructureProcessorRule(BlockMatchRuleTest(Blocks.BLACKSTONE_WALL), AlwaysTrueRuleTest.INSTANCE, LCCBlocks.cobbled_fortstone_wall.defaultState)
        ))

    }

}