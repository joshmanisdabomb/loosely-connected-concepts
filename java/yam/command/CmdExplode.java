package yam.command;

import java.util.Arrays;
import java.util.List;

import yam.explosion.AmplifyExplosion;
import yam.explosion.NuclearExplosion;
import yam.explosion.Rainsplosion;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.Explosion;

public class CmdExplode extends CommandBase {

    public List getCommandAliases()
    {
        return Arrays.asList(new String[] {"boom", "explosion", "crater"});
    }

    public String getCommandName()
    {
        return "explode";
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
        return "commands.explode.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length > 5) {
            throw new WrongUsageException("commands.explode.usage", new Object[0]);
        } else {
        	EntityPlayerMP me = getCommandSenderAsPlayer(par1ICommandSender);
        	EntityPlayerMP entityplayermp;
        	boolean player = false;
        	
        	String type;
        	float size = 0.0F;
        	
        	try {
        		Integer.parseInt(par2ArrayOfStr[0]);
        	} catch (Exception e) {
        		player = true;
        	}
        	
        	if (player) {
            	EntityPlayerMP entity;
            	double x; double y; double z;
            	
        		if (par2ArrayOfStr.length == 0) {
        			type = "normal"; size = 4.0F; entity = me; x = (int)me.posX; y = (int)me.posY; z = (int)me.posZ;
        		} else if (par2ArrayOfStr.length == 1) {
    	        	entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        			type = "normal"; size = 4.0F; entity = entityplayermp; x = (int)entityplayermp.posX; y = (int)entityplayermp.posY; z = (int)entityplayermp.posZ;
        		} else if (par2ArrayOfStr.length == 2) {
    	        	entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        			type = par2ArrayOfStr[1]; size = 4.0F; entity = entityplayermp; x = (int)entityplayermp.posX; y = (int)entityplayermp.posY; z = (int)entityplayermp.posZ;
        		} else {
    	        	entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        			type = par2ArrayOfStr[1]; size = (float)this.parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[2], 0); entity = entityplayermp; x = (int)entityplayermp.posX; y = (int)entityplayermp.posY; z = (int)entityplayermp.posZ;
        		}

        		if (type.equalsIgnoreCase("normal")) {
        			me.worldObj.createExplosion(entity, x, y, z, size, true);
        		} else if (type.equalsIgnoreCase("fire")) {
        			Explosion explosion = new Explosion(entity.worldObj, entity, x, y, z, size);
        	        explosion.isFlaming = true;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else if (type.equalsIgnoreCase("rainbow")) {
        			Explosion explosion = new Rainsplosion(entity.worldObj, entity, x, y, z, size);
        	        explosion.isFlaming = true;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else if (type.equalsIgnoreCase("nuclear")) {
        			Explosion explosion = new NuclearExplosion(entity.worldObj, entity, x, y, z, size);
        	        explosion.isFlaming = true;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else if (type.equalsIgnoreCase("amplify")) {
        			Explosion explosion = new AmplifyExplosion(entity.worldObj, entity, x, y, z, size);
        	        explosion.isFlaming = false;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else {
                    throw new WrongUsageException("commands.explode.usage.types", new Object[0]);
        		}
        		ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.explode.display.player", new Object[] {type,size,entity.getCommandSenderName()});
                par1ICommandSender.addChatMessage(chatcomponenttranslation);
        	} else {
            	double x; double y; double z;
            	
        		if (par2ArrayOfStr.length == 3) {
        			type = "normal"; size = 4.0F; x = this.parseDouble(par1ICommandSender, par2ArrayOfStr[0]); y = this.parseDouble(par1ICommandSender, par2ArrayOfStr[1]); z = this.parseDouble(par1ICommandSender, par2ArrayOfStr[2]);
        		} else if (par2ArrayOfStr.length == 4) {
        			type = par2ArrayOfStr[3]; size = 4.0F; x = this.parseDouble(par1ICommandSender, par2ArrayOfStr[0]); y = this.parseDouble(par1ICommandSender, par2ArrayOfStr[1]); z = this.parseDouble(par1ICommandSender, par2ArrayOfStr[2]);
        		} else {
    	        	entityplayermp = getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        			type = par2ArrayOfStr[3]; size = (float)this.parseDoubleWithMin(par1ICommandSender, par2ArrayOfStr[2], 0); x = this.parseDouble(par1ICommandSender, par2ArrayOfStr[0]); y = this.parseDouble(par1ICommandSender, par2ArrayOfStr[1]); z = this.parseDouble(par1ICommandSender, par2ArrayOfStr[2]);
        		}

        		if (type.equalsIgnoreCase("normal")) {
        			me.worldObj.createExplosion(null, x, y, z, size, true);
        		} else if (type.equalsIgnoreCase("fire")) {
        			Explosion explosion = new Explosion(me.worldObj, null, x, y, z, size);
        	        explosion.isFlaming = true;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else if (type.equalsIgnoreCase("rainbow")) {
        			Explosion explosion = new Rainsplosion(me.worldObj, null, x, y, z, size);
        	        explosion.isFlaming = true;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else if (type.equalsIgnoreCase("nuclear")) {
        			Explosion explosion = new NuclearExplosion(me.worldObj, null, x, y, z, size);
        	        explosion.isFlaming = true;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else if (type.equalsIgnoreCase("amplify")) {
        			Explosion explosion = new AmplifyExplosion(me.worldObj, null, x, y, z, size);
        	        explosion.isFlaming = false;
        	        explosion.isSmoking = true;
        	        explosion.doExplosionA();
        	        explosion.doExplosionB(true);
        		} else {
                    throw new WrongUsageException("commands.explode.usage.types", new Object[0]);
        		}
        		ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.explode.display.location", new Object[] {type,size,x,y,z});
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