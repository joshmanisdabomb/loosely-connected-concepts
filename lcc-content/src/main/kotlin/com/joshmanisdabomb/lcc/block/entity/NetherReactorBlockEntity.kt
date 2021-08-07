package com.joshmanisdabomb.lcc.block.entity

import com.google.common.collect.ImmutableList
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.NetherReactorBlock
import com.joshmanisdabomb.lcc.block.NetherReactorBlock.Companion.reactor_state
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCCriteria
import com.joshmanisdabomb.lcc.entity.PocketZombiePigmanEntity
import com.joshmanisdabomb.lcc.extensions.NBT_INT_ARRAY
import com.joshmanisdabomb.lcc.extensions.NBT_LIST
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.boss.BossBar
import net.minecraft.entity.boss.ServerBossBar
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.structure.StructurePlacementData
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor
import net.minecraft.structure.processor.RuleStructureProcessor
import net.minecraft.structure.processor.StructureProcessorRule
import net.minecraft.structure.rule.AlwaysTrueRuleTest
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.structure.rule.RandomBlockMatchRuleTest
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*
import kotlin.math.ceil
import kotlin.math.roundToInt

class NetherReactorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.nether_reactor, pos, state) {

    private var activeTicks = -1
    private val reactionVariance = FloatArray(10)
    val active get() = activeTicks in 0..860 && cachedState[reactor_state] === NetherReactorBlock.NetherReactorState.ACTIVE || activeTicks in 861..900 && cachedState[reactor_state] === NetherReactorBlock.NetherReactorState.USED

    var customName: Text? = null
    val name get() = customName ?: TranslatableText(LCCBlocks.nether_reactor.translationKey)

    val boss by lazy { ServerBossBar(name, BossBar.Color.YELLOW, BossBar.Style.PROGRESS).apply { percent = 0.0f } }

    var challengers: MutableList<UUID>? = null
    val pigmen: MutableList<UUID> = mutableListOf()

    fun activate() {
        val world = world as? ServerWorld ?: return

        activeTicks = 0
        for (v in 0..9) {
            reactionVariance[v] = world.random.nextFloat()
        }

        world.timeOfDay = 24000 * ceil(world.timeOfDay / 24000f).toLong() + 14000

        val netherSpire = world.structureManager.getStructure(nether_spire) ?: return
        val size = netherSpire.size
        playerTracking(world)
        netherSpire.place(world, pos.add(-size.x / 2, -1, -size.z / 2), pos.add(-size.x / 2, -1, -size.z / 2), StructurePlacementData().setRandom(world.random).addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS).addProcessor(nether_spire_air).addProcessor(nether_spire_always_netherrack), world.random, 3)
        val bp = BlockPos.Mutable()
        for (i in -8..8) {
            for (k in -8..8) {
                world.setBlockState(bp.set(pos).move(i, -2, k), Blocks.NETHERRACK.defaultState)
                world.setBlockState(bp.set(pos).move(i, -3, k), Blocks.NETHERRACK.defaultState)
            }
        }
    }

    fun spawnItems(world: ServerWorld) {
        val loot = world.server.lootManager.getTable(LCC.id("gameplay/nether_reactor"))
        val builder = LootContext.Builder(world).random(world.random)
        for (stack in loot.generateLoot(builder.build(LootContextTypes.EMPTY))) {
            val p = (world.random.nextFloat() * 5 + 2) * if (world.random.nextBoolean()) 1 else -1
            val s = world.random.nextFloat() * 14 - 7
            val xp = world.random.nextBoolean()
            val position = Vec3d(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()).add(0.5 + if (xp) p else s, -0.5, 0.5 + if (xp) s else p)
            val i = ItemEntity(world, position.getX(), position.getY(), position.getZ(), stack)
            world.spawnEntity(i)
            i.setToDefaultPickupDelay()
        }
    }

    fun spawnPigmen(world: ServerWorld) {
        repeat (world.random.nextInt(3) + 1) {
            val p = (world.random.nextFloat() * 5 + 2) * if (world.random.nextBoolean()) 1 else -1
            val s = world.random.nextFloat() * 14 - 7
            val xp = world.random.nextBoolean()
            val pigman = PocketZombiePigmanEntity(world)
            pigman.updatePosition(pos.x + 0.5 + if (xp) p else s, pos.y - 0.5, pos.z + 0.5 + if (xp) s else p)
            pigman.initialize(world, world.getLocalDifficulty(BlockPos(pos)), SpawnReason.EVENT, null, null)
            pigman.reactorWorld = world
            pigman.reactorPos = pos
            world.spawnEntity(pigman)
            pigmen.add(pigman.uuid)
        }
    }

    fun playerTracking(world: ServerWorld) {
        if (!world.isClient) {
            val range = Box(pos.down(), pos.up(2)).expand(9.0, 0.0, 9.0)
            val players = world.server.playerManager.playerList

            if (this.activeTicks >= 0) {
                players.filter { !boss.players.contains(it) && it.world.dimension == world.dimension && range.intersects(it.boundingBox) }.forEach(boss::addPlayer)
                players.filter { boss.players.contains(it) && (it.world.dimension != world.dimension || !range.intersects(it.boundingBox)) }.forEach(boss::removePlayer)
                if (challengers == null) {
                    challengers = players.filter { it.world.dimension == world.dimension && range.intersects(it.boundingBox) }.map { it.uuid }.toMutableList()
                }
            }

            if (challengers?.isNotEmpty() == true) {
                challengers?.removeAll(players.filter { challengers?.contains(it.uuid) == true && (it.isDead || it.world.dimension != world.dimension || !range.intersects(it.boundingBox)) }.map { it.uuid })
            }
        }
    }

    override fun readNbt(tag: NbtCompound) {
        activeTicks = tag.getInt("activeTicks")

        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))
        boss.name = name

        if (tag.contains("Challengers", NBT_LIST)) {
            challengers = tag.getList("Challengers", NBT_INT_ARRAY).map { NbtHelper.toUuid(it) }.toMutableList()
        }

        super.readNbt(tag)
    }

    override fun writeNbt(tag: NbtCompound): NbtCompound {
        tag.putInt("activeTicks", activeTicks)

        if (customName != null) tag.putString("CustomName", Text.Serializer.toJson(customName))

        if (challengers?.isNotEmpty() == true) {
            tag.put("Challengers", NbtList().apply {
                challengers?.forEach {
                    this.add(NbtHelper.fromUuid(it))
                }
            })
        }

        return super.writeNbt(tag)
    }

    override fun markRemoved() {
        if (world?.isClient == false) {
            boss.isVisible = false
            boss.clearPlayers()
        }
        super.markRemoved()
    }

    fun pigmanKilled(pigman: PocketZombiePigmanEntity) {
        pigmen.remove(pigman.uuid)
    }

    companion object {

        val nether_spire = LCC.id("nether_spire")

        val nether_spire_always_netherrack = RuleStructureProcessor(ImmutableList.of(StructureProcessorRule(BlockMatchRuleTest(Blocks.LIGHT_BLUE_CONCRETE), AlwaysTrueRuleTest.INSTANCE, Blocks.NETHERRACK.defaultState)))
        val nether_spire_air = RuleStructureProcessor(ImmutableList.of(StructureProcessorRule(BlockMatchRuleTest(Blocks.MAGENTA_CONCRETE), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.defaultState)))
        val nether_spire_ignore = BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.MAGENTA_CONCRETE))
        val nether_spire_integrity = RuleStructureProcessor(ImmutableList.of(StructureProcessorRule(RandomBlockMatchRuleTest(Blocks.NETHERRACK, 0.25f), BlockMatchRuleTest(Blocks.NETHERRACK), Blocks.AIR.defaultState)))

        fun tick(world: World, pos: BlockPos, state: BlockState, entity: NetherReactorBlockEntity) {
            val sworld = world as? ServerWorld ?: return
            entity.boss.isVisible = entity.active
            if (entity.active) {
                val bp = BlockPos.Mutable()
                if (entity.activeTicks > 900) {
                    entity.activeTicks = -1
                    return
                } else if (entity.activeTicks == 900) {
                    if (!world.isClient) {
                        entity.boss.isVisible = false
                        entity.boss.clearPlayers()
                        entity.activeTicks = -1
                        val netherSpire = sworld.structureManager.getStructure(nether_spire) ?: return
                        val size = netherSpire.size
                        netherSpire.place(sworld, pos.add(-size.x / 2, -1, -size.z / 2), pos.add(-size.x / 2, -1, -size.z / 2), StructurePlacementData().setRandom(sworld.random).addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS).addProcessor(nether_spire_ignore).addProcessor(nether_spire_integrity).addProcessor(nether_spire_always_netherrack), sworld.random, 3)
                        return
                    }
                } else if (entity.activeTicks == 880) {
                    for (i in -1..1) {
                        for (k in -1..1) {
                            world.setBlockState(bp.set(pos).move(i, -1, k), Blocks.OBSIDIAN.defaultState, 3)
                        }
                    }
                } else if (entity.activeTicks == 860) {
                    for (i in -1..1) {
                        for (k in -1..1) {
                            if (i == 0 && k == 0) {
                                world.setBlockState(pos, state.with(reactor_state, NetherReactorBlock.NetherReactorState.USED), 3)
                            } else {
                                world.setBlockState(bp.set(pos).move(i, 0, k), Blocks.OBSIDIAN.defaultState, 3)
                            }
                        }
                    }
                } else if (entity.activeTicks == 840) {
                    entity.boss.color = BossBar.Color.BLUE
                    for (i in -1..1) {
                        for (k in -1..1) {
                            world.setBlockState(bp.set(pos).move(i, 1, k), Blocks.OBSIDIAN.defaultState, 3)
                        }
                    }
                } else if (entity.activeTicks == 750 + (entity.reactionVariance.get(9) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                } else if (entity.activeTicks == 680 + (entity.reactionVariance.get(8) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                    entity.spawnPigmen(sworld)
                } else if (entity.activeTicks == 610 + (entity.reactionVariance.get(7) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                } else if (entity.activeTicks == 540 + (entity.reactionVariance.get(6) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                    entity.spawnPigmen(sworld)
                } else if (entity.activeTicks == 480 + (entity.reactionVariance.get(5) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                } else if (entity.activeTicks == 410 + (entity.reactionVariance.get(4) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                    entity.spawnPigmen(sworld)
                } else if (entity.activeTicks == 360 + (entity.reactionVariance.get(3) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                } else if (entity.activeTicks == 290 + (entity.reactionVariance.get(2) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                    entity.spawnPigmen(sworld)
                } else if (entity.activeTicks == 220 + (entity.reactionVariance.get(1) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                } else if (entity.activeTicks == 150 + (entity.reactionVariance.get(0) * 60).roundToInt()) {
                    entity.spawnItems(sworld)
                    entity.spawnPigmen(sworld)
                } else if (entity.activeTicks == 130) {
                    entity.boss.color = BossBar.Color.RED
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.NORTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.NORTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.SOUTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.SOUTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.defaultState, 3)
                } else if (entity.activeTicks == 70) {
                    world.setBlockState(bp.set(pos).move(Direction.UP), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.UP).move(Direction.NORTH), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.UP).move(Direction.EAST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.UP).move(Direction.SOUTH), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.UP).move(Direction.WEST), LCCBlocks.glowing_obsidian.defaultState, 3)
                } else if (entity.activeTicks == 50) {
                    world.setBlockState(bp.set(pos).move(Direction.NORTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.NORTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.SOUTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.SOUTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.defaultState, 3)
                } else if (entity.activeTicks == 30) {
                    world.setBlockState(bp.set(pos).move(Direction.DOWN), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.NORTH), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.EAST), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.SOUTH), LCCBlocks.glowing_obsidian.defaultState, 3)
                    world.setBlockState(bp.set(pos).move(Direction.DOWN).move(Direction.WEST), LCCBlocks.glowing_obsidian.defaultState, 3)
                }
                entity.activeTicks++
                entity.boss.percent = entity.activeTicks / 900f
            }

            if (entity.challengers?.isNotEmpty() == true) {
                entity.playerTracking(sworld)
                if (state[reactor_state] == NetherReactorBlock.NetherReactorState.USED && entity.pigmen.isEmpty()) {
                    val challengers = sworld.server.playerManager.playerList.filter { entity.challengers?.contains(it.uuid) == true }
                    challengers.forEach {
                        LCCCriteria.netherReactor.trigger(it)
                    }
                    entity.challengers = null
                }
            }
        }

    }

}
