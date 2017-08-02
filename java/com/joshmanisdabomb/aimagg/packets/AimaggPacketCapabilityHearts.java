package com.joshmanisdabomb.aimagg.packets;

import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketCapabilityHearts implements IMessage {

	private float ironHealth = 0.0F;
	private float ironHealthMax = 20.0F;
	private float crystalHealth = 0.0F;
	private float crystalHealthMax = 0.0F;

	public AimaggPacketCapabilityHearts() {}
	
	public void setFromCapability(IHearts hearts) {
		this.ironHealth = hearts.getIronHealth();
		this.ironHealthMax = hearts.getIronMaxHealth();
		this.crystalHealth = hearts.getCrystalHealth();
		this.crystalHealthMax = hearts.getCrystalMaxHealth();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.ironHealth = buf.readFloat();
		this.ironHealthMax = buf.readFloat();
		this.crystalHealth = buf.readFloat();
		this.crystalHealthMax = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.ironHealth);
		buf.writeFloat(this.ironHealthMax);
		buf.writeFloat(this.crystalHealth);
		buf.writeFloat(this.crystalHealthMax);
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketCapabilityHearts, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketCapabilityHearts message, final MessageContext ctx) {
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

        private void handle(AimaggPacketCapabilityHearts message, MessageContext ctx) {
            // This code is run on the client side. So you can do client-side calculations here.
        	IHearts hearts = Minecraft.getMinecraft().player.getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
        	hearts.setIronHealth(message.ironHealth);
        	hearts.setIronMaxHealth(message.ironHealthMax);
        	hearts.setCrystalHealth(message.crystalHealth);
        	hearts.setCrystalMaxHealth(message.crystalHealthMax);
        }
    }

}
