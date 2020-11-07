package com.joshmanisdabomb.lcc.directory

import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig

object LCCSurfaceBuilders : RegistryDirectory<ConfiguredSurfaceBuilder<*>, Unit>() {

    override val _registry by lazy { BuiltinRegistries.CONFIGURED_SURFACE_BUILDER }

    val wasteland by create { SurfaceBuilder.DEFAULT.withConfig(TernarySurfaceConfig(LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState, LCCBlocks.cracked_mud.defaultState)) }

}