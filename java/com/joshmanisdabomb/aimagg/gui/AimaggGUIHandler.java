package com.joshmanisdabomb.aimagg.gui;

import com.joshmanisdabomb.aimagg.container.AimaggContainerSpreaderConstructor;
import com.joshmanisdabomb.aimagg.te.AimaggTESpreaderConstructor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class AimaggGUIHandler implements IGuiHandler {

	public static final int SpreaderConstructorID = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == SpreaderConstructorID) {
			return new AimaggContainerSpreaderConstructor(player, (AimaggTESpreaderConstructor)world.getTileEntity(new BlockPos(x,y,z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == SpreaderConstructorID) {
			return new AimaggGUISpreaderConstructor(player, (AimaggTESpreaderConstructor)world.getTileEntity(new BlockPos(x,y,z)));
		}
		return null;
	}

}
