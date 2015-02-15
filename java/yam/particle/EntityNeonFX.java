package yam.particle;

import java.awt.Color;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yam.YetAnotherMod;

@SideOnly(Side.CLIENT)
public class EntityNeonFX extends EntityFX {

    private static final ResourceLocation texture = new ResourceLocation(YetAnotherMod.MODID, "textures/particle/neon.png");
	private Random rand = new Random();
	private float initialParticleScale;
    
	public EntityNeonFX(World par1World, double x, double y, double z) {
        super(par1World, x, y, z);
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        setMaxAge(15+rand.nextInt(15));
        setScale(0.5F+(rand.nextFloat()*2));
        setGravity(0.0F);
        newRandomColour();
    }
	
	public EntityNeonFX(World par1World, double x, double y, double z, double speedX, double speedY, double speedZ) {
        this(par1World, x, y, z);
        this.motionX = speedX;
        this.motionY = speedY;
        this.motionZ = speedZ;
    }
	
	private void newRandomColour() {
		Color col = Color.getHSBColor(0.5F+(rand.nextFloat()*0.375F),1.0F,0.5F);
		this.particleRed = col.getRed();
		this.particleGreen = col.getGreen();
		this.particleBlue = col.getBlue();
	}
	
	public void onUpdate() {
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead(); return;
        }
        
		particleScale *= 0.9;
                
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        this.moveEntity(motionX, motionY, motionZ);
        this.motionX *= 0.9;
        this.motionY *= 0.9;
        this.motionZ *= 0.9;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

	public void renderParticle(Tessellator tess, float partialTicks, float par3, float par4, float par5, float par6, float par7) {       
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    	GL11.glDepthMask(false);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	tess.startDrawingQuads();
    	tess.setBrightness(getBrightnessForRender(partialTicks));
    	float scale = 0.1F*particleScale;
    	float x = (float)(prevPosX + (posX - prevPosX) * partialTicks - interpPosX);
    	float y = (float)(prevPosY + (posY - prevPosY) * partialTicks - interpPosY);
    	float z = (float)(prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ);
        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
    	tess.addVertexWithUV(x - par3 * scale - par6 * scale, y - par4 * scale, z - par5 * scale - par7 * scale, 0, 0);
    	tess.addVertexWithUV(x - par3 * scale + par6 * scale, y + par4 * scale, z - par5 * scale + par7 * scale, 1, 0);
    	tess.addVertexWithUV(x + par3 * scale + par6 * scale, y + par4 * scale, z + par5 * scale + par7 * scale, 1, 1);
    	tess.addVertexWithUV(x + par3 * scale - par6 * scale, y - par4 * scale, z + par5 * scale - par7 * scale, 0, 1);
    	tess.draw();
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glDepthMask(true);
    	GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
    }
    
    public int getFXLayer() {
    	return 3;
    }

    public EntityNeonFX setMaxAge(int maxAge) {
    	particleMaxAge = maxAge;
    	return this;
    }

    public EntityNeonFX setGravity(float gravity) {
    	particleGravity = gravity;
    	return this;
    }
    
    public EntityNeonFX setScale(float scale) {
    	particleScale = scale;
    	initialParticleScale = scale;
    	return this;
    }
    
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    public float getBrightness(float par1)
    {
        return 1.0F;
    }

}
