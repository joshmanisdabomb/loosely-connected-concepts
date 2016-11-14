package com.joshmanisdabomb.aimagg.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.MissileType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class AimaggEntityMissile extends Entity {
	
	private static final byte chunkLoadRadius = 1;

	private static final DataParameter<Float> CURRENTSPEED = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> STRENGTH = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.FLOAT);

	private static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<BlockPos> DESTINATION = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BLOCK_POS);
	
	private static final DataParameter<Byte> MISSILETYPE = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BYTE);

	private static final DataParameter<Boolean> LAUNCHED = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> FREE = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BOOLEAN);
	
	private Ticket ticket;

	public AimaggEntityMissile(World worldIn) {
		super(worldIn);
		this.setSize(2.5F, 2.5F);
	}

	public AimaggEntityMissile(World worldIn, double x, double y, double z) {
		this(worldIn);
        this.setPosition(x, y, z);
	}

	public ResourceLocation getEntityTexturePath() {
		return this.getMissileType().getEntityTexture();
	}

	public void detonate() {
		switch (this.getMissileType()) {
			case EXPLOSIVE:
				if (!this.worldObj.isRemote) {
					Explosion explosion = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, 3.0F*this.getStrength(), false, true);
			        if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.worldObj, explosion)) {
			        	explosion.doExplosionA();
			        	explosion.doExplosionB(true);
			        }
				} else {
			        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
				}
				break;
			case FIRE:
				if (!this.worldObj.isRemote) {
					Explosion explosion = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, 3.0F*this.getStrength(), true, true);
			        if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.worldObj, explosion)) {
			        	explosion.doExplosionA();
			        	explosion.doExplosionB(true);
			        }
				} else {
			        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
				}
				break;
			case NUCLEAR:
				if (!this.worldObj.isRemote) {
					AimaggEntityNuclearExplosion nukeExp = new AimaggEntityNuclearExplosion(worldObj, this.posX, this.posY, this.posZ);
					nukeExp.setStrength(this.getStrength());
					this.worldObj.spawnEntityInWorld(nukeExp);
				}
			default:
				break;
		}
	}

	@Override
	protected void entityInit() {		
        this.getDataManager().register(LAUNCHED, false);
        this.getDataManager().register(FREE, false);
        this.getDataManager().register(MISSILETYPE, (byte)0);
        this.getDataManager().register(CURRENTSPEED, 0.0F);
        this.getDataManager().register(STRENGTH, 1.0F);
        this.getDataManager().register(ORIGIN, new BlockPos(0,0,0));
        this.getDataManager().register(DESTINATION, new BlockPos(0,0,0));
	}
	
	public void onUpdate() {
		if (this.isLaunched()) {
			if (this.getCurrentSpeed() < this.getMissileType().getInitialSpeed()) {
				this.setCurrentSpeed(this.getMissileType().getInitialSpeed());
			} else if (this.getCurrentSpeed() < this.getMissileType().getTopSpeed()) {
				this.setCurrentSpeed(this.getCurrentSpeed()*this.getMissileType().getSpeedModifier());
			} 

			//Motion
			if (!this.inFreeMotion()) {
				Vec3d v1 = new Vec3d(this.getDestination().getX(),0,this.getDestination().getZ()).subtract(new Vec3d(this.getOrigin().getX(),0,this.getOrigin().getZ()));
				Vec3d v2 = new Vec3d(this.getDestination().getX(),0,this.getDestination().getZ()).subtract(new Vec3d(this.posX,0,this.posZ));
				Vec3d v3 = new Vec3d(this.getDestination().add(0,((v2.lengthVector()/v1.lengthVector())-0.5)*Math.PI*v1.lengthVector(),0)).subtract(new Vec3d(this.getOrigin()));
				Vec3d v4 = v3.normalize();
				
		        this.motionX = v4.xCoord*this.getCurrentSpeed();
		        this.motionY = v4.yCoord*this.getCurrentSpeed();
		        this.motionZ = v4.zCoord*this.getCurrentSpeed();
			}

	        this.setPosition(this.posX+this.motionX,this.posY+this.motionY,this.posZ+this.motionZ);
	        
	        //Rotation
	        double d0 = this.motionX;
            double d1 = this.motionY;
            double d2 = this.motionZ;
            double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
            float f = (float)(-(MathHelper.atan2(d2, d0) * (180D / Math.PI))) + 90;
            float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI))) + 90;
            float s = (float)Math.min(Math.max(Math.abs(this.motionX),Math.max(Math.abs(this.motionY),Math.abs(this.motionZ))),1);
            this.rotationPitch = this.updateRotation(this.rotationPitch, f1, s);
            this.rotationYaw = f;

            //Be Free
            if (this.getEntityBoundingBox().expand(1, 1, 1).isVecInside(new Vec3d(this.getDestination()))) {
            	this.setFreeMotion(true);
            }
            
            //Collision
            List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
            int i = MathHelper.floor_double(this.getEntityBoundingBox().minX) - 1;
            int j = MathHelper.ceiling_double_int(this.getEntityBoundingBox().maxX) + 1;
            int k = MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1;
            int l = MathHelper.ceiling_double_int(this.getEntityBoundingBox().maxY) + 1;
            int i1 = MathHelper.floor_double(this.getEntityBoundingBox().minZ) - 1;
            int j1 = MathHelper.ceiling_double_int(this.getEntityBoundingBox().maxZ) + 1;
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

            try
            {
                for (int k1 = i; k1 < j; ++k1)
                {
                    for (int l1 = i1; l1 < j1; ++l1)
                    {
                        int i2 = (k1 != i && k1 != j - 1 ? 0 : 1) + (l1 != i1 && l1 != j1 - 1 ? 0 : 1);

                        if (i2 != 2 && this.getEntityWorld().isBlockLoaded(blockpos$pooledmutableblockpos.setPos(k1, 64, l1)))
                        {
                            for (int j2 = k; j2 < l; ++j2)
                            {
                                if (i2 <= 0 || j2 != k && j2 != l - 1)
                                {
                                    blockpos$pooledmutableblockpos.setPos(k1, j2, l1);

                                    if (k1 < -30000000 || k1 >= 30000000 || l1 < -30000000 || l1 >= 30000000)
                                    {
                                        this.detonate();
                                        this.setDead();
                                        return;
                                    }

                                    IBlockState iblockstate = this.getEntityWorld().getBlockState(blockpos$pooledmutableblockpos);
                                    if (iblockstate.getBlock() != AimaggBlocks.launchPad) {
                                    	iblockstate.addCollisionBoxToList(this.getEntityWorld(), blockpos$pooledmutableblockpos, this.getEntityBoundingBox(), list, (Entity)null);
                                    }
                                    
                                    if (!list.isEmpty())
                                    {
                                        this.detonate();
                                        this.setDead();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            finally
            {
                blockpos$pooledmutableblockpos.release();
            }
            
            //Chunk Loading
            if (ticket == null)
                requestTicket();
            
            this.forceChunkLoading(chunkCoordX, chunkCoordZ);
		}
	}
	
	//START Chunk Loading
    protected ForgeChunkManager.Ticket getTicketFromForge() {
        return ForgeChunkManager.requestTicket(AimlessAgglomeration.instance, worldObj, ForgeChunkManager.Type.ENTITY);
    }
	
	protected void releaseTicket() {
        ForgeChunkManager.releaseTicket(ticket);
        ticket = null;
    }
	
	private boolean requestTicket() {
        if (!this.isDead) {
            Ticket chunkTicket = getTicketFromForge();
            if (chunkTicket != null) {
                chunkTicket.getModData();
                chunkTicket.setChunkListDepth(9);
                chunkTicket.bindEntity(this);
                setChunkTicket(chunkTicket);
                forceChunkLoading(chunkCoordX, chunkCoordZ);
                return true;
            }
        }
        return false;
    }
	
	public void setChunkTicket(@Nullable Ticket ticket) {
        if (this.ticket != ticket)
            ForgeChunkManager.releaseTicket(this.ticket);
        this.ticket = ticket;
    }

    public void forceChunkLoading(int xChunk, int zChunk) {
        if (ticket == null)
            return;

        Set<ChunkPos> chunkList = new HashSet<ChunkPos>();
        for (int xx = xChunk - chunkLoadRadius; xx <= xChunk + chunkLoadRadius; xx++) {
            for (int zz = zChunk - chunkLoadRadius; zz <= zChunk + chunkLoadRadius; zz++) {
                chunkList.add(new ChunkPos(xx, zz));
            }
        }

        for (ChunkPos chunk : chunkList) {
            ForgeChunkManager.forceChunk(ticket, chunk);
            ForgeChunkManager.reorderChunk(ticket, chunk);
        }


        ChunkPos myChunk = new ChunkPos(xChunk, zChunk);
        ForgeChunkManager.forceChunk(ticket, myChunk);
        ForgeChunkManager.reorderChunk(ticket, myChunk);
    }
    
    @Override
    public void setDead() {
        releaseTicket();
        super.setDead();
    }
	
	//END Chunk Loading
	
	private float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_)
    {
        float f = MathHelper.wrapDegrees(p_75652_2_ - p_75652_1_);

        /*if (f > p_75652_3_)
        {
            f = p_75652_3_;
        }

        if (f < -p_75652_3_)
        {
            f = -p_75652_3_;
        }*/

        return p_75652_1_ + p_75652_3_*f;
    }
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, @Nullable ItemStack stack, EnumHand hand)
    {
        player.startRiding(this);
        return super.processInitialInteract(player, stack, hand);
    }
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
	
	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return entityIn instanceof EntityPlayer;
	}
	
	@Override
	protected boolean canFitPassenger(Entity passenger) {
		return passenger instanceof EntityPlayer;
	}
	
	public void setLaunched(boolean launched) {
        this.getDataManager().set(LAUNCHED, launched);
	}
	
	public boolean isLaunched() {
		return this.getDataManager().get(LAUNCHED);
	}
	
	public void setFreeMotion(boolean free) {
        this.getDataManager().set(FREE, free);
	}
	
	public boolean inFreeMotion() {
		return this.getDataManager().get(FREE);
	}
	
	public void setDestination(BlockPos bp) {
		this.getDataManager().set(DESTINATION, bp);
	}
	
	public BlockPos getDestination() {
		return this.getDataManager().get(DESTINATION);
	}
	
	public void setOrigin(BlockPos bp) {
		this.getDataManager().set(ORIGIN, bp);
	}
	
	public BlockPos getOrigin() {
		return this.getDataManager().get(ORIGIN);
	}
	
	public void setCurrentSpeed(float v) {
		this.getDataManager().set(CURRENTSPEED, v);
	}
	
	public float getCurrentSpeed() {
		return this.getDataManager().get(CURRENTSPEED);
	}
	
	public void setStrength(float v) {
		this.getDataManager().set(STRENGTH, v);
	}
	
	public float getStrength() {
		return this.getDataManager().get(STRENGTH);
	}

	public void setMissileType(MissileType mt) {
		this.getDataManager().set(MISSILETYPE, (byte)mt.getMetadata());
	}
	
	public MissileType getMissileType() {
		return MissileType.getFromMetadata(this.getDataManager().get(MISSILETYPE));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setLaunched(compound.getBoolean("Launched"));
		this.setFreeMotion(compound.getBoolean("FreeMotion"));
		this.setMissileType(MissileType.getFromMetadata(compound.getByte("MissileType")));
		this.setCurrentSpeed(compound.getFloat("CurrentSpeed"));
		this.setStrength(compound.getFloat("Strength"));
		this.setOrigin(new BlockPos(compound.getInteger("OriginX"), compound.getInteger("OriginY"), compound.getInteger("OriginZ")));
		this.setDestination(new BlockPos(compound.getInteger("DestinationX"), compound.getInteger("DestinationY"), compound.getInteger("DestinationZ")));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("Launched", this.isLaunched());
		compound.setBoolean("FreeMotion", this.inFreeMotion());
		compound.setByte("MissileType", this.getDataManager().get(MISSILETYPE));
		compound.setFloat("CurrentSpeed", this.getCurrentSpeed());
		compound.setFloat("Strength", this.getStrength());
		compound.setInteger("OriginX", this.getOrigin().getX());
		compound.setInteger("OriginY", this.getOrigin().getY());
		compound.setInteger("OriginZ", this.getOrigin().getZ());
		compound.setInteger("DestinationX", this.getDestination().getX());
		compound.setInteger("DestinationY", this.getDestination().getY());
		compound.setInteger("DestinationZ", this.getDestination().getZ());
	}
	
}
