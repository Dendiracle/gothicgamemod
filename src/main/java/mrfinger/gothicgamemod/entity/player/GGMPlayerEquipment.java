package mrfinger.gothicgamemod.entity.player;

import mrfinger.gothicgamemod.item.equipment.IItemGGMEquip;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GGMPlayerEquipment implements IGGMInventoryPlayer {


    protected ItemStack[] equip;

    protected int weaponSlotsAmount;

    protected int currentWeapon;

    protected final IGGMEntityPlayer player;

    protected boolean inventoryChanged;


    public GGMPlayerEquipment(IGGMEntityPlayer player) {

        this.player = player;
        this.weaponSlotsAmount = 3;
        this.equip = new ItemStack[12];
    }


    @Override
    public int getCurrentItemIndex() {
        return this.currentWeapon;
    }

    @Override
    public void setCurrentItem(int index) {

        if (index != this.currentWeapon && index >= 0 && index < this.weaponSlotsAmount) {

            if (this.equip[this.currentWeapon] != null) {

                this.onRemovingItem(this.equip[this.currentWeapon]);
            }

            if (this.equip[index] != null && this.equip[index].getItem() instanceof IItemGGMEquip) {

                ((IItemGGMEquip) this.equip[index].getItem()).onItemEquiped(this.equip[index], this.player);
            }

            this.currentWeapon = index;
        }
    }

    @Override
    public void changeCurrentItem(boolean toMore) {

        int a = toMore ? (this.currentWeapon >= this.weaponSlotsAmount - 1 ? 0 : this.currentWeapon + 1) : (this.currentWeapon <= 0 ? this.weaponSlotsAmount - 1 : this.currentWeapon - 1);
        this.setCurrentItem(a);
    }

    @Override
    public ItemStack getHeldItem() {
        return this.currentWeapon < this.weaponSlotsAmount && this.currentWeapon >= 0 ? this.equip[this.currentWeapon * 2] : null;
    }

    @Override
    public ItemStack getSecHeldItem() {
        return this.currentWeapon < this.weaponSlotsAmount && this.currentWeapon >= 0 ? this.equip[this.currentWeapon * 2 + 1] : null;
    }


    @Override
    public ItemStack getNecklace() {
        return this.equip[this.weaponSlotsAmount];
    }

    @Override
    public ItemStack getRing(int index) {
        if (index < 0 || index > 1) return null;
        return this.equip[this.weaponSlotsAmount + index];
    }

    @Override
    public ItemStack getQuiver() {
        return this.equip[this.weaponSlotsAmount + 3];
    }

    @Override
    public ItemStack getGloves() {
        return this.equip[this.weaponSlotsAmount + 4];
    }

    @Override
    public ItemStack getBelt() {
        return this.equip[this.weaponSlotsAmount + 5];
    }


    @Override
    public int getSizeInventory() {
        return this.equip.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.equip[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int value) {
        return this.removeItem(slot);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return this.removeItem(slot);
    }


    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        this.removeItem(slot);
        this.equip[slot] = itemStack;
        this.onEquipItem(slot, itemStack);
    }


    @Override
    public String getInventoryName() {
        return "container.ggmequipment";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void markDirty() {
        this.inventoryChanged = true;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }


    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack is) {

        return true;
    }


    protected int findItemStack(Item item)
    {
        for (int i = 0; i < this.equip.length; ++i) {

            if (this.equip[i] != null && this.equip[i].getItem() == item) {

                return i + this.equip.length;
            }
        }

        return -1;
    }


    @Override
    public void clearInventory() {

        for (int i = 0; i < this.equip.length; ++i) {

            if (this.equip[i] != null) {

                this.removeItem(i);
            }
        }
    }

    @Override
    public boolean consumeInventoryItem(Item slot) {

        int i = this.findItemStack(slot);

        if (i < 0) {

            return false;
        }
        else {

            this.getStackInSlotOnClosing(i);

            return true;
        }
    }


    public ItemStack removeItem(int slot) {

        if (this.equip[slot] != null) {

            ItemStack itemStack = this.equip[slot];
            if (slot >= this.weaponSlotsAmount * 2 || slot == this.currentWeapon * 2 || slot == this.currentWeapon * 2 + 1) this.onRemovingItem(itemStack);
            this.equip[slot] = null;
            return itemStack;
        }

        return null;
    }


    @Override
    public void damageArmor(float value) {

        value /= 4.0F;

        if (value < 1.0F)
        {
            value = 1.0F;
        }

        for (int i = this.weaponSlotsAmount; i < this.equip.length; ++i)
        {
            if (this.equip[i] != null && this.equip[i].getItem() instanceof ItemArmor)
            {
                this.equip[i].damageItem((int)value, (EntityPlayer) this.player);

                if (this.equip[i].stackSize == 0)
                {
                    this.removeItem(i);
                }
            }
        }
    }


    @Override
    public void dropAllItems() {

        for (int i = 0; i < this.equip.length; ++i)
        {
            if (this.equip[i] != null)
            {
                this.player.func_146097_a(this.equip[i], true, false);
                this.equip[i] = null;
            }
        }
    }

    protected void onRemovingItem(ItemStack itemStack) {

        Item i = itemStack.getItem();

        if (i instanceof IItemGGMEquip) {

            IItemGGMEquip item = (IItemGGMEquip) i;
            item.onItemRemoved(itemStack, this.player);
        }
    }

    protected void onEquipItem(int slot, ItemStack itemStack) {

        if (itemStack != null && (slot >= this.weaponSlotsAmount * 2 || slot == this.currentWeapon * 2 || slot == this.currentWeapon * 2 + 1)) {

            Item i = itemStack.getItem();

            if (i instanceof IItemGGMEquip) {

                IItemGGMEquip item = (IItemGGMEquip) i;
                item.onItemEquiped(itemStack, this.player);
            }
        }
    }


    @Override
    public NBTTagList writeToNBT(NBTTagList tagList) {

        int i;
        NBTTagCompound nbttagcompound;

        for (i = 0; i < this.equip.length; ++i)
        {
            if (this.equip[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.equip[i].writeToNBT(nbttagcompound);
                tagList.appendTag(nbttagcompound);
            }
        }

        return tagList;
    }

    @Override
    public void readFromNBT(NBTTagList list) {

        this.equip = new ItemStack[12];

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

            if (itemstack != null) {
                if (j >= 0 && j < this.equip.length) {
                    this.setInventorySlotContents(j, itemstack);
                }
            }
        }
    }
}
