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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CmdHarm extends CommandBase {

    public List getCommandAliases()
    {
        return Arrays.asList(new String[] {"hurt", "attack"});
    }

    public String getCommandName()
    {
        return "harm";
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
        return "commands.harm.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 0 || par2ArrayOfStr.length > 2) {
            throw new WrongUsageException("commands.harm.usage", new Object[0]);
        } else {
        	EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(par1ICommandSender);
        	
	        if (par2ArrayOfStr.length == 1) {
	        	entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
	        }
	        
	        if (entityplayermp == null) {
	            throw new PlayerNotFoundException();
	        } else {
        		float harm = (float)this.parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[1],0);
                entityplayermp.attackEntityFrom(DamageSource.generic, harm);
                
                if (entityplayermp.getHealth() > 0) {
	                ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.harm.display", new Object[] {entityplayermp.getCommandSenderName(),harm,entityplayermp.getHealth()});
	                par1ICommandSender.addChatMessage(chatcomponenttranslation);
                } else {
	                ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.harm.display.kill", new Object[] {entityplayermp.getCommandSenderName(),harm});
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