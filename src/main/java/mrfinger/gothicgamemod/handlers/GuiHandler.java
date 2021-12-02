package mrfinger.gothicgamemod.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import mrfinger.gothicgamemod.client.gui.GGMGuiInventory;
import mrfinger.gothicgamemod.entity.inventory.GGMContainerPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.init.GGMGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case GGMGui
                    .GGMGuiInvenory:
                return new GGMContainerPlayer((IGGMEntityPlayer) player);
                //return ((IGGMEntityPlayer) player).getGGMContainer();
            default:
                break;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (ID) {
            case GGMGui
                    .GGMGuiInvenory:
                return new GGMGuiInventory((IGGMEntityPlayer) player);
            default:
                break;
        }

        return null;
    }
}
