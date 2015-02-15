package yam.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yam.YetAnotherMod;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockRainbow extends BlockGeneric {

	public BlockRainbow(String texture1, String texture2) {
		super(Material.iron, "storage/rainbow/" + texture1, "storage/rainbow/" + texture1, "storage/rainbow/" + texture2, "storage/rainbow/" + texture2);
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		ParticleHandler.particleSpark(par1World, ParticleType.RAINBOW, par2+0.5, par3+0.5, par4+0.5, 0.05, 20);
		ParticleHandler.particleSpark(par1World, ParticleType.CLOUD, par2+0.5, par3+0.5, par4+0.5, 0.05, 10);
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
	}

	@SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
		ParticleHandler.particleSpark(worldObj, ParticleType.RAINBOW, target.blockX+0.5, target.blockY+0.5, target.blockZ+0.5, 0.05, 1);
		ParticleHandler.particleSpark(worldObj, ParticleType.CLOUD, target.blockX+0.5, target.blockY+0.5, target.blockZ+0.5, 0.05, 1);
		return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
		ParticleHandler.particleSpark(world, ParticleType.RAINBOW, x+0.5, y+0.5, z+0.5, 0.05, 50);
		ParticleHandler.particleSpark(world, ParticleType.CLOUD, x+0.5, y+0.5, z+0.5, 0.05, 25);
        return true;
    }

}
