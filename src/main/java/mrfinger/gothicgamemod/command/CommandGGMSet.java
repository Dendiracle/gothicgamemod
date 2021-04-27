package mrfinger.gothicgamemod.command;

import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMModifiableAttributeInstance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayerMP;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class CommandSetExp extends CommandBase {



    public static final String Usage = "/gset";

    public static final String[] Variables = {"level", "experience", "LearningPoints", "attributeBaseValue", "attributeDynamicValue", "attributeBaseRegenValue"};


    @Override
    public String getCommandName() {
        return "gset";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
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
                player.getExpCap().setExp(exp);
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
                IGGMModifiableAttributeInstance ai = (IGGMModifiableAttributeInstance) player.getEntityAttribute(GGMCapabilities.AttributesMapByString.get(variables[1]));
                if (ai != null)
                {
                    ai.setBaseValue(Double.parseDouble(variables[2]));
                }
            }
            else if (variables[0].equals(Variables[4]))
            {
                IGGMModifiableAttributeInstance ai = (IGGMModifiableAttributeInstance) player.getEntityAttribute(GGMCapabilities.AttributesMapByString.get(variables[1]));
                if (ai != null && ai instanceof IGGMDynamicAttributeInstance)
                {
                    ((IGGMDynamicAttributeInstance) ai).setCurrentValue(Double.parseDouble(variables[2]));
                }
            }
            else if (variables[0].equals(Variables[5]))
            {
                IGGMModifiableAttributeInstance ai = (IGGMModifiableAttributeInstance) player.getEntityAttribute(GGMCapabilities.AttributesMapByString.get(variables[1]));
                if (ai != null && ai instanceof IGGMDynamicAttributeInstance)
                {
                    ((IGGMDynamicAttributeInstance) ai).setNaturalRegen(Float.parseFloat(variables[2]));
                }
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] variables)
    {
        return variables.length == 1 ? getListOfStringsMatchingLastWord(variables, Variables) : null;
    }
}
