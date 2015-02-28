package yam.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import yam.blocks.entity.TileEntityLaunchPad;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketMissileLaunch implements IMessage {

	private int x, y, z;
	
	public PacketMissileLaunch() {}
	
	public PacketMissileLaunch(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
    public void fromBytes(ByteBuf buffer) {
        //text = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
    }

    public static class Handler implements IMessageHandler<PacketMissileLaunch, IMessage> {
        @Override
        public IMessage onMessage(PacketMissileLaunch message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
        		TileEntityLaunchPad te = ((TileEntityLaunchPad)Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z));
            }
            return null; // no response in this case
        }
    }

}