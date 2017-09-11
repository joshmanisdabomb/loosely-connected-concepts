package com.joshmanisdabomb.aimagg.items;

import java.util.List;
import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggDamage;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.HeartsProvider;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityHearts.IHearts;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.IPills;
import com.joshmanisdabomb.aimagg.data.capabilities.AimaggCapabilityPills.PillsProvider;
import com.joshmanisdabomb.aimagg.data.world.AimaggSeedData;
import com.joshmanisdabomb.aimagg.gui.AimaggOverlayPills;
import com.joshmanisdabomb.aimagg.items.AimaggItemHeart.HeartType;
import com.joshmanisdabomb.aimagg.util.PillModifier;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemPill extends AimaggItemBasic implements AimaggItemColored {
	
	public static Random creativePillRand = new Random();
	public static int creativePillSeed = -1;
	
	public static final int[] defaultPrimaryColors   = new int[]{0xFFFFFF,0x42B6F4,0xFFD4D4,0xE3FF44,0xFFFFFF,0x342CAA,0xE09100,0xFFE9C1,0xFFFFFF,0x272727,0xFFFFFF,0xFFFFFF};
	public static final int[] defaultSecondaryColors = new int[]{0xFFFFFF,0x42B6F4,0xC90000,0xE09100,0x009DE0,0x0AFF83,0xE09100,0xFFFFFF,0x272727,0xE3FF44,0x00FFFF,0xE3FF44};
	
	public AimaggItemPill(String internalName) {
		super(internalName);
		this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		NBTTagCompound pNBT = stack.getSubCompound(Constants.MOD_ID + "_pill");
		switch (PillType.getFromMetadata(stack.getMetadata())) {
			case NORMAL:
				return pNBT == null ? (tintIndex == 0 ? 0x000000 : 0xFF00FF) : (tintIndex == 0 ? pNBT.getInteger("color1") : pNBT.getInteger("color2"));
			case RANDOM:
				return 0xFFFFFF;
			case SET:
				return pNBT == null ? (tintIndex == 0 ? 0x000000 : 0xFF00FF) : (tintIndex == 0 ? PillEffect.values()[pNBT.getInteger("effect")].getPolarity().color : PillEffect.values()[pNBT.getInteger("effect")].getPolarity().colorEffect4);
			default:
				return tintIndex == 0 ? 0x000000 : 0xFF00FF;
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
			for (int i = 0; i < defaultPrimaryColors.length; i++) {
				ItemStack is = new ItemStack(this, 1);
				NBTTagCompound pNBT = is.getOrCreateSubCompound(Constants.MOD_ID + "_pill");
				pNBT.setInteger("color1", defaultPrimaryColors[i]);
				pNBT.setInteger("color2", defaultSecondaryColors[i]);
				items.add(is);
			}
			ItemStack is = new ItemStack(this, 1, 1);
			items.add(is);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		NBTTagCompound pNBT = stack.getSubCompound(Constants.MOD_ID + "_pill");
		
		if (PillType.getFromMetadata(stack.getMetadata()) == PillType.RANDOM) {
			tooltip.add(I18n.format("tooltip.aimagg:pill.random", new Object[]{}));
		} else if (pNBT == null) {
			tooltip.add(I18n.format("tooltip.aimagg:pill.invalid", new Object[]{}));
		} else if (PillType.getFromMetadata(stack.getMetadata()) == PillType.SET) {
			tooltip.add(I18n.format("tooltip.aimagg:pill.effect." + PillEffect.values()[pNBT.getInteger("effect")].name().toLowerCase(), new Object[]{}));
		} else if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getCapability(PillsProvider.PILLS_CAPABILITY, null).isDiscovered(pNBT.getInteger("color1"), pNBT.getInteger("color2"))) {
			tooltip.add(I18n.format("tooltip.aimagg:pill.effect." + AimaggItemPill.getPillEffect(worldIn, pNBT.getInteger("color1"), pNBT.getInteger("color2")).name().toLowerCase(), new Object[]{}));
		} else {
			tooltip.add(I18n.format("tooltip.aimagg:pill.unknown", new Object[]{}));
		}
	}

	@Override
	public void registerRender() {
		for (PillType pt : PillType.values()) {
			ModelLoader.setCustomModelResourceLocation(this, pt.getMetadata(), pt.getModel());
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        IPills pills = playerIn.getCapability(PillsProvider.PILLS_CAPABILITY, null);
        
        long seed = 0;
        PillEffect pe = null;

        PillType p;
		pills.setLastType(p = PillType.getFromMetadata(itemstack.getMetadata()));
    	if (p == PillType.NORMAL) {
    		seed = AimaggItemPill.getPillSeed(worldIn, itemstack);
    		pe = AimaggItemPill.getPillEffect(worldIn, itemstack);

    		NBTTagCompound pNBT = itemstack.getSubCompound(Constants.MOD_ID + "_pill");
    		
    		int color1 = pNBT.getInteger("color1");
    		int color2 = pNBT.getInteger("color2");
    		
    		pills.addDiscovery(color1, color2);
    		/*if (!worldIn.isRemote) {
    			AimaggCapabilityPills.sendPillsPacket(playerIn);
    		}*/
    	} else if (p == PillType.RANDOM) {    		
    		if (creativePillRand == null || creativePillSeed != playerIn.getEntityId()) {
    			creativePillRand = new Random(playerIn.getEntityId());
    			creativePillSeed = playerIn.getEntityId();
    		}

    		seed = creativePillRand.nextLong();
    		pe = PillEffect.values()[creativePillRand.nextInt(PillEffect.values().length)];
    	} else if (p == PillType.SET) {
    		NBTTagCompound pNBT = itemstack.getSubCompound(Constants.MOD_ID + "_pill");
    		
    		seed = creativePillRand.nextLong();
    		pe = PillEffect.values()[pNBT.getInteger("effect")];
    	}
    	
    	AimaggItemPill.doPillEffect(pe, seed, worldIn, playerIn, itemstack);
		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 0.5F, 2.0F);
		
		if (worldIn.isRemote) {    		
			AimaggOverlayPills.pillTakenOnSystemTime = System.currentTimeMillis();
			AimaggOverlayPills.recentEffect = pe;
		}

    	if (p == PillType.NORMAL) {
			NBTTagCompound pNBT = itemstack.getSubCompound(Constants.MOD_ID + "_pill");
    		
    		int color1 = pNBT.getInteger("color1");
    		int color2 = pNBT.getInteger("color2");
    		
			pills.setLastPrimaryColor(color1);
			pills.setLastSecondaryColor(color2);
    	}
		
		if (playerIn instanceof EntityPlayerMP) {CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)playerIn, itemstack);}
        if (!playerIn.isCreative()) {itemstack.shrink(1);}
        
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
	
	public static void doPillEffect(PillEffect pe, long seed, World world, EntityPlayer player, ItemStack stack) {
		Random rand = new Random(seed);
		IPills pills = player.getCapability(PillsProvider.PILLS_CAPABILITY, null);
		switch(pe) {
			case FULL_HEALTH:
				player.setHealth(player.getMaxHealth());
				break;
			case BAD_TRIP:
				if (player.getHealth() > 4.0F) {
					player.attackEntityFrom(AimaggDamage.PILL, 4.0F);
				} else {
					AimaggItemPill.doPillEffect(pe.getFallback(), seed, world, player, stack);
				}
				break;
			case BAD_GAS:
				//TODO Add custom green particle effects and no need for use of Minecraft explosion. No damage to terrain.
				//maybe use lingering potion instead?
				break;
			case HEALTH_IS_FOOD:
				float healthFraction = player.getHealth()/player.getMaxHealth();
				float foodFraction = player.getFoodStats().getFoodLevel()/20.0F;
				player.setHealth(2.0F + (foodFraction*(player.getMaxHealth() - 2.0F)));
				player.getFoodStats().setFoodLevel(MathHelper.floor(healthFraction*20.0F));
				break;
			case EXPLOSIVE_DIARRHEA:
				//TODO Drops poops that explode. (not TNT this time)
				break;
			case I_FOUND_PILLS:
				//TODO Probably nothing (maybe render something on player)
				break;
			case I_CAN_SEE_FOREVER:
				player.removePotionEffect(MobEffects.BLINDNESS);
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, rand.nextInt(6000) + 600, 0, true, true));
				break;
			case I_CANT_SEE_FOREVER:
				player.removePotionEffect(MobEffects.NIGHT_VISION);
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, rand.nextInt(3000) + 300, 0, true, true));
				break;
			case PRETTY_FLY:
				//TODO Add flies as mobs, that actually fight for player and hover around this time.
				break;
			case PUBERTY:
				//TODO Probably nothing (maybe render something on player)
				break;
			case TELEPILLS:
				if (world.isRemote) {
					double d0 = player.posX;
					double d1 = player.posY;
					double d2 = player.posZ;

					for (int i = 0; i < 16; ++i) {
						double d3 = player.posX + (player.getRNG().nextDouble() - 0.5D) * 16.0D;
						double d4 = MathHelper.clamp(player.posY + (double) (player.getRNG().nextInt(16) - 8), 0.0D, (double) (world.getActualHeight() - 1));
						double d5 = player.posZ + (player.getRNG().nextDouble() - 0.5D) * 16.0D;

						if (player.isRiding()) {
							player.dismountRidingEntity();
						}

						if (player.attemptTeleport(d3, d4, d5)) {
							world.playSound((EntityPlayer) null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
							player.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
							break;
						}
					}
				}
				break;
			case BALLS_OF_STEEL:
				IHearts hearts = player.getCapability(HeartsProvider.HEARTS_CAPABILITY, null);
				hearts.addIronHealth(4.0F);
				AimaggCapabilityHearts.sendHeartsPacket(player);
				break;
			case AMNESIA:
				//TODO Players need an analyser to analyse pills beforehand so they don't get Amnesia'd without them being able to stop it.
				pills.clearDiscoveries();
				AimaggCapabilityPills.sendPillsPacket(player);
				break;
			case FORTY_EIGHT_HOUR_ENERGY:
				//TODO This needs some electrical based use. Maybe RF?
				break;
			case HEALTH_UP:
				float health = PillModifier.EXTRA_HEALTH.getRandomAmountOnUse(rand);
				pills.addExtraHealth(health);
				PillModifier.applyAllModifiers(player);
				if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				break;
			case HEALTH_DOWN:
				if (pills.getExtraHealth() <= PillModifier.EXTRA_HEALTH.getMinimumAmount()) {
					AimaggItemPill.doPillEffect(pe.getFallback(), seed, world, player, stack);
				} else {
					float health2 = -PillModifier.EXTRA_HEALTH.getRandomAmountOnUse(rand);
					pills.addExtraHealth(health2);
					PillModifier.applyAllModifiers(player);
					if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				}
				break;
			case HEMATEMESIS:
				if (player.getHealth() > 2.0F) {
					player.attackEntityFrom(AimaggDamage.PILL, player.getHealth() - 2.0F);
				} else if (player.getHealth() < 2.0F) {
					player.setHealth(2.0F);
				}
				
				int heartDropAmount = rand.nextInt(12) + 1;
				for (int i = 0; i < heartDropAmount; i++) {
					player.dropItem(new ItemStack(AimaggItems.heart, 1, HeartType.RED_FULL.getMetadata()), true, false);
				}
				break;
			case LEMON_PARTY:
				if (!world.isRemote) {
					EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(world, player.posX, player.posY, player.posZ);
			        entityareaeffectcloud.setOwner(player);
			        entityareaeffectcloud.setRadius(6.0F);
			        entityareaeffectcloud.setRadiusOnUse(-0.5F);
			        entityareaeffectcloud.setWaitTime(10);
			        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
			        entityareaeffectcloud.setPotion(PotionType.getPotionTypeForName("harming"));
			        entityareaeffectcloud.setColor(0xFFFF00);
			        world.spawnEntity(entityareaeffectcloud);
				}
				break;
			case ADDICTED:
				//TODO Amplify Damage effect.
				break;
			case ATTACK_SPEED_UP:
				float aspeed = PillModifier.EXTRA_ATTACK_SPEED.getRandomAmountOnUse(rand);
				pills.addExtraAttackSpeed(aspeed);
				PillModifier.applyAllModifiers(player);
				if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				break;
			case ATTACK_SPEED_DOWN:
				if (pills.getExtraHealth() <= PillModifier.EXTRA_ATTACK_SPEED.getMinimumAmount()) {
					AimaggItemPill.doPillEffect(pe.getFallback(), seed, world, player, stack);
				} else {
					float aspeed2 = -PillModifier.EXTRA_ATTACK_SPEED.getRandomAmountOnUse(rand);
					pills.addExtraAttackSpeed(aspeed2);
					PillModifier.applyAllModifiers(player);
					if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				}
				break;
			case DAMAGE_UP:
				float damage = PillModifier.EXTRA_DAMAGE.getRandomAmountOnUse(rand);
				pills.addExtraDamage(damage);
				PillModifier.applyAllModifiers(player);
				if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				break;
			case DAMAGE_DOWN:
				if (pills.getExtraHealth() <= PillModifier.EXTRA_DAMAGE.getMinimumAmount()) {
					AimaggItemPill.doPillEffect(pe.getFallback(), seed, world, player, stack);
				} else {
					float damage2 = -PillModifier.EXTRA_DAMAGE.getRandomAmountOnUse(rand);
					pills.addExtraDamage(damage2);
					PillModifier.applyAllModifiers(player);
					if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				}
				break;
			case FEELS_LIKE_IM_WALKING_ON_SUNSHINE:
				//TODO Starburst capability, different, does not hurt nearby mobs.
				break;
			case FRIENDS_TILL_THE_END:
				//TODO Spawn three friendly flies.
				break;
			case GULP:
				//TODO Add trinkets that effects can be swallowed with via this pill.
				break;
			case HORF:
				//TODO Shoots a poison bomb from your head (sicked up)
				break;
			case IM_DROWSY:
				//TODO Make a potion effect that gives all (nearby) mobs slowness.
				break;
			case IM_EXCITED:
				//TODO Make a potion effect that gives all (nearby) speed.
				break;
			case INFESTED_1:
				//TODO Destroy all poop in a radius and spawn a blue spider (new friendly mob). 1 at player, 1 for every poop.
				break;
			case INFESTED_2:
				//TODO Spawns 1-3 blue spiders at player, 1 for each nearby enemy.
				break;
			case LUCK_UP:
				float luck = PillModifier.EXTRA_ATTACK_SPEED.getRandomAmountOnUse(rand);
				pills.addExtraLuck(luck);
				PillModifier.applyAllModifiers(player);
				if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				break;
			case LUCK_DOWN:
				if (pills.getExtraHealth() <= PillModifier.EXTRA_LUCK.getMinimumAmount()) {
					AimaggItemPill.doPillEffect(pe.getFallback(), seed, world, player, stack);
				} else {
					float luck2 = -PillModifier.EXTRA_LUCK.getRandomAmountOnUse(rand);
					pills.addExtraLuck(luck2);
					PillModifier.applyAllModifiers(player);
					if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				}
				break;
			case PARALYSIS:
				//TODO re-add the stunned status effect from YAM.
				break;
			case PERCS:
				player.removePotionEffect(MobEffects.RESISTANCE);
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, rand.nextInt(6000) + 600, 0, true, true));
				break;
			case PHEROMONES:
				//TODO Add new potion effects that have mobs fight for you
				//maybe use lingering potion instead?
				break;
			case POWER_PILL:
				//TODO Add starburst capability that is invincibility and hurt nearby mobs.
				break;
			case QUESTION_MARKS:
				//TODO Makes maps not render.
				break;
			case RELAX:
				//TODO Spawns poop around player.
				break;
			case R_U_A_WIZARD:
				//TODO Shoot two arrows at 45 degree angles.
				break;
			case SOMETHINGS_WRONG:
				//TODO works weirdly due to effect applied outside the cloud
				if (!world.isRemote) {
					EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(world, player.posX, player.posY, player.posZ);
			        entityareaeffectcloud.setOwner(player);
			        entityareaeffectcloud.setRadius(5.0F);
			        entityareaeffectcloud.setRadiusOnUse(-0.5F);
			        entityareaeffectcloud.setWaitTime(10);
			        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
			        entityareaeffectcloud.setPotion(new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.SLOWNESS, 100, 1)}));
			        entityareaeffectcloud.setColor(0x000000);
			        world.spawnEntity(entityareaeffectcloud);
				}
				break;
			case SPEED_UP:
				float speed = PillModifier.EXTRA_SPEED.getRandomAmountOnUse(rand);
				pills.addExtraSpeed(speed);
				PillModifier.applyAllModifiers(player);
				if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				break;
			case SPEED_DOWN:
				if (pills.getExtraHealth() <= PillModifier.EXTRA_SPEED.getMinimumAmount()) {
					AimaggItemPill.doPillEffect(pe.getFallback(), seed, world, player, stack);
				} else {
					float speed2 = -PillModifier.EXTRA_SPEED.getRandomAmountOnUse(rand);
					pills.addExtraSpeed(speed2);
					PillModifier.applyAllModifiers(player);
					if (!world.isRemote) {AimaggCapabilityPills.sendPillsPacket(player);}
				}
				break;
			case VURP:
				if (pills.getLastPrimaryColor() >= 0 && pills.getLastSecondaryColor() >= 0) {
					ItemStack newPill = new ItemStack(AimaggItems.pill, 1, PillType.NORMAL.getMetadata());
					NBTTagCompound pNBT = newPill.getOrCreateSubCompound("aimagg_pill");
					pNBT.setInteger("color1", pills.getLastPrimaryColor());
					pNBT.setInteger("color2", pills.getLastSecondaryColor());
					player.dropItem(newPill, false, false);
				} else {
					ItemStack newPill = stack.copy();
					newPill.setCount(1);
					player.dropItem(newPill, false, false);
				}
				break;
			case XLAX:
				//TODO works weirdly due to effect applied outside the cloud
				if (!world.isRemote) {
					EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(world, player.posX, player.posY, player.posZ);
			        entityareaeffectcloud.setOwner(player);
			        entityareaeffectcloud.setRadius(5.0F);
			        entityareaeffectcloud.setRadiusOnUse(-0.5F);
			        entityareaeffectcloud.setWaitTime(10);
			        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
			        entityareaeffectcloud.setPotion(new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.SPEED, 100, 7)}));
			        entityareaeffectcloud.setColor(0x885511);
			        world.spawnEntity(entityareaeffectcloud);
				}
				break;
			default:
				break;
		}
	}
	
	public static long getPillSeed(World worldIn, int primaryColor, int secondaryColor) {
		return AimaggSeedData.getAimaggSeed(worldIn) + ((long)primaryColor << 24 | (long)secondaryColor);
	}
	
	public static long getPillSeed(World worldIn, ItemStack stack) {
		NBTTagCompound pNBT = stack.getSubCompound(Constants.MOD_ID + "_pill");
		return pNBT != null ? AimaggItemPill.getPillSeed(worldIn, pNBT.getInteger("color1"), pNBT.getInteger("color2")) : 0;
	}
	
	public static PillEffect getPillEffect(World worldIn, int primaryColor, int secondaryColor) {
		return PillEffect.values()[new Random(AimaggItemPill.getPillSeed(worldIn, primaryColor, secondaryColor)).nextInt(PillEffect.values().length)];
	}
	
	public static PillEffect getPillEffect(World worldIn, ItemStack stack) {
		return PillEffect.values()[new Random(AimaggItemPill.getPillSeed(worldIn, stack)).nextInt(PillEffect.values().length)];
	}
	
	public static enum PillType {
		
		NORMAL(),
		RANDOM(),
		SET();
		
		private final ModelResourceLocation mrl;

		PillType() {
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":pill/" + this.name().toLowerCase(), "inventory");
		}
		
		public int getMetadata() {
			return this.ordinal();
		}
		
		public ModelResourceLocation getModel() {
			return mrl;
		}

		public static PillType getFromMetadata(int metadata) {
			return PillType.values()[metadata];
		}
		
	}
	
	public static enum PillEffect {
		
		FULL_HEALTH(Polarity.POSITIVE, null),                       //0
		BAD_TRIP(Polarity.NEGATIVE, FULL_HEALTH),					//1
		BAD_GAS(Polarity.POSITIVE, null),							//2
		HEALTH_IS_FOOD(Polarity.NEUTRAL, null),						//3
		EXPLOSIVE_DIARRHEA(Polarity.NEUTRAL, null),					//4
		I_FOUND_PILLS(Polarity.NEUTRAL, null),						//5
		I_CAN_SEE_FOREVER(Polarity.POSITIVE, null),					//6
		I_CANT_SEE_FOREVER(Polarity.NEGATIVE, I_CAN_SEE_FOREVER),   //7
		PRETTY_FLY(Polarity.POSITIVE, null),                        //8
		PUBERTY(Polarity.NEUTRAL, null), 							//9
		TELEPILLS(Polarity.NEUTRAL, null),							//10
		BALLS_OF_STEEL(Polarity.POSITIVE, null),					//11
		AMNESIA(Polarity.NEGATIVE, I_FOUND_PILLS),					//12
		FORTY_EIGHT_HOUR_ENERGY(Polarity.POSITIVE, null),			//13
		HEALTH_UP(Polarity.POSITIVE, null),							//14
		HEALTH_DOWN(Polarity.NEGATIVE, HEALTH_UP),					//15
		HEMATEMESIS(Polarity.NEUTRAL, null),						//16
		LEMON_PARTY(Polarity.NEUTRAL, null),						//17
		LUCK_UP(Polarity.POSITIVE, null),							//18
		LUCK_DOWN(Polarity.NEGATIVE, LUCK_UP),						//19
		PARALYSIS(Polarity.NEGATIVE, null),							//20
		R_U_A_WIZARD(Polarity.NEUTRAL, null),						//21
		PERCS(Polarity.POSITIVE, null),								//22
		ADDICTED(Polarity.NEGATIVE, PERCS),							//23
		INFESTED_1(Polarity.POSITIVE, null),						//24
		INFESTED_2(Polarity.POSITIVE, null),						//25
		RELAX(Polarity.NEUTRAL, null),								//26
		SPEED_UP(Polarity.POSITIVE, null),							//27
		SPEED_DOWN(Polarity.NEGATIVE, SPEED_UP),					//28
		DAMAGE_UP(Polarity.POSITIVE, null),							//29
		DAMAGE_DOWN(Polarity.NEGATIVE, DAMAGE_UP),					//30
		PHEROMONES(Polarity.POSITIVE, null),						//31
		POWER_PILL(Polarity.POSITIVE, null),						//32
		ATTACK_SPEED_UP(Polarity.POSITIVE, null),					//33
		ATTACK_SPEED_DOWN(Polarity.NEGATIVE, ATTACK_SPEED_UP),		//34
		FRIENDS_TILL_THE_END(Polarity.POSITIVE, null),				//35
		QUESTION_MARKS(Polarity.NEGATIVE, PUBERTY),					//36
		FEELS_LIKE_IM_WALKING_ON_SUNSHINE(Polarity.POSITIVE, null), //37
		GULP(Polarity.POSITIVE, null),								//38
		HORF(Polarity.NEUTRAL, null),								//39
		IM_DROWSY(Polarity.POSITIVE, null),							//40
		IM_EXCITED(Polarity.NEGATIVE, IM_DROWSY),					//41
		SOMETHINGS_WRONG(Polarity.NEUTRAL, null),					//42
		VURP(Polarity.NEUTRAL, null),								//43
		XLAX(Polarity.NEUTRAL, null);								//44
		
		private final Polarity polarity;
		private final PillEffect fallback;

		PillEffect(Polarity pol, PillEffect fallback) {
			this.polarity = pol;
			this.fallback = fallback;
		}
		
		public PillEffect getFallback() {
			return fallback;
		}
		
		public Polarity getPolarity() {
			return polarity;
		}

		public static enum Polarity {
			POSITIVE(0x00BB00,0x003300,0xFFFFFF,0x88FF44,0x44FF00,0x22DD00),
			NEUTRAL(0xCC9900,0x331700,0xFFFF77,0xFFFF00,0xFFBB00,0xFF8800),
			NEGATIVE(0xAA0000,0x330000,0xFF6600,0xFF0000,0xCC0022,0xDD0000);
			
			public final int color;
			public final int colorShadow;
			public final int colorEffect1;
			public final int colorEffect2;
			public final int colorEffect3;
			public final int colorEffect4;
			
			Polarity(int color, int colorShadow, int colorEffect1, int colorEffect2, int colorEffect3, int colorEffect4) {
				this.color = color;
				this.colorShadow = colorShadow;
				this.colorEffect1 = colorEffect1;
				this.colorEffect2 = colorEffect2;
				this.colorEffect3 = colorEffect3;
				this.colorEffect4 = colorEffect4;
			}
		}
		
	}

}
