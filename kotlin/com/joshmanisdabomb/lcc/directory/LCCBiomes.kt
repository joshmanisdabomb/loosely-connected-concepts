package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.directory.LCCItems.defaults
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes
import net.fabricmc.fabric.api.biome.v1.OverworldClimate
import net.minecraft.item.Item
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeCreator

object LCCBiomes : RegistryDirectory<Biome, Function1<RegistryKey<Biome>, Unit>>() {

    override val _registry by lazy { BuiltinRegistries.BIOME }

    val test_biome by create({ OverworldBiomes.addContinentalBiome(it, OverworldClimate.DRY, 5.0) }) { DefaultBiomeCreator.createWarpedForest() }

    override fun register(key: String, thing: Biome, properties: (RegistryKey<Biome>) -> Unit): RegistryKey<Biome> = super.register(key, thing, properties).apply { properties(this) }

}
