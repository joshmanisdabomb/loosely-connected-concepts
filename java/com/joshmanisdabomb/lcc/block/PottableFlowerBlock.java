package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;

public class PottableFlowerBlock extends FlowerBlock implements IPottableBlock {

    private final IPottableBlock potted;

    public PottableFlowerBlock(IPottableBlock potted, Effect effect, int duration, Properties properties) {
        super(effect, duration, properties);
        this.potted = potted;
    }

    @Override
    public BlockState getPottedState() {
        return this.potted.getPottedState();
    }

}
