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
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
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
        double xOffset = (gate.getX() + 0.5F) - posX;
        double zOffset = (gate.getZ() + 0.5F) - posZ;

        if (currentWorld.getDimension().getType() == LCCDimensions.rainbow.getType()) {
            x = RainbowTeleporter.shiftFromRainbow(gate.getX(), kx1, kx2);
            z = RainbowTeleporter.shiftFromRainbow(gate.getZ(), kz1, kz2);
        } else {
            x = RainbowTeleporter.shiftToRainbow(gate.getX(), kx1, kx2);
            z = RainbowTeleporter.shiftToRainbow(gate.getZ(), kz1, kz2);
        }

        ChunkPos cp = new ChunkPos(new BlockPos(x, 0, z));
        destWorld.getChunkProvider().getChunk(cp.x, cp.z, true);
        int y = destWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) + 1;
        boolean skyblock = false;
        if (y < 10) {
            y = 100;
            skyblock = true;
        }

        BlockPos pos = new BlockPos(x, y, z);

        BlockPos.Mutable bp = new BlockPos.Mutable(pos);
        int ii = 1 + (axis == Direction.Axis.X ? 2 : 0);
        int kk = 1 + (axis == Direction.Axis.Z ? 2 : 0);
        for (int i = -ii; i <= ii; i++) {
            for (int k = -kk; k <= kk; k++) {
                bp.setPos(pos).move(i, -1, k);
                if (i == -3 || i == 3 || k == -3 || k == 3) {
                    destWorld.setBlockState(bp, Blocks.DARK_PRISMARINE_SLAB.getDefaultState());
                } else {
                    destWorld.setBlockState(bp, Blocks.DARK_PRISMARINE.getDefaultState());
                }
            }
        }
        for (int j = 0; j < 4; j++) {
            for (Direction.AxisDirection ad : Direction.AxisDirection.values()) {
                Direction d = Direction.getFacingFromAxis(ad, axis);
                bp.setPos(pos).move(Direction.UP, j).move(d, 2);
                destWorld.setBlockState(bp, LCCBlocks.rainbow_gate.getDefaultState().with(RainbowGateBlock.Y, j));
            }
        }

        if (skyblock && GenUtility.allInAreaClear(destWorld, pos.getX()-ii-3, y-25, pos.getZ()-kk-3, pos.getX()+ii+3, y-2, pos.getZ()+kk+3, 0)) {
            for (int i = -ii - 1; i <= ii + 1; i++) {
                for (int k = -kk - 1; k <= kk + 1; k++) {
                    bp.setPos(pos).move(i, -2, k);
                    destWorld.setBlockState(bp, LCCBlocks.rainbow_grass_block.getDefaultState());
                }
            }
        }

        newEntity.setLocationAndAngles((x + 0.5F) - xOffset, y, (z + 0.5F) - zOffset, yaw, entity.rotationPitch);
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
