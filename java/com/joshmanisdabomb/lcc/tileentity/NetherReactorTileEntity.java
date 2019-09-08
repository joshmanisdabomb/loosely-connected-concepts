package com.joshmanisdabomb.lcc.tileentity;

import com.google.common.collect.ImmutableList;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.NetherReactorBlock;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.ServerBossInfo;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class NetherReactorTileEntity extends TileEntity implements ITickableTileEntity {

    private int activeTicks = -1;

    public static final ResourceLocation NETHER_SPIRE = new ResourceLocation(LCC.MODID, "nether_spire");
    public static final StructureProcessor NETHER_SPIRE_ALWAYS_NETHERRACK = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new BlockMatchRuleTest(Blocks.LIGHT_BLUE_CONCRETE), AlwaysTrueRuleTest.INSTANCE, Blocks.NETHERRACK.getDefaultState())));
    public static final StructureProcessor NETHER_SPIRE_AIR = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new BlockMatchRuleTest(Blocks.MAGENTA_CONCRETE), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState())));
    public static final StructureProcessor NETHER_SPIRE_IGNORE = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.MAGENTA_CONCRETE));
    public static final StructureProcessor NETHER_SPIRE_INTEGRITY = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.NETHERRACK, 0.2F), new BlockMatchRuleTest(Blocks.NETHERRACK), Blocks.AIR.getDefaultState())));

    private final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS);

    private ITextComponent customName;

    public NetherReactorTileEntity() {
        super(LCCTileEntities.nether_reactor);
    }

    @Override
    public void tick() {
        boolean active = (this.activeTicks >= 0 && this.activeTicks <= 860 && this.getBlockState().get(NetherReactorBlock.STATE) == NetherReactorBlock.ReactorState.ACTIVE) || (this.activeTicks > 860 && this.activeTicks <= 900 && this.getBlockState().get(NetherReactorBlock.STATE) == NetherReactorBlock.ReactorState.USED);
        this.bossInfo.setVisible(active);
        if (active) {
            BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos();
            if (this.activeTicks > 900) {
                this.activeTicks = -1;
                return;
            } else if (this.activeTicks == 900) {
                if (!world.isRemote) {
                    this.bossInfo.setVisible(false);
                    this.bossInfo.removeAllPlayers();

                    Template netherSpire = ((ServerWorld) world).getStructureTemplateManager().getTemplate(NETHER_SPIRE);
                    BlockPos size = netherSpire.getSize();
                    netherSpire.addBlocksToWorld(world, pos.add(-size.getX() / 2, -1, -size.getZ() / 2), new PlacementSettings().setRandom(world.rand).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK).addProcessor(NETHER_SPIRE_IGNORE).addProcessor(NETHER_SPIRE_INTEGRITY).addProcessor(NETHER_SPIRE_ALWAYS_NETHERRACK), 3);

                    this.activeTicks = -1;
                    return;
                }
            } else if (this.activeTicks == 880) {
                for (int i = -1; i <= 1; i++) {
                    for (int k = -1; k <= 1; k++) {
                        this.world.setBlockState(bp.setPos(pos).move(i,-1,k), Blocks.OBSIDIAN.getDefaultState(), 3);
                    }
                }
            } else if (this.activeTicks == 860) {
                for (int i = -1; i <= 1; i++) {
                    for (int k = -1; k <= 1; k++) {
                        if (i == 0 && k == 0) {
                            this.world.setBlockState(pos, this.getBlockState().with(NetherReactorBlock.STATE, NetherReactorBlock.ReactorState.USED), 3);
                            this.world.setTileEntity(pos, this);
                        }
                        else this.world.setBlockState(bp.setPos(pos).move(i,0,k), Blocks.OBSIDIAN.getDefaultState(), 3);
                    }
                }
            } else if (this.activeTicks == 840) {
                for (int i = -1; i <= 1; i++) {
                    for (int k = -1; k <= 1; k++) {
                        this.world.setBlockState(bp.setPos(pos).move(i,1,k), Blocks.OBSIDIAN.getDefaultState(), 3);
                    }
                }
            } else if (this.activeTicks == 130) {
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
            bossInfo.setPercent(this.activeTicks / 900F);
            this.bossInfoTracking();
        }
    }

    private void bossInfoTracking() {
        if (!world.isRemote) {
            AxisAlignedBB range = new AxisAlignedBB(pos.down(), pos.up(2)).grow(9, 0, 9);
            MinecraftServer s = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
            s.getPlayerList().getPlayers().stream().filter(player -> !bossInfo.getPlayers().contains(player) && player.dimension == world.dimension.getType() && range.intersects(player.getBoundingBox())).forEach(this.bossInfo::addPlayer);
            s.getPlayerList().getPlayers().stream().filter(player -> bossInfo.getPlayers().contains(player) && (player.dimension != world.dimension.getType() || !range.intersects(player.getBoundingBox()))).forEach(this.bossInfo::removePlayer);
        }
    }

    public void activate() {
        this.activeTicks = 0;

        world.setDayTime((24000 * Math.round(world.getDayTime() / 24000F)) + 13000);

        if (!world.isRemote) {
            this.bossInfoTracking();

            Template netherSpire = ((ServerWorld) world).getStructureTemplateManager().getTemplate(NETHER_SPIRE);
            BlockPos size = netherSpire.getSize();
            netherSpire.addBlocksToWorld(world, pos.add(-size.getX() / 2, -1, -size.getZ() / 2), new PlacementSettings().setRandom(world.rand).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK).addProcessor(NETHER_SPIRE_AIR).addProcessor(NETHER_SPIRE_ALWAYS_NETHERRACK), 3);
            BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos();
            for (int i = -8; i <= 8; i++) {
                for (int k = -8; k <= 8; k++) {
                    world.setBlockState(bp.setPos(pos).move(i, -2, k), Blocks.NETHERRACK.getDefaultState());
                    world.setBlockState(bp.setPos(pos).move(i, -3, k), Blocks.NETHERRACK.getDefaultState());
                }
            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        this.activeTicks = tag.getInt("activeTicks");
        if (tag.contains("CustomName", 8)) {
            this.customName = ITextComponent.Serializer.fromJson(tag.getString("CustomName"));
            this.bossInfo.setName(this.getDisplayName());
        }
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("activeTicks", this.activeTicks);
        if (this.customName != null) {
            tag.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        return super.write(tag);
    }

    public ITextComponent getDisplayName() {
        return this.customName != null ? this.customName : new TranslationTextComponent("block.lcc.nether_reactor");
    }

    public void setCustomName(ITextComponent displayName) {
        this.customName = displayName;
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void remove() {
        if (!world.isRemote) {
            this.bossInfo.setVisible(false);
            this.bossInfo.removeAllPlayers();
        }
        super.remove();
    }
}
