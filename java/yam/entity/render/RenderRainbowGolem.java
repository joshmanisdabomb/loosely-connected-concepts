package yam.entity.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.EntityRainbowGolem;

public class RenderRainbowGolem extends RenderIronGolem {

	private static final ResourceLocation ironGolemTextures = new ResourceLocation(YetAnotherMod.MODID + ":textures/entity/rainbowgolem.png");
    /** Iron Golem's Model. */
    private final ModelIronGolem ironGolemModel;

    public RenderRainbowGolem()
    {
        super();
        this.ironGolemModel = (ModelIronGolem)this.mainModel;
    }
    
    protected ResourceLocation getEntityTexture(EntityRainbowGolem par1EntityRainbowGolem)
    {
        return ironGolemTextures;
    }
    
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return ironGolemTextures;
    }
    
}
