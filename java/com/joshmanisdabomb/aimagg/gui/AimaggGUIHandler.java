package com.joshmanisdabomb.aimagg.gui;

import com.joshmanisdabomb.aimagg.container.AimaggContainerBouncePad;
import com.joshmanisdabomb.aimagg.container.AimaggContainerClassicChest;
import com.joshmanisdabomb.aimagg.container.AimaggContainerComputerCase;
import com.joshmanisdabomb.aimagg.container.AimaggContainerLaunchPad;
import com.joshmanisdabomb.aimagg.container.AimaggContainerSpreaderInterface;
import com.joshmanisdabomb.aimagg.te.AimaggTEBouncePad;
import com.joshmanisdabomb.aimagg.te.AimaggTEClassicChest;
import com.joshmanisdabomb.aimagg.te.AimaggTEComputerCase;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class AimaggGUIHandler implements IGuiHandler {

	public static final int SpreaderInterfaceID = 0;
	public static final int LaunchPadID = 1;
	public static final int ComputerCaseID = 2;
	public static final int BouncePadID = 3;
	public static final int ClassicChestID = 4;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (ID == SpreaderInterfaceID) {
				return new AimaggContainerSpreaderInterface(player, world);
			} else if (ID == LaunchPadID) {
				return new AimaggContainerLaunchPad(player, (AimaggTELaunchPad)world.getTileEntity(new BlockPos(x,y,z)));
			} else if (ID == ComputerCaseID) {
				return new AimaggContainerComputerCase(player, (AimaggTEComputerCase)world.getTileEntity(new BlockPos(x,y,z)));
			} else if (ID == BouncePadID) {
				return new AimaggContainerBouncePad(player, (AimaggTEBouncePad)world.getTileEntity(new BlockPos(x,y,z)));
			} else if (ID == ClassicChestID) {
				return new AimaggContainerClassicChest(player, (AimaggTEClassicChest)world.getTileEntity(new BlockPos(x,y,z)));
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
			} else if (ID == ComputerCaseID) {
				return new AimaggGUIComputerCase(player, (AimaggTEComputerCase)world.getTileEntity(new BlockPos(x,y,z)));
			} else if (ID == BouncePadID) {
				return new AimaggGUIBouncePad(player, (AimaggTEBouncePad)world.getTileEntity(new BlockPos(x,y,z)));
			} else if (ID == ClassicChestID) {
				return new AimaggGUIClassicChest(player, (AimaggTEClassicChest)world.getTileEntity(new BlockPos(x,y,z)));
			}
		}
		return null;
	}

}
