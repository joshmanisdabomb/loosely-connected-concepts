package com.joshmanisdabomb.aimagg.blocks;

import java.util.Collection;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public interface AimaggBlockAdvancedRendering {
	
    public ModelResourceLocation getCustomModelLocation();
    
    public String getInternalName();
	
	public IModel newModel(Block b);

	public Collection<ResourceLocation> getTextures();
	
}