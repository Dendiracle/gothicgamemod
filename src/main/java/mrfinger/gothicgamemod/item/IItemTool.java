package mrfinger.gothicgamemod.item;

import net.minecraft.item.Item;

public interface IItemTool {


    float getDamageVsEntity();

    Item.ToolMaterial getToolMaterial();

}
