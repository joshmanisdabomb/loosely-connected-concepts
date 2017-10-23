package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.te.AimaggTEBouncePad;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketBouncePadTextClient implements IMessage {
	
	private int teX;
	private int teY;
	private int teZ;
	private float strengthPrimary;
	private float strengthSecondary1;
	private float strengthSecondary2;

	public AimaggPacketBouncePadTextClient() {}

	public void setTileEntityPosition(BlockPos pos) {
		this.teX = pos.getX();
		this.teY = pos.getY();
		this.teZ = pos.getZ();
	}

	public void setStrength(float s1, float s2, float s3) {
		this.strengthPrimary = s1;
		this.strengthSecondary1 = s2;
		this.strengthSecondary2 = s3;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.teX = buf.readInt();
		this.teY = buf.readInt();
		this.teZ = buf.readInt();
		this.strengthPrimary = buf.readFloat();
		this.strengthSecondary1 = buf.readFloat();
		this.strengthSecondary2 = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(teX);
		buf.writeInt(teY);
		buf.writeInt(teZ);
		buf.writeFloat(this.strengthPrimary);
		buf.writeFloat(this.strengthSecondary1);
		buf.writeFloat(this.strengthSecondary2);
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketBouncePadTextClient, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketBouncePadTextClient message, final MessageContext ctx) {
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

        private void handle(AimaggPacketBouncePadTextClient message, MessageContext ctx) {
            // This code is run on the client side. So you can do server-side calculations here.
        	TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(message.teX, message.teY, message.teZ));
        	if (te instanceof AimaggTEBouncePad) {
        		((AimaggTEBouncePad)te).setStrength(message.strengthPrimary, message.strengthSecondary1, message.strengthSecondary2);
        	}
        }
    }

}
