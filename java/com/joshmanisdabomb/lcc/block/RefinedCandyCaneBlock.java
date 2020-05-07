package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class RefinedCandyCaneBlock extends PillarBlock {

    public RefinedCandyCaneBlock(Properties p) {
        super(p);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        Block b = state.getBlock();
        if (stack.getItem() instanceof PickaxeItem) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote) {
                if (LCCTags.COLORED_CANDY_CANE_COATING.contains(b)) {
                    world.setBlockState(pos, LCCBlocks.stripped_candy_cane_coating.getDefaultState().with(BlockStateProperties.AXIS, state.get(BlockStateProperties.AXIS)), 11);
                } else if (LCCTags.REFINED_COLORED_CANDY_CANE.contains(b)) {
                    world.setBlockState(pos, LCCBlocks.refined_stripped_candy_cane.getDefaultState().with(BlockStateProperties.AXIS, state.get(BlockStateProperties.AXIS)), 11);
                } else if (LCCTags.REFINED_COLORED_CANDY_CANE_COATING.contains(b)) {
                    world.setBlockState(pos, LCCBlocks.refined_stripped_candy_cane_coating.getDefaultState().with(BlockStateProperties.AXIS, state.get(BlockStateProperties.AXIS)), 11);
                } else {
                    world.setBlockState(pos, LCCBlocks.stripped_candy_cane.getDefaultState().with(BlockStateProperties.AXIS, state.get(BlockStateProperties.AXIS)), 11);
                }
                stack.damageItem(1, player, (p_220040_1_) -> p_220040_1_.sendBreakAnimation(hand));
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

}
