package yam.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.server.MinecraftServer;
import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityLaunchPad;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketMissileLaunch implements IMessage {

	private int x, y, z;
	private int dim;
	
	public PacketMissileLaunch() {}
	
	public PacketMissileLaunch(int dim, int x, int y, int z)
	{
		this.dim = dim;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
    public void fromBytes(ByteBuf buffer) {
        //text = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
		dim = buffer.readInt();
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
		buffer.writeInt(dim);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(z);
    }

    public static class Handler implements IMessageHandler<PacketMissileLaunch, IMessage> {
        @Override
        public IMessage onMessage(PacketMissileLaunch message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
        		TileEntityLaunchPad te = ((TileEntityLaunchPad)MinecraftServer.getServer().worldServerForDimension(message.dim).getTileEntity(message.x, message.y, message.z));
        		te.launchMissile();
        		//EntityCreeper creeper = new EntityCreeper(te.getWorldObj());
        		//creeper.setPosition(message.x, message.y, message.z);
        		//te.getWorldObj().spawnEntityInWorld(creeper);
        		te.setInventorySlotContents(0, null);
        		te.setInventorySlotContents(1, null);
        		te.markDirty();
            }
            return null; // no response in this case
        }
    }

}