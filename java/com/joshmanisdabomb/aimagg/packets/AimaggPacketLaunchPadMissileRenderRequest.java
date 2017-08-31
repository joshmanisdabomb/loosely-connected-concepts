package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;
import com.joshmanisdabomb.aimagg.util.MissileType;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketLaunchPadMissileRenderRequest implements IMessage {
	
	private int teX;
	private int teY;
	private int teZ;

	public AimaggPacketLaunchPadMissileRenderRequest() {}

	public void setTileEntityPosition(BlockPos pos) {
		this.teX = pos.getX();
		this.teY = pos.getY();
		this.teZ = pos.getZ();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.teX = buf.readInt();
		this.teY = buf.readInt();
		this.teZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(teX);
		buf.writeInt(teY);
		buf.writeInt(teZ);
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketLaunchPadMissileRenderRequest, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketLaunchPadMissileRenderRequest message, final MessageContext ctx) {
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

        private void handle(AimaggPacketLaunchPadMissileRenderRequest message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here.
        	TileEntity te = ctx.getServerHandler().player.world.getTileEntity(new BlockPos(message.teX, message.teY, message.teZ));

        	if (te instanceof AimaggTELaunchPad) {
        		AimaggPacketLaunchPadMissileRenderResponse packet = new AimaggPacketLaunchPadMissileRenderResponse();
        		packet.setTileEntityPosition(te.getPos());
        		packet.setMissileType(((AimaggTELaunchPad)te).getMissileType());
        		if (ctx.getServerHandler().player != null) {
        			AimaggPacketHandler.INSTANCE.sendTo(packet, ctx.getServerHandler().player);
        		}
        	}
        }
    }

}
