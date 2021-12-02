package mrfinger.gothicgamemod.command;

import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class CommandGGMSet extends CommandBase
{

    public static final String Usage = "/gset";

    public static final String[] Variables = {"level", "experience", "LearningPoints", "attributeBaseValue", "attributeDynamicValue", "attributeBaseRegenValue"};


    @Override
    public String getCommandName()
    {
        return "gset";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_)
    {
        return Usage;
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] variables)
    {
        IGGMEntityPlayerMP player = (IGGMEntityPlayerMP) getCommandSenderAsPlayer(commandSender);

        if (variables.length == 2) {

            if (variables[0].equals(Variables[0]))
            {
                player.setLvl(Integer.parseInt(variables[1]));
            }
            else if (variables[0].equals(Variables[1]))
            {
                long exp = Long.parseLong(variables[1]);
                player.getExpCap().setExpValue(exp);
            }
            else if (variables[0].equals(Variables[2]))
            {
                player.getExpCap().setLP(Integer.parseInt(variables[1]));
            }
        }
        else if (variables.length == 3)
        {
            if (variables[0].equals(Variables[3]))
            {
                IGGMAttributeInstance ai = (IGGMAttributeInstance) player.getEntityAttribute(GGMCapabilities.AttributesMapByString.get(variables[1]));
                if (ai != null)
                {
                    ai.setBaseValue(Double.parseDouble(variables[2]));
                }
            }
            else if (variables[0].equals(Variables[4]))
            {
                IGGMAttributeInstance ai = (IGGMAttributeInstance) player.getEntityAttribute(GGMCapabilities.AttributesMapByString.get(variables[1]));
                if (ai instanceof IGGMDynamicAttributeInstance)
                {
                    ((IGGMDynamicAttributeInstance) ai).setDynamicValue(Double.parseDouble(variables[2]));
                }
            }
            else if (variables[0].equals(Variables[5]))
            {
                IGGMAttributeInstance ai = (IGGMAttributeInstance) player.getEntityAttribute(GGMCapabilities.AttributesMapByString.get(variables[1]));
                if (ai instanceof IGGMDynamicAttributeInstance)
                {
                    ((IGGMDynamicAttributeInstance) ai).setNaturalRegen(Float.parseFloat(variables[2]));
                }
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] variables)
    {
        return variables.length == 1 ? getListOfStringsMatchingLastWord(variables, Variables) : (variables.length == 2 && (variables[1].equals(Variables[3]) || variables[1].equals(Variables[4]) || variables[1].equals(Variables[5]))) ? getListOfStringsMatchingLastWord(variables, GGMCapabilities.AttributesMapByString.keySet().toArray(new String[0])) : null;
    }
}
