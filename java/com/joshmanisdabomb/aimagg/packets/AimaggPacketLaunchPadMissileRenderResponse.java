package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.data.MissileType;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketLaunchPadMissileRenderResponse implements IMessage {
	
	private int teX;
	private int teY;
	private int teZ;
	private MissileType mt;

	public AimaggPacketLaunchPadMissileRenderResponse() {}

	public void setTileEntityPosition(BlockPos pos) {
		this.teX = pos.getX();
		this.teY = pos.getY();
		this.teZ = pos.getZ();
	}

	public void setMissileType(MissileType mt) {
		this.mt = mt;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.teX = buf.readInt();
		this.teY = buf.readInt();
		this.teZ = buf.readInt();
		int mtmeta = buf.readInt();
		this.mt = mtmeta == -1 ? null : MissileType.getFromMetadata(mtmeta);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(teX);
		buf.writeInt(teY);
		buf.writeInt(teZ);
		buf.writeInt(mt == null ? -1 : mt.getMetadata());
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketLaunchPadMissileRenderResponse, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketLaunchPadMissileRenderResponse message, final MessageContext ctx) {
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

        private void handle(AimaggPacketLaunchPadMissileRenderResponse message, MessageContext ctx) {
            // This code is run on the client side. So you can do client-side calculations here.
        	TileEntity te = Minecraft.getMinecraft().player.world.getTileEntity(new BlockPos(message.teX, message.teY, message.teZ));
        	if (te instanceof AimaggTELaunchPad) {
        		if (message.mt == null) {
        			((AimaggTELaunchPad)te).setInventorySlotContents(0, ItemStack.EMPTY);
        		} else {
        			((AimaggTELaunchPad)te).setInventorySlotContents(0, new ItemStack(AimaggItems.missile, 1, message.mt.getMetadata()));
        		}
        	}
        }
    }

}
