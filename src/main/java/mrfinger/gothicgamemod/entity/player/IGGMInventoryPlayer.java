package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.entity.IGGMHumanoidInventory;
import net.minecraft.item.ItemStack;

public interface IGGMInventoryPlayer extends IGGMHumanoidInventory {


    void changeCurrentItem(boolean toMore);

}
