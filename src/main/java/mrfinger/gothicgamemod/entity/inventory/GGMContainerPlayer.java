package mrfinger.gothicgamemod.entity.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMPlayerEquipmentAnimationHelperFightStance;
import mrfinger.gothicgamemod.inventory.IGGMContainer;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import mrfinger.gothicgamemod.item.equipment.IItemGGMEquip;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.List;

public class GGMContainerPlayer extends Container implements IGGMContainer {


    protected final IGGMEntityPlayer player;

    public List<GGMSlot> ggmSlots;


    public GGMContainerPlayer(IGGMEntityPlayer player) {

        this.ggmSlots = new ArrayList<>();
        this.player = player;
        InventoryPlayer inventoryPlayer = player.getInventoryPlayer();
        IGGMPlayerEquipmentAnimationHelperFightStance ggmInventoryPlayer = player.getGGMEquipment();
        int i, j;

        for (i = 0; i < 4; ++i) {

            this.addSlotToContainer(new SlotArmor(inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, (byte) i, 26, 8 + i * 18));
        }

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }

        for (i = 0; i < 3; ++i) {

            this.addSlotToContainerWithSync(new SlotHand(this, ggmInventoryPlayer, i * 2, true, 134, 8 + i * 18).setIconPos(16, 0));
            this.addSlotToContainerWithSync(new SlotHand(this, ggmInventoryPlayer, i * 2 + 1,false,152, 8 + i * 18).setIconPos(32, 0));

            this.addSlotToContainerWithSync(new GGMSlotArmor(this, ggmInventoryPlayer, i + 9, (byte) (i + 3), 44, 8 + i * 18).setIconPos(i * 16 + 96, 0));
        }

        this.addSlotToContainerWithSync(new GGMSlotArmor(this, ggmInventoryPlayer, 6, (byte) 0, 8, 8).setIconPos(48, 0));
        this.addSlotToContainerWithSync(new GGMSlotArmor(this, ggmInventoryPlayer, 7, (byte) 1, 8, 26).setIconPos(64, 0));
        this.addSlotToContainerWithSync(new GGMSlotArmor(this, ggmInventoryPlayer, 8, (byte) 1, 8, 44).setIconPos(64, 0));
    }


    protected Slot addSlotToContainerWithSync(Slot slot)
    {
        this.ggmSlots.add((GGMSlot) slot);
        return this.addSlotToContainer(slot);
    }


    @Override
    public IGGMEntityPlayer getEntityPlayer() {
        return this.player;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ == 0)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (p_82846_2_ >= 1 && p_82846_2_ < 5)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 5 && p_82846_2_ < 9)
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack())
            {
                int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 9 && p_82846_2_ < 36)
            {
                if (!this.mergeItemStack(itemstack1, 36, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 36 && p_82846_2_ < 45)
            {
                if (!this.mergeItemStack(itemstack1, 9, 36, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 9, 45, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p_82846_1_, itemstack1);
        }

        return itemstack;
    }


    @Override
    public void detectAndSendChanges() {

        int k = this.inventorySlots.size();
        int j = k - this.ggmSlots.size();

        for (int i = j; i < k; ++i)
        {
            ItemStack itemstack = this.ggmSlots.get(i - j).getStack();
            ItemStack itemstack1 = (ItemStack)this.inventoryItemStacks.get(i);

            if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
            {
                itemstack1 = itemstack == null ? null : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                ((ICrafting)this.player).sendSlotContents(this, i, itemstack1);

            }
        }
    }

    public static class SlotArmor extends Slot {


        byte armorIndex;


        public SlotArmor(IInventory inventory, int slotIndex, byte armorIndex, int x, int y) {
            super(inventory, slotIndex, x, y);

            this.armorIndex = armorIndex;
        }


        @Override
        public int getSlotStackLimit()
        {
            return 1;
        }

        @Override
        public boolean isItemValid(ItemStack p_75214_1_) {
            if (p_75214_1_ == null) return false;
            return p_75214_1_.getItem().isValidArmor(p_75214_1_, this.armorIndex, ((InventoryPlayer) this.inventory).player);
        }

        @SideOnly(Side.CLIENT)
        @Override
        public IIcon getBackgroundIconIndex()
        {
            return ItemArmor.func_94602_b(this.armorIndex);
        }
    }


    public abstract static class GGMSlot extends Slot {


        protected final IGGMContainer container;

        private int iconX, iconY;


        public GGMSlot(IGGMContainer container, IInventory inventory, int slotIndex, int x, int y) {
            super(inventory, slotIndex, x, y);

            this.container = container;
        }


        public GGMSlot setIconPos(int x, int y) {

            this.iconX = x;
            this.iconY = y;
            return this;
        }

        public int getIconX() {
            return iconX;
        }

        public int getIconY() {
            return iconY;
        }

        @Override
        public int getSlotStackLimit()
        {
            return 1;
        }
    }


    public static class GGMSlotArmor extends GGMSlot {


        byte armorIndex;


        public GGMSlotArmor(IGGMContainer container, IInventory inventory, int slotIndex, byte armorIndex, int x, int y) {
            super(container, inventory, slotIndex, x, y);

            this.armorIndex = armorIndex;
        }

        @Override
        public boolean isItemValid(ItemStack itemStack)
        {
            return itemStack != null && itemStack.getItem() instanceof IItemGGMEquip && ((IItemGGMEquip) itemStack.getItem()).getIndex() == this.armorIndex && ((IItemGGMEquip) itemStack.getItem()).isMayEquip(this.container.getEntityPlayer());
        }
    }


    public static class SlotHand extends GGMSlot {


        protected final boolean rh;


        public SlotHand(IGGMContainer container,  IInventory inventory, int slotIndex, boolean rightHand, int x, int y) {
            super(container, inventory, slotIndex, x, y);

            this.rh = rightHand;
        }


        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return stack != null && stack.getItem() instanceof IItemMeleeWeapon && ((IItemMeleeWeapon) stack.getItem()).isMayEquip(this.container.getEntityPlayer());
        }

    }
}
