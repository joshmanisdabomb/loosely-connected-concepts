package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.AimaggDimension;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;
import com.joshmanisdabomb.aimagg.world.AimaggTeleporter;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketRainbowPadTeleport implements IMessage {

	public AimaggPacketRainbowPadTeleport() {}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketRainbowPadTeleport, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketRainbowPadTeleport message, final MessageContext ctx) {
            // Always use a construct like this to actually handle your message. This ensures that
            // your 'handle' code is run on the main Minecraft thread. 'onMessage' itself
            // is called on the networking thread so it is not safe to do a lot of things
            // here.
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(new Runnable() {
            	
            	@Override
            	public void run()
            	{
            		handle(message, ctx);
            	}
            	
            });
            
            return null;
        }

        private void handle(AimaggPacketRainbowPadTeleport message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here.
        	ctx.getServerHandler().player.getServer().getPlayerList().transferPlayerToDimension(ctx.getServerHandler().player, AimaggDimension.RAINBOW.getDimensionID(), new AimaggTeleporter(ctx.getServerHandler().player.getServer().getWorld(AimaggDimension.RAINBOW.getDimensionID())));
        }
    }

}
