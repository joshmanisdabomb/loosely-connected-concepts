package yam.packet;

import net.minecraft.client.Minecraft;
import io.netty.buffer.ByteBuf;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketParticle implements IMessage {

	private ParticleType type;
	private double x, y, z;
	private double speedX, speedY, speedZ;
	
	public PacketParticle() {}
	
	public PacketParticle(ParticleType type, double x, double y, double z, double speedX, double speedY, double speedZ)
	{
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.speedX = speedX;
		this.speedY = speedY;
		this.speedZ = speedZ;
	}
	
	@Override
    public void fromBytes(ByteBuf buffer) {
        //text = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
        type = ParticleType.getFromCode(buffer.readInt());
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		speedX = buffer.readDouble();
		speedY = buffer.readDouble();
		speedZ = buffer.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
    	buffer.writeInt(type.getCode());
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeDouble(speedX);
		buffer.writeDouble(speedY);
		buffer.writeDouble(speedZ);
    }

    public static class Handler implements IMessageHandler<PacketParticle, IMessage> {
        @Override
        public IMessage onMessage(PacketParticle message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
        		ParticleHandler.spawnParticleClient(Minecraft.getMinecraft().theWorld, message.type, message.x, message.y, message.z, message.speedX, message.speedY, message.speedZ);
            }
            return null; // no response in this case
        }
    }

}