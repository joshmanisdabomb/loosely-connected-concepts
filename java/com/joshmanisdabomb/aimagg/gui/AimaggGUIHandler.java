package com.joshmanisdabomb.aimagg.gui;

import com.joshmanisdabomb.aimagg.container.AimaggContainerLaunchPad;
import com.joshmanisdabomb.aimagg.container.AimaggContainerSpreaderInterface;
import com.joshmanisdabomb.aimagg.data.world.SpreaderData;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class AimaggGUIHandler implements IGuiHandler {

	public static final int SpreaderInterfaceID = 0;
	public static final int LaunchPadID = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (ID == SpreaderInterfaceID) {
				return new AimaggContainerSpreaderInterface(player, world);
			} else if (ID == LaunchPadID) {
				return new AimaggContainerLaunchPad(player, (AimaggTELaunchPad)world.getTileEntity(new BlockPos(x,y,z)));
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (world.isRemote) {
			if (ID == SpreaderInterfaceID) {
				return new AimaggGUISpreaderInterface(player, world);
			} else if (ID == LaunchPadID) {
				return new AimaggGUILaunchPad(player, (AimaggTELaunchPad)world.getTileEntity(new BlockPos(x,y,z)));
			}
		}
		return null;
	}

}
