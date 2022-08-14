package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.world.biome.region.WastelandRegion
import com.joshmanisdabomb.lcc.world.biome.surface.WastelandMaterialRule
import net.minecraft.block.Blocks
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.surfacebuilder.MaterialRules
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules
import terrablender.api.*

object LCCTerraRegions : BasicDirectory<Region, Unit>(), TerraBlenderApi {

    val wasteland by entry(LCCTerraRegions::initialiser) { WastelandRegion(id, LCC.id("wasteland"), RegionType.OVERWORLD, 5) }

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String) = Unit

    fun initialiser(input: Region, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun onTerraBlenderInitialized() {
        init()
        all.values.forEach(Regions::register)

        LCCMaterialRules.init()

        val vanilla = VanillaSurfaceRules.createDefaultRule(true, false, true)
        val barrens = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, WastelandMaterialRule(0.0f, null, MaterialRules.block(Blocks.AIR.defaultState))))
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, LCC.modid, MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.biome(RegistryKey.of(BuiltinRegistries.BIOME.key, LCC.id("wasteland"))), barrens),
            vanilla
        ))
    }

}