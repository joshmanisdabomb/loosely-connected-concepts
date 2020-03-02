package com.joshmanisdabomb.lcc.gen.feature;

import com.joshmanisdabomb.lcc.block.ChanneliteBlock;
import com.joshmanisdabomb.lcc.gen.biome.RainbowColorfulBiome;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class ChanneliteFeature extends Feature<NoFeatureConfig> {

    private final int minHeight;
    private final int variation;

    public ChanneliteFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config, boolean notify, int minHeight, int variation) {
        super(config, notify);
        this.minHeight = minHeight;
        this.variation = variation;
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos(pos);
        BlockPos pos2 = pos.down(2);

        if (world.getBlockState(pos2).getBlock() != LCCBlocks.sparkling_dirt) return false;

        if (!world.isRemote()) {
            DyeColor color = RainbowColorfulBiome.getColor(pos2.getX(), pos2.getY(), pos2.getZ());

            BlockState ore; double chance;
            for (int i = -4; i <= 4; i++) {
                for (int j = -4; j <= 4; j++) {
                    nextBlock:
                    for (int k = -4; k <= 4; k++) {
                        if (i >= -1 && i <= 1 && k >= -1 && k <= 1 && !(i == 0 && k == 0)) continue;
                        Block base = world.getBlockState(bp.setPos(pos2).move(i, j, k)).getBlock();

                        double distance = Math.sqrt(bp.distanceSq(pos));
                        if (base == LCCBlocks.sparkling_dirt || LCCBlocks.sparkling_grass_block.containsValue(base)) {
                            ore = LCCBlocks.sparkling_channelite_source.get(color).getDefaultState();
                            chance = 0.4 / Math.max(distance * 1.9, 1);
                        } else if (base == LCCBlocks.twilight_stone) {
                            ore = LCCBlocks.twilight_channelite_source.get(color).getDefaultState();
                            chance = 0.05 / Math.max(distance * 1.9, 1);
                        } else {
                            continue;
                        }

                        for (int l = 0; l < 4; l++) {
                            Direction d = Direction.byHorizontalIndex(l);
                            if (world.isAirBlock(bp.move(d))) continue nextBlock;
                            bp.move(d.getOpposite());
                        }

                        if (rand.nextDouble() <= chance || (i == 0 && k == 0)) {
                            if (world.isAirBlock(bp.move(0, 1, 0))) {
                                if (rand.nextInt(2) == 0) {
                                    world.setBlockState(bp.move(0, -1, 0), ore, 18);
                                    int height = (i == 0 && k == 0) ? (minHeight + variation) : (minHeight + rand.nextInt(variation + 1));

                                    for (int l = 0; l <= height; l++) {
                                        bp.move(0, 1, 0);

                                        for (int m = 0; m < 4; m++) {
                                            Direction d = Direction.byHorizontalIndex(m);
                                            if (world.getBlockState(bp.move(d)).getBlock() instanceof ChanneliteBlock) continue nextBlock;
                                            bp.move(d.getOpposite());
                                        }

                                        if (l > 0 && !world.isAirBlock(bp)) break;

                                        ChanneliteBlock.ChanneliteConnection c = ChanneliteBlock.ChanneliteConnection.BOTH;
                                        if (l == 0) c = ChanneliteBlock.ChanneliteConnection.TOP;
                                        else if (l == height) c = ChanneliteBlock.ChanneliteConnection.BOTTOM;

                                        world.setBlockState(bp, LCCBlocks.channelite.get(color).getDefaultState().with(BlockStateProperties.FACING, Direction.UP).with(ChanneliteBlock.CONNECTION, c), 18);
                                    }
                                }
                            } else {
                                world.setBlockState(bp.move(0, -1, 0), ore, 18);
                            }
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }

}