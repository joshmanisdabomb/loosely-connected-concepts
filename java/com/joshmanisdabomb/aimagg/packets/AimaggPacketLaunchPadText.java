package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketLaunchPadText implements IMessage {
	
	private int teX;
	private int teY;
	private int teZ;
	private int destX;
	private int destY;
	private int destZ;

	public AimaggPacketLaunchPadText(String modId, AimaggTELaunchPad te, int i, int j, int k) {
		this.teX = te.getPos().getX();
		this.teY = te.getPos().getY();
		this.teZ = te.getPos().getZ();
		this.destX = i;
		this.destY = j;
		this.destZ = k;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.teX = buf.readInt();
		this.teY = buf.readInt();
		this.teZ = buf.readInt();
		this.destX = buf.readInt();
		this.destY = buf.readInt();
		this.destZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(teX);
		buf.writeInt(teY);
		buf.writeInt(teZ);
		buf.writeInt(this.destX);
		buf.writeInt(this.destY);
		buf.writeInt(this.destZ);
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketLaunchPadText, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketLaunchPadText message, final MessageContext ctx) {
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

        private void handle(AimaggPacketLaunchPadText message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here.
        	TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(new BlockPos(message.teX, message.teY, message.teZ));
        	if (te instanceof AimaggTELaunchPad) {
        		((AimaggTELaunchPad)te).getTileData().setInteger("destinationx", message.destX);
        		((AimaggTELaunchPad)te).getTileData().setInteger("destinationy", message.destY);
        		((AimaggTELaunchPad)te).getTileData().setInteger("destinationz", message.destZ);
        	}
        }
    }

}
