package com.joshmanisdabomb.lcc

import com.google.gson.GsonBuilder

object DataUtils {

    internal val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

}