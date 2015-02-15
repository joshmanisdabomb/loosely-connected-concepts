package yam.entity;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import yam.NameGenerator;
import yam.YetAnotherMod;
import yam.entity.ai.EntityAIGoHome;
import yam.entity.ai.EntityAINearestAttackableHuman;

public class EntityHalfPlayer extends EntityMob implements IRangedAttackMob {
	
	private static double moveSpeedSneak = 0.6D;
	private static double moveSpeed = 0.99D;
	private static double moveSpeedSprint = 1D;
	
	public static NameGenerator namegen = new NameGenerator();

	private static Class[] entitiesToKillHalfPlayer = {EntityHalfPlayer.class};
	private static Class[] entitiesToKillTop = {EntityPlayer.class};
	private static Class[] entitiesToKill = {EntityZombie.class, EntitySkeleton.class, EntityCreeper.class, EntitySilverfish.class, EntitySpider.class, EntityBlaze.class, EntityEnderman.class, EntityGolem.class, EntitySlime.class, EntityWitch.class, EntityGhast.class};
	private static Class[] entitiesToKillBottom = {EntityAnimal.class, EntityAmbientCreature.class, EntityWaterMob.class};
	
	private int tier = -1;
	public String lastname;
	
	public int homeX = -1;
	public int homeY = -1;
	public int homeZ = -1;
	public int homeX2 = -1;
	public int homeZ2 = -1;
	
	public EntityHalfPlayer(World par1World) {
		this(par1World, namegen.compose(3), new Random().nextInt(4 + par1World.difficultySetting.getDifficultyId()));
	}
	
	public EntityHalfPlayer(World par1World, String lastname, int tier) {
		super(par1World);
        
		this.lastname = lastname;
		this.tier = tier;
		
		this.setCanPickUpLoot(true);
		this.setAlwaysRenderNameTag(true);

        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setEnterDoors(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityTNTPrimed.class, 16F, moveSpeedSprint, moveSpeedSprint));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMinecartTNT.class, 16F, moveSpeedSprint, moveSpeedSprint));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityFallingBlock.class, 4F, moveSpeed, moveSpeed));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityDragon.class, 50F, moveSpeedSprint, moveSpeedSprint));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityWither.class, 50F, moveSpeedSprint, moveSpeedSprint));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityGiantZombie.class, 50F, moveSpeedSprint, moveSpeedSprint));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityNukePrimed.class, 200F, moveSpeedSprint, moveSpeedSprint));
		this.tasks.addTask(3, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(4, new EntityAIGoHome(this, moveSpeed, 10, 60));
		this.tasks.addTask(7, new EntityAIWander(this, moveSpeed));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityHalfPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityVillager.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		
		if (par1World != null && !par1World.isRemote)
        {
            this.addRandomArmor();
            this.setCombatTask();
            this.setCustomNameTag(namegen.compose(2) + " " + lastname);
        }
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
	public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
	
	public String getLivingSound() {
		return this.targetTasks.taskEntries.isEmpty() ? YetAnotherMod.MODID + ":mob.human.scream" : null;
	}
	
	public String getHurtSound() {
		return YetAnotherMod.MODID + ":mob.human.hit";
	}
	
	public String getDeathSound() {
		return YetAnotherMod.MODID + ":mob.human.death";
	}
	
	protected boolean canDespawn() {
        return false;
    }
	
	public static EntityAIAttackOnCollide createMeleeAI(EntityCreature creature, Class entity) {
		return new EntityAIAttackOnCollide(creature, entity, moveSpeedSprint, false);
	}
	
	public static EntityAIArrowAttack createRangedAI(IRangedAttackMob creature, Class entity) {
		return new EntityAIArrowAttack(creature, moveSpeedSneak, 5, 80, 16.0F);
	}
	
	public static EntityAIAvoidEntity createFleeAI(EntityCreature creature, Class entity) {
		return new EntityAIAvoidEntity(creature, EntityPlayer.class, 50F, moveSpeedSprint, moveSpeedSprint);
	}
	
	public void setMeleeAI() {
		this.targetTasks.taskEntries.clear();
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		
		for (int i = 0; i < entitiesToKillTop.length; i++) {
			this.tasks.addTask(4, createMeleeAI(this, entitiesToKillTop[i]));
			this.tasks.removeTask(createRangedAI(this, entitiesToKillTop[i]));
			this.tasks.removeTask(createFleeAI(this, entitiesToKillTop[i]));
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, entitiesToKillTop[i], 1, true));
		}

		this.tasks.addTask(4, createMeleeAI(this, EntityHalfPlayer.class));
		this.tasks.removeTask(createRangedAI(this, EntityHalfPlayer.class));
		this.tasks.removeTask(createFleeAI(this, EntityHalfPlayer.class));
		this.targetTasks.addTask(2, new EntityAINearestAttackableHuman(this, 1, true));
		
		for (int i = 0; i < entitiesToKill.length; i++) {
			this.tasks.addTask(5, createMeleeAI(this, entitiesToKill[i]));
			this.tasks.removeTask(createRangedAI(this, entitiesToKill[i]));
			this.tasks.removeTask(createFleeAI(this, entitiesToKill[i]));
			this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, entitiesToKill[i], 5, true));
		}
		for (int i = 0; i < entitiesToKillBottom.length; i++) {
			this.tasks.addTask(6, createMeleeAI(this, entitiesToKillBottom[i]));
			this.tasks.removeTask(createRangedAI(this, entitiesToKillBottom[i]));
			this.tasks.removeTask(createFleeAI(this, entitiesToKillBottom[i]));
			this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, entitiesToKillBottom[i], 9, true));
		}
	}
	
	public void setRangedAI() {
		this.targetTasks.taskEntries.clear();
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		
		for (int i = 0; i < entitiesToKillTop.length; i++) {
			this.tasks.removeTask(createMeleeAI(this, entitiesToKillTop[i]));
			this.tasks.addTask(4, createRangedAI(this, entitiesToKillTop[i]));
			this.tasks.removeTask(createFleeAI(this, entitiesToKillTop[i]));
			this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, entitiesToKillTop[i], 1, true));
		}
		
		this.tasks.removeTask(createMeleeAI(this, EntityHalfPlayer.class));
		this.tasks.addTask(4, createRangedAI(this, EntityHalfPlayer.class));
		this.tasks.removeTask(createFleeAI(this, EntityHalfPlayer.class));
		this.targetTasks.addTask(2, new EntityAINearestAttackableHuman(this, 1, true));
		
		for (int i = 0; i < entitiesToKill.length; i++) {
			this.tasks.removeTask(createMeleeAI(this, entitiesToKill[i]));
			this.tasks.addTask(5, createRangedAI(this, entitiesToKill[i]));
			this.tasks.removeTask(createFleeAI(this, entitiesToKill[i]));
			this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, entitiesToKill[i], 5, true));
		}
		for (int i = 0; i < entitiesToKillBottom.length; i++) {
			this.tasks.removeTask(createMeleeAI(this, entitiesToKillBottom[i]));
			this.tasks.addTask(6, createRangedAI(this, entitiesToKillBottom[i]));
			this.tasks.removeTask(createFleeAI(this, entitiesToKillBottom[i]));
			this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, entitiesToKillBottom[i], 9, true));
		}
	}
	
	public void setFleeAI() {
		this.targetTasks.taskEntries.clear();
		
		for (int i = 0; i < entitiesToKillTop.length; i++) {
			this.tasks.removeTask(createMeleeAI(this, entitiesToKillTop[i]));
			this.tasks.removeTask(createRangedAI(this, entitiesToKillTop[i]));
			this.tasks.addTask(4, createFleeAI(this, entitiesToKillTop[i]));
		}
		
		this.tasks.removeTask(createMeleeAI(this, EntityHalfPlayer.class));
		this.tasks.removeTask(createRangedAI(this, EntityHalfPlayer.class));
		this.tasks.addTask(4, createFleeAI(this, EntityHalfPlayer.class));
		
		for (int i = 0; i < entitiesToKill.length; i++) {
			this.tasks.removeTask(createMeleeAI(this, entitiesToKill[i]));
			this.tasks.removeTask(createRangedAI(this, entitiesToKill[i]));
			this.tasks.addTask(5, createFleeAI(this, entitiesToKill[i]));
		}
		for (int i = 0; i < entitiesToKillBottom.length; i++) {
			this.tasks.removeTask(createMeleeAI(this, entitiesToKillBottom[i]));
			this.tasks.removeTask(createRangedAI(this, entitiesToKillBottom[i]));
			this.tasks.addTask(6, createFleeAI(this, entitiesToKillBottom[i]));
		}
	}
	
	public int calculateWorth() {
		return calculateWorth(this.getEquipmentInSlot(1), this.getEquipmentInSlot(2), this.getEquipmentInSlot(3), this.getEquipmentInSlot(4));
	}
	
	public int calculateWorth(ItemStack par1, ItemStack par2, ItemStack par3, ItemStack par4) {
		return calculateWorth(par1 != null ? par1.getItem() : null, par2 != null ? par2.getItem() : null, par3 != null ? par3.getItem() : null, par4 != null ? par4.getItem() : null);
	}
	
	public int calculateWorth(Item par1, Item par2, Item par3, Item par4) {
		int worth = 0;
		
		if (par1 == Items.leather_helmet) {worth += 1;}
		else if (par1 == YetAnotherMod.cactusHelmet) {worth += 1;}
		else if (par1 == Items.golden_helmet) {worth += 2;}
		else if (par1 == Items.chainmail_helmet) {worth += 2;}
		else if (par1 == Items.iron_helmet) {worth += 3;}
		else if (par1 == Items.diamond_helmet) {worth += 4;}
		else if (par1 == YetAnotherMod.rubyHelmet) {worth += 5;}
		else if (par1 == YetAnotherMod.emeraldHelmet) {worth += 5;}
		else if (par1 == YetAnotherMod.lapisHelmet) {worth += 5;}
		else if (par1 == YetAnotherMod.crystalHelmet) {worth += 6;}
		else if (par1 == YetAnotherMod.cloudHelmet) {worth += 7;}
		else if (par1 == YetAnotherMod.neonHelmet) {worth += 8;}
		if (par2 == Items.leather_chestplate) {worth += 1;}
		else if (par2 == YetAnotherMod.cactusChestplate) {worth += 1;}
		else if (par2 == Items.golden_chestplate) {worth += 2;}
		else if (par2 == Items.chainmail_chestplate) {worth += 2;}
		else if (par2 == Items.iron_chestplate) {worth += 3;}
		else if (par2 == Items.diamond_chestplate) {worth += 4;}
		else if (par2 == YetAnotherMod.rubyChestplate) {worth += 5;}
		else if (par2 == YetAnotherMod.emeraldChestplate) {worth += 5;}
		else if (par2 == YetAnotherMod.lapisChestplate) {worth += 5;}
		else if (par2 == YetAnotherMod.crystalChestplate) {worth += 6;}
		else if (par2 == YetAnotherMod.cloudChestplate) {worth += 7;}
		else if (par2 == YetAnotherMod.neonChestplate) {worth += 8;}
		if (par3 == Items.leather_leggings) {worth += 1;}
		else if (par3 == YetAnotherMod.cactusLeggings) {worth += 1;}
		else if (par3 == Items.golden_leggings) {worth += 2;}
		else if (par3 == Items.chainmail_leggings) {worth += 2;}
		else if (par3 == Items.iron_leggings) {worth += 3;}
		else if (par3 == Items.diamond_leggings) {worth += 4;}
		else if (par3 == YetAnotherMod.rubyLeggings) {worth += 5;}
		else if (par3 == YetAnotherMod.emeraldLeggings) {worth += 5;}
		else if (par3 == YetAnotherMod.lapisLeggings) {worth += 5;}
		else if (par3 == YetAnotherMod.crystalLeggings) {worth += 6;}
		else if (par3 == YetAnotherMod.cloudLeggings) {worth += 7;}
		else if (par3 == YetAnotherMod.neonLeggings) {worth += 8;}
		if (par4 == Items.leather_boots) {worth += 1;}
		else if (par4 == YetAnotherMod.cactusBoots) {worth += 1;}
		else if (par4 == Items.golden_boots) {worth += 2;}
		else if (par4 == Items.chainmail_boots) {worth += 2;}
		else if (par4 == Items.iron_boots) {worth += 3;}
		else if (par4 == Items.diamond_boots) {worth += 4;}
		else if (par4 == YetAnotherMod.rubyBoots) {worth += 5;}
		else if (par4 == YetAnotherMod.emeraldBoots) {worth += 5;}
		else if (par4 == YetAnotherMod.lapisBoots) {worth += 5;}
		else if (par4 == YetAnotherMod.crystalBoots) {worth += 6;}
		else if (par4 == YetAnotherMod.cloudBoots) {worth += 7;}
		else if (par4 == YetAnotherMod.neonBoots) {worth += 8;}
		
		return worth;
	}
	
	protected Entity findPlayerToAttack() {
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, 32.0D);
    }
	
	public void updateAITick() {
		if (this.getMoveHelper().isUpdating()) {
			this.setSneaking(this.getMoveHelper().getSpeed() <= moveSpeedSneak);
			this.setSprinting(this.getMoveHelper().getSpeed() >= moveSpeedSprint);
        }
	}
	
	public void onUpdate() {
		this.experienceValue = 5 + calculateWorth();
		super.onUpdate();
	}
	
	public boolean attackEntityFrom(DamageSource damagesource, float par2) {
		if (damagesource.getEntity() instanceof EntityPlayer) {
			int playerworth = calculateWorth(((EntityPlayer)damagesource.getEntity()).inventory.armorItemInSlot(0), ((EntityPlayer)damagesource.getEntity()).inventory.armorItemInSlot(1), ((EntityPlayer)damagesource.getEntity()).inventory.armorItemInSlot(2), ((EntityPlayer)damagesource.getEntity()).inventory.armorItemInSlot(3));
					
			if (((EntityPlayer)damagesource.getEntity()).capabilities.isCreativeMode || this.getHealth() < (((EntityLivingBase)damagesource.getEntity()).getHealth() + (playerworth - calculateWorth())) / 2) {
				setFleeAI();
			} else {
			}
		} else if (damagesource.getEntity() != null && damagesource.getEntity() instanceof EntityLiving) {
			if (this.getHealth() < ((EntityLivingBase)damagesource.getEntity()).getHealth() / (3 - calculateWorth() / 10)) {
				setFleeAI();
			}
		}
		return super.attackEntityFrom(damagesource, par2);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase var1, float var2) {
        if (this.getHeldItem() != null && (this.getHeldItem().getItem() == Items.bow || this.getHeldItem().getItem() == YetAnotherMod.crystalRepeater)) {
			EntityArrow entityarrow = new EntityArrow(this.worldObj, this, var1, 1.6F, 10 - this.worldObj.difficultySetting.getDifficultyId());
	        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
	        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
	        entityarrow.setDamage(var2 * 3 + this.worldObj.difficultySetting.getDifficultyId());
	
	        if (i > 0) {
	            entityarrow.setDamage(entityarrow.getDamage() + i);
	        }
	
	        if (j > 0) {
	            entityarrow.setKnockbackStrength(j);
	        }
	
	        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0) {
	            entityarrow.setFire(100);
	        }
	
	        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        this.worldObj.spawnEntityInWorld(entityarrow);
        } else {
        	this.setCombatTask();
        }
	}
	
	public void setCombatTask() {
        if (this.getHeldItem() != null && (this.getHeldItem().getItem() == Items.bow || this.getHeldItem().getItem() == YetAnotherMod.crystalRepeater)) {
        	setRangedAI();
        }
        else {
        	setMeleeAI();
        }
    }
	
	public void addRandomArmor() {
		switch (this.tier) {
			default: return;
			case 1: {
				this.setCurrentItemOrArmor(0, new ItemStack(Items.wooden_sword));
				if (rand.nextInt(3) == 0) {this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_helmet));}
				if (rand.nextInt(3) == 0) {this.setCurrentItemOrArmor(2, new ItemStack(Items.leather_chestplate));}
				if (rand.nextInt(3) == 0) {this.setCurrentItemOrArmor(3, new ItemStack(Items.leather_leggings));}
				if (rand.nextInt(3) == 0) {this.setCurrentItemOrArmor(4, new ItemStack(Items.leather_boots));}
				
				return;
			}
			case 2: {
				this.setCurrentItemOrArmor(0, rand.nextInt(4) == 0 ? new ItemStack(Items.bow) : new ItemStack(Items.stone_sword));
				if (rand.nextInt(2) == 0) {this.setCurrentItemOrArmor(1, new ItemStack(Items.chainmail_helmet));}
				if (rand.nextInt(2) == 0) {this.setCurrentItemOrArmor(2, new ItemStack(Items.chainmail_chestplate));}
				if (rand.nextInt(2) == 0) {this.setCurrentItemOrArmor(3, new ItemStack(Items.chainmail_leggings));}
				if (rand.nextInt(2) == 0) {this.setCurrentItemOrArmor(4, new ItemStack(Items.chainmail_boots));}
				
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(1) == null) {this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_helmet));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(2) == null) {this.setCurrentItemOrArmor(2, new ItemStack(Items.leather_chestplate));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(3) == null) {this.setCurrentItemOrArmor(3, new ItemStack(Items.leather_leggings));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(4) == null) {this.setCurrentItemOrArmor(4, new ItemStack(Items.leather_boots));}
				
				return;
			}
			case 3: {
				this.setCurrentItemOrArmor(0, rand.nextInt(3) == 0 ? new ItemStack(Items.bow) : new ItemStack(Items.iron_sword));
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(1, new ItemStack(Items.iron_helmet));}
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(2, new ItemStack(Items.iron_chestplate));}
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(3, new ItemStack(Items.iron_leggings));}
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(4, new ItemStack(Items.iron_boots));}
				
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(1) == null) {this.setCurrentItemOrArmor(1, new ItemStack(Items.chainmail_helmet));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(2) == null) {this.setCurrentItemOrArmor(2, new ItemStack(Items.chainmail_chestplate));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(3) == null) {this.setCurrentItemOrArmor(3, new ItemStack(Items.chainmail_leggings));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(4) == null) {this.setCurrentItemOrArmor(4, new ItemStack(Items.chainmail_boots));}
				
				return;
			}
			case 4: {
				this.setCurrentItemOrArmor(0, rand.nextInt(2) == 0 ? new ItemStack(Items.bow) : new ItemStack(Items.diamond_sword));
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(1, new ItemStack(Items.diamond_helmet));}
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_chestplate));}
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(3, new ItemStack(Items.diamond_leggings));}
				if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_boots));}
				
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(1) == null) {this.setCurrentItemOrArmor(1, new ItemStack(Items.iron_helmet));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(2) == null) {this.setCurrentItemOrArmor(2, new ItemStack(Items.iron_chestplate));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(3) == null) {this.setCurrentItemOrArmor(3, new ItemStack(Items.iron_leggings));}
				if (rand.nextInt(4) == 0 && this.getEquipmentInSlot(4) == null) {this.setCurrentItemOrArmor(4, new ItemStack(Items.iron_boots));}
				
				return;
			}
			case 5: {
				int kind = rand.nextInt(3);
				switch (kind) {
					default: {
						this.setCurrentItemOrArmor(0, rand.nextInt(2) == 0 ? new ItemStack(Items.bow) : new ItemStack(YetAnotherMod.rubySword));
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(1, new ItemStack(YetAnotherMod.rubyHelmet));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(2, new ItemStack(YetAnotherMod.rubyChestplate));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(3, new ItemStack(YetAnotherMod.rubyLeggings));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(4, new ItemStack(YetAnotherMod.rubyBoots));}
					}
					case 1: {
						this.setCurrentItemOrArmor(0, rand.nextInt(2) == 0 ? new ItemStack(Items.bow) : new ItemStack(YetAnotherMod.emeraldSword));
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(1, new ItemStack(YetAnotherMod.emeraldHelmet));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(2, new ItemStack(YetAnotherMod.emeraldChestplate));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(3, new ItemStack(YetAnotherMod.emeraldLeggings));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(4, new ItemStack(YetAnotherMod.emeraldBoots));}
					}
					case 2: {
						this.setCurrentItemOrArmor(0, rand.nextInt(2) == 0 ? new ItemStack(Items.bow) : new ItemStack(YetAnotherMod.lapisSword));
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(1, new ItemStack(YetAnotherMod.lapisHelmet));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(2, new ItemStack(YetAnotherMod.lapisChestplate));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(3, new ItemStack(YetAnotherMod.lapisLeggings));}
						if (rand.nextInt(3) != 0) {this.setCurrentItemOrArmor(4, new ItemStack(YetAnotherMod.lapisBoots));}
					}
				}
				
				if (rand.nextInt(3) == 0 && this.getEquipmentInSlot(1) == null) {this.setCurrentItemOrArmor(1, new ItemStack(Items.diamond_helmet));}
				if (rand.nextInt(3) == 0 && this.getEquipmentInSlot(2) == null) {this.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_chestplate));}
				if (rand.nextInt(3) == 0 && this.getEquipmentInSlot(3) == null) {this.setCurrentItemOrArmor(3, new ItemStack(Items.diamond_leggings));}
				if (rand.nextInt(3) == 0 && this.getEquipmentInSlot(4) == null) {this.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_boots));}
				
				return;
			}
			case 6: {
				this.setCurrentItemOrArmor(0, rand.nextInt(2) == 0 ? new ItemStack(YetAnotherMod.crystalRepeater) : new ItemStack(YetAnotherMod.crystalSword));
				if (rand.nextInt(4) != 0) {this.setCurrentItemOrArmor(1, new ItemStack(YetAnotherMod.crystalHelmet));}
				if (rand.nextInt(4) != 0) {this.setCurrentItemOrArmor(2, new ItemStack(YetAnotherMod.crystalChestplate));}
				if (rand.nextInt(4) != 0) {this.setCurrentItemOrArmor(3, new ItemStack(YetAnotherMod.crystalLeggings));}
				if (rand.nextInt(4) != 0) {this.setCurrentItemOrArmor(4, new ItemStack(YetAnotherMod.crystalBoots));}
				
				return;
			}
		}
    }
	
	public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack) {
        super.setCurrentItemOrArmor(par1, par2ItemStack);
        this.setEquipmentDropChance(0, 0);

        if (!this.worldObj.isRemote && par1 == 0) {
            this.setCombatTask();
        }
    }

	public void setHome(int x, int y, int z, int x2, int z2) {
		homeX = x;
		homeY = y;
		homeZ = z;
		homeX2 = x2;
		homeZ2 = z2;
	}
	
	public boolean canAttackClass(Class par1Class) {
        return true;
    }
	
	public void onDeath(DamageSource src) {
		super.onDeath(src);
		if (this.deathTime == 0 && !this.worldObj.isRemote) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(src.func_151519_b(this));
		}
	}

	public ItemStack getPickedResult(MovingObjectPosition target)
    {
		return new ItemStack(YetAnotherMod.halfplayerSpawnEgg, 1);
    }

}
