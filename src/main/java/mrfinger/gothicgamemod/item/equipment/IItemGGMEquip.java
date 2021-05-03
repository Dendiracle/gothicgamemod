package mrfinger.gothicgamemod.item.equipment;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.item.IItemEquip;
import net.minecraft.item.ItemStack;

public interface IItemGGMEquip extends IItemRequiring {


    byte getIndex();


    void onItemEquiped(ItemStack itemStack, IGGMEntityLivingBase player);

    void onItemRemoved(ItemStack itemStack, IGGMEntityLivingBase player);

}
