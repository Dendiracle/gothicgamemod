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
		
		if (commandSender instanceof EntityPlayer) {
			
			String statS;
			
			if (args.length == 4) {	                                      
				statS = args[0];//.substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();
				String function = args[1];
				String valueOf = args[2];
				String value = args[3];
				double valueD = CommandBase.parseDouble(commandSender, value);
				
				/*if (GGMCapabilities.statsNamesList.indexOf(statS) >= 0) {
					System.out.println("Stat is " + statS);
					ISynchronizable stat = IGothicPlayer.get(this.getCommandSenderAsPlayer(commandSender)).getStat(EntityAttributes.valueOf(statS));
					if (function.equals("set")) {
						
						if (valueOf.equals("base")) {
							stat.setBaseValue(valueD);
							System.out.println("Value setted to " + valueD);
						}						
						
						else if (valueOf.equals("current")) {
							try {
								stat.getDynamicParametr().setCurrentValue(valueD);
							} catch (Throwable t) {}
						
						}
						else throw new WrongUsageException(this.getCommandUsage(commandSender));
					}
				}	*/			
			}
	            
			else {	            	
				throw new WrongUsageException(this.getCommandUsage(commandSender));	            	
			}
		}
	}
	
	/*@Override
	public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, GGMCapabilities.statsNamesList.toArray(new String[GGMCapabilities.statsNamesList.size()])) :
        	(args.length == 2 ? getListOfStringsMatchingLastWord(args, new String[] {"set"}) : ((args.length == 3 && GGMCapabilities.dynamicStatsNamesList.indexOf(args[0]) > 0) ? getListOfStringsMatchingLastWord(args, new String[] {"base", "current"}) :
        		((args.length == 3) ? getListOfStringsMatchingLastWord(args, new String[] {"base"}) : null)));
    }*/

}
