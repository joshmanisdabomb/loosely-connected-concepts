package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.OreBlock;

import java.util.Random;
import java.util.function.ToIntFunction;

public class FunctionalOreBlock extends OreBlock {

    private final ToIntFunction<Random> experience;

    public FunctionalOreBlock(Properties p, ToIntFunction<Random> experience) {
        super(p);
        this.experience = experience;
    }

    @Override
    protected int getExperience(Random rand) {
        return experience.applyAsInt(rand);
    }

}
