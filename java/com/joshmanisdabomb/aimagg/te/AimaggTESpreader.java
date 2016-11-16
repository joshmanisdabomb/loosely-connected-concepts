package com.joshmanisdabomb.aimagg.te;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpreader;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class AimaggTESpreader extends TileEntity {

	private int speed;
	private int damage;
	private int range;
	private boolean rangeinf;
	private int spread;
	private int child;
	private boolean eating;
	
	private boolean inGround;
	private boolean inLiquid;
	private boolean inAir;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("speed", speed);
		compound.setInteger("damage", damage);
		compound.setInteger("range", range);
		compound.setInteger("spread", spread);
		compound.setBoolean("eating", eating);
		compound.setInteger("child", child);
		
		compound.setBoolean("inGround", inGround);
		compound.setBoolean("inLiquid", inLiquid);
		compound.setBoolean("inAir", inAir);
	    
	    return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		speed = compound.getInteger("speed");
		damage = compound.getInteger("damage");
		range = compound.getInteger("range");
		spread = compound.getInteger("spread");
		eating = compound.getBoolean("eating");
		child = compound.getInteger("child");
		
		inGround = compound.getBoolean("inGround");
		inLiquid = compound.getBoolean("inLiquid");
		inAir = compound.getBoolean("inAir");
	}

	public void setFromValues(int speed, int damage, int range, int spread, boolean eating, int child, boolean inGround, boolean inLiquid, boolean inAir) {
		NBTTagCompound nbt = this.getTileData();
		
	    nbt.setInteger("speed", this.speed = speed);
	    nbt.setInteger("damage", this.damage = damage);
	    nbt.setInteger("range", this.range = range);
	    nbt.setInteger("spread", this.spread = spread);
	    nbt.setBoolean("eating", this.eating = eating);
	    nbt.setInteger("child", this.child = child);
	    nbt.setBoolean("inGround", this.inGround = inGround);
	    nbt.setBoolean("inLiquid", this.inLiquid = inLiquid);
	    nbt.setBoolean("inAir", this.inAir = inAir);
	}

	public void setFromOtherTE(AimaggTESpreader te) {
		NBTTagCompound nbt = this.getTileData();
		
		nbt.setInteger("speed", this.speed = te.speed);
	    nbt.setInteger("damage", this.damage = te.damage);
	    nbt.setInteger("range", this.range = te.range);
	    nbt.setInteger("spread", this.spread = te.spread);
	    nbt.setBoolean("eating", this.eating = te.eating);
	    nbt.setInteger("child", this.child = te.child);
	    nbt.setBoolean("inGround", this.inGround = te.inGround);
	    nbt.setBoolean("inLiquid", this.inLiquid = te.inLiquid);
	    nbt.setBoolean("inAir", this.inAir = te.inAir);
	}
	
}
