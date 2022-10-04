package com.joshmanisdabomb.lcc.world.feature.structure

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.directory.LCCStructureTypes
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.Blocks
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.structure.SimpleStructurePiece
import net.minecraft.structure.StructureContext
import net.minecraft.structure.StructurePlacementData
import net.minecraft.structure.StructureTemplateManager
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor
import net.minecraft.structure.processor.RuleStructureProcessor
import net.minecraft.structure.processor.StructureProcessorRule
import net.minecraft.structure.rule.AlwaysTrueRuleTest
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.structure.rule.RandomBlockMatchRuleTest
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.Heightmap
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.StructureTerrainAdaptation
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.structure.Structure
import java.util.*

class WastelandTentStructure(config: Config) : Structure(config) {

    class Piece : SimpleStructurePiece {

        constructor(manager: StructureTemplateManager, template: Identifier, pos: BlockPos, rotation: BlockRotation, mirror: BlockMirror) : super(LCCStructurePieceTypes.wasteland_tent, 0, manager, template, template.toString(), getData(rotation, mirror, template), pos) {

        }

        constructor(manager: StructureTemplateManager, nbt: NbtCompound) : super(LCCStructurePieceTypes.wasteland_tent, nbt, manager, { getData(BlockRotation.valueOf(nbt.getString("Rot")), BlockMirror.valueOf(nbt.getString("Mir")), it) }) {

        }

        override fun writeNbt(context: StructureContext, nbt: NbtCompound) {
            super.writeNbt(context, nbt)
            nbt.putString("Rot", placementData.rotation.name)
            nbt.putString("Mir", placementData.mirror.name)
        }

        override fun handleMetadata(metadata: String, pos: BlockPos, world: ServerWorldAccess, random: net.minecraft.util.math.random.Random, boundingBox: BlockBox) {
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

        override fun generate(world: StructureWorldAccess, accessor: StructureAccessor, gen: ChunkGenerator, random: net.minecraft.util.math.random.Random, chunkBox: BlockBox, chunkPos: ChunkPos, pivot: BlockPos) {
            val original = this.pos
            this.pos = this.pos.down(1)
            super.generate(world, accessor, gen, random, boundingBox, chunkPos, pos)
            this.pos = original
        }

        companion object {

            private fun getData(rot: BlockRotation, mirror: BlockMirror, template: Identifier): StructurePlacementData {
                return StructurePlacementData().setRotation(rot).setMirror(mirror).setPosition(BlockPos.ORIGIN).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS).addProcessor(tent_path).addProcessor(tent_floor).addProcessor(tent_wall)
            }

        }

    }

    override fun getStructurePosition(context: Context): Optional<StructurePosition> {
        val id = (context.random.nextInt(4) == 0).transform(template_alt, template)
        val template = context.structureTemplateManager.getTemplate(id).orElseThrow()
        if (getMinCornerHeight(context, template.size.x, template.size.z) < context.chunkGenerator().seaLevel) return Optional.empty()
        val y = context.chunkGenerator.getHeight(context.chunkPos.startX, context.chunkPos.startZ, Heightmap.Type.WORLD_SURFACE_WG, context.world, context.noiseConfig)
        val pos = BlockPos(context.chunkPos.startX, y, context.chunkPos.startZ)
        return Optional.of(StructurePosition(pos) {
            it.addPiece(Piece(context.structureTemplateManager, id, pos, BlockRotation.random(context.random), Util.getRandom(BlockMirror.values(), context.random)))
        })
    }

    override fun getTerrainAdaptation() = StructureTerrainAdaptation.BEARD_BOX

    override fun getType() = LCCStructureTypes.wasteland_tent

    companion object {

        val codec = createCodec(::WastelandTentStructure)

        val template = LCC.id("wasteland/tent")
        val template_alt = LCC.id("wasteland/fallen_tent")

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