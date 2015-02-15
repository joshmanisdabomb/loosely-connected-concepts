package yam.blocks.entity.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import yam.YetAnotherMod;
import yam.blocks.BlockTrashCan;
import yam.blocks.entity.TileEntityTrashCan;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityTrashCanRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation texture = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/trashcan.png");
    private ModelChest field_147510_h = new ModelChest();

    public TileEntityTrashCanRenderer() {}

    public void renderTileEntityAt(TileEntityTrashCan p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        int i;

        if (!p_147500_1_.hasWorldObj())
        {
            i = 0;
        }
        else
        {
            Block block = p_147500_1_.getBlockType();
            i = p_147500_1_.getBlockMetadata();
        }

        this.bindTexture(texture);
        
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)p_147500_2_, (float)p_147500_4_ + 1.0F, (float)p_147500_6_ + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short short1 = (short)(i * 90);

        GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        
        this.field_147510_h.chestLid.rotateAngleX = -1.6F;
        
        this.field_147510_h.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        this.renderTileEntityAt((TileEntityTrashCan)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}