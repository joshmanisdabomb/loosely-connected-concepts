package yam.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yam.YetAnotherMod;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityUnicorn extends EntityHorse {

	public EntityUnicorn(World world) {
		super(world);
	}

	private static final IAttribute horseJumpStrength = (new RangedAttribute("horse.jumpStrength", 1.2D, 0.0D, 3.0D)).setDescription("Jump Strength").setShouldWatch(true);
    private static final String[] horseArmorTextures = new String[] {null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png"};
    private static final String[] horseTextures = new String[] {"textures/entity/unicorn/horse_blue.png", "textures/entity/unicorn/horse_purple.png", "textures/entity/unicorn/horse_yellow.png"};
    private static final String[] horseMarkingTextures = new String[] {null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png"};

    private String field_110286_bQ;
    private String[] field_110280_bR = new String[3];   
    private static final String[] field_110269_bA = new String[] {"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] field_110292_bC = new String[] {"", "wo_", "wmo", "wdo", "bdo"};
    private static final String[] field_110273_bx = new String[] {"", "meo", "goo", "dio"};
    
	private static final IEntitySelector horseBreedingSelector = new IEntitySelector()
    {
        private static final String __OBFID = "CL_00001642";
        /**
         * Return whether the specified entity is applicable to this filter.
         */
        public boolean isEntityApplicable(Entity par1Entity)
        {
            return par1Entity instanceof EntityUnicorn;
        }
    };
   
    public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(YetAnotherMod.unicornSpawnEgg, 1);
    }
    
    /*private int getHorseArmorIndex(ItemStack p_110260_1_)
    {
        if (p_110260_1_ == null)
        {
            return 0;
        }
        else
        {
            Item item = p_110260_1_.getItem();
            return item == Items.iron_horse_armor ? 1 : (item == Items.golden_horse_armor ? 2 : (item == Items.diamond_horse_armor ? 3 : 0));
        }
    }
    
    public static boolean func_146085_a(Item p_146085_0_)
    {
        return p_146085_0_ == Items.iron_horse_armor || p_146085_0_ == Items.golden_horse_armor || p_146085_0_ == Items.diamond_horse_armor;
    }*/
    
    @SideOnly(Side.CLIENT)
    public String getHorseTexture()
    {
        if (this.field_110286_bQ == null)
        {
            this.setHorseTexturePaths();
        }

        return this.field_110286_bQ;
    }
    
    @SideOnly(Side.CLIENT)
    private void setHorseTexturePaths()
    {
        this.field_110286_bQ = "horse/";
        this.field_110280_bR[0] = null;
        this.field_110280_bR[1] = null;
        this.field_110280_bR[2] = null;
        int i = this.getHorseType();
        int j = this.getHorseVariant();
        int k;

        if (i == 0)
        {
            k = j & 255;
            int l = (j & 65280) >> 8;
            this.field_110280_bR[0] = horseTextures[k];
            this.field_110286_bQ = this.field_110286_bQ + field_110269_bA[k];
            this.field_110280_bR[1] = horseMarkingTextures[l];
            this.field_110286_bQ = this.field_110286_bQ + field_110292_bC[l];
        }
        else
        {
            this.field_110280_bR[0] = "";
            this.field_110286_bQ = this.field_110286_bQ + "_" + i + "_";
        }

        k = this.func_110241_cb();
        this.field_110280_bR[2] = horseArmorTextures[k];
        this.field_110286_bQ = this.field_110286_bQ + field_110273_bx[k];
    }
	
}