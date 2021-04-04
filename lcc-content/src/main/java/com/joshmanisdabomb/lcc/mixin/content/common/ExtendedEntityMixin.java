package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent;
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedEntityContent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
abstract class ExtendedEntityMixin implements LCCExtendedEntityContent {

}