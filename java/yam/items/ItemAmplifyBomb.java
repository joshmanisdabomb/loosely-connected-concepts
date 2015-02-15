package yam.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.entity.EntityAmplifyBomb;

public class ItemAmplifyBomb extends ItemGeneric {

	public ItemAmplifyBomb(String texture) {
		super(texture);
	}
	
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if (!p_77659_3_.capabilities.isCreativeMode)
        {
            --p_77659_1_.stackSize;
        }

        p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!p_77659_2_.isRemote)
        {
            p_77659_2_.spawnEntityInWorld(new EntityAmplifyBomb(p_77659_2_, p_77659_3_));
        }

        return p_77659_1_;
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Fuse Time: \24780.0 seconds");
		list.add("Radius: \24784.0 blocks");
		list.add("Point Blank Damage: \24780.0 hearts");
		list.add("Debuffs: \247eAmplify Damage I-IV");
	}

}
