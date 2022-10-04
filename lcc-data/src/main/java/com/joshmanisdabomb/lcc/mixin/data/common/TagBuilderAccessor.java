package com.joshmanisdabomb.lcc.mixin.data.common;

import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.tag.TagBuilder;
import net.minecraft.tag.TagEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(TagBuilder.class)
public interface TagBuilderAccessor {

    @Accessor("entries")
    List<TagEntry> getEntries();

}
