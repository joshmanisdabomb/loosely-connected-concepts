package yam.blocks.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import yam.blocks.BlockClassicChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityClassicChest extends TileEntity implements IInventory {

	private ItemStack[] stacks = new ItemStack[27];
	private Random rand = new Random();
	private String customName;
    /** Determines if the check for adjacent chests has taken place. */
    public boolean adjacentChestChecked;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityClassicChest adjacentChestZNeg;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityClassicChest adjacentChestXPos;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityClassicChest adjacentChestXNeg;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityClassicChest adjacentChestZPos;
	
	private int ticksSinceSync;
    private int cachedChestType;
    
    public TileEntityClassicChest()
    {
        this.cachedChestType = -1;
    }

    @SideOnly(Side.CLIENT)
    public TileEntityClassicChest(int par1)
    {
        this.cachedChestType = par1;
    }
    
    /**
    * Returns the number of slots in the inventory.
    */
   public int getSizeInventory()
   {
       return 27;
   }

   /**
    * Returns the stack in slot i
    */
   public ItemStack getStackInSlot(int par1)
   {
       return this.stacks[par1];
   }
   
   public ItemStack decrStackSize(int par1, int par2)
   {
       if (this.stacks[par1] != null)
       {
           ItemStack itemstack;

           if (this.stacks[par1].stackSize <= par2)
           {
               itemstack = this.stacks[par1];
               this.stacks[par1] = null;
               this.markDirty();
               return itemstack;
           }
           else
           {
               itemstack = this.stacks[par1].splitStack(par2);

               if (this.stacks[par1].stackSize == 0)
               {
                   this.stacks[par1] = null;
               }

               this.markDirty();
               return itemstack;
           }
       }
       else
       {
           return null;
       }
   }

   /**
    * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
    * like when you close a workbench GUI.
    */
   public ItemStack getStackInSlotOnClosing(int par1)
   {
       if (this.stacks[par1] != null)
       {
           ItemStack itemstack = this.stacks[par1];
           this.stacks[par1] = null;
           return itemstack;
       }
       else
       {
           return null;
       }
   } /**
    * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
    */
   public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
   {
       this.stacks[par1] = par2ItemStack;

       if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
       {
           par2ItemStack.stackSize = this.getInventoryStackLimit();
       }

       this.markDirty();
   }

   /**
    * Returns the name of the inventory
    */
   public String getInventoryName()
   {
       return this.hasCustomInventoryName() ? this.customName : "container.chest";
   }

   /**
    * Returns if the inventory is named
    */
   public boolean hasCustomInventoryName()
   {
       return this.customName != null && this.customName.length() > 0;
   }

   public void func_145976_a(String p_145976_1_)
   {
       this.customName = p_145976_1_;
   }

   public void readFromNBT(NBTTagCompound p_145839_1_)
   {
       super.readFromNBT(p_145839_1_);
       NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
       this.stacks = new ItemStack[this.getSizeInventory()];

       if (p_145839_1_.hasKey("CustomName", 8))
       {
           this.customName = p_145839_1_.getString("CustomName");
       }

       for (int i = 0; i < nbttaglist.tagCount(); ++i)
       {
           NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
           int j = nbttagcompound1.getByte("Slot") & 255;

           if (j >= 0 && j < this.stacks.length)
           {
               this.stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
           }
       }
   }

   public void writeToNBT(NBTTagCompound p_145841_1_)
   {
       super.writeToNBT(p_145841_1_);
       NBTTagList nbttaglist = new NBTTagList();

       for (int i = 0; i < this.stacks.length; ++i)
       {
           if (this.stacks[i] != null)
           {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               nbttagcompound1.setByte("Slot", (byte)i);
               this.stacks[i].writeToNBT(nbttagcompound1);
               nbttaglist.appendTag(nbttagcompound1);
           }
       }

       p_145841_1_.setTag("Items", nbttaglist);

       if (this.hasCustomInventoryName())
       {
           p_145841_1_.setString("CustomName", this.customName);
       }
   }

   /**
    * Returns the maximum stack size for a inventory slot.
    */
   public int getInventoryStackLimit()
   {
       return 64;
   }

   /**
    * Do not make give this method the name canInteractWith because it clashes with Container
    */
   public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
   {
       return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
   }

   /**
    * Causes the TileEntity to reset all it's cached values for it's container Block, metadata and in the case of
    * chests, the adjacent chest check
    */
   public void updateContainingBlockInfo()
   {
       super.updateContainingBlockInfo();
       this.adjacentChestChecked = false;
   }

   private void func_145978_a(TileEntityClassicChest p_145978_1_, int p_145978_2_)
   {
       if (p_145978_1_.isInvalid())
       {
           this.adjacentChestChecked = false;
       }
       else if (this.adjacentChestChecked)
       {
           switch (p_145978_2_)
           {
               case 0:
                   if (this.adjacentChestZPos != p_145978_1_)
                   {
                       this.adjacentChestChecked = false;
                   }

                   break;
               case 1:
                   if (this.adjacentChestXNeg != p_145978_1_)
                   {
                       this.adjacentChestChecked = false;
                   }

                   break;
               case 2:
                   if (this.adjacentChestZNeg != p_145978_1_)
                   {
                       this.adjacentChestChecked = false;
                   }

                   break;
               case 3:
                   if (this.adjacentChestXPos != p_145978_1_)
                   {
                       this.adjacentChestChecked = false;
                   }
           }
       }
   }

   /**
    * Performs the check for adjacent chests to determine if this chest is double or not.
    */
   public void checkForAdjacentChests()
   {
       if (!this.adjacentChestChecked)
       {
           this.adjacentChestChecked = true;
           this.adjacentChestZNeg = null;
           this.adjacentChestXPos = null;
           this.adjacentChestXNeg = null;
           this.adjacentChestZPos = null;

           if (this.func_145977_a(this.xCoord - 1, this.yCoord, this.zCoord))
           {
               this.adjacentChestXNeg = (TileEntityClassicChest)this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
           }

           if (this.func_145977_a(this.xCoord + 1, this.yCoord, this.zCoord))
           {
               this.adjacentChestXPos = (TileEntityClassicChest)this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
           }

           if (this.func_145977_a(this.xCoord, this.yCoord, this.zCoord - 1))
           {
               this.adjacentChestZNeg = (TileEntityClassicChest)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
           }

           if (this.func_145977_a(this.xCoord, this.yCoord, this.zCoord + 1))
           {
               this.adjacentChestZPos = (TileEntityClassicChest)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
           }

           if (this.adjacentChestZNeg != null)
           {
               this.adjacentChestZNeg.func_145978_a(this, 0);
           }

           if (this.adjacentChestZPos != null)
           {
               this.adjacentChestZPos.func_145978_a(this, 2);
           }

           if (this.adjacentChestXPos != null)
           {
               this.adjacentChestXPos.func_145978_a(this, 1);
           }

           if (this.adjacentChestXNeg != null)
           {
               this.adjacentChestXNeg.func_145978_a(this, 3);
           }
       }
   }

   private boolean func_145977_a(int p_145977_1_, int p_145977_2_, int p_145977_3_)
   {
       Block block = this.worldObj.getBlock(p_145977_1_, p_145977_2_, p_145977_3_);
       return block instanceof BlockChest && ((BlockChest)block).field_149956_a == this.func_145980_j();
   }

   public void updateEntity()
   {
       super.updateEntity();
       this.checkForAdjacentChests();
       ++this.ticksSinceSync;
       float f;

       if (!this.worldObj.isRemote && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
       {
           f = 5.0F;
           List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((double)((float)this.xCoord - f), (double)((float)this.yCoord - f), (double)((float)this.zCoord - f), (double)((float)(this.xCoord + 1) + f), (double)((float)(this.yCoord + 1) + f), (double)((float)(this.zCoord + 1) + f)));
           Iterator iterator = list.iterator();

           while (iterator.hasNext())
           {
               EntityPlayer entityplayer = (EntityPlayer)iterator.next();

               if (entityplayer.openContainer instanceof ContainerChest)
               {
                   IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
               }
           }
       }

       f = 0.1F;
       double d2;

       if (this.adjacentChestZNeg == null && this.adjacentChestXNeg == null)
       {
           double d1 = (double)this.xCoord + 0.5D;
           d2 = (double)this.zCoord + 0.5D;

           if (this.adjacentChestZPos != null)
           {
               d2 += 0.5D;
           }

           if (this.adjacentChestXPos != null)
           {
               d1 += 0.5D;
           }

           this.worldObj.playSoundEffect(d1, (double)this.yCoord + 0.5D, d2, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
       }
   }

   /**
    * Called when a client event is received with the event number and argument, see World.sendClientEvent
    */
   public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
   {
       if (p_145842_1_ == 1)
       {
           return true;
       }
       else
       {
           return super.receiveClientEvent(p_145842_1_, p_145842_2_);
       }
   }

   public void openInventory()
   {
       this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, 1);
       this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
       this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
   }

   public void closeInventory()
   {
       if (this.getBlockType() instanceof BlockChest)
       {
           this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, 0);
           this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
           this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
       }
   }

   /**
    * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
    */
   public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
   {
       return true;
   }

   /**
    * invalidates a tile entity
    */
   public void invalidate()
   {
       super.invalidate();
       this.updateContainingBlockInfo();
       this.checkForAdjacentChests();
   }

   public int func_145980_j()
   {
       if (this.cachedChestType == -1)
       {
           if (this.worldObj == null || !(this.getBlockType() instanceof BlockClassicChest))
           {
               return 0;
           }
       }

       return this.cachedChestType;
   }
   
}
