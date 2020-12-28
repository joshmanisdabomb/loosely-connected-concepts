package com.joshmanisdabomb.lcc.data

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.data.base.BaseLangData
import com.joshmanisdabomb.lcc.directory.LCCItems
import net.minecraft.data.DataGenerator

class LangData(dg: DataGenerator) : BaseLangData(dg, LCC.modid) {

    override fun createTranslations() {
        //Items
        addAll(this::add, this::tkey, LCCItems.test_item)

        //Commands
        add("commands.lcc.data.gen.success", "All providers successfully generated in %s ms.")
        add("commands.lcc.data.gen.failure", "All providers failed to generate, see console for more info.")
        add("commands.lcc.data.commit.success", "Successfully merged generated to source in %s ms.")
        add("commands.lcc.data.commit.failure", "Failed to merge generated to source, see console for more info.")
    }

}
