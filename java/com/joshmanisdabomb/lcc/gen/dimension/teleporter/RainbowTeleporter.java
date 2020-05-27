package com.joshmanisdabomb.lcc.gen.dimension.teleporter;

import com.joshmanisdabomb.lcc.block.RainbowGateBlock;
import com.joshmanisdabomb.lcc.gen.world.GenUtility;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class RainbowTeleporter implements ITeleporter {

    public static final RainbowTeleporter INSTANCE = new RainbowTeleporter();

    public static final int kx1 = 7273505;
    public static final int kx2 = 267802;
    public static final int kz1 = 11693240;
    public static final int kz2 = 929481;

    private BlockPos gate;
    private Direction.Axis axis;

    public RainbowTeleporter setGateMiddle(BlockPos pos) {
        this.gate = pos;
        return this;
    }

    public RainbowTeleporter setGateAxis(Direction.Axis axis) {
        this.axis = axis;
        return this;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> defaultImpl) {
        double posX = entity.getPosX();
        double posZ = entity.getPosZ();
        Entity newEntity = defaultImpl.apply(false);

        int x;
        int z;
        double xOffset = gate.getX() - posX;
        double zOffset = gate.getZ() - posZ;

        if (currentWorld.getDimension().getType() == LCCDimensions.rainbow.getType()) {
            x = RainbowTeleporter.shiftFromRainbow(gate.getX(), kx1, kx2);
            z = RainbowTeleporter.shiftFromRainbow(gate.getZ(), kz1, kz2);
        } else {
            x = RainbowTeleporter.shiftToRainbow(gate.getX(), kx1, kx2);
            z = RainbowTeleporter.shiftToRainbow(gate.getZ(), kz1, kz2);
        }

        System.out.println(x);
        System.out.println(z);

        ChunkPos cp = new ChunkPos(new BlockPos(x, 0, z));
        destWorld.getChunkProvider().getChunk(cp.x, cp.z, true);
        int y = destWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        if (y < 10) {
            //TODO: unsafe, spawn a skyblock
            y = 100;
        }

        BlockPos pos = new BlockPos(x + xOffset, y, z + zOffset);

        BlockPos.Mutable bp = new BlockPos.Mutable(pos);
        for (int i = -1 - (axis == Direction.Axis.X ? 2 : 0); i <= 1 + (axis == Direction.Axis.X ? 2 : 0); i++) {
            for (int k = -1 - (axis == Direction.Axis.Z ? 2 : 0); k <= 1 + (axis == Direction.Axis.Z ? 2 : 0); k++) {
                bp.setPos(pos).move(i, -1, k);
                destWorld.setBlockState(bp, Blocks.DARK_PRISMARINE.getDefaultState());
            }
        }
        for (int j = 0; j < 4; j++) {
            for (Direction.AxisDirection ad : Direction.AxisDirection.values()) {
                Direction d = Direction.getFacingFromAxis(ad, axis);
                bp.setPos(pos).move(Direction.UP, j).move(d, 2);
                destWorld.setBlockState(bp, LCCBlocks.rainbow_gate.getDefaultState().with(RainbowGateBlock.Y, j));
            }
        }

        newEntity.moveToBlockPosAndAngles(pos, yaw, entity.rotationPitch);
        if (newEntity instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)newEntity).connection.setPlayerLocation(newEntity.getPosX(), newEntity.getPosY(), newEntity.getPosZ(), yaw, entity.rotationPitch);
        }
        return newEntity;
    }

    public static long getGateRandomToRainbow(BlockPos pos, int y) {
        return MathHelper.getCoordinateRandom(pos.getX(), y, pos.getZ());
    }

    public static long getGateRandomFromRainbow(BlockPos pos, int y) {
        return MathHelper.getCoordinateRandom(RainbowTeleporter.shiftFromRainbow(pos.getX(), kx1, kx2), y, RainbowTeleporter.shiftFromRainbow(pos.getZ(), kz1, kz2));
    }

    protected static int shiftToRainbow(int a, int xor1, int xor2) {
        int b = a ^ xor1;
        int c = b ^ xor2;
        return c;
    }

    protected static int shiftFromRainbow(int c, int xor1, int xor2) {
        int b = c ^ xor2;
        int a = b ^ xor1;
        return a;
    }

    public static int getCode(long r) {
        return (int)(Math.abs(r / 179) % 6);
    }

}
