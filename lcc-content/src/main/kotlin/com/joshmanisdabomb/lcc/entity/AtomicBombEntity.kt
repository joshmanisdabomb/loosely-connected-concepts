package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.block.AtomicBombBlock
import com.joshmanisdabomb.lcc.block.entity.AtomicBombBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCChunkTickets
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.inventory.AtomicBombInventory
import com.joshmanisdabomb.lcc.trait.LCCEntityTrait
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.MovementType
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.AutomaticItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Unit
import net.minecraft.util.math.Box
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.GameRules
import net.minecraft.world.World
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class AtomicBombEntity(type: EntityType<*>, world: World) : Entity(type, world), LCCEntityTrait {

    constructor(world: World, x: Double, y: Double, z: Double, facing: Direction, be: AtomicBombBlockEntity?) : this(LCCEntities.atomic_bomb, world) {
        facing.also { _facing = it; this.facing = it }
        updatePosition(x, y, z)
        velocity = Vec3d.ZERO
        this.be = be?.let {
            val nbt = NbtCompound()
            it.writeNbt(nbt)
            nbt
        }
        prevX = x
        prevY = y
        prevZ = z
    }

    constructor(world: World, x: Double, y: Double, z: Double, facing: Direction, be: AtomicBombBlockEntity?, igniter: LivingEntity?) : this(world, x, y, z, facing, be) {
        updatePosition(x, y, z)
        val d = world.random.nextDouble() * 6.2831854820251465
        this.setVelocity(-sin(d) * 0.02, 0.20000000298023224, -cos(d) * 0.02)
        true.also { _active = it; this.active = it }
        placedBy = igniter
    }

    var be: NbtCompound? = null

    var placedBy: LivingEntity? = null

    private var _active = false
    private var _fuse = 1200
    private var _facing = Direction.NORTH

    var active by active_data
    var fuse by fuse_data
    var facing by facing_data

    private var fallTime = 0

    val inventory get() = be?.let { AtomicBombInventory().apply { Inventories.readNbt(it, list) } }

    override fun initDataTracker() {
        dataTracker.startTracking(active_data, false)
        dataTracker.startTracking(fuse_data, 1200)
        dataTracker.startTracking(facing_data, Direction.NORTH)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        if (data == active_data) _active = dataTracker.get(active_data)
        if (data == fuse_data) _fuse = dataTracker.get(fuse_data)
        if (data == facing_data) {
            _facing = dataTracker.get(facing_data)
            calculateDimensions()
        }
    }

    override fun readCustomDataFromNbt(tag: NbtCompound) {
        tag.getBoolean("Active").apply { _active = this; active = this }
        tag.getShort("Fuse").run { if (this > 0) this else 1200 }.toInt().apply { _fuse = this; fuse = this }
        tag.getByte("Facing").run { Direction.fromHorizontal(this.toInt()) }.apply { _facing = this; facing = this }
        tag.getShort("Fall").toInt().apply { fallTime = this }
        if (tag.contains("TileEntityData", NBT_COMPOUND)) tag.getCompound("TileEntityData").apply { be = this }
    }

    override fun writeCustomDataToNbt(tag: NbtCompound) {
        tag.putBoolean("Active", _active)
        tag.putShort("Fuse", _fuse.toShort())
        tag.putByte("Facing", _facing.horizontal.toByte())
        tag.putShort("Fall", fallTime.toShort())
        be?.also { tag.put("TileEntityData", it) }
    }

    override fun createSpawnPacket() = lcc_createSpawnPacket()// { it.writeByte(_facing.horizontal) }

    override fun tick() {
        prevX = x
        prevY = y
        prevZ = z
        if (!hasNoGravity()) velocity = velocity.add(0.0, -0.08, 0.0)
        move(MovementType.SELF, velocity)
        velocity = velocity.multiply(0.98)
        if (onGround) velocity = velocity.multiply(0.12, -0.5, 0.12)
        if (_active) {
            if (!world.isClient) {
                for (i in -1..1) {
                    for (k in -1..1) {
                        if (i != 0 && k != 0) {
                            (world as ServerWorld).chunkManager.removeTicket(LCCChunkTickets.nuclear, ChunkPos(chunkPos.x + i, chunkPos.z + k), 17, Unit.INSTANCE)
                        }
                    }
                }
                (world as ServerWorld).chunkManager.addTicket(LCCChunkTickets.nuclear, chunkPos, 17, Unit.INSTANCE)
            }
            --fuse
            if (fuse <= 0) {
                discard()
                if (!world.isClient) inventory?.also { explode(it.uraniumCount) }
            } else {
                this.updateWaterState()
                if (fuse % 20 == 19) {
                    if (!world.isClient) world.playSound(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, LCCSounds.atomic_bomb_fuse, SoundCategory.BLOCKS, 5.0F, 1.2F - fuse.div(1200f).times(0.2f))
                }
            }
        } else {
            if (!world.isClient) {
                val bp = blockPos
                if (onGround) {
                    var movingPiston = false
                    var replaceable = true
                    var setBlockState: Boolean
                    for (i in -1..1) {
                        val bp2 = bp.offset(_facing, i)
                        val state = world.getBlockState(bp)
                        movingPiston = movingPiston || state.block === Blocks.MOVING_PISTON
                        replaceable = replaceable && state.canReplace(AutomaticItemPlacementContext(world, bp2, Direction.DOWN, ItemStack.EMPTY, Direction.UP))
                    }
                    if (!movingPiston) {
                        if (replaceable) {
                            val newState = LCCBlocks.atomic_bomb.defaultState.with(Properties.HORIZONTAL_FACING, _facing)
                            setBlockState = world.setBlockState(bp, newState, 3)
                            setBlockState = setBlockState && world.setBlockState(bp.offset(_facing), newState.with(AtomicBombBlock.segment, AtomicBombBlock.AtomicBombSegment.HEAD), 3)
                            setBlockState = setBlockState && world.setBlockState(bp.offset(_facing.opposite), newState.with(AtomicBombBlock.segment, AtomicBombBlock.AtomicBombSegment.TAIL), 3)
                            if (setBlockState) {
                                be?.also {
                                    (world.getBlockEntity(bp) as? AtomicBombBlockEntity)?.apply {
                                        val nbt = NbtCompound()
                                        this.writeNbt(nbt)
                                        val data = nbt.copyFrom(it)
                                        data.putInt("x", bp.x)
                                        data.putInt("y", bp.y)
                                        data.putInt("z", bp.z)
                                        this.readNbt(data)
                                        this.markDirty()
                                    }
                                }
                                this.discard()
                            } else {
                                this.spawnDrops()
                                this.discard()
                            }
                        } else {
                            this.spawnDrops()
                            this.discard()
                        }
                    }
                } else {
                    if (fallTime > 100 && (bp.y < 1 || bp.y > 256) || fallTime > 600) {
                        this.spawnDrops()
                        this.discard()
                    }
                }
                fallTime++
            }
        }
    }

    override fun interact(player: PlayerEntity, hand: Hand): ActionResult {
        if (this.active) {
            val stack = player.getStackInHand(hand)
            if (!player.isSurvival && stack.isOf(Items.FLINT_AND_STEEL)) {
                if (!world.isClient) explode(inventory?.uraniumCount ?: return ActionResult.PASS)
                return ActionResult.SUCCESS
            } else if (stack.isIn(FabricToolTags.SHEARS)) {
                world.playSound(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, LCCSounds.atomic_bomb_defuse, SoundCategory.BLOCKS, 1.0F, 1.0F)
                player.swingHand(hand)
                if (!world.isClient) {
                    spawnDrops()
                    discard()
                    stack.damage(1, player) { it.sendToolBreakStatus(hand) }
                    return ActionResult.SUCCESS
                }
            }
        }
        return ActionResult.PASS
    }

    private fun spawnDrops() {
        if (!world.gameRules.getBoolean(GameRules.DO_ENTITY_DROPS)) return

        dropItem(LCCBlocks.atomic_bomb)
        inventory?.forEach(this@AtomicBombEntity::dropStack)
    }

    private fun explode(uranium: Int) {
        NuclearExplosionEntity(world, x, y.plus(0.5), z, placedBy).also {
            it.radius = NuclearUtil.getExplosionRadiusFromUranium(uranium)
            it.lifetime = NuclearUtil.getExplosionLifetimeFromUranium(uranium)
            world.spawnEntity(it)
        }
        discard()
        (world as ServerWorld).chunkManager.addTicket(LCCChunkTickets.nuclear, chunkPos, 17, Unit.INSTANCE)
    }

    override fun calculateBoundingBox() = expandBoundingBox(super.calculateBoundingBox())

    private fun expandBoundingBox(default: Box): Box? {
        dataTracker.get(facing_data).apply { return default.expand(abs(offsetX.toDouble()), 0.0, abs(offsetZ.toDouble())) }
    }

    override fun isCollidable() = isAlive

    override fun collides() = isAlive

    override fun isPushable() = false

    override fun getName(): Text {
        customName?.apply { return this }
        be?.also {
            if (it.contains("CustomName", NBT_STRING)) return Text.Serializer.fromJson(it.getString("CustomName")) as Text
        }
        return super.getName()
    }

    override fun remove(reason: RemovalReason) {
        if (!world.isClient) (world as? ServerWorld)?.chunkManager?.removeTicket(LCCChunkTickets.nuclear, chunkPos, 17, Unit.INSTANCE)
        super.remove(reason)
    }

    companion object {
        val active_data = DataTracker.registerData(AtomicBombEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
        val fuse_data = DataTracker.registerData(AtomicBombEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        val facing_data = DataTracker.registerData(AtomicBombEntity::class.java, TrackedDataHandlerRegistry.FACING)
    }

}