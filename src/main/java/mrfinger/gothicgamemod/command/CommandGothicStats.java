package mrfinger.gothicgamemod.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;

public class CommandGothicStats extends CommandBase{

	@Override
	public String getCommandName() {		
		return "gstat";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/gstat";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
        return 2;
    }

	@Override
	public void processCommand(ICommandSender commandSender, String[] args) {
		

	}
	
	/*@Override
	public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, GGMCapabilities.statsNamesList.toArray(new String[GGMCapabilities.statsNamesList.size()])) :
        	(args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[] {"set"}) : ((args.length == 3 && GGMCapabilities.dynamicStatsNamesList.indexOf(args[0]) > 0) ? getListOfStringsMatchingLastWord(args, new String[] {"base", "current"}) :
        		((args.length == 3) ? getListOfStringsMatchingLastWord(args, new String[] {"base"}) : null)));
    }*/

}
