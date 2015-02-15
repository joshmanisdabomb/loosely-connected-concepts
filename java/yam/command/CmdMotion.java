package yam.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CmdMotion extends CommandBase {

    public List getCommandAliases()
    {
        return Arrays.asList(new String[] {"move", "fling", "throw", "setmotion"});
    }

    public String getCommandName()
    {
        return "motion";
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
        return "commands.motion.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 3 || par2ArrayOfStr.length > 4) {
            throw new WrongUsageException("commands.motion.usage", new Object[0]);
        } else {
        	EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(par1ICommandSender);
        	
	        if (par2ArrayOfStr.length == 4) {
	        	entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
	        }
	        
	        if (entityplayermp == null) {
	            throw new PlayerNotFoundException();
	        } else {
	        	double motionX = (par2ArrayOfStr.length == 3) ? this.parseInt(par1ICommandSender, par2ArrayOfStr[0]) : this.parseInt(par1ICommandSender, par2ArrayOfStr[1]);
	        	double motionY = (par2ArrayOfStr.length == 3) ? this.parseInt(par1ICommandSender, par2ArrayOfStr[1]) : this.parseInt(par1ICommandSender, par2ArrayOfStr[2]);
	        	double motionZ = (par2ArrayOfStr.length == 3) ? this.parseInt(par1ICommandSender, par2ArrayOfStr[2]) : this.parseInt(par1ICommandSender, par2ArrayOfStr[3]);
	        	
                ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.motion.display", new Object[] {});
                par1ICommandSender.addChatMessage(chatcomponenttranslation);
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