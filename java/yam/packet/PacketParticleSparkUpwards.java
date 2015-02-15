package yam.packet;

import net.minecraft.client.Minecraft;
import io.netty.buffer.ByteBuf;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketParticleSparkUpwards implements IMessage {

	private ParticleType type;
	private double x, y, z;
	private double speed, speedY;
	private int intensity;
	
	public PacketParticleSparkUpwards() {}
	
	public PacketParticleSparkUpwards(ParticleType type, double x, double y, double z, double speed, double speedY, int intensity) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.speed = speed;
		this.speedY = speedY;
		this.intensity = intensity;
	}
	
	@Override
    public void toBytes(ByteBuf buffer) {
		buffer.writeInt(type.getCode());
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeDouble(speed);
		buffer.writeDouble(speedY);
		buffer.writeInt(intensity);
		
	}

	@Override
    public void fromBytes(ByteBuf buffer) {
		type = ParticleType.getFromCode(buffer.readInt());
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		speed = buffer.readDouble();
		speedY = buffer.readDouble();
		intensity = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<PacketParticleSparkUpwards, IMessage> {
        @Override
        public IMessage onMessage(PacketParticleSparkUpwards message, MessageContext ctx) {
        	if (ctx.side == Side.CLIENT) {
        		ParticleHandler.particleSparkUpwardsClient(Minecraft.getMinecraft().theWorld, message.type, message.x, message.y, message.z, message.speed, message.speedY, message.intensity);
            }
            return null; // no response in this case
        }
    }

}
