package yam.entity.render;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.EntityPsychoPig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPsychoPig extends RenderLiving {

    public RenderPsychoPig(float par3) {
		super(new ModelPig(), par3);
	}

	private static final ResourceLocation pigTexture = new ResourceLocation(YetAnotherMod.MODID, "textures/entity/psychopig.png");
    
    protected ResourceLocation getEntityTexture() {
        return pigTexture;
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		return this.getEntityTexture();
	}
    
}
