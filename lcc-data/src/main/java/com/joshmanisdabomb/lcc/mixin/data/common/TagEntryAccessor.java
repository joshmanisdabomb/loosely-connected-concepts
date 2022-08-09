package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.tag.TagBuilder;
import net.minecraft.tag.TagEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(TagEntry.class)
public interface TagEntryAccessor {

    @Accessor("tag")
    boolean isTag();

    @Accessor("required")
    boolean isRequired();

}
