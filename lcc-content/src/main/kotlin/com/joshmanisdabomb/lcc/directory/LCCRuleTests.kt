package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.world.feature.rule.MultipleMatchRuleTest
import com.mojang.serialization.Codec
import net.minecraft.structure.rule.RuleTest
import net.minecraft.structure.rule.RuleTestType
import net.minecraft.util.registry.Registry

object LCCRuleTests : AdvancedDirectory<Codec<out RuleTest>, RuleTestType<out RuleTest>, Unit, Unit>(), RegistryDirectory<RuleTestType<out RuleTest>, Unit, Unit> {

    override val registry = Registry.RULE_TEST

    override fun regId(name: String) = LCC.id(name)

    val multiple_match by entry(::rtInitialiser) { MultipleMatchRuleTest.codec }

    fun <R : RuleTest> rtInitialiser(input: Codec<R>, context: DirectoryContext<Unit>, parameters: Unit): RuleTestType<R> {
        return initialiser(RuleTestType { input }, context, parameters)
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

}
