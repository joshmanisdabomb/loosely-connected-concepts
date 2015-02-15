package yam.blocks.sounds;

import yam.YetAnotherMod;
import net.minecraft.block.Block.SoundType;

public class BlockClassicSounds extends SoundType {
	
	public BlockClassicSounds(String arg0, float arg1, float arg2) {
		super(arg0, arg1, arg2);
	}
	
	public String getBreakSound()
    {
        return "blocks." + this.soundName + ".dig";
    }

    public String getStepResourcePath()
    {
        return "blocks." + this.soundName + ".dig";
    }

}
