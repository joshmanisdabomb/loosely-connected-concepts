package com.joshmanisdabomb.lcc.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object DataUtils {

    internal val parser = JsonParser()

    internal val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

}