package com.joshmanisdabomb.lcc.world.feature.structure

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.CodedPortals
import com.joshmanisdabomb.lcc.block.RainbowGateBlock
import com.joshmanisdabomb.lcc.block.entity.RainbowGateBlockEntity
import com.joshmanisdabomb.lcc.block.shape.WorldHelper
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.directory.LCCStructureTypes
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.trait.LCCStructurePieceTrait
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Properties
import net.minecraft.structure.*
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor
import net.minecraft.structure.processor.ProtectedBlocksStructureProcessor
import net.minecraft.structure.processor.RuleStructureProcessor
import net.minecraft.structure.processor.StructureProcessorRule
import net.minecraft.structure.rule.AlwaysTrueRuleTest
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.tag.BlockTags
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.Util
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.StructureTerrainAdaptation
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.structure.Structure
import java.util.*

class BifrostShrineStructure(config: Config) : Structure(config) {

    class Piece : SimpleStructurePiece, LCCStructurePieceTrait {

        constructor(manager: StructureTemplateManager, template: Identifier, pos: BlockPos, rotation: BlockRotation, mirror: BlockMirror) : super(LCCStructurePieceTypes.bifrost_shrine, 0, manager, template, template.toString(), getData(rotation, mirror, manager.getTemplate(template).orElseThrow()), pos) {

        }

        constructor(manager: StructureTemplateManager, nbt: NbtCompound) : super(LCCStructurePieceTypes.bifrost_shrine, nbt, manager, { getData(BlockRotation.valueOf(nbt.getString("Rot")), BlockMirror.valueOf(nbt.getString("Mir")), manager.getTemplate(it).orElseThrow()) }) {

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
                "Gate1", "Gate2" -> {
                    val destinations = LCCComponents.portal_destinations.maybeGet(world.levelProperties).orElseThrow()
                    destinations.init(world.toServerWorld())
                    val origin = ChunkPos(this.pos.add(template.size.x.div(2), 0, template.size.z.div(2)))
                    val code = destinations.getCode(origin) ?: CodedPortals.RainbowCodedPortals.randomCode(random)
                    val first = metadata == "Gate1"
                    val half = code.split("-").reversed().map(if (first) String::first else String::last)
                    world.setBlockState(pos, world.getBlockState(pos.up()), Block.FORCE_STATE or Block.NO_REDRAW)
                    for (i in 0..2) {
                        val pos2 = pos.down(i+1)
                        var state = world.getBlockState(pos2)
                        if (i == 0 && first) {
                            state = state.with(RainbowGateBlock.type, RainbowGateBlock.RainbowGateState.MAIN)
                            val gate = world.getBlockEntity(pos2) as? RainbowGateBlockEntity ?: continue
                            gate.assignCode(code)
                        }
                        else state = state.with(RainbowGateBlock.type, RainbowGateBlock.RainbowGateState.COMPLETE)
                        world.setBlockState(pos2, state.with(RainbowGateBlock.symbol, half[i].digitToInt().plus(1)), Block.FORCE_STATE or Block.NO_REDRAW)
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
            return BlockBox.create(center.add(-5, 0, -5), center.add(5, template.size.y, 5))
        }

        companion object {

            private fun getData(rot: BlockRotation, mirror: BlockMirror, template: StructureTemplate): StructurePlacementData {
                return StructurePlacementData().setRotation(rot).setMirror(mirror).setPosition(BlockPos(template.size.x.div(2), 0, template.size.z.div(2))).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS).addProcessor(tree_leaves).addProcessor(tree_wood).addProcessor(irreplaceable)
            }

        }

    }

    override fun getStructurePosition(context: Context): Optional<StructurePosition> {
        val id = Util.getRandom(templates, context.random)
        val column = context.chunkGenerator.getColumnSample(context.chunkPos.startX, context.chunkPos.startZ, context.world, context.noiseConfig)
        val template = context.structureTemplateManager.getTemplate(id).orElse(null) ?: return Optional.empty()

        val y: Int
        val zones = WorldHelper.getZones(column, context.chunkGenerator).filter { it.state.isAir && it.start != -64 && !context.world.isOutOfHeightLimit(it.start + template.size.y) }
        val spaces = zones.filter { it.height >= template.size.y }
        val space = Util.getRandomOrEmpty(spaces, context.random).orElse(null)
        if (space != null) {
            y = space.start
        } else {
            val other = Util.getRandomOrEmpty(zones, context.random).orElse(null)
            if (other != null) {
                y = other.start
            } else {
                y = context.random.nextInt(128) + 64
            }
        }

        val pos = BlockPos(context.chunkPos.startX + 8, y, context.chunkPos.startZ + 8)
        return Optional.of(StructurePosition(pos) {
            it.addPiece(Piece(context.structureTemplateManager, id, pos.add(template.size.x.div(-2), 0, template.size.z.div(-2)), BlockRotation.random(context.random), BlockMirror.NONE/*Util.getRandom(BlockMirror.values(), context.random)*/))
        })
    }

    override fun getTerrainAdaptation() = StructureTerrainAdaptation.BEARD_BOX

    override fun getType() = LCCStructureTypes.bifrost_shrine

    companion object {

        val codec = createCodec(::BifrostShrineStructure)

        val templates = Array(3) { LCC.id("rainbow/bifrost_shrine_${it+1}") }

        val tree_leaves = RuleStructureProcessor(listOf(
            StructureProcessorRule(BlockMatchRuleTest(Blocks.ORANGE_CONCRETE), AlwaysTrueRuleTest.INSTANCE, LCCBlocks.ash_leaves.defaultState.with(Properties.PERSISTENT, true))
        ))

        val tree_wood = RuleStructureProcessor(listOf(
            StructureProcessorRule(BlockMatchRuleTest(Blocks.MAGENTA_CONCRETE), AlwaysTrueRuleTest.INSTANCE, LCCBlocks.ash_wood.defaultState)
        ))

        val irreplaceable = ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE)

    }

}