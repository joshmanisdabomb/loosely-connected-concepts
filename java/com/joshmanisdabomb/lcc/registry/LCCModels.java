package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import org.antlr.v4.runtime.atn.BasicState;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public abstract class LCCModels {

    public static void register(final ModelRegistryEvent e) {
        registerObj(e, new ResourceLocation(LCC.MODID, "entity/nuke"));
        registerObj(e, new ResourceLocation(LCC.MODID, "entity/nukesmoke"));
    }

    private static void registerObj(final ModelRegistryEvent e, ResourceLocation obj) {
        ModelLoader.addSpecialModel(obj);
        /*if (model instanceof OBJModel) {
            IBakedModel bakedModel = model.bake(e.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.ITEM);
            e.getModelRegistry().put(mrl, bakedModel);
        }*/
    }

    /*public static IBakedModel getBakedModel(ModelResourceLocation mrl) {
        return bakedModels.computeIfAbsent(mrl, k -> Minecraft.getInstance().getModelManager().getModel(mrl).getBakedModel());
    }*/

}
