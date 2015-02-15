package yam.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CmdHeal extends CommandBase {

    public List getCommandAliases()
    {
        return Arrays.asList(new String[] {"restore", "health"});
    }

    public String getCommandName()
    {
        return "heal";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.heal.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 2) {
            throw new WrongUsageException("commands.heal.usage", new Object[0]);
        } else {
        	EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(par1ICommandSender);
        	
	        if (par2ArrayOfStr.length > 0) {
	        	entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
	        }
	        
	        if (entityplayermp == null) {
	            throw new PlayerNotFoundException();
	        } else {
	        	if (par2ArrayOfStr.length > 1) {
	        		float heal = (float)this.parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[1],0);
	                entityplayermp.setHealth(entityplayermp.getHealth() + heal);
	                
	                ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.heal.display.amount", new Object[] {entityplayermp.getCommandSenderName(),heal,entityplayermp.getHealth()});
	                par1ICommandSender.addChatMessage(chatcomponenttranslation);
	        	} else {
	                entityplayermp.setHealth(entityplayermp.getMaxHealth());
	                
	                ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.heal.display.full", new Object[] {entityplayermp.getCommandSenderName()});
	                par1ICommandSender.addChatMessage(chatcomponenttranslation);
	        	}
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}