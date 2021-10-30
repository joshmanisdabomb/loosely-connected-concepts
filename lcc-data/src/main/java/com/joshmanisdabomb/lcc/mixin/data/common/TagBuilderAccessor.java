package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Tag.Builder.class)
public interface TagBuilderAccessor {

    @Accessor("entries")
    List<Tag.TrackedEntry> getEntries();

}
