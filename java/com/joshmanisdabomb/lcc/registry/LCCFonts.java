package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gui.font.FixedWidthFontWrapper;
import com.joshmanisdabomb.lcc.gui.font.FontWrapperWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LCCFonts {

    public static final FontWrapperWrapper FIXED_WIDTH = new FontWrapperWrapper(new ResourceLocation(LCC.MODID, "fixed"), FixedWidthFontWrapper::new);

}
