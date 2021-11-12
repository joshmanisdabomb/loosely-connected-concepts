package com.joshmanisdabomb.lcc.data

import net.minecraft.data.DataProvider

interface WaitingDataProvider : DataProvider {

    var done: Boolean

}
