package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.CryingObsidianModel;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.data.capability.CryingObsidianCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.Arrays;
import java.util.Collection;

public class CryingObsidianBlock extends Block implements AdvancedBlockRender {

    public CryingObsidianBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (player.getHeldItem(hand).isEmpty()) {
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                boolean oldUpdate = world.isRemote && !co.isEmpty() && co.dimension == Minecraft.getInstance().world.getDimension().getType() && world.isAreaLoaded(co.pos, 0);
                BlockPos oldPos = oldUpdate ? co.pos : null;
                co.pos = pos;
                co.dimension = world.getDimension().getType();
                world.notifyBlockUpdate(pos, state, state, 3);
                if (oldUpdate) world.notifyBlockUpdate(oldPos, state, state, 3);
            });
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBakedModel newModel(Block block) {
        return new CryingObsidianModel(block);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Collection<ResourceLocation> getTextures() {
        return Arrays.asList(new ResourceLocation(LCC.MODID, "block/nostalgia/crying_obsidian_off"), new ResourceLocation(LCC.MODID, "block/nostalgia/crying_obsidian_on"));
    }

}
