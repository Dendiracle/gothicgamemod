package mrfinger.gothicgamemod.entity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public interface IGGMHumanoidInventory extends IInventory {


    int getCurrentItemIndex();

    void setCurrentItem(int index);


    ItemStack getHeldItem();

    ItemStack getSecHeldItem();

    ItemStack getNecklace();

    ItemStack getRing(int index);

    ItemStack getQuiver();

    ItemStack getGloves();

    ItemStack getBelt();


    NBTTagList writeToNBT(NBTTagList list);

    void readFromNBT(NBTTagList list);


    void clearInventory();

    boolean consumeInventoryItem(Item slot);

    void damageArmor(float value);

    void dropAllItems();

}
