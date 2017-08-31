package com.joshmanisdabomb.aimagg.data.capabilities;

import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketCapabilityHearts;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AimaggCapabilityHearts implements IStorage<IHearts> {

	@Override
	public NBTBase writeNBT(Capability<IHearts> capability, IHearts instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("iron_health", instance.getIronHealth());
		nbt.setFloat("iron_health_max", instance.getIronMaxHealth());
		nbt.setFloat("crystal_health", instance.getCrystalHealth());
		nbt.setFloat("crystal_health_max", instance.getCrystalMaxHealth());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IHearts> capability, IHearts instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound)nbt;
		instance.setIronHealth(compound.getFloat("iron_health"));
		instance.setIronMaxHealth(compound.getFloat("iron_health_max"));
		instance.setCrystalHealth(compound.getFloat("crystal_health"));
		instance.setCrystalMaxHealth(compound.getFloat("crystal_health_max"));
	}
	
	public static void sendHeartsPacket(Entity entity) {
		if (entity instanceof EntityPlayerMP) {
			AimaggPacketCapabilityHearts packet = new AimaggPacketCapabilityHearts();
			packet.setFromCapability(entity.getCapability(HeartsProvider.HEARTS_CAPABILITY, null));
			AimaggPacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)entity);
		}
	}

	public static interface IHearts {

		public float getIronHealth();
		
		public float getIronMaxHealth();

		public float getCrystalHealth();
		
		public float getCrystalMaxHealth();

		public void setIronHealth(float amount);
		
		public void setIronMaxHealth(float amount);

		public void setCrystalHealth(float amount);
		
		public void setCrystalMaxHealth(float amount);

		public void addIronHealth(float amount);
		
		public void addIronMaxHealth(float amount);

		public void addCrystalHealth(float amount);
		
		public void addCrystalMaxHealth(float amount);
		
	}
	
	public static class Hearts implements IHearts {

		public static final float MAX_CRYSTAL_HEARTS = 20;
		
		private float ironHealth = 0.0F;
		private float ironHealthMax = 20.0F;
		private float crystalHealth = 0.0F;
		private float crystalHealthMax = 0.0F;
		
		@Override
		public float getIronHealth() {
			return this.ironHealth;
		}

		@Override
		public float getIronMaxHealth() {
			return this.ironHealthMax;
		}

		@Override
		public float getCrystalHealth() {
			return this.crystalHealth;
		}

		@Override
		public float getCrystalMaxHealth() {
			return this.crystalHealthMax;
		}

		@Override
		public void setIronHealth(float amount) {
			this.ironHealth = amount;
		}

		@Override
		public void setIronMaxHealth(float amount) {
			this.ironHealthMax = amount;
		}

		@Override
		public void setCrystalHealth(float amount) {
			this.crystalHealth = amount;
		}

		@Override
		public void setCrystalMaxHealth(float amount) {
			this.crystalHealthMax = amount;
		}

		@Override
		public void addIronHealth(float amount) {
			this.setIronHealth(Math.max(Math.min(this.getIronHealth() + amount, this.getIronMaxHealth()), 0));
		}

		@Override
		public void addIronMaxHealth(float amount) {
			this.setIronMaxHealth(Math.max(this.getIronMaxHealth() + amount, 0));
		}

		@Override
		public void addCrystalHealth(float amount) {
			this.setCrystalHealth(Math.max(Math.min(this.getCrystalHealth() + amount, this.getCrystalMaxHealth()), 0));
		}

		@Override
		public void addCrystalMaxHealth(float amount) {
			this.setCrystalMaxHealth(Math.max(this.getCrystalMaxHealth() + amount, 0));
		}
		
	}
	
	public static class HeartsProvider implements ICapabilitySerializable<NBTBase> {
		
		@CapabilityInject(IHearts.class)
		public static final Capability<IHearts> HEARTS_CAPABILITY = null;
		
		private IHearts instance = HEARTS_CAPABILITY.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == HEARTS_CAPABILITY;
		}
		
		@Override
		public NBTBase serializeNBT() {
			return HEARTS_CAPABILITY.getStorage().writeNBT(HEARTS_CAPABILITY, this.instance, null);
		}
		
		@Override
		public void deserializeNBT(NBTBase nbt) {
			HEARTS_CAPABILITY.getStorage().readNBT(HEARTS_CAPABILITY, this.instance, null, nbt);
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			 return capability == HEARTS_CAPABILITY ? HEARTS_CAPABILITY.<T> cast(this.instance) : null;
		}
		
	}

}
