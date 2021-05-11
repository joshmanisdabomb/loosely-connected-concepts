package com.joshmanisdabomb.lcc.directory

abstract class BasicDirectory<T, P> : AdvancedDirectory<T, T, P, Unit>() {

    override fun defaultContext() = Unit

}