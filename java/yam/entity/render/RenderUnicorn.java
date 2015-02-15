package yam.entity.render;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;
import yam.YetAnotherMod;
import yam.entity.EntityUnicorn;

public class RenderUnicorn extends RenderHorse {

    private static final Map field_110852_a = Maps.newHashMap();
    
	public RenderUnicorn(ModelBase p_i1256_1_, float p_i1256_2_) {
		super(p_i1256_1_, p_i1256_2_);
	}
	
	private ResourceLocation func_110848_b(EntityHorse p_110848_1_)
    {
        String s = p_110848_1_.getHorseTexture();
        ResourceLocation resourcelocation = (ResourceLocation)field_110852_a.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(p_110848_1_.getVariantTexturePaths()));
            field_110852_a.put(s, resourcelocation);
        }

        return resourcelocation;
    }

}
