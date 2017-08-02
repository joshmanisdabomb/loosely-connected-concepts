package com.joshmanisdabomb.aimagg.world.explosion;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AimaggExplosion extends Explosion {

	/** whether or not the explosion sets fire to blocks around it */
	protected final boolean causesFire;
	/** whether or not this explosion spawns particles */
	protected final boolean spawnParticles;
	/** whether or not this explosion damages terrain */
	protected final boolean damagesTerrain;
	/** the drop chance of stuff destroyed */
	protected final float dropChance;
	protected final boolean playSound;
	
	protected boolean sendToClients = false;
	
	protected long randomSeed;
	protected final Random random;
	
	protected final World world;
	protected final double x;
	protected final double y;
	protected final double z;
	protected final Entity exploder;
	protected final float size;
	/** A list of ChunkPositions of blocks affected by this explosion */
	protected final List<BlockPos> affectedBlockPositions;
	/**
	 * Maps players to the knockback vector applied by the explosion, to send to
	 * the client
	 */
	protected final Map<EntityPlayer, Vec3d> playerKnockbackMap;
	protected final Vec3d position;

	@SideOnly(Side.CLIENT)
    public AimaggExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean affectEntities, boolean playSound, boolean spawnParticles, boolean damagesTerrain, boolean flaming, float dropChance, List<BlockPos> affectedPositions)
    {
        this(worldIn, entityIn, x, y, z, size, affectEntities, playSound, spawnParticles, damagesTerrain, flaming, dropChance);
        this.affectedBlockPositions.addAll(affectedPositions);
    }
	
	public AimaggExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean affectEntities, boolean playSound, boolean spawnParticles, boolean damagesTerrain, boolean flaming, float dropChance) {
		super(worldIn, entityIn, x, y, z, size, flaming, damagesTerrain);
		this.randomSeed = System.currentTimeMillis();
		this.random = new Random(randomSeed);
		this.affectedBlockPositions = Lists.<BlockPos>newArrayList();
		this.playerKnockbackMap = Maps.<EntityPlayer, Vec3d>newHashMap();
		this.world = worldIn;
		this.exploder = entityIn;
		this.size = size;
		this.x = x;
		this.y = y;
		this.z = z;
		this.causesFire = flaming;
		this.damagesTerrain = damagesTerrain;
		this.spawnParticles = spawnParticles;
		this.playSound = playSound;
		this.dropChance = dropChance;
		this.position = new Vec3d(this.x, this.y, this.z);
	}

	public void addBlocksToList() {
		Set<BlockPos> set = Sets.<BlockPos>newHashSet();
		int i = 16;
		
		for (int j = 0; j < 16; ++j) {
			for (int k = 0; k < 16; ++k) {
				for (int l = 0; l < 16; ++l) {
					if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
						double d0 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
						double d1 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
						double d2 = (double) ((float) l / 15.0F * 2.0F - 1.0F);
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 = d0 / d3;
						d1 = d1 / d3;
						d2 = d2 / d3;
						float f = this.size * (0.7F + this.world.rand.nextFloat() * 0.6F);
						double d4 = this.x;
						double d6 = this.y;
						double d8 = this.z;

						for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
							BlockPos blockpos = new BlockPos(d4, d6, d8);
							IBlockState iblockstate = this.world.getBlockState(blockpos);

							if (iblockstate.getMaterial() != Material.AIR) {
								float f2 = this.getEffectiveResistance(world, iblockstate, blockpos);
								f -= (f2 + 0.3F) * 0.3F;
							}

							if (f > 0.0F && (this.exploder == null || this.exploder.canExplosionDestroyBlock(this, this.world, blockpos, iblockstate, f))) {
								set.add(blockpos);
							}

							d4 += d0 * 0.30000001192092896D;
							d6 += d1 * 0.30000001192092896D;
							d8 += d2 * 0.30000001192092896D;
						}
					}
				}
			}
		}

		this.affectedBlockPositions.addAll(set);

	}

	public void affectEntities() {
		float f3 = this.size * 2.0F;
		int k1 = MathHelper.floor(this.x - (double) f3 - 1.0D);
		int l1 = MathHelper.floor(this.x + (double) f3 + 1.0D);
		int i2 = MathHelper.floor(this.y - (double) f3 - 1.0D);
		int i1 = MathHelper.floor(this.y + (double) f3 + 1.0D);
		int j2 = MathHelper.floor(this.z - (double) f3 - 1.0D);
		int j1 = MathHelper.floor(this.z + (double) f3 + 1.0D);
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
		Vec3d vec3d = new Vec3d(this.x, this.y, this.z);

		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);

			if (!entity.isImmuneToExplosions()) {
				double d12 = entity.getDistance(this.x, this.y, this.z) / (double) f3;

				if (d12 <= 1.0D) {
					double d5 = entity.posX - this.x;
					double d7 = entity.posY + (double) entity.getEyeHeight() - this.y;
					double d9 = entity.posZ - this.z;
					double d13 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

					if (d13 != 0.0D) {
						d5 = d5 / d13;
						d7 = d7 / d13;
						d9 = d9 / d13;
						double d14 = (double) this.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
						double d10 = (1.0D - d12) * d14;
						double d11 = d10;

						if (entity instanceof EntityLivingBase) {
							d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase) entity, d10);
						}
						
						this.onEntityExploded(entity, d5 * d11, d7 * d11, d9 * d11, (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f3 + 1.0D)));
					}
				}
			}
		}
	}

	public void playSound() {
		if (this.playSound) {
			this.world.playSound((EntityPlayer) null, this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		}
	}

	public void spawnParticles() {
		if (this.spawnParticles) {
			if (this.size >= 2.0F && this.damagesTerrain) {
				this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
			} else {
				this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
			}
	
			for (BlockPos blockpos : this.affectedBlockPositions) {
				double d0 = (double) ((float) blockpos.getX() + this.world.rand.nextFloat());
				double d1 = (double) ((float) blockpos.getY() + this.world.rand.nextFloat());
				double d2 = (double) ((float) blockpos.getZ() + this.world.rand.nextFloat());
				double d3 = d0 - this.x;
				double d4 = d1 - this.y;
				double d5 = d2 - this.z;
				double d6 = (double) MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
				d3 = d3 / d6;
				d4 = d4 / d6;
				d5 = d5 / d6;
				double d7 = 0.5D / (d6 / (double) this.size + 0.1D);
				d7 = d7 * (double) (this.world.rand.nextFloat() * this.world.rand.nextFloat() + 0.3F);
				d3 = d3 * d7;
				d4 = d4 * d7;
				d5 = d5 * d7;
				this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.x) / 2.0D, (d1 + this.y) / 2.0D, (d2 + this.z) / 2.0D, d3, d4, d5);
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
			}
		}
	}

	public void damageTerrain() {
		if (this.damagesTerrain) {
			for (BlockPos blockpos : this.affectedBlockPositions) {
				IBlockState iblockstate = this.world.getBlockState(blockpos);
				Block block = iblockstate.getBlock();

				this.onBlockExploded(this.world, iblockstate, blockpos);
				block.onBlockDestroyedByExplosion(world, blockpos, this);
			}
		}

		if (this.causesFire) {
			for (BlockPos blockpos1 : this.affectedBlockPositions) {
				this.placeFire(this.world, this.world.getBlockState(blockpos1), blockpos1);
			}
		}
	}

	public void detonate() {
		this.addBlocksToList();
		if (this.sendToClients) {}
		this.affectEntities();
		this.playSound();
		this.spawnParticles();
		this.damageTerrain();
	}

	public void sendToClients() {
		this.sendToClients = true;
	}
	
	public void onBlockExploded(World world, IBlockState iblockstate, BlockPos pos) {
		if (iblockstate.getMaterial() != Material.AIR) {
			Block b = iblockstate.getBlock();
			if (b.canDropFromExplosion(this)) {
				b.dropBlockAsItemWithChance(this.world, pos, iblockstate, this.dropChance, 0);
			}
			world.setBlockToAir(pos);
		}
	}
	
	public void placeFire(World world, IBlockState iblockstate, BlockPos pos) {
		if (this.world.getBlockState(pos).getMaterial() == Material.AIR && this.world.getBlockState(pos.down()).isFullBlock() && this.random.nextInt(3) == 0) {
			this.world.setBlockState(pos, Blocks.FIRE.getDefaultState());
		}
	}

	public float getEffectiveResistance(World world, IBlockState iblockstate, BlockPos blockpos) {
		return this.exploder != null ? this.exploder.getExplosionResistance(this, this.world, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(world, blockpos, (Entity)null, this);
	}

	public void onEntityExploded(Entity entity, double knockbackX, double knockbackY, double knockbackZ, float originalDamage) {
		this.damageEntity(entity, originalDamage);
		this.knockbackEntity(entity, knockbackX, knockbackY, knockbackZ);
	}

	public void damageEntity(Entity entity, float originalDamage) {
		entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), originalDamage);
	}

	public void knockbackEntity(Entity entity, double knockbackX, double knockbackY, double knockbackZ) {
		entity.motionX += knockbackX;
		entity.motionY += knockbackY;
		entity.motionZ += knockbackZ;
		
		if (entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;

			if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying)) {
				this.playerKnockbackMap.put(entityplayer, new Vec3d(knockbackX, knockbackY, knockbackZ));
			}
		}
	}

	@Override
	@Deprecated
	public void doExplosionA() {

	}

	@Override
	@Deprecated
	public void doExplosionB(boolean spawnParticles) {

	}
	
}
