package mrfinger.gothicgamemod.item;

import mrfinger.gothicgamemod.item.equipment.IItemRequiring;
import net.minecraft.item.Item;

public interface IItemTool extends IItemRequiring {


    float getDamageVsEntity();

    Item.ToolMaterial getToolMaterial();

}
