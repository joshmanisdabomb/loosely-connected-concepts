package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.gui.overlay.GauntletOverlay;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FontStorage.class)
public abstract class FontStorageMixin {

    @Shadow @Final
    private Identifier id;

    @ModifyArg(method = "(Ljava/util/List;Ljava/util/Set;I)V", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;computeIfAbsent(ILit/unimi/dsi/fastutil/ints/Int2ObjectFunction;)Ljava/lang/Object;"))
    private int modifyAdvance(int original) {
        if (!id.equals(LCC.INSTANCE.id("fixed"))) return original;
        return 6;
    }

    @ModifyArg(method = "getGlyph", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;computeIfAbsent(ILit/unimi/dsi/fastutil/ints/Int2ObjectFunction;)Ljava/lang/Object;"))
    private Int2ObjectFunction<Glyph> modifyGlyph(Int2ObjectFunction<Glyph> original) {
        if (!id.equals(LCC.INSTANCE.id("fixed"))) return original;
        return codePoint -> codePoint == 32 ? () -> 6 : original.get(codePoint);
    }

}
