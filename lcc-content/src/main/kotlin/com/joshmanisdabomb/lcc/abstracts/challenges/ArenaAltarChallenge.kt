package com.joshmanisdabomb.lcc.abstracts.challenges

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.SapphireAltarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCStructurePieceTypes
import com.joshmanisdabomb.lcc.extensions.isSurvival
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
import net.minecraft.text.Text
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.*
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
        val facing = state.get(Properties.HORIZONTAL_FACING)
        val xz1 = pos.offset(facing, 3).offset(facing.rotateYClockwise(), 8)
        val xz2 = pos.offset(facing, 20).offset(facing.rotateYCounterclockwise(), 8)
        if (!verifyAltar(world, facing.opposite, pos, be.data.getInt("PillarFlags"))) {
            player.sendMessage(Text.translatable("block.lcc.sapphire_altar.arena.malformed"), true)
            return false
        }
        if (player.isSurvival && !Box(xz1, xz2.up(3)).intersects(player.boundingBox)) {
            player.sendMessage(Text.translatable("block.lcc.sapphire_altar.arena.player_position"), true)
            return false
        }
        return true
    }

    override fun verify(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState {
        return ChallengeState.ACTIVE
    }

    override fun verifyTick(world: World, state: BlockState, pos: BlockPos, be: SapphireAltarBlockEntity): ChallengeState {
        return ChallengeState.ACTIVE
    }

    fun verifyAltar(world: World, facing: Direction, origin: BlockPos, flags: Int): Boolean {
        var flag = false
        foreachPlane(origin.offset(facing, 3).down(), facing, 17, 17) { p, x, y ->
            if (flag) return@foreachPlane
            val state2 = world.getBlockState(p)
            if (!state2.isOf(LCCBlocks.sapphire_altar_brick)) flag = true
            if (y == 0) {
                if (!verifyAltarFrontWall(world, p, x)) flag = true
            } else if (y == 16) {
                if (!verifyAltarWall(world, p, x)) flag = true
            } else if (x == 0) {
                if (!verifyAltarWall(world, p, y)) flag = true
            } else if (x == 16) {
                if (!verifyAltarWall(world, p, y)) flag = true
            } else {
                if (!verifyAltarInside(world, p, x, y, flags)) flag = true
            }
        }
        return !flag
    }

    fun verifyAltarWall(world: World, pos: BlockPos, x: Int): Boolean {
        if (x == 2 || x == 5 || x == 8 || x == 11 || x == 14) {
            for (i in 1..2) {
                if (!world.isAir(pos.up(i))) return false
            }
            if (x == 5 || x == 11) {
                if (world.getBlockState(pos.up(3)) != top_slab) return false
            } else if (x == 8) {
                if (!world.isAir(pos.up(3))) return false
            }
        } else {
            for (i in 1..3) {
                if (!world.getBlockState(pos.up(i)).isOf(LCCBlocks.sapphire_altar_brick)) return false
            }
            val state3 = world.getBlockState(pos.up(4))
            if (x != 8 && !state3.isOf(LCCBlocks.sapphire_altar_brick) && state3 != bottom_slab) return false
            if (x == 8 && state3 != top_slab) return false
        }
        return true
    }

    fun verifyAltarFrontWall(world: World, pos: BlockPos, x: Int): Boolean {
        if (x == 2 || x == 4 || x in 7..9 || x == 12 || x == 14) {
            for (i in 1..2) {
                if (!world.isAir(pos.up(i))) return false
            }
            if (x == 4 || x == 12) {
                if (world.getBlockState(pos.up(3)) != top_slab) return false
            } else if (x in 7..9) {
                if (!world.isAir(pos.up(3))) return false
                if (x == 8 && world.getBlockState(pos.up(4)) != top_slab) return false
            }
            val state3 = world.getBlockState(pos.up(4))
            if (x != 8 && !state3.isOf(LCCBlocks.sapphire_altar_brick) && state3 != bottom_slab) return false
            if (x == 8 && state3 != top_slab) return false
        } else {
            for (i in 1..3) {
                if (!world.getBlockState(pos.up(i)).isOf(LCCBlocks.sapphire_altar_brick)) return false
            }
            val state3 = world.getBlockState(pos.up(4))
            if (!state3.isOf(LCCBlocks.sapphire_altar_brick) && state3 != bottom_slab) return false
        }
        return true
    }

    fun verifyAltarInside(world: World, pos: BlockPos, x: Int, y: Int, flags: Int): Boolean {
        var pillar = false
        if (x == 8 && (y in 6..7 || y == 10)) pillar = true
        if (y == 8 && (x == 6 || x == 10)) pillar = true
        if (flags and 1 != 0 && (x == 2 || x == 4 || x == 12 || x == 14) && (y == 2 || y == 4 || y == 12 || y == 14)) pillar = true
        if (flags and 2 != 0 && (x == 6 || x == 10) && (y == 6 || y == 10)) pillar = true
        if (flags and 4 != 0 && y == 8 && (x == 3 || x == 13)) pillar = true
        if (flags and 4 != 0 && x == 8 && (y == 3 || y == 13)) pillar = true
        if (flags and 8 != 0 && (x == 7 || x == 9) && (y == 6 || y == 10)) pillar = true
        if (pillar) {
            for (i in 1..4) {
                if (!world.getBlockState(pos.up(i)).isOf(LCCBlocks.sapphire_altar_brick)) return false.also { println(1) }
            }
        } else {
            if (x == 8 && y == 8) {
                if (!world.isAir(pos.up(1))) return false.also { println(2) }
                if (!world.getBlockState(pos.up(2)).isOf(LCCBlocks.sapphire_altar_brick_stairs)) return false.also { println(3) }
                for (i in 3..4) {
                    if (!world.getBlockState(pos.up(i)).isOf(LCCBlocks.sapphire_altar_brick)) return false.also { println(x); println(y); println(4) }
                }
            } else if (x in 6..10 && y in 6..10 && !((x == 6 || x == 10) && (y == 6 || y == 10))) {
                for (i in 1..2) {
                    if (!world.isAir(pos.up(i))) return false.also { println(5) }
                }
                if (world.getBlockState(pos.up(3)) != top_slab) return false.also { println(6) }
            } else {
                for (i in 1..3) {
                    if (!world.isAir(pos.up(i))) return false.also { println(x); println(y); println(7) }
                }
            }
            val state3 = world.getBlockState(pos.up(4))
            if (x in 6..10 && y in 6..10) {
                if (!state3.isOf(LCCBlocks.sapphire_altar_brick)) return false.also { println(8) }
            } else {
                if (state3 != top_slab) return false.also { println(x); println(y); println(9) }
            }
        }
        return true
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
            this.pos = pos.up(3)
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

    companion object {
        private val top_slab = LCCBlocks.sapphire_altar_brick_slab.defaultState.with(Properties.SLAB_TYPE, SlabType.TOP)
        private val bottom_slab = LCCBlocks.sapphire_altar_brick_slab.defaultState.with(Properties.SLAB_TYPE, SlabType.BOTTOM)
    }

}
