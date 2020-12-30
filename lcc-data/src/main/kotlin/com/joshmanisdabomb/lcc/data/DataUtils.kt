package com.joshmanisdabomb.lcc.data

import com.google.gson.GsonBuilder

object DataUtils {

    internal val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

}