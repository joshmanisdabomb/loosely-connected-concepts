package com.joshmanisdabomb.lcc.block.model;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;

public interface DefaultModelAcceptor {

    void acceptModel(ModelResourceLocation mrl, IBakedModel model);

}
