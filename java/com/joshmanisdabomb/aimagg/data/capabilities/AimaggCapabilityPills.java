package com.joshmanisdabomb.aimagg.data.capabilities;

import java.util.ArrayList;

import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.IPills;
import com.joshmanisdabomb.aimagg.items.AimaggItemPill.PillType;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketCapabilityPills;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.util.PillModifier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AimaggCapabilityPills implements IStorage<IPills> {
	
	@Override
	public NBTBase writeNBT(Capability<IPills> capability, IPills instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setFloat("extra_health", instance.getExtraHealth());
		nbt.setFloat("extra_speed", instance.getExtraSpeed());
		nbt.setFloat("extra_luck", instance.getExtraLuck());
		nbt.setFloat("extra_damage", instance.getExtraDamage());
		nbt.setFloat("extra_attack_speed", instance.getExtraAttackSpeed());
		
		nbt.setInteger("last_used_color1", instance.getLastPrimaryColor());
		nbt.setInteger("last_used_color2", instance.getLastSecondaryColor());
		
		nbt.setInteger("last_used_type", instance.getLastType().getMetadata());
		
		NBTTagCompound nbtDiscoveries = new NBTTagCompound();
		for (String s : instance.getDiscoveries()) {
			nbtDiscoveries.setBoolean(s, true);
		}
		nbt.setTag("discoveries", nbtDiscoveries);
		
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPills> capability, IPills instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound)nbt;

		instance.setExtraHealth(compound.getFloat("extra_health"));
		instance.setExtraSpeed(compound.getFloat("extra_speed"));
		instance.setExtraLuck(compound.getFloat("extra_luck"));
		instance.setExtraDamage(compound.getFloat("extra_damage"));
		instance.setExtraAttackSpeed(compound.getFloat("extra_attack_speed"));
		
		instance.setLastPrimaryColor(compound.getInteger("last_used_color1"));
		instance.setLastSecondaryColor(compound.getInteger("last_used_color2"));
		
		instance.setLastType(PillType.values()[compound.getInteger("last_used_type")]);
		
		instance.clearDiscoveries();
		NBTTagCompound nbtDiscoveries = compound.getCompoundTag("discoveries");
		for (String s : nbtDiscoveries.getKeySet()) {
			instance.addDiscovery(s);
		}
	}
	
	public static void sendPillsPacket(Entity entity) {
		if (entity instanceof EntityPlayerMP) {
			AimaggPacketCapabilityPills packet = new AimaggPacketCapabilityPills();
			packet.setFromCapability(entity.getCapability(PillsProvider.PILLS_CAPABILITY, null));
			AimaggPacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP)entity);
		}
	}

	public static interface IPills {

		public float getExtraHealth();

		public float getExtraSpeed();

		public float getExtraLuck();

		public float getExtraDamage();

		public float getExtraAttackSpeed();

		public int getLastPrimaryColor();

		public int getLastSecondaryColor();

		public PillType getLastType();

		public void setExtraHealth(float f);

		public void setExtraSpeed(float f);

		public void setExtraLuck(float f);

		public void setExtraDamage(float f);

		public void setExtraAttackSpeed(float f);

		public void setLastPrimaryColor(int color1);

		public void setLastSecondaryColor(int color2);

		public void setLastType(PillType p);
		
		public void addExtraHealth(float f);
		
		public void addExtraSpeed(float f);
		
		public void addExtraLuck(float f);
		
		public void addExtraDamage(float f);
		
		public void addExtraAttackSpeed(float f);

		public ArrayList<String> getDiscoveries();

		public void clearDiscoveries();

		public void addDiscovery(String s);

		public void addDiscovery(int color1, int color2);
		
		public boolean isDiscovered(int color1, int color2);
		
	}
	
	public static class Pills implements IPills {

		private float extraHealth = 0.0F;
		private float extraSpeed = 0.0F;
		private float extraLuck = 0.0F;
		private float extraDamage = 0.0F;
		private float extraAttackSpeed = 0.0F;
		
		private int lastColor1 = -1;
		private int lastColor2 = -1;
		
		private PillType lastType = PillType.NORMAL;

		private ArrayList<String> discoveries = new ArrayList<String>();

		@Override
		public float getExtraHealth() {
			return this.extraHealth;
		}

		@Override
		public float getExtraSpeed() {
			return this.extraSpeed;
		}

		@Override
		public float getExtraLuck() {
			return this.extraLuck;
		}

		@Override
		public float getExtraDamage() {
			return this.extraDamage;
		}

		@Override
		public float getExtraAttackSpeed() {
			return this.extraAttackSpeed;
		}

		@Override
		public int getLastPrimaryColor() {
			return this.lastColor1;
		}

		@Override
		public int getLastSecondaryColor() {
			return this.lastColor2;
		}

		@Override
		public PillType getLastType() {
			return this.lastType;
		}

		@Override
		public void setExtraHealth(float f) {
			this.extraHealth = f;
		}

		@Override
		public void setExtraSpeed(float f) {
			this.extraSpeed = f;
		}

		@Override
		public void setExtraLuck(float f) {
			this.extraLuck = f;
		}

		@Override
		public void setExtraDamage(float f) {
			this.extraDamage = f;
		}

		@Override
		public void setExtraAttackSpeed(float f) {
			this.extraAttackSpeed = f;
		}

		@Override
		public void setLastPrimaryColor(int color1) {
			this.lastColor1 = color1;
		}

		@Override
		public void setLastSecondaryColor(int color2) {
			this.lastColor2 = color2;
		}

		@Override
		public void setLastType(PillType p) {
			this.lastType = p;
		}

		@Override
		public void addExtraHealth(float f) {
			this.extraHealth = Math.max(Math.min(this.extraHealth + f, PillModifier.EXTRA_HEALTH.getMaximumAmount()), PillModifier.EXTRA_HEALTH.getMinimumAmount());
		}

		@Override
		public void addExtraSpeed(float f) {
			this.extraSpeed = Math.max(Math.min(this.extraSpeed + f, PillModifier.EXTRA_SPEED.getMaximumAmount()), PillModifier.EXTRA_SPEED.getMinimumAmount());
		}

		@Override
		public void addExtraLuck(float f) {
			this.extraLuck = Math.max(Math.min(this.extraLuck + f, PillModifier.EXTRA_LUCK.getMaximumAmount()), PillModifier.EXTRA_LUCK.getMinimumAmount());
		}

		@Override
		public void addExtraDamage(float f) {
			this.extraDamage = Math.max(Math.min(this.extraDamage + f, PillModifier.EXTRA_DAMAGE.getMaximumAmount()), PillModifier.EXTRA_DAMAGE.getMinimumAmount());
		}

		@Override
		public void addExtraAttackSpeed(float f) {
			this.extraAttackSpeed = Math.max(Math.min(this.extraAttackSpeed + f, PillModifier.EXTRA_ATTACK_SPEED.getMaximumAmount()), PillModifier.EXTRA_ATTACK_SPEED.getMinimumAmount());
		}
		
		@Override
		public ArrayList<String> getDiscoveries() {
			return this.discoveries;
		}

		@Override
		public void clearDiscoveries() {
			this.discoveries.clear();
		}

		@Override
		public void addDiscovery(String s) {
			for (String d : this.discoveries) {
				if (s.equals(d)) {
					return;
				}
			}
			this.discoveries.add(s);
		}

		@Override
		public void addDiscovery(int color1, int color2) {
			this.addDiscovery(Pills.serialise(color1, color2));
		}

		@Override
		public boolean isDiscovered(int color1, int color2) {
			String l = Pills.serialise(color1, color2);
			for (String s : this.discoveries) {
				if (s.equals(l)) {
					return true;
				}
			}
			return false;
		}
		
		protected static String serialise(int color1, int color2) {
			return Long.toString(((long)color1 << 24 | (long)color2), Character.MAX_RADIX);
		}
		
	}
	
	public static class PillsProvider implements ICapabilitySerializable<NBTBase> {
		
		@CapabilityInject(IPills.class)
		public static final Capability<IPills> PILLS_CAPABILITY = null;
		
		private IPills instance = PILLS_CAPABILITY.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == PILLS_CAPABILITY;
		}
		
		@Override
		public NBTBase serializeNBT() {
			return PILLS_CAPABILITY.getStorage().writeNBT(PILLS_CAPABILITY, this.instance, null);
		}
		
		@Override
		public void deserializeNBT(NBTBase nbt) {
			PILLS_CAPABILITY.getStorage().readNBT(PILLS_CAPABILITY, this.instance, null, nbt);
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			 return capability == PILLS_CAPABILITY ? PILLS_CAPABILITY.<T> cast(this.instance) : null;
		}
		
	}

}
