package com.joshmanisdabomb.lcc.settings

abstract class ExtraSettings {

    protected val list = mutableListOf<ExtraSetting>()

    open fun add(setting: ExtraSetting) = list.add(setting).let { this }

}