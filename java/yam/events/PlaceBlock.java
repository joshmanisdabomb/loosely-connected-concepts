package yam.events;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Event that is fired when an Block is placed by a player.
 * Cancelling this event will cause the block not to be placed.
 */
@Cancelable
public class PlaceBlock extends BlockEvent {
	public final int x;
    public final int y;
    public final int z;
    public final World world;
    public final Block block;
    public final int blockMetadata;
    public final EntityPlayer player;

	public PlaceBlock(int x, int y, int z, World world, Block block, int blockMetadata, EntityPlayer player) {
		super(x, y, z, world, block, blockMetadata);
		this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.block = block;
        this.blockMetadata = blockMetadata;
        this.player = player;
	}
	
    public Block getBlock()
    {
        return block;
    }
	
    public int getMetadata()
    {
        return blockMetadata;
    }
	
    public void setBlock(Block b)
    {
        world.setBlock(x,y,z,b,blockMetadata,3);
    }
	
    public void setMetadata(int m)
    {
        world.setBlock(x,y,z,block,m,3);
    }
	
    public EntityPlayer getPlayer()
    {
        return player;
    }

}
