package com.joshmanisdabomb.lcc.data;

import com.joshmanisdabomb.lcc.registry.LCCFluids;
import com.joshmanisdabomb.lcc.registry.LCCTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;

public class FluidTagData extends FluidTagsProvider {

    public FluidTagData(DataGenerator dg) {
        super(dg);
    }

    @Override
    protected void registerTags() {
        this.getBuilder(LCCTags.OIL)
            .add(LCCFluids.oil, LCCFluids.flowing_oil)
            .ordered(true);
    }

}
