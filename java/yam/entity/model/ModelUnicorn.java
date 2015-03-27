package yam.entity.model;

import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import yam.entity.EntityUnicorn;

public class ModelUnicorn extends ModelHorse {

    private ModelRenderer head;
    private ModelRenderer horn;
    
    public ModelUnicorn() {
        super();
        
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
        this.head.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.setBoxRotation(this.head, 0.5235988F, 0.0F, 0.0F);
        
    	this.horn = new ModelRenderer(this, 0, 68);
        this.horn.addBox(-0.5F, -15.0F, 0.5F, 1, 8, 1);
        this.horn.setRotationPoint(0.0F, 9.0F, -8.0F);
        this.setBoxRotation(this.horn, 0.5235988F, 0.0F, 0.0F);
    }
    
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    	
    	if (((EntityUnicorn)par1Entity).isAdultHorse()) {	
            this.horn.render(par7);
    	}
    }
    
    private void setBoxRotation(ModelRenderer par1ModelRenderer, float par2, float par3, float par4)
    {
        par1ModelRenderer.rotateAngleX = par2;
        par1ModelRenderer.rotateAngleY = par3;
        par1ModelRenderer.rotateAngleZ = par4;
    }
    
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
    	super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
    	
    	float f3 = this.updateHorseRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par4);
        float f4 = this.updateHorseRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par4);
        float f5 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par4;
        float f6 = f4 - f3;
        float f7 = f5 / (180F / (float)Math.PI);

        if (f6 > 20.0F)
        {
            f6 = 20.0F;
        }

        if (f6 < -20.0F)
        {
            f6 = -20.0F;
        }

        if (par3 > 0.2F)
        {
            f7 += MathHelper.cos(par2 * 0.4F) * 0.15F * par3;
        }

        EntityHorse entityhorse = (EntityHorse)par1EntityLivingBase;
        float f8 = entityhorse.getGrassEatingAmount(par4);
        float f9 = entityhorse.getRearingAmount(par4);
    	
        this.head.rotationPointY = 4.0F;
        this.head.rotationPointZ = -10.0F;
        this.head.rotateAngleX = 0.5235988F + f7;
        this.head.rotateAngleY = f6 / (180F / (float)Math.PI);
        this.head.rotateAngleX = f9 * (0.2617994F + f7) + f8 * 2.18166F + (1.0F - Math.max(f9, f8)) * this.head.rotateAngleX;
        this.head.rotateAngleY = f9 * (f6 / (180F / (float)Math.PI)) + (1.0F - Math.max(f9, f8)) * this.head.rotateAngleY;
        this.head.rotationPointY = f9 * -6.0F + f8 * 11.0F + (1.0F - Math.max(f9, f8)) * this.head.rotationPointY;
        this.head.rotationPointZ = f9 * -1.0F + f8 * -10.0F + (1.0F - Math.max(f9, f8)) * this.head.rotationPointZ;

        this.horn.rotateAngleX = this.head.rotateAngleX;
        this.horn.rotateAngleY = this.head.rotateAngleY;
        this.horn.rotationPointY = this.head.rotationPointY;
        this.horn.rotationPointZ = this.head.rotationPointZ;
    }
    
    private float updateHorseRotation(float par1, float par2, float par3)
    {
        float f3;

        for (f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F)
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return par1 + par3 * f3;
    }
	
}
