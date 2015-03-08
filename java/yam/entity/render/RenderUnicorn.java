package yam.entity.render;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import yam.entity.EntityUnicorn;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUnicorn extends RenderLiving
{
    private static final Map field_110852_a = Maps.newHashMap();
    private static final String __OBFID = "CL_00001000";

    public RenderUnicorn(ModelBase par1ModelBase, float par2)
    {
        super(par1ModelBase, par2);
    }
    
    protected void preRenderCallback(EntityLivingBase par1Entity, float par2)
    {
        float f1 = 1.2F;

        GL11.glScalef(f1, f1, f1);
        super.preRenderCallback(par1Entity, par2);
    }
    
    public ResourceLocation getEntityTexture(Entity var1)
    {
        String s = ((EntityUnicorn)var1).getHorseTexture();
        ResourceLocation resourcelocation = (ResourceLocation)field_110852_a.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(((EntityUnicorn)var1).getVariantTexturePaths()));
            field_110852_a.put(s, resourcelocation);
        }

        return resourcelocation;
    }
}