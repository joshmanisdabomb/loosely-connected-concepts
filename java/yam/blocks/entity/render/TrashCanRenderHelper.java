package yam.blocks.entity.render;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityTrashCan;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TrashCanRenderHelper extends TileEntityRendererChestHelper {

	@Override
    public void renderChest(Block block, int i, float f)
    {
        if (block == YetAnotherMod.trashCanBlock)
        {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityTrashCan(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else
        {
            super.renderChest(block, i, f);
        }
    }
	
}
