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

object LCCFluids : BasicDirectory<Fluid, Unit>(), RegistryDirectory<Fluid, Unit, Unit> {

    override val registry by lazy { Registry.FLUID }

    override fun regId(name: String) = LCC.id(name)

    val oil_still by entry(::initialiser) { OilFluid(true) }
    val oil_flowing by entry(::initialiser) { OilFluid(false) }

    val asphalt_still by entry(::initialiser) { AsphaltFluid(true) }
    val asphalt_flowing by entry(::initialiser) { AsphaltFluid(false) }

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        OilRenderer().register()
        AsphaltRenderer().register()
    }

    override fun defaultProperties(name: String) = Unit

}