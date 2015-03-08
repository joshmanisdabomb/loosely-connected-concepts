package yam.entity;

import yam.YetAnotherMod;
import yam.explosion.NuclearExplosion;
import yam.explosion.Rainsplosion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityUnicorn extends EntityHorse {
	
	private static final IAttribute horseJumpStrength = (new RangedAttribute("horse.jumpStrength", 0.0D, 0.0D, 1.0D)).setDescription("Jump Strength").setShouldWatch(true);
    private static final String[] horseArmorTextures = new String[] {null, YetAnotherMod.MODID + ":textures/entity/unicorn/armor/cloud.png", YetAnotherMod.MODID + ":textures/entity/unicorn/armor/neon.png"};
    private static final int[] armorValues = new int[] {0, 5};
    private static final String[] horseTextures = new String[] {YetAnotherMod.MODID + ":textures/entity/unicorn/horse_blue.png", YetAnotherMod.MODID + ":textures/entity/unicorn/horse_purple.png", YetAnotherMod.MODID + ":textures/entity/unicorn/horse_yellow.png"};
    private static final String[] horseMarkingTextures = new String[] {null, YetAnotherMod.MODID + ":textures/entity/unicorn/horse_markings_white.png", YetAnotherMod.MODID + ":textures/entity/unicorn/horse_markings_whitedots.png", YetAnotherMod.MODID + ":textures/entity/unicorn/horse_markings_whitefield.png"};
	private String field_110286_bQ;
	private String[] field_110280_bR = new String[1];
	private int field_110285_bP;
    private boolean field_110294_bI;
    private int openMouthCounter;
    private AnimalChest horseChest;

	public EntityUnicorn(World par1World) {
		super(par1World);
        this.setSize(1.4F*1.2F, 1.6F*1.2F);
	}
	
	public void setHorseType(int par1)
    {
        this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
    }
	
	public int getHorseType()
    {
        return 0;
    }
	
	public ItemStack getPickedResult(MovingObjectPosition target)
    {
		return new ItemStack(YetAnotherMod.unicornSpawnEgg, 1);
    }
	
	@SideOnly(Side.CLIENT)
    private void setHorseTexturePaths()
    {
    	int horsecolor = (int)Math.floor((double)this.getHorseVariant()/(double)(horseMarkingTextures.length - 1));
    	int horsemarkings = this.getHorseVariant() % (horseMarkingTextures.length - 1);
    	
    	this.field_110286_bQ = horseTextures[horsecolor];
    	this.field_110280_bR = new String[] {field_110286_bQ, horseMarkingTextures[horsemarkings+1]};
    }

    @SideOnly(Side.CLIENT)
    public String getHorseTexture()
    {
    	if (this.field_110286_bQ == null)
        {
            this.setHorseTexturePaths();
        }
    	
        return field_110286_bQ;
    }
    
    @SideOnly(Side.CLIENT)
    public String[] getVariantTexturePaths()
    {
    	if (this.field_110280_bR == null)
        {
            this.setHorseTexturePaths();
        }
    	
        return field_110280_bR;
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData) {
    	super.onSpawnWithEgg(par1EntityLivingData);
    	this.setHorseType(0);
    	this.setHorseVariant(rand.nextInt((horseMarkingTextures.length - 1) * horseTextures.length));

    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D + rand.nextInt(21));
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D + (rand.nextDouble() * 0.2D));
    	
        this.setHealth(this.getMaxHealth());
    	
		return par1EntityLivingData;
    }
    
    protected void fall(float par1) {}
    
    public void moveEntityWithHeading(float par1, float par2)
    {
    	this.fall(par1);
        if (this.riddenByEntity != null && this.isHorseSaddled())
        {
            this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
            par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;

            if (par2 <= 0.0F)
            {
                par2 *= 0.25F;
                this.field_110285_bP = 0;
            }

            if (this.onGround && this.jumpPower == 0.0F && this.isRearing() && !this.field_110294_bI)
            {
                par1 = 0.0F;
                par2 = 0.0F;
            }

            if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround)
            {
                this.motionY = this.getHorseJumpStrength() * 1.5 * (double)this.jumpPower;

                if (this.isPotionActive(Potion.jump))
                {
                    this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                }

                this.setHorseJumping(true);
                this.isAirBorne = true;

                if (par2 > 0.0F)
                {
                    float f2 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
                    float f3 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
                    this.motionX += (double)(-0.4F * f2 * this.jumpPower);
                    this.motionZ += (double)(0.4F * f3 * this.jumpPower);
                    this.playSound("mob.horse.jump", 0.4F, 1.0F);
                }

                this.jumpPower = 0.0F;
                net.minecraftforge.common.ForgeHooks.onLivingJump(this);
            }

            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if (!this.worldObj.isRemote)
            {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(par1, par2);
            }

            if (this.onGround)
            {
                this.jumpPower = 0.0F;
                this.setHorseJumping(false);
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

            if (f4 > 1.0F)
            {
                f4 = 1.0F;
            }

            this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        }
        else
        {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.moveEntityWithHeading(par1, par2);
        }
    }
    
    private boolean func_110200_cJ()
    {
        return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && !this.func_110222_cv() && this.getHealth() >= this.getMaxHealth();
    }
    
    public boolean canMateWith(EntityAnimal par1EntityAnimal)
    {
    	if (par1EntityAnimal == this)
        {
            return false;
        }
        else if (par1EntityAnimal.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            EntityUnicorn entityhorse = (EntityUnicorn)par1EntityAnimal;

            if (this.func_110200_cJ() && entityhorse.func_110200_cJ())
            {
                return this.getHorseVariant() == entityhorse.getHorseVariant();
            }
            else
            {
                return false;
            }
        }
    }
    
    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        EntityUnicorn unicorn = new EntityUnicorn(this.worldObj);

    	unicorn.setHorseType(0);
    	unicorn.setHorseVariant(this.getHorseVariant());
    	
    	unicorn.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(Math.max(
    			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue(),
    			((EntityUnicorn)par1EntityAgeable).getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()));
    	unicorn.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(Math.max(
    			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue(),
    			((EntityUnicorn)par1EntityAgeable).getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue()));
    	
        return unicorn;
    }
    
    /**
     * 0 = iron, 1 = gold, 2 = diamond
     */
    private int getHorseArmorIndex(ItemStack par1ItemStack)
    {
        if (par1ItemStack == null)
        {
            return 0;
        }
        else
        {
            Item item = par1ItemStack.getItem();
            return item == YetAnotherMod.cloudHorseArmor ? 1 : (item == YetAnotherMod.neonHorseArmor ? 2 : 0);
        }
    }
    
    private void func_110237_h(EntityPlayer par1EntityPlayer)
    {
        par1EntityPlayer.rotationYaw = this.rotationYaw;
        par1EntityPlayer.rotationPitch = this.rotationPitch;
        this.setEatingHaystack(false);
        this.setRearing(false);

        if (!this.worldObj.isRemote)
        {
            par1EntityPlayer.mountEntity(this);
        }
    }
    
    private void func_110266_cB()
    {
        this.openHorseMouth();
        this.worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
    }
    
    private void openHorseMouth()
    {
        if (!this.worldObj.isRemote)
        {
            this.openMouthCounter = 1;
            this.setHorseWatchableBoolean(128, true);
        }
    }
    
    private void setHorseWatchableBoolean(int par1, boolean par2)
    {
        int j = this.dataWatcher.getWatchableObjectInt(16);

        if (par2)
        {
            this.dataWatcher.updateObject(16, Integer.valueOf(j | par1));
        }
        else
        {
            this.dataWatcher.updateObject(16, Integer.valueOf(j & ~par1));
        }
    }
    
    private void func_110226_cD()
    {
        AnimalChest animalchest = this.horseChest;
        this.horseChest = new AnimalChest("HorseChest", this.func_110225_cC());
        this.horseChest.func_110133_a(this.getCommandSenderName());

        if (animalchest != null)
        {
            animalchest.func_110132_b(this);
            int i = Math.min(animalchest.getSizeInventory(), this.horseChest.getSizeInventory());

            for (int j = 0; j < i; ++j)
            {
                ItemStack itemstack = animalchest.getStackInSlot(j);

                if (itemstack != null)
                {
                    this.horseChest.setInventorySlotContents(j, itemstack.copy());
                }
            }

            animalchest = null;
        }

        this.horseChest.func_110134_a(this);
        this.func_110232_cE();
    }

    private void func_110232_cE()
    {
        if (!this.worldObj.isRemote)
        {
            this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);

            if (this.func_110259_cr())
            {
                this.func_146086_d(this.horseChest.getStackInSlot(1));
            }
        }
    }
    
    private int func_110225_cC()
    {
        int i = this.getHorseType();
        return this.isChested() && (i == 1 || i == 2) ? 17 : 2;
    }

}
