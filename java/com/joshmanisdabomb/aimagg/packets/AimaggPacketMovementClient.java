package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketMovementClient implements IMessage {
	
	private int entityID;

	//0 is nothing, 1 is set, 2 is add, 3 is times, 4 is subtract, 5 is divide, 6 is exponential
	private int operationX = 0;
	private int operationY = 0;
	private int operationZ = 0;
	
	private double motionX;
	private double motionY;
	private double motionZ;

	private boolean setInWeb;
	
	public AimaggPacketMovementClient() {}
	
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	
	public void setInWeb() {
		this.setInWeb = true;
	}
	
	public void setEntityVelocityX(double motionX) {
		this.operationX = 1;
		this.motionX = motionX;
	}
	
	public void setEntityVelocityY(double motionY) {
		this.operationY = 1;
		this.motionY = motionY;
	}
	
	public void setEntityVelocityZ(double motionZ) {
		this.operationZ = 1;
		this.motionZ = motionZ;
	}
	
	public void addEntityVelocityX(double motionX) {
		this.operationX = 2;
		this.motionX = motionX;
	}
	
	public void addEntityVelocityY(double motionY) {
		this.operationY = 2;
		this.motionY = motionY;
	}
	
	public void addEntityVelocityZ(double motionZ) {
		this.operationZ = 2;
		this.motionZ = motionZ;
	}
	
	public void multiplyEntityVelocityX(double motionX) {
		this.operationX = 3;
		this.motionX = motionX;
	}
	
	public void multiplyEntityVelocityY(double motionY) {
		this.operationY = 3;
		this.motionY = motionY;
	}
	
	public void multiplyEntityVelocityZ(double motionZ) {
		this.operationZ = 3;
		this.motionZ = motionZ;
	}
	
	public void subtractEntityVelocityX(double motionX) {
		this.operationX = 4;
		this.motionX = motionX;
	}
	
	public void subtractEntityVelocityY(double motionY) {
		this.operationY = 4;
		this.motionY = motionY;
	}
	
	public void subtractEntityVelocityZ(double motionZ) {
		this.operationZ = 4;
		this.motionZ = motionZ;
	}
	
	public void divideEntityVelocityX(double motionX) {
		this.operationX = 5;
		this.motionX = motionX;
	}
	
	public void divideEntityVelocityY(double motionY) {
		this.operationY = 5;
		this.motionY = motionY;
	}
	
	public void divideEntityVelocityZ(double motionZ) {
		this.operationZ = 5;
		this.motionZ = motionZ;
	}
	
	public void exponEntityVelocityX(double motionX) {
		this.operationX = 6;
		this.motionX = motionX;
	}
	
	public void exponEntityVelocityY(double motionY) {
		this.operationY = 6;
		this.motionY = motionY;
	}
	
	public void exponEntityVelocityZ(double motionZ) {
		this.operationZ = 6;
		this.motionZ = motionZ;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		this.operationX = buf.readInt();
		this.motionX = buf.readDouble();
		this.operationY = buf.readInt();
		this.motionY = buf.readDouble();
		this.operationZ = buf.readInt();
		this.motionZ = buf.readDouble();
		this.setInWeb = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeInt(this.operationX);
		buf.writeDouble(this.motionX);
		buf.writeInt(this.operationY);
		buf.writeDouble(this.motionY);
		buf.writeInt(this.operationZ);
		buf.writeDouble(this.motionZ);
		buf.writeBoolean(this.setInWeb);
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketMovementClient, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketMovementClient message, final MessageContext ctx) {
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

        private void handle(AimaggPacketMovementClient message, MessageContext ctx) {
            // This code is run on the client side. So you can do client-side calculations here.
        	Entity e = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
        	if (e != null) {
        		switch (message.operationX) {
	        		case 1: e.motionX = message.motionX; break;
					case 2: e.motionX += message.motionX; break;
					case 3: e.motionX *= message.motionX; break;
					case 4: e.motionX -= message.motionX; break;
					case 5: e.motionX /= message.motionX; break;
					case 6: e.motionX = Math.pow(e.motionX, message.motionX); break;
					default: break;
				}
        		switch (message.operationY) {
	        		case 1: e.motionY = message.motionY; break;
					case 2: e.motionY += message.motionY; break;
					case 3: e.motionY *= message.motionY; break;
					case 4: e.motionY -= message.motionY; break;
					case 5: e.motionY /= message.motionY; break;
					case 6: e.motionY = Math.pow(e.motionY, message.motionY); break;
					default: break;
				}
        		switch (message.operationZ) {
					case 1: e.motionZ = message.motionZ; break;
					case 2: e.motionZ += message.motionZ; break;
					case 3: e.motionZ *= message.motionZ; break;
					case 4: e.motionZ -= message.motionZ; break;
					case 5: e.motionZ /= message.motionZ; break;
					case 6: e.motionZ = Math.pow(e.motionZ, message.motionZ); break;
					default: break;
				}
        		if (message.setInWeb) {e.setInWeb();}
        	}
        }
    }

}
