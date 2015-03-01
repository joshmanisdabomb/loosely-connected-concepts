package yam.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import yam.blocks.entity.TileEntityClassicChest;
import yam.blocks.entity.TileEntityComputer;
import yam.blocks.entity.TileEntityLaunchPad;
import yam.blocks.entity.TileEntityTickField;
import yam.blocks.entity.TileEntityTrashCan;
import yam.container.ContainerClassicChest;
import yam.container.ContainerLaunchPad;
import yam.container.ContainerTickField;
import yam.container.ContainerTrashCan;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int trash = 1;
	public static final int tickfield = 2;
	public static final int classicchest = 3;
	public static final int stonecutter = 4;
	public static final int computer = 5;
	public static final int computerSpace = 6;
	public static final int computerDeveloper = 7;
	public static final int drive = 8;
	public static final int pad = 9;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityTrashCan) {
            return new ContainerTrashCan(player.inventory, (TileEntityTrashCan) tileEntity);
        } else if (tileEntity instanceof TileEntityTickField) {
            return new ContainerTickField(player.inventory, (TileEntityTickField) tileEntity);
        } else if (tileEntity instanceof TileEntityClassicChest) {
            return new ContainerClassicChest(player.inventory, (TileEntityClassicChest) tileEntity, null);
        } else if (tileEntity instanceof TileEntityLaunchPad) {
        	return new ContainerLaunchPad(player.inventory, (TileEntityLaunchPad) tileEntity);
        /*} else if (tileEntity instanceof TileEntityStonecutter) {
		    return new ContainerStonecutter(player.inventory, (TileEntityStonecutter) tileEntity);
		} else if (tileEntity instanceof TileEntityComputerSpace) {
		    return new ContainerComputerSpace(player.inventory, (TileEntityComputerSpace) tileEntity);
		} else if (tileEntity instanceof TileEntityComputerDev) {
		    return new ContainerComputerDev(player.inventory, (TileEntityComputerDev) tileEntity);
		} else if (ID == drive) {
		    return new ContainerDrive(player.inventory, player.getCurrentEquippedItem());
		*/
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityTrashCan) {
            return new GuiTrashCan(player.inventory, (TileEntityTrashCan) tileEntity);
        } else if (tileEntity instanceof TileEntityTickField) {
            return new GuiTickField(player.inventory, (TileEntityTickField) tileEntity);
        } else if (tileEntity instanceof TileEntityClassicChest) {
            return new GuiClassicChest(player.inventory, (TileEntityClassicChest)tileEntity, null);
		} else if (tileEntity instanceof TileEntityComputer) {
		    return new GuiComputer((TileEntityComputer)tileEntity);
        } else if (tileEntity instanceof TileEntityLaunchPad) {
        	return new GuiLaunchPad(player.inventory, (TileEntityLaunchPad) tileEntity);
        /*} else if (tileEntity instanceof TileEntityStonecutter) {
		    return new GuiStonecutter(player.inventory, (TileEntityStonecutter) tileEntity);
		} else if (tileEntity instanceof TileEntityComputerSpace) {
		    return new GuiComputerSpace(player.inventory, (TileEntityComputerSpace) tileEntity);
		} else if (tileEntity instanceof TileEntityComputerDev) {
		    return new GuiComputerDev(player.inventory, (TileEntityComputerDev) tileEntity);
		} else if (ID == drive) {
		    return new GuiDrive(player.inventory, player.getCurrentEquippedItem());
		*/
        }
		
        return null;
	}

}
