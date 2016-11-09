package com.joshmanisdabomb.aimagg.entity.missile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public abstract class AimaggEntityMissile extends Entity {
	
	private static final byte chunkLoadRadius = 1;

	private static final DataParameter<Float> CURRENTSPEED = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> STRENGTH = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.FLOAT);

	private static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BLOCK_POS);
	private static final DataParameter<BlockPos> DESTINATION = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BLOCK_POS);

	private static final DataParameter<Boolean> LAUNCHED = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.BOOLEAN);
	
	private Ticket ticket;

	public AimaggEntityMissile(World worldIn) {
		super(worldIn);
		this.setSize(2.5F, 2.5F);
	}

	public AimaggEntityMissile(World worldIn, double x, double y, double z) {
		this(worldIn);
        this.setPosition(x, y, z);
	}

	@Override
	protected void entityInit() {		
        this.getDataManager().register(LAUNCHED, false);
        this.getDataManager().register(CURRENTSPEED, 0.0F);
        this.getDataManager().register(STRENGTH, 1.0F);
        this.getDataManager().register(ORIGIN, new BlockPos(0,0,0));
        this.getDataManager().register(DESTINATION, new BlockPos(0,0,0));
	}
	
	public void onUpdate() {
		if (this.isLaunched()) {
			if (this.getCurrentSpeed() < this.getLaunchSpeed()) {
				this.setCurrentSpeed(this.getLaunchSpeed());
			} else if (this.getCurrentSpeed() < this.getTopSpeed()) {
				this.setCurrentSpeed(this.getCurrentSpeed()*this.getSpeedModifier());
			} 

			//Motion
			Vec3d v1 = new Vec3d(this.getDestination().getX(),0,this.getDestination().getZ()).subtract(new Vec3d(this.getOrigin().getX(),0,this.getOrigin().getZ()));
			Vec3d v2 = new Vec3d(this.getDestination().getX(),0,this.getDestination().getZ()).subtract(new Vec3d(this.posX,0,this.posZ));
			Vec3d v3 = new Vec3d(this.getDestination().add(0,((v2.lengthVector()/v1.lengthVector())-0.5)*Math.PI*v1.lengthVector(),0)).subtract(new Vec3d(this.getOrigin()));
			Vec3d v4 = v3.normalize();
			
			if (v2.lengthVector()/v1.lengthVector() > 1) {
				this.detonate();
                this.setDead();
                return;
			}
			
	        this.motionX = v4.xCoord*this.getCurrentSpeed();
	        this.motionY = v4.yCoord*this.getCurrentSpeed();
	        this.motionZ = v4.zCoord*this.getCurrentSpeed();

	        this.setPosition(this.posX+this.motionX,this.posY+this.motionY,this.posZ+this.motionZ);
	        
	        //Rotation
	        double d0 = this.motionX;
            double d1 = this.motionY;
            double d2 = this.motionZ;
            double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
            float f = (float)(-(MathHelper.atan2(d2, d0) * (180D / Math.PI))) + 90;
            float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI))) + 90;
            float s = (float)Math.max(Math.abs(this.motionX),Math.max(Math.abs(this.motionY),Math.abs(this.motionZ)))*10;
            this.rotationPitch = this.updateRotation(this.rotationPitch, f1, s);
            this.rotationYaw = this.updateRotation(this.rotationYaw, f, s);

            //Collision
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

            for (int i = 0; i < 8; ++i)
            {
                int j = MathHelper.floor_double(this.posY + (double)(((float)((i >> 1) % 2) - 0.5F) * this.height * 0.8F));
                int k = MathHelper.floor_double(this.posX + (double)(((float)((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
                int l = MathHelper.floor_double(this.posZ + (double)(((float)((i >> 2) % 2) - 0.5F) * this.width * 0.8F));

                if (blockpos$pooledmutableblockpos.getX() != k || blockpos$pooledmutableblockpos.getY() != j || blockpos$pooledmutableblockpos.getZ() != l)
                {
                    blockpos$pooledmutableblockpos.setPos(k, j, l);

                    if (this.worldObj.getBlockState(blockpos$pooledmutableblockpos).getBlock().isVisuallyOpaque())
                    {
                        blockpos$pooledmutableblockpos.release();
                        this.detonate();
                        this.setDead();
                        return;
                    }
                }
            }

            blockpos$pooledmutableblockpos.release();
            
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
        float lerp = 0.11F;

        /*if (f > p_75652_3_)
        {
            f = p_75652_3_;
        }

        if (f < -p_75652_3_)
        {
            f = -p_75652_3_;
        }*/

        return p_75652_1_ + lerp*f;
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

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setLaunched(compound.getBoolean("Launched"));
		this.setCurrentSpeed(compound.getFloat("CurrentSpeed"));
		this.setStrength(compound.getFloat("Strength"));
		this.setOrigin(new BlockPos(compound.getInteger("OriginX"), compound.getInteger("OriginY"), compound.getInteger("OriginZ")));
		this.setDestination(new BlockPos(compound.getInteger("DestinationX"), compound.getInteger("DestinationY"), compound.getInteger("DestinationZ")));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("Launched", this.isLaunched());
		compound.setFloat("CurrentSpeed", this.getCurrentSpeed());
		compound.setFloat("Strength", this.getStrength());
		compound.setInteger("OriginX", this.getOrigin().getX());
		compound.setInteger("OriginY", this.getOrigin().getY());
		compound.setInteger("OriginZ", this.getOrigin().getZ());
		compound.setInteger("DestinationX", this.getDestination().getX());
		compound.setInteger("DestinationY", this.getDestination().getY());
		compound.setInteger("DestinationZ", this.getDestination().getZ());
	}

	public abstract ResourceLocation getEntityTexturePath();

	public abstract float getTopSpeed();

	public abstract float getLaunchSpeed();

	public abstract float getSpeedModifier();

	public abstract void detonate();
	
}
