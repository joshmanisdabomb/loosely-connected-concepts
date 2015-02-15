package yam.command;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CmdSuperEnchant extends CommandBase {

    public List getCommandAliases()
    {
        return Arrays.asList(new String[] {"senchant", "enchantext", "extenchant", "enchantsuper", "enchants", "allenchant", "enchantall", "bulkenchant"});
    }

    public String getCommandName()
    {
        return "superenchant";
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
        return "commands.senchant.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 1) {
            throw new WrongUsageException("commands.senchant.usage", new Object[0]);
        } else {
        	EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(par1ICommandSender);
            ItemStack itemstack = entityplayermp.getCurrentEquippedItem();
            
        	for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
        		if (par2ArrayOfStr.length == 1) {
        			if (Enchantment.enchantmentsList[i] != null) {
    	                itemstack.addEnchantment(Enchantment.enchantmentsList[i], this.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[0], 0));
    					Enchantment enchantment = Enchantment.enchantmentsList[i];
            		}
        		} else {
        			if (Enchantment.enchantmentsList[i] != null) {
    	                itemstack.addEnchantment(Enchantment.enchantmentsList[i], Enchantment.enchantmentsList[i].getMaxLevel());
    					Enchantment enchantment = Enchantment.enchantmentsList[i];
            		}
        		}
        	}
        	
        	ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.senchant.display", new Object[] {});
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