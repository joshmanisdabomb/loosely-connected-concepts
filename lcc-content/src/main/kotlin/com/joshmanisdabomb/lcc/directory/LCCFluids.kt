package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.fluid.AsphaltFluid
import com.joshmanisdabomb.lcc.fluid.OilFluid
import com.joshmanisdabomb.lcc.fluid.render.AsphaltRenderer
import com.joshmanisdabomb.lcc.fluid.render.OilRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.fluid.Fluid
import net.minecraft.util.registry.Registry

object LCCFluids : RegistryDirectory<Fluid, Unit>() {

    override val _registry by lazy { Registry.FLUID }

    override fun id(path: String) = LCC.id(path)

    val oil_still by create { OilFluid(true) }
    val oil_flowing by create { OilFluid(false) }

    val asphalt_still by create { AsphaltFluid(true) }
    val asphalt_flowing by create { AsphaltFluid(false) }

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        OilRenderer().register()
        AsphaltRenderer().register()
    }

}