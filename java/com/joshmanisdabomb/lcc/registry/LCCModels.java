package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
public abstract class LCCModels {

    public static final ModelResourceLocation SPHERE = new ModelResourceLocation(new ResourceLocation(LCC.MODID, "entity/sphere"), "inventory");

    public static final HashMap<ModelResourceLocation, IBakedModel> bakedModels = new HashMap<>();

    public static void bake(final ModelBakeEvent e) {
        registerObj(e, SPHERE, new ResourceLocation(LCC.MODID, "entity/sphere.obj"));
    }

    private static void registerObj(final ModelBakeEvent e, ModelResourceLocation mrl, ResourceLocation obj) {
        /*IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(obj);
        if (model instanceof OBJModel) {
            IBakedModel bakedModel = model.bake(e.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.ITEM);
            e.getModelRegistry().put(mrl, bakedModel);
        }*/
    }

    public static IBakedModel getBakedModel(ModelResourceLocation mrl) {
        return bakedModels.computeIfAbsent(mrl, k -> Minecraft.getInstance().getModelManager().getModel(mrl).getBakedModel());
    }

}
