package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.block.BouncePadBlock;
import com.joshmanisdabomb.lcc.registry.LCCParticles;
import com.joshmanisdabomb.lcc.registry.LCCSounds;
import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BouncePadExtensionPacket implements LCCPacket {

    private final BlockPos pos;
    private final float extension;

    public BouncePadExtensionPacket(BlockPos pos, float extension) {
        this.pos = pos;
        this.extension = extension;
    }

    public static void encode(BouncePadExtensionPacket msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeFloat(msg.extension);
    }

    public static BouncePadExtensionPacket decode(PacketBuffer buf) {
        return new BouncePadExtensionPacket(buf.readBlockPos(), buf.readFloat());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleClient() {
        ((BouncePadTileEntity)Minecraft.getInstance().world.getTileEntity(this.pos)).extension = this.extension;
        int setting = Minecraft.getInstance().world.getBlockState(this.pos).get(BouncePadBlock.SETTING);
        Minecraft.getInstance().world.addParticle(LCCParticles.hydrated_soul_sand_jump, false, this.pos.getX() + 0.5, this.pos.getY() + 0.4375, this.pos.getZ() + 0.5, 0.875, ((setting + 1)*4) - 2, 0.5 + ((setting + 1) * 0.2));
        Minecraft.getInstance().world.playSound(this.pos.getX() + 0.5, this.pos.getY() + 0.4375, this.pos.getZ() + 0.5, LCCSounds.block_bounce_pad_jump, SoundCategory.BLOCKS, 0.4F, 0.95F + ((4-setting)*0.05F), false);
    }

}