package com.joshmanisdabomb.lcc.world.feature.structure

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.directory.LCCStructureTypes
import com.joshmanisdabomb.lcc.trait.LCCStructurePieceTrait
import net.minecraft.block.Blocks
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Properties
import net.minecraft.structure.SimpleStructurePiece
import net.minecraft.structure.StructureContext
import net.minecraft.structure.StructurePlacementData
import net.minecraft.structure.StructureTemplateManager
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor
import net.minecraft.structure.processor.RuleStructureProcessor
import net.minecraft.structure.processor.StructureProcessorRule
import net.minecraft.structure.rule.AlwaysTrueRuleTest
import net.minecraft.structure.rule.BlockMatchRuleTest
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

class BifrostShrineStructure(config: Config) : Structure(config) {

    class Piece : SimpleStructurePiece, LCCStructurePieceTrait {

        constructor(manager: StructureTemplateManager, template: Identifier, pos: BlockPos, rotation: BlockRotation, mirror: BlockMirror) : super(LCCStructurePieceTypes.bifrost_shrine, 0, manager, template, template.toString(), getData(rotation, mirror, template), pos) {

        }

        constructor(manager: StructureTemplateManager, nbt: NbtCompound) : super(LCCStructurePieceTypes.bifrost_shrine, nbt, manager, { getData(BlockRotation.valueOf(nbt.getString("Rot")), BlockMirror.valueOf(nbt.getString("Mir")), it) }) {

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
            super.generate(world, accessor, gen, random, chunkBox, chunkPos, pos)
            this.pos = original
        }

        override fun getAdaptationBoundingBox(original: BlockBox): BlockBox {
            val center = original.center.down(original.blockCountY.div(2))
            return BlockBox.create(center.add(-5, 0, -5), center.add(5, 10, 5))
        }

        companion object {

            private fun getData(rot: BlockRotation, mirror: BlockMirror, template: Identifier): StructurePlacementData {
                return StructurePlacementData().setRotation(rot).setMirror(mirror).setPosition(BlockPos.ORIGIN).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS).addProcessor(tree_leaves).addProcessor(tree_wood)
            }

        }

    }

    override fun getStructurePosition(context: Context): Optional<StructurePosition> {
        val id = template
        val y = context.chunkGenerator.getHeight(context.chunkPos.startX, context.chunkPos.startZ, Heightmap.Type.WORLD_SURFACE_WG, context.world, context.noiseConfig)
        val pos = BlockPos(context.chunkPos.startX, y, context.chunkPos.startZ)
        return Optional.of(StructurePosition(pos) {
            it.addPiece(Piece(context.structureTemplateManager, id, pos, BlockRotation.random(context.random), Util.getRandom(BlockMirror.values(), context.random)))
        })
    }

    override fun getTerrainAdaptation() = StructureTerrainAdaptation.BEARD_THIN

    override fun getType() = LCCStructureTypes.bifrost_shrine

    companion object {

        val codec = createCodec(::BifrostShrineStructure)

        val template = LCC.id("rainbow/bifrost_shrine_1")

        val tree_leaves = RuleStructureProcessor(listOf(
            StructureProcessorRule(BlockMatchRuleTest(Blocks.ORANGE_CONCRETE), AlwaysTrueRuleTest.INSTANCE, LCCBlocks.ash_leaves.defaultState.with(Properties.PERSISTENT, true))
        ))

        val tree_wood = RuleStructureProcessor(listOf(
            StructureProcessorRule(BlockMatchRuleTest(Blocks.MAGENTA_CONCRETE), AlwaysTrueRuleTest.INSTANCE, LCCBlocks.ash_wood.defaultState)
        ))

    }

}