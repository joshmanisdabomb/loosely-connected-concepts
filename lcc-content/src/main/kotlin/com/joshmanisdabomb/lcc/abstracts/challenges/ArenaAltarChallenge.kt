package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.world.feature.structure.SapphireAltarStructure
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.enums.SlabType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Properties
import net.minecraft.structure.*
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor
import net.minecraft.structure.processor.RuleStructureProcessor
import net.minecraft.structure.processor.StructureProcessorRule
import net.minecraft.structure.rule.AlwaysTrueRuleTest
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.World
import net.minecraft.world.gen.StructureAccessor
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.structure.Structure

class ArenaAltarChallenge : AltarChallenge() {

    override fun initialData(random: Random, nbt: NbtCompound): NbtCompound {
        nbt.putInt("Width", 17)
        nbt.putInt("Depth", 17)
        nbt.putInt("PillarFlags", random.nextInt(16))
        return nbt
    }

    override fun generate(world: StructureWorldAccess, piece: SapphireAltarStructure.Piece, yOffset: Int, boundingBox: BlockBox, data: NbtCompound, random: Random) = Unit

    override fun getExtraPieces(main: SapphireAltarStructure.Piece, data: NbtCompound, context: Structure.Context, pos: BlockPos, width: Int, depth: Int, rot: Direction): Iterable<StructurePiece> {
        return listOf(Piece(context.structureTemplateManager, Piece.template, main.offsetPos(1, 0, 20), main.getRotation(), main.mirror, data.getInt("PillarFlags")))
    }

    override fun start(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity, player: PlayerEntity): Boolean {
        return false
    }

    override fun verify(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState {
        return ChallengeState.ACTIVE
    }

    override fun verifyTick(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState {
        return ChallengeState.ACTIVE
    }

    class Piece : SimpleStructurePiece {

        var flags = 0

        constructor(manager: StructureTemplateManager, template: Identifier, pos: BlockPos, rotation: BlockRotation, mirror: BlockMirror, flags: Int) : super(LCCStructurePieceTypes.sapphire_altar_arena, 0, manager, template, template.toString(), getData(rotation, mirror, flags, template), pos) {
            this.flags = flags
        }

        constructor(manager: StructureTemplateManager, nbt: NbtCompound) : super(LCCStructurePieceTypes.sapphire_altar_arena, nbt, manager, { getData(BlockRotation.valueOf(nbt.getString("Rot")), BlockMirror.valueOf(nbt.getString("Mir")), nbt.getByte("PillarFlags").toInt(), it) }) {
            this.flags = nbt.getByte("PillarFlags").toInt()
        }

        override fun writeNbt(context: StructureContext, nbt: NbtCompound) {
            super.writeNbt(context, nbt)
            nbt.putString("Rot", placementData.rotation.name)
            nbt.putString("Mir", placementData.mirror.name)
            nbt.putByte("PillarFlags", flags.toByte())
        }

        override fun handleMetadata(metadata: String, pos: BlockPos, world: ServerWorldAccess, random: Random, boundingBox: BlockBox) = Unit

        override fun generate(world: StructureWorldAccess, accessor: StructureAccessor, gen: ChunkGenerator, random: Random, chunkBox: BlockBox, chunkPos: ChunkPos, pivot: BlockPos) {
            val original = this.pos
            val pos2 = BlockPos.Mutable().set(this.pos)
            while (!world.isOutOfHeightLimit(pos2) && world.getBlockState(pos2) != LCCBlocks.sapphire_altar_brick.defaultState) {
                pos2.move(0, 1, 0)
            }
            this.pos = pos2.move(0, 3, 0)
            super.generate(world, accessor, gen, random, boundingBox, chunkPos, pos)
            this.pos = original
        }

        companion object {
            val template = LCC.id("wasteland/arena")

            private fun getData(rot: BlockRotation, mirror: BlockMirror, flags: Int, template: Identifier): StructurePlacementData {
                val placement = StructurePlacementData().setRotation(rot).setMirror(mirror).setPosition(BlockPos.ORIGIN).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS)

                placement.addProcessor(getPillarProcessor(flags and 1 != 0, Blocks.LIGHT_BLUE_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE_POWDER))
                placement.addProcessor(getPillarProcessor(flags and 2 != 0, Blocks.YELLOW_CONCRETE, Blocks.YELLOW_CONCRETE_POWDER))
                placement.addProcessor(getPillarProcessor(flags and 4 != 0, Blocks.LIME_CONCRETE, Blocks.LIME_CONCRETE_POWDER))
                placement.addProcessor(getPillarProcessor(flags and 8 != 0, Blocks.PINK_CONCRETE, Blocks.PINK_CONCRETE_POWDER))

                return placement
            }

            private fun getPillarProcessor(filled: Boolean, replace: Block, replaceSlab: Block): RuleStructureProcessor {
                return RuleStructureProcessor(listOf(
                    StructureProcessorRule(BlockMatchRuleTest(replace), AlwaysTrueRuleTest.INSTANCE, filled.transform(LCCBlocks.sapphire_altar_brick.defaultState, Blocks.AIR.defaultState)),
                    StructureProcessorRule(BlockMatchRuleTest(replaceSlab), AlwaysTrueRuleTest.INSTANCE, filled.transform(LCCBlocks.sapphire_altar_brick.defaultState, LCCBlocks.sapphire_altar_brick_slab.defaultState.with(Properties.SLAB_TYPE, SlabType.TOP)))
                ))
            }
        }

    }

}
