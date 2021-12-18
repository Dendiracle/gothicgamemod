package mrfinger.gothicgamemod.entity.player;

/*public class GGMPlayerEquipmentAnimationFightStance extends InventoryPlayer implements IGGMPlayerEquipmentAnimationFightStance {


    public GGMPlayerEquipmentAnimationFightStance(IGGMEntityPlayer player) {
        super((EntityPlayer) player);
        this.mainInventory = new ItemStack[6];
        this.armorInventory = new ItemStack[6];
    }


    @Override
    public int getCurrentItemIndex() {
        return this.currentItem;
    }

    @Override
    public ItemStack getHeldItem() {
        return this.currentItem < this.mainInventory.length / 2 - 1 && this.currentItem >= 0 ? this.mainInventory[this.currentItem * 2] : null;
    }

    @Override
    public ItemStack getSecHeldItem() {
        return this.currentItem < this.mainInventory.length / 2 - 1 && this.currentItem >= 0 ? this.mainInventory[this.currentItem * 2 + 1] : null;
    }

    @Override
    public ItemStack getNecklace() {
        return this.armorInventory[0];
    }

    @Override
    public ItemStack getRing(int index) {
        if (index < 0 || index > 1) return null;
        return this.armorInventory[index + 1];
    }

    @Override
    public ItemStack getQuiver() {
        return this.mainInventory[3];
    }

    @Override
    public ItemStack getGloves() {
        return this.armorInventory[4];
    }

    @Override
    public ItemStack getBelt() {
        return this.armorInventory[5];
    }


    @Override
    public String getInventoryName() {
        return "container.ggmequipment";
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack is) {

        boolean b = false;
        switch (slot) {
            case 0: b = is != null && is.getItem() instanceof IItemMeleeWeapon;
            break;
        }

        return b;
    }

    @Override
    public int getSizeInventory() {
        return this.mainInventory.length + this.armorInventory.length;
    }


    protected int findItemStack(Item p_146029_1_)
    {
        for (int i = 0; i < this.armorInventory.length; ++i)
        {
            if (this.armorInventory[i] != null && this.armorInventory[i].getItem() == p_146029_1_)
            {
                return i + this.mainInventory.length;
            }
        }

        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == p_146029_1_)
            {
                return i;
            }
        }

        return -1;
    }


    @Override
    public int clearInventory(Item p_146027_1_, int p_146027_2_) {

        for (ItemStack itemStack : this.armorInventory) {

            if (itemStack != null) {

                this.onRemovingItem(itemStack);
            }
        }

        return super.clearInventory(p_146027_1_, p_146027_2_);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {

        ItemStack[] aitemstack = this.mainInventory;

        boolean flag = false;

        if (p_70304_1_ >= this.mainInventory.length)
        {
            aitemstack = this.armorInventory;
            p_70304_1_ -= this.mainInventory.length;
            flag = true;
        }

        if (aitemstack[p_70304_1_] != null)
        {
            ItemStack itemstack = aitemstack[p_70304_1_];
            if (flag) this.onRemovingItem(itemstack);
            aitemstack[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean consumeInventoryItem(Item p_146026_1_) {

        int i = this.findItemStack(p_146026_1_);

        if (i < 0) {

            return false;
        }
        else {

            this.getStackInSlotOnClosing(i);

            return true;
        }
    }


    public void removeItem(int slot) {

        ItemStack[] aitemstack = this.mainInventory;

        boolean flag = false;

        if (slot >= this.mainInventory.length) {

            slot -= this.mainInventory.length;
            aitemstack = this.armorInventory;
            flag = true;
        }

        ItemStack itemStack = aitemstack[slot];

        if (itemStack != null) {

            if (flag) this.onRemovingItem(itemStack);

            aitemstack[slot] = null;
        }

    }


    @Override
    public ItemStack decrStackSize(int slot, int value) {

        ItemStack[] aitemstack = this.mainInventory;
        boolean flag = false;

        if (slot >= this.mainInventory.length)
        {
            aitemstack = this.armorInventory;
            slot -= this.mainInventory.length;
            flag = true;
        }

        if (aitemstack[slot] != null) {

            ItemStack itemStack = aitemstack[slot];
            if (flag) this.onRemovingItem(itemStack);
            aitemstack[slot] = null;
            return itemStack;
        }
        else {
            return null;
        }
    }


    @Override
    public void damageArmor(float value) {

        value /= 4.0F;

        if (value < 1.0F)
        {
            value = 1.0F;
        }

        for (int i = 0; i < this.armorInventory.length; ++i)
        {
            if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor)
            {
                this.armorInventory[i].damageItem((int)value, this.player);

                if (this.armorInventory[i].stackSize == 0)
                {
                    this.onRemovingItem(this.armorInventory[i]);
                    this.armorInventory[i] = null;
                }
            }
        }
    }


    @Override
    public void dropAllItems() {

        int i;

        for (i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null)
            {
                this.player.func_146097_a(this.mainInventory[i], true, false);
                this.mainInventory[i] = null;
            }
        }

        for (i = 0; i < this.armorInventory.length; ++i)
        {
            if (this.armorInventory[i] != null)
            {
                this.player.func_146097_a(this.armorInventory[i], true, false);
                this.onRemovingItem(this.armorInventory[i]);
                this.armorInventory[i] = null;
            }
        }
    }

    protected void onRemovingItem(ItemStack itemStack) {

        Item i = itemStack.getItem();

        if (i instanceof ItemGGMEquipAttributeBonus) {

            IItemGGMEquip item = (IItemGGMEquip) i;
            item.onItemRemoved(itemStack, (IGGMEntityPlayer) this.player);
        }
    }

    protected void onEquipItem(int slot, ItemStack itemStack) {

        if (itemStack != null && slot >= this.mainInventory.length) {

            Item i = itemStack.getItem();

            if (i instanceof ItemGGMEquipAttributeBonus) {

                IItemGGMEquip item = (IItemGGMEquip) i;
                item.onItemEquiped(itemStack, (IGGMEntityPlayer) this.player);
            }
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        super.setInventorySlotContents(slot, itemStack);

        this.onEquipItem(slot, itemStack);
    }


    @Override
    public NBTTagList writeToNBT(NBTTagList p_70442_1_) {

        int i;
        NBTTagCompound nbttagcompound;

        for (i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.mainInventory[i].writeToNBT(nbttagcompound);
                p_70442_1_.appendTag(nbttagcompound);
            }
        }

        for (i = 0; i < this.armorInventory.length; ++i)
        {
            if (this.armorInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)(i + 20));
                this.armorInventory[i].writeToNBT(nbttagcompound);
                p_70442_1_.appendTag(nbttagcompound);
            }
        }

        return p_70442_1_;
    }

    @Override
    public void readFromNBT(NBTTagList list) {

        this.mainInventory = new ItemStack[6];
        this.armorInventory = new ItemStack[6];
        System.out.println("Debug in " + this.getClass() + " Reading from");
        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

            if (itemstack != null)
            {
                if (j >= 0 && j < this.mainInventory.length)
                {
                    this.mainInventory[j] = itemstack;
                }

                if (j >= 20 && j < this.armorInventory.length + 20)
                {
                    this.setInventorySlotContents(j - 20 + this.armorInventory.length, itemstack);
                }
            }
        }
    }
}*/
