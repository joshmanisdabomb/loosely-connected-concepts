package com.joshmanisdabomb.aimagg.blocks.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.joshmanisdabomb.aimagg.blocks.AimaggBlockAdvancedRendering;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicConnected;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public class AimaggBlockConnectedTextureModel implements IModel {

	private final Block block;

	public AimaggBlockConnectedTextureModel(Block b) {
		this.block = b;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new AimaggBlockConnectedTextureBakedModel(this, state, format, bakedTextureGetter);
	}
	
	@Override
    public Collection<ResourceLocation> getTextures() {
        return ((AimaggBlockAdvancedRendering)this.block).getTextures();
    }
	
	public static class AimaggBlockConnectedTextureBakedModel implements IBakedModel {

		private final TextureAtlasSprite[] sprites;
	    private final VertexFormat format;

		public AimaggBlockConnectedTextureBakedModel(IModel model, IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
	        this.format = format;
	        this.sprites = new TextureAtlasSprite[model.getTextures().size()];
	        int i = 0;
	        for (ResourceLocation rl : model.getTextures()) {
	        	this.sprites[i++] = bakedTextureGetter.apply(rl);
	        }
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {	
			//TODO finish orientating everything properly BEFORE RELEASING
			
			List<BakedQuad> quads = new ArrayList<BakedQuad>();
			
			int metadata = ((AimaggBlockBasicConnected)state.getBlock()).getMetaFromState(state);
			
			IExtendedBlockState estate = (IExtendedBlockState)state;
			boolean n = false;
			boolean s = false;
			boolean e = false;
			boolean w = false;
			boolean u = false;
			boolean d = false;
			boolean nw = false;
			boolean ne = false;
			boolean sw = false;
			boolean se = false;
			boolean nu = false;
			boolean eu = false;
			boolean su = false;
			boolean wu = false;
			boolean nd = false;
			boolean ed = false;
			boolean sd = false;
			boolean wd = false;
			try {
				n = estate.getValue(AimaggBlockBasicConnected.N);
				s = estate.getValue(AimaggBlockBasicConnected.S);
				e = estate.getValue(AimaggBlockBasicConnected.E);
				w = estate.getValue(AimaggBlockBasicConnected.W);
				u = estate.getValue(AimaggBlockBasicConnected.U);
				d = estate.getValue(AimaggBlockBasicConnected.D);
				nw = estate.getValue(AimaggBlockBasicConnected.NW);
				ne = estate.getValue(AimaggBlockBasicConnected.NE);
				sw = estate.getValue(AimaggBlockBasicConnected.SW);
				se = estate.getValue(AimaggBlockBasicConnected.SE);
				nu = estate.getValue(AimaggBlockBasicConnected.NU);
				eu = estate.getValue(AimaggBlockBasicConnected.EU);
				su = estate.getValue(AimaggBlockBasicConnected.SU);
				wu = estate.getValue(AimaggBlockBasicConnected.WU);
				nd = estate.getValue(AimaggBlockBasicConnected.ND);
				ed = estate.getValue(AimaggBlockBasicConnected.ED);
				sd = estate.getValue(AimaggBlockBasicConnected.SD);
				wd = estate.getValue(AimaggBlockBasicConnected.WD);
			} catch (NullPointerException ex) {
				//TODO need a better way to figure out whether the block is being pushed or is falling
			}
			
			//Side: Up
			//saving for spreader model: quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), this.getTexture(TextureType.SIDE_BASE, metadata), 0, 0, 16, 16));
			if (!u) {
				quads.add(createQuad(new Vec3d(0.25, 1, 0.25), new Vec3d(0.25, 1, 0.75), new Vec3d(0.75, 1, 0.75), new Vec3d(0.75, 1, 0.25), this.getTexture(TextureType.TOP_BASE, metadata), 4, 4, 12, 12));
				
				quads.add(createQuad(new Vec3d(0.25, 1, 0), new Vec3d(0.25, 1, 0.25), new Vec3d(0.75, 1, 0.25), new Vec3d(0.75, 1, 0), n ? this.getTexture(TextureType.TOP_BASE, metadata) : this.getTexture(TextureType.TOP_LINES_H, metadata), 4, 0, 12, 4));
				quads.add(createQuad(new Vec3d(0.25, 1, 0.75), new Vec3d(0.25, 1, 1), new Vec3d(0.75, 1, 1), new Vec3d(0.75, 1, 0.75), s ? this.getTexture(TextureType.TOP_BASE, metadata) : this.getTexture(TextureType.TOP_LINES_H, metadata), 4, 12, 12, 16));
				quads.add(createQuad(new Vec3d(0.75, 1, 0.25), new Vec3d(0.75, 1, 0.75), new Vec3d(1, 1, 0.75), new Vec3d(1, 1, 0.25), e ? this.getTexture(TextureType.TOP_BASE, metadata) : this.getTexture(TextureType.TOP_LINES_V, metadata), 12, 4, 16, 12));
				quads.add(createQuad(new Vec3d(0, 1, 0.25), new Vec3d(0, 1, 0.75), new Vec3d(0.25, 1, 0.75), new Vec3d(0.25, 1, 0.25), w ? this.getTexture(TextureType.TOP_BASE, metadata) : this.getTexture(TextureType.TOP_LINES_V, metadata), 0, 4, 4, 12));
				
				quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 1, 0.25), new Vec3d(0.25, 1, 0.25), new Vec3d(0.25, 1, 0), (n && w && nw) ? this.getTexture(TextureType.TOP_BASE, metadata) : (n && !w) ? this.getTexture(TextureType.TOP_LINES_V, metadata) : (!n && w) ? this.getTexture(TextureType.TOP_LINES_H, metadata) : this.getTexture(TextureType.TOP_CORNERS, metadata), (n && w && !nw) ? 4 : 0, 0, (n && w && !nw) ? 8 : 4, 4));
				quads.add(createQuad(new Vec3d(0.75, 1, 0), new Vec3d(0.75, 1, 0.25), new Vec3d(1, 1, 0.25), new Vec3d(1, 1, 0), (n && e && ne) ? this.getTexture(TextureType.TOP_BASE, metadata) : (n && !e) ? this.getTexture(TextureType.TOP_LINES_V, metadata) : (!n && e) ? this.getTexture(TextureType.TOP_LINES_H, metadata) : this.getTexture(TextureType.TOP_CORNERS, metadata), (n && e && !ne) ? 8 : 12, 0, (n && e && !ne) ? 12 : 16, 4));
				quads.add(createQuad(new Vec3d(0, 1, 0.75), new Vec3d(0, 1, 1), new Vec3d(0.25, 1, 1), new Vec3d(0.25, 1, 0.75), (s && w && sw) ? this.getTexture(TextureType.TOP_BASE, metadata) : (s && !w) ? this.getTexture(TextureType.TOP_LINES_V, metadata) : (!s && w) ? this.getTexture(TextureType.TOP_LINES_H, metadata) : this.getTexture(TextureType.TOP_CORNERS, metadata), (s && w && !sw) ? 4 : 0, 12, (s && w && !sw) ? 8 : 4, 16));
				quads.add(createQuad(new Vec3d(0.75, 1, 0.75), new Vec3d(0.75, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0.75), (s && e && se) ? this.getTexture(TextureType.TOP_BASE, metadata) : (s && !e) ? this.getTexture(TextureType.TOP_LINES_V, metadata) : (!s && e) ? this.getTexture(TextureType.TOP_LINES_H, metadata) : this.getTexture(TextureType.TOP_CORNERS, metadata), (s && e && !se) ? 8 : 12, 12, (s && e && !se) ? 12 : 16, 16));
			}
			
			//Side: Down
			//saving for spreader model: quads.add(createQuad(new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), this.getTexture(TextureType.SIDE_BASE, metadata), 0, 0, 16, 16));
			if (!d) {
				quads.add(createQuad(new Vec3d(0.25, 0.001, 0.25), new Vec3d(0.75, 0.001, 0.25), new Vec3d(0.75, 0.001, 0.75), new Vec3d(0.25, 0.001, 0.75), this.getTexture(TextureType.BOTTOM_BASE, metadata), 4, 4, 12, 12));
				
				quads.add(createQuad(new Vec3d(0.75, 0.001, 0.25), new Vec3d(0.25, 0.001, 0.25), new Vec3d(0.25, 0.001, 0), new Vec3d(0.75, 0.001, 0), n ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : this.getTexture(TextureType.BOTTOM_LINES_V, metadata), 12, 4, 16, 12));
				quads.add(createQuad(new Vec3d(0.75, 0.001, 1), new Vec3d(0.25, 0.001, 1), new Vec3d(0.25, 0.001, 0.75), new Vec3d(0.75, 0.001, 0.75), s ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : this.getTexture(TextureType.BOTTOM_LINES_V, metadata), 0, 4, 4, 12));
				quads.add(createQuad(new Vec3d(0.75, 0.001, 0.25), new Vec3d(1, 0.001, 0.25), new Vec3d(1, 0.001, 0.75), new Vec3d(0.75, 0.001, 0.75), e ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : this.getTexture(TextureType.BOTTOM_LINES_H, metadata), 4, 12, 12, 16));
				quads.add(createQuad(new Vec3d(0, 0.001, 0.25), new Vec3d(0.25, 0.001, 0.25), new Vec3d(0.25, 0.001, 0.75), new Vec3d(0, 0.001, 0.75), w ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : this.getTexture(TextureType.BOTTOM_LINES_H, metadata), 4, 0, 12, 4));
				
				quads.add(createQuad(new Vec3d(0, 0.001, 0), new Vec3d(0.25, 0.001, 0), new Vec3d(0.25, 0.001, 0.25), new Vec3d(0, 0.001, 0.25), (n && w && nw) ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : (n && !w) ? this.getTexture(TextureType.BOTTOM_LINES_H, metadata) : (!n && w) ? this.getTexture(TextureType.BOTTOM_LINES_V, metadata) : this.getTexture(TextureType.BOTTOM_CORNERS, metadata), (n && w && !nw) ? 4 : 0, 0, (n && w && !nw) ? 8 : 4, 4));
				quads.add(createQuad(new Vec3d(0.75, 0.001, 0), new Vec3d(1, 0.001, 0), new Vec3d(1, 0.001, 0.25), new Vec3d(0.75, 0.001, 0.25), (n && e && ne) ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : (n && !e) ? this.getTexture(TextureType.BOTTOM_LINES_H, metadata) : (!n && e) ? this.getTexture(TextureType.BOTTOM_LINES_V, metadata) : this.getTexture(TextureType.BOTTOM_CORNERS, metadata), (n && e && !ne) ? 4 : 0, 12, (n && e && !ne) ? 8 : 4, 16));
				quads.add(createQuad(new Vec3d(0, 0.001, 0.75), new Vec3d(0.25, 0.001, 0.75), new Vec3d(0.25, 0.001, 1), new Vec3d(0, 0.001, 1), (s && w && sw) ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : (s && !w) ? this.getTexture(TextureType.BOTTOM_LINES_H, metadata) : (!s && w) ? this.getTexture(TextureType.BOTTOM_LINES_V, metadata) : this.getTexture(TextureType.BOTTOM_CORNERS, metadata), (s && w && !sw) ? 8 : 12, 0, (s && w && !sw) ? 12 : 16, 4));
				quads.add(createQuad(new Vec3d(0.75, 0.001, 0.75), new Vec3d(1, 0.001, 0.75), new Vec3d(1, 0.001, 1), new Vec3d(0.75, 0.001, 1), (s && e && se) ? this.getTexture(TextureType.BOTTOM_BASE, metadata) : (s && !e) ? this.getTexture(TextureType.BOTTOM_LINES_H, metadata) : (!s && e) ? this.getTexture(TextureType.BOTTOM_LINES_V, metadata) : this.getTexture(TextureType.BOTTOM_CORNERS, metadata), (s && e && !se) ? 8 : 12, 12, (s && e && !se) ? 12 : 16, 16));
			}
			
			//Side: North
			//saving for spreader model: quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), this.getTexture(TextureType.SIDE_BASE, metadata), 0, 0, 16, 16));
			if (!n) {
				quads.add(createQuad(new Vec3d(0.25, 0.75, 0), new Vec3d(0.75, 0.75, 0), new Vec3d(0.75, 0.25, 0), new Vec3d(0.25, 0.25, 0), this.getTexture(TextureType.SIDE_BASE, metadata), 4, 4, 12, 12));
				
				quads.add(createQuad(new Vec3d(0.25, 0.25, 0), new Vec3d(0.75, 0.25, 0), new Vec3d(0.75, 0, 0), new Vec3d(0.25, 0, 0), d ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 12, 4, 16, 12));
				quads.add(createQuad(new Vec3d(0.25, 1, 0), new Vec3d(0.75, 1, 0), new Vec3d(0.75, 0.75, 0), new Vec3d(0.25, 0.75, 0), u ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 0, 4, 4, 12));
				quads.add(createQuad(new Vec3d(0.75, 0.75, 0), new Vec3d(1, 0.75, 0), new Vec3d(1, 0.25, 0), new Vec3d(0.75, 0.25, 0), e ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 12, 12, 16));
				quads.add(createQuad(new Vec3d(0, 0.75, 0), new Vec3d(0.25, 0.75, 0), new Vec3d(0.25, 0.25, 0), new Vec3d(0, 0.25, 0), w ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 0, 12, 4));
				
				quads.add(createQuad(new Vec3d(0, 0.25, 0), new Vec3d(0.25, 0.25, 0), new Vec3d(0.25, 0, 0), new Vec3d(0, 0, 0), (d && w && wd) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (d && !w) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!d && w) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (d && w && !wd) ? 8 : 12, 0, (d && w && !wd) ? 12 : 16, 4));
				quads.add(createQuad(new Vec3d(0.75, 0.25, 0), new Vec3d(1, 0.25, 0), new Vec3d(1, 0, 0), new Vec3d(0.75, 0, 0), (d && e && ed) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (d && !e) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!d && e) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (d && e && !ed) ? 8 : 12, 12, (d && e && !ed) ? 12 : 16, 16));
				quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0.25, 1, 0), new Vec3d(0.25, 0.75, 0), new Vec3d(0, 0.75, 0), (u && w && wu) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (u && !w) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!u && w) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (u && w && !wu) ? 4 : 0, 0, (u && w && !wu) ? 8 : 4, 4));
				quads.add(createQuad(new Vec3d(0.75, 1, 0), new Vec3d(1, 1, 0), new Vec3d(1, 0.75, 0), new Vec3d(0.75, 0.75, 0), (u && e && eu) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (u && !e) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!u && e) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (u && e && !eu) ? 4 : 0, 12, (u && e && !eu) ? 8 : 4, 16));
			}
			
			//Side: South
			//saving for spreader model: quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), this.getTexture(TextureType.SIDE_BASE, metadata), 0, 0, 16, 16));
			if (!s) {
				quads.add(createQuad(new Vec3d(0.25, 0.25, 1), new Vec3d(0.75, 0.25, 1), new Vec3d(0.75, 0.75, 1), new Vec3d(0.25, 0.75, 1), this.getTexture(TextureType.SIDE_BASE, metadata), 4, 4, 12, 12));
				
				quads.add(createQuad(new Vec3d(0.25, 0, 1), new Vec3d(0.75, 0, 1), new Vec3d(0.75, 0.25, 1), new Vec3d(0.25, 0.25, 1), d ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 0, 4, 4, 12));
				quads.add(createQuad(new Vec3d(0.25, 0.75, 1), new Vec3d(0.75, 0.75, 1), new Vec3d(0.75, 1, 1), new Vec3d(0.25, 1, 1), u ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 12, 4, 16, 12));
				quads.add(createQuad(new Vec3d(0.75, 0.25, 1), new Vec3d(1, 0.25, 1), new Vec3d(1, 0.75, 1), new Vec3d(0.75, 0.75, 1), e ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 12, 12, 16));
				quads.add(createQuad(new Vec3d(0, 0.25, 1), new Vec3d(0.25, 0.25, 1), new Vec3d(0.25, 0.75, 1), new Vec3d(0, 0.75, 1), w ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 0, 12, 4));
				
				quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(0.25, 0, 1), new Vec3d(0.25, 0.25, 1), new Vec3d(0, 0.25, 1), (d && w && wd) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (d && !w) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!d && w) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (d && w && !wd) ? 4 : 0, 0, (d && w && !wd) ? 8 : 4, 4));
				quads.add(createQuad(new Vec3d(0.75, 0, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0.25, 1), new Vec3d(0.75, 0.25, 1), (d && e && ed) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (d && !e) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!d && e) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (d && e && !ed) ? 4 : 0, 12, (d && e && !ed) ? 8 : 4, 16));
				quads.add(createQuad(new Vec3d(0, 0.75, 1), new Vec3d(0.25, 0.75, 1), new Vec3d(0.25, 1, 1), new Vec3d(0, 1, 1), (u && w && wu) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (u && !w) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!u && w) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (u && w && !wu) ? 8 : 12, 0, (u && w && !wu) ? 12 : 16, 4));
				quads.add(createQuad(new Vec3d(0.75, 0.75, 1), new Vec3d(1, 0.75, 1), new Vec3d(1, 1, 1), new Vec3d(0.75, 1, 1), (u && e && eu) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (u && !e) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!u && e) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (u && e && !eu) ? 8 : 12, 12, (u && e && !eu) ? 12 : 16, 16));
			}
			
			//Side: East
			//saving for spreader model: quads.add(createQuad(new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), this.getTexture(TextureType.SIDE_BASE, metadata), 0, 0, 16, 16));
			if (!e) {
				quads.add(createQuad(new Vec3d(1, 0.25, 0.25), new Vec3d(1, 0.75, 0.25), new Vec3d(1, 0.75, 0.75), new Vec3d(1, 0.25, 0.75), this.getTexture(TextureType.SIDE_BASE, metadata), 4, 4, 12, 12));
				
				quads.add(createQuad(new Vec3d(1, 0.25, 0), new Vec3d(1, 0.75, 0), new Vec3d(1, 0.75, 0.25), new Vec3d(1, 0.25, 0.25), n ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 0, 4, 4, 12));
				quads.add(createQuad(new Vec3d(1, 0.25, 0.75), new Vec3d(1, 0.75, 0.75), new Vec3d(1, 0.75, 1), new Vec3d(1, 0.25, 1), s ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 12, 4, 16, 12));
				quads.add(createQuad(new Vec3d(1, 0.75, 0.25), new Vec3d(1, 1, 0.25), new Vec3d(1, 1, 0.75), new Vec3d(1, 0.75, 0.75), u ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 12, 12, 16));
				quads.add(createQuad(new Vec3d(1, 0, 0.25), new Vec3d(1, 0.25, 0.25), new Vec3d(1, 0.25, 0.75), new Vec3d(1, 0, 0.75), d ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 0, 12, 4));
				
				quads.add(createQuad(new Vec3d(1, 0, 0), new Vec3d(1, 0.25, 0), new Vec3d(1, 0.25, 0.25), new Vec3d(1, 0, 0.25), (n && d && nd) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (n && !d) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!n && d) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (n && d && !nd) ? 4 : 0, 0, (n && d && !nd) ? 8 : 4, 4));
				quads.add(createQuad(new Vec3d(1, 0.75, 0), new Vec3d(1, 1, 0), new Vec3d(1, 1, 0.25), new Vec3d(1, 0.75, 0.25), (n && u && nu) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (n && !u) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!n && u) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (n && u && !nu) ? 4 : 0, 12, (n && u && !nu) ? 8 : 4, 16));
				quads.add(createQuad(new Vec3d(1, 0, 0.75), new Vec3d(1, 0.25, 0.75), new Vec3d(1, 0.25, 1), new Vec3d(1, 0, 1), (s && d && sd) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (s && !d) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!s && d) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (s && d && !sd) ? 8 : 12, 0, (s && d && !sd) ? 12 : 16, 4));
				quads.add(createQuad(new Vec3d(1, 0.75, 0.75), new Vec3d(1, 1, 0.75), new Vec3d(1, 1, 1), new Vec3d(1, 0.75, 1), (s && u && su) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (s && !u) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!s && u) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (s && u && !su) ? 8 : 12, 12, (s && u && !su) ? 12 : 16, 16));
			}
			
			//Side: West
			//saving for spreader model: quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), this.getTexture(TextureType.SIDE_BASE, metadata), 0, 0, 16, 16));
			if (!w) {
				quads.add(createQuad(new Vec3d(0, 0.25, 0.75),new Vec3d(0, 0.75, 0.75) , new Vec3d(0, 0.75, 0.25), new Vec3d(0, 0.25, 0.25), this.getTexture(TextureType.SIDE_BASE, metadata), 4, 4, 12, 12));
				
				quads.add(createQuad(new Vec3d(0, 0.25, 0.25), new Vec3d(0, 0.75, 0.25), new Vec3d(0, 0.75, 0), new Vec3d(0, 0.25, 0), n ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 12, 4, 16, 12));
				quads.add(createQuad(new Vec3d(0, 0.25, 1), new Vec3d(0, 0.75, 1), new Vec3d(0, 0.75, 0.75), new Vec3d(0, 0.25, 0.75), s ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_V, metadata), 0, 4, 4, 12));
				quads.add(createQuad(new Vec3d(0, 0.75, 0.75), new Vec3d(0, 1, 0.75), new Vec3d(0, 1, 0.25), new Vec3d(0, 0.75, 0.25), u ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 12, 12, 16));
				quads.add(createQuad(new Vec3d(0, 0, 0.75), new Vec3d(0, 0.25, 0.75), new Vec3d(0, 0.25, 0.25), new Vec3d(0, 0, 0.25), d ? this.getTexture(TextureType.SIDE_BASE, metadata) : this.getTexture(TextureType.SIDE_LINES_H, metadata), 4, 0, 12, 4));
				
				quads.add(createQuad(new Vec3d(0, 0.25, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 0.25), new Vec3d(0, 0.25, 0.25), (n && d && nd) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (n && !d) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!n && d) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (n && d && !nd) ? 8 : 12, 0, (n && d && !nd) ? 12 : 16, 4));
				quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0.75, 0), new Vec3d(0, 0.75, 0.25), new Vec3d(0, 1, 0.25), (n && u && nu) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (n && !u) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!n && u) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (n && u && !nu) ? 8 : 12, 12, (n && u && !nu) ? 12 : 16, 16));
				quads.add(createQuad(new Vec3d(0, 0.25, 0.75), new Vec3d(0, 0, 0.75), new Vec3d(0, 0, 1), new Vec3d(0, 0.25, 1), (s && d && sd) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (s && !d) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!s && d) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (s && d && !sd) ? 4 : 0, 0, (s && d && !sd) ? 8 : 4, 4));
				quads.add(createQuad(new Vec3d(0, 1, 0.75), new Vec3d(0, 0.75, 0.75), new Vec3d(0, 0.75, 1), new Vec3d(0, 1, 1), (s && u && su) ? this.getTexture(TextureType.SIDE_BASE, metadata) : (s && !u) ? this.getTexture(TextureType.SIDE_LINES_H, metadata) : (!s && u) ? this.getTexture(TextureType.SIDE_LINES_V, metadata) : this.getTexture(TextureType.SIDE_CORNERS, metadata), (s && u && !su) ? 4 : 0, 12, (s && u && !su) ? 8 : 4, 16));
			}
			
			return quads;
		}

		@Override
		public boolean isAmbientOcclusion() {
			return true;
		}

		@Override
		public boolean isGui3d() {
			return true;
		}

		@Override
		public boolean isBuiltInRenderer() {
			return true;
		}

		@Override
		public TextureAtlasSprite getParticleTexture() {
			return this.getTexture(TextureType.SIDE_BASE, 0);
		}

		@Override
		public ItemOverrideList getOverrides() {
			return null;
		}
		
		private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite) {
	        for (int e = 0; e < format.getElementCount(); e++) {
	            switch (format.getElement(e).getUsage()) {
	                case POSITION:
	                    builder.put(e, (float)x, (float)y, (float)z, 1.0f);
	                    break;
	                case COLOR:
	                    builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
	                    break;
	                case UV:
	                    if (format.getElement(e).getIndex() == 0) {
	                        u = sprite.getInterpolatedU(u);
	                        v = sprite.getInterpolatedV(v);
	                        builder.put(e, u, v, 0f, 1f);
	                        break;
	                    }
	                case NORMAL:
	                    builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
	                    break;
	                default:
	                    builder.put(e);
	                    break;
	            }
	        }
	    }

	    private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite, int uvX1, int uvY1, int uvX2, int uvY2) {
	        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

	        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
	        builder.setTexture(sprite);
	        putVertex(builder, normal, v1.x, v1.y, v1.z, uvX1, uvY1, sprite);
	        putVertex(builder, normal, v2.x, v2.y, v2.z, uvX1, uvY2, sprite);
	        putVertex(builder, normal, v3.x, v3.y, v3.z, uvX2, uvY2, sprite);
	        putVertex(builder, normal, v4.x, v4.y, v4.z, uvX2, uvY1, sprite);
	        return builder.build();
	    }
	    
	    private TextureAtlasSprite getTexture(TextureType tt, int metadata) {
			return sprites[tt.ordinal() + (metadata * TextureType.values().length)];
	    }
	    
	    public static enum TextureType {
	    	TOP_BASE,
	    	TOP_CORNERS,
	    	TOP_LINES_H,
	    	TOP_LINES_V,
	    	SIDE_BASE,
	    	SIDE_CORNERS,
	    	SIDE_LINES_H,
	    	SIDE_LINES_V,
	    	BOTTOM_BASE,
	    	BOTTOM_CORNERS,
	    	BOTTOM_LINES_H,
	    	BOTTOM_LINES_V;
	    }

	}

}
