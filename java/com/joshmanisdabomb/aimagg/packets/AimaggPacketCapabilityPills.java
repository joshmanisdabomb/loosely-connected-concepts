package com.joshmanisdabomb.aimagg.packets;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.IPills;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.PillsProvider;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill.PillType;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AimaggPacketCapabilityPills implements IMessage {

	private float extraHealth = 0.0F;
	private float extraSpeed = 0.0F;
	private float extraLuck = 0.0F;
	private float extraDamage = 0.0F;
	private float extraAttackSpeed = 0.0F;
	
	private int lastColor1 = -1;
	private int lastColor2 = -1;
	
	private PillType lastType = PillType.NORMAL;

	private ArrayList<String> discoveries = new ArrayList<String>();

	public AimaggPacketCapabilityPills() {}
	
	public void setFromCapability(IPills pills) {
		this.extraHealth = pills.getExtraHealth();
		this.extraSpeed = pills.getExtraSpeed();
		this.extraLuck = pills.getExtraLuck();
		this.extraDamage = pills.getExtraDamage();
		this.extraAttackSpeed = pills.getExtraAttackSpeed();
		
		this.lastColor1 = pills.getLastPrimaryColor();
		this.lastColor2 = pills.getLastSecondaryColor();
		
		this.lastType = pills.getLastType();
		
		this.discoveries = pills.getDiscoveries();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.extraHealth = buf.readFloat();
		this.extraSpeed = buf.readFloat();
		this.extraLuck = buf.readFloat();
		this.extraDamage = buf.readFloat();
		this.extraAttackSpeed = buf.readFloat();
		
		this.lastColor1 = buf.readInt();
		this.lastColor2 = buf.readInt();
		
		this.lastType = PillType.values()[buf.readInt()];
		
		while (buf.isReadable()) {
			this.discoveries.add(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.extraHealth);
		buf.writeFloat(this.extraSpeed);
		buf.writeFloat(this.extraLuck);
		buf.writeFloat(this.extraDamage);
		buf.writeFloat(this.extraAttackSpeed);

		buf.writeInt(this.lastColor1);
		buf.writeInt(this.lastColor2);
		
		buf.writeInt(this.lastType.getMetadata());
		
		for (String s : discoveries) {
			ByteBufUtils.writeUTF8String(buf, s);
		}
	}
	
	public static class Handler implements IMessageHandler<AimaggPacketCapabilityPills, IMessage> {
        @Override
        public IMessage onMessage(final AimaggPacketCapabilityPills message, final MessageContext ctx) {
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

        private void handle(AimaggPacketCapabilityPills message, MessageContext ctx) {
            // This code is run on the client side. So you can do client-side calculations here.
        	IPills pills = Minecraft.getMinecraft().player.getCapability(PillsProvider.PILLS_CAPABILITY, null);
        	pills.setExtraHealth(message.extraHealth);
        	pills.setExtraSpeed(message.extraSpeed);
        	pills.setExtraLuck(message.extraLuck);
        	pills.setExtraDamage(message.extraDamage);
        	pills.setExtraAttackSpeed(message.extraAttackSpeed);
        	
        	pills.setLastPrimaryColor(message.lastColor1);
        	pills.setLastSecondaryColor(message.lastColor2);
        	
        	pills.setLastType(message.lastType);

        	pills.clearDiscoveries();
        	for (String s : message.discoveries) {
            	pills.addDiscovery(s);
        	}
        }
    }

}
