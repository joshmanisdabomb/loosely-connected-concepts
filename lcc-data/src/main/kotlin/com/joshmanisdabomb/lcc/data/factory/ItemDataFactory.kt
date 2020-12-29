package com.joshmanisdabomb.lcc.data.factory

import com.joshmanisdabomb.lcc.DataAccessor
import net.minecraft.item.Item

interface ItemDataFactory {

    fun apply(data: DataAccessor, entry: Item)
    
}