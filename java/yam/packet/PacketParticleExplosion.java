package yam.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketParticleExplosion implements IMessage {

	private ParticleType type;
	private double x, y, z;
	private double range;
	private int amount;
	
	public PacketParticleExplosion() {}
	
	public PacketParticleExplosion(ParticleType type, double x, double y, double z, double range, int amount) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.range = amount;
		this.amount = amount;
	}
	
	@Override
    public void toBytes(ByteBuf buffer) {
		buffer.writeInt(type.getCode());
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeDouble(range);
		buffer.writeInt(amount);
		
	}

	@Override
    public void fromBytes(ByteBuf buffer) {
		type = ParticleType.getFromCode(buffer.readInt());
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		range = buffer.readDouble();
		amount = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<PacketParticleExplosion, IMessage> {
        @Override
        public IMessage onMessage(PacketParticleExplosion message, MessageContext ctx) {
        	if (ctx.side == Side.CLIENT) {
        		ParticleHandler.particleExplosionClient(Minecraft.getMinecraft().theWorld, message.type, message.x, message.y, message.z, message.range, message.amount);
            }
            return null; // no response in this case
        }
    }

}
