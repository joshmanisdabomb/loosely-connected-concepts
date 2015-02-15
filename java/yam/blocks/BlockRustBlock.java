package yam.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRustBlock extends BlockGeneric {

	private String[] textures = new String[8];
	private IIcon[] icons = new IIcon[8];
	
	public BlockRustBlock(Material material, String texture) {
		super(material, texture + "/8");
		for (int i = 0; i < 8; i++) {
			int j = i+1;
			textures[i] = texture + "/" + j;
		}
	}
	
	@SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
		return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
        return true;
    }
	
	public boolean onBlockActivated(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_, int par6, float par7, float par8, float par9) {
		int meta = p_149699_1_.getBlockMetadata(p_149699_2_, p_149699_3_, p_149699_4_);
		if (!p_149699_1_.isRemote) {
			if (meta >= 7) {
				p_149699_1_.setBlock(p_149699_2_, p_149699_3_, p_149699_4_, Blocks.iron_block, 0, 3);
			} else {
				p_149699_1_.setBlockMetadataWithNotify(p_149699_2_, p_149699_3_, p_149699_4_, meta++, 3);
			}
			System.out.println(meta);
		}
		return true;
	}
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {}
	
	public IIcon getIcon(int par1, int par2) {
		return icons[par2];
	}
	
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		for (int i = 0; i < 8; i++) {
			icons[i] = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + textures[i]);
		}
		this.blockIcon = icons[7];
	}

}
