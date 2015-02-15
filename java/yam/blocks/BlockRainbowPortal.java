package yam.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.dimension.rainbow.TeleporterRainbow;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;

public class BlockRainbowPortal extends BlockGeneric {

	public BlockRainbowPortal(String texture) {
		super(Material.portal, texture);
		this.setBlockUnbreakable();
		this.setResistance(0.0F);
		this.setLightLevel(1.0F);
		this.setLightOpacity(0);
		this.setCreativeTab(null);
		this.setBlockBounds(0.0F, 0.49F, 0.0F, 1.0F, 0.5F, 1.0F);
		this.canPassThrough();
		this.setRenderType(0, false);
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5Block) {
		if (!checkPortal(par1World, par2, par3, par4)) {breakPortal(par1World, par2, par3, par4);}
	}

	public boolean checkPortal(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlock(par2 + 1, par3, par4) != YetAnotherMod.rainbowBlock && par1World.getBlock(par2 + 1, par3, par4) != YetAnotherMod.rainbowPortal) {return false;}
		if (par1World.getBlock(par2, par3, par4 + 1) != YetAnotherMod.rainbowBlock && par1World.getBlock(par2, par3, par4 + 1) != YetAnotherMod.rainbowPortal) {return false;}
		if (par1World.getBlock(par2 - 1, par3, par4) != YetAnotherMod.rainbowBlock && par1World.getBlock(par2 - 1, par3, par4) != YetAnotherMod.rainbowPortal) {return false;}
		if (par1World.getBlock(par2, par3, par4 - 1) != YetAnotherMod.rainbowBlock && par1World.getBlock(par2, par3, par4 - 1) != YetAnotherMod.rainbowPortal) {return false;}
		return true;
	}
	
	public void breakPortal(World par1World, int par2, int par3, int par4) {
		if (par1World.isRemote) {
			ParticleHandler.particleSparkUpwards(par1World, ParticleType.RAINBOW, par2+0.5, par3+0.5, par4+0.5, 0.05, 0.2, 4);
			ParticleHandler.particleSparkUpwards(par1World, ParticleType.CLOUD, par2+0.5, par3+0.5, par4+0.5, 0.05, 0.2, 2);
		}
		par1World.setBlockToAir(par2, par3, par4);
	}
	
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity par5Entity) {
		if ((par5Entity.ridingEntity == null) && (par5Entity.riddenByEntity == null) && ((par5Entity instanceof EntityPlayerMP)) && par5Entity.dimension == 0)
        {
            EntityPlayerMP player = (EntityPlayerMP) par5Entity;
            MinecraftServer mServer = MinecraftServer.getServer();
            player.mcServer.getConfigurationManager().transferPlayerToDimension(player, YetAnotherMod.rainbowDimID, new TeleporterRainbow(mServer.worldServerForDimension(YetAnotherMod.rainbowDimID)));
        }
	}

}
