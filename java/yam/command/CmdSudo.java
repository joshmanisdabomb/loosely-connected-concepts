package yam.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class CmdSudo extends CommandBase {

    public List getCommandAliases()
    {
        return Arrays.asList(new String[] {"as", "execute"});
    }

    public String getCommandName()
    {
        return "sudo";
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
        return "commands.sudo.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.sudo.usage", new Object[0]);
        } else {
    	    EntityPlayerMP entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
    	    
    	    String command = "";
    	    for (int i = 1; i < par2ArrayOfStr.length; i++) {
    	    	command += par2ArrayOfStr[i] + " ";
    	    }

    	    command = command.trim();
    	    
    	    MinecraftServer.getServer().getCommandManager().executeCommand(entityplayermp, command);
    	    
    		ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.sudo.display", new Object[] {par2ArrayOfStr[0], command});
            par1ICommandSender.addChatMessage(chatcomponenttranslation);
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