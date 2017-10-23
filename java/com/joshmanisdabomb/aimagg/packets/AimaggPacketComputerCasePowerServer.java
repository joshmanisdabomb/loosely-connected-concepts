package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockComputerCase;
import com.joshmanisdabomb.aimagg.te.AimaggTEComputerCase;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketComputerCasePowerServer implements IMessage {
	
	private int teX;
	private int teY;
	private int teZ;

	public AimaggPacketComputerCasePowerServer() {}

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
		buf.writeInt(this.teX);
		buf.writeInt(this.teY);
		buf.writeInt(this.teZ);
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketComputerCasePowerServer, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketComputerCasePowerServer message, final MessageContext ctx) {
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

        private void handle(AimaggPacketComputerCasePowerServer message, MessageContext ctx) {
            // This code is run on the server side. So you can do server-side calculations here.
        	TileEntity te = ctx.getServerHandler().player.world.getTileEntity(new BlockPos(message.teX, message.teY, message.teZ));
        	if (te instanceof AimaggTEComputerCase) {
        		((AimaggTEComputerCase)te).setPowerState(!((AimaggTEComputerCase)te).getPowerState());
        		((AimaggTEComputerCase)te).getWorld().notifyBlockUpdate(((AimaggTEComputerCase)te).getPos(), ((AimaggTEComputerCase)te).getWorld().getBlockState(((AimaggTEComputerCase)te).getPos()), ((AimaggTEComputerCase)te).getWorld().getBlockState(((AimaggTEComputerCase)te).getPos()).withProperty(AimaggBlockComputerCase.POWER_STATE, ((AimaggTEComputerCase)te).getPowerState()), 2);
        	}
        }
    }

}
