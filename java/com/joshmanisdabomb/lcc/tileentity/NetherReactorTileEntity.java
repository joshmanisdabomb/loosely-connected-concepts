package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.block.NetherReactorBlock;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class NetherReactorTileEntity extends TileEntity implements ITickableTileEntity {

    private int activeTicks = -1;

    public NetherReactorTileEntity() {
        super(LCCTileEntities.nether_reactor);
    }

    @Override
    public void tick() {
        if (this.getBlockState().get(NetherReactorBlock.STATE) != NetherReactorBlock.ReactorState.READY && this.activeTicks >= 0) {
            BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos();
            if (this.activeTicks >= 900) {
                return;
            } else if (this.activeTicks == 880) {
                for (int i = -1; i <= 1; i++) {
                    for (int k = -1; k <= 1; k++) {
                        this.world.setBlockState(bp.setPos(pos).move(i,-1,k), Blocks.OBSIDIAN.getDefaultState(), 3);
                    }
                }
            } else if (this.activeTicks == 860) {
                for (int i = -1; i <= 1; i++) {
                    for (int k = -1; k <= 1; k++) {
                        if (i == 0 && k == 0) this.world.setBlockState(pos, this.getBlockState().with(NetherReactorBlock.STATE, NetherReactorBlock.ReactorState.USED));
                        else this.world.setBlockState(bp.setPos(pos).move(i,0,k), Blocks.OBSIDIAN.getDefaultState(), 3);
                    }
                }
            } else if (this.activeTicks == 840) {
                for (int i = -1; i <= 1; i++) {
                    for (int k = -1; k <= 1; k++) {
                        this.world.setBlockState(bp.setPos(pos).move(i,1,k), Blocks.OBSIDIAN.getDefaultState(), 3);
                    }
                }
            } else if (this.activeTicks == 100) {
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.NORTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.NORTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.SOUTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.SOUTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
            } else if (this.activeTicks == 70) {
                this.world.setBlockState(bp.setPos(pos).move(Direction.UP), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.UP).move(Direction.NORTH), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.UP).move(Direction.EAST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.UP).move(Direction.SOUTH), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.UP).move(Direction.WEST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
            } else if (this.activeTicks == 50) {
                this.world.setBlockState(bp.setPos(pos).move(Direction.NORTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.NORTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.SOUTH).move(Direction.EAST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.SOUTH).move(Direction.WEST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
            } else if (this.activeTicks == 30) {
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.NORTH), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.EAST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.SOUTH), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
                this.world.setBlockState(bp.setPos(pos).move(Direction.DOWN).move(Direction.WEST), LCCBlocks.glowing_obsidian.getDefaultState(), 3);
            }
            this.activeTicks++;
        }
    }

    public void activate() {
        this.activeTicks = 0;
    }

    @Override
    public void read(CompoundNBT tag) {
        this.activeTicks = tag.getInt("activeTicks");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("activeTicks", this.activeTicks);
        return super.write(tag);
    }

}
