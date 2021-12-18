package mrfinger.gothicgamemod.mixin.common.entity.player;

import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMPlayerEquipmentAnimationFightStance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryPlayer.class)
public abstract class GGMInventoryPlayer implements IGGMPlayerEquipmentAnimationFightStance
{


    @Shadow public ItemStack[] mainInventory;

    @Shadow public ItemStack[] armorInventory;

    @Shadow public EntityPlayer player;

    @Shadow private ItemStack itemStack;


    @Inject(method = "getCurrentItem", at = @At("HEAD"), cancellable = true)
    private void modifyCurItemGetter(CallbackInfoReturnable<ItemStack> cir) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) this.player;
        if (player.inFightStance()) {

            cir.setReturnValue(player.getGGMEquipment().getHeldItem());
        }
    }


    /*@ModifyVariable(method = "decrStackSize", at = @At(value = "JUMP", ordinal = 1))
    private ItemStack[] fixDecrStackSize(ItemStack[] aitemstack, int index, int countdown) {

        IGGMEntityPlayer player = (IGGMEntityPlayer) this.player;

        if (player.inFightStance()) aitemstack = player.getGGMEquipment().get;

        return aitemstack;
    }*/


   /* @Override
    public ItemStack getFirstEquippedItem() {
        return this.equip[0];
    }

    @Override
    public ItemStack getSecHeldItem() {
        return this.equip[1];
    }

    @Override
    public ItemStack getNecklace() {
        return this.equip[2];
    }

    @Override
    public ItemStack getRing(int index) {
        if (index < 0 || index > 1) return null;
        return this.equip[index + 2];
    }

    @Override
    public ItemStack getBelt() {
        return null;
    }

    @Override
    public ItemStack getGGMEquipmentInSlot(int slot) {
        return this.equip[slot];
    }


    @Overwrite
    public int clearInventory(Item p_146027_1_, int p_146027_2_)
    {
        int j = 0;
        int k;
        ItemStack itemstack;

        for (k = 0; k < this.mainInventory.length; ++k)
        {
            itemstack = this.mainInventory[k];

            if (itemstack != null && (p_146027_1_ == null || itemstack.getItem() == p_146027_1_) && (p_146027_2_ <= -1 || itemstack.getItemDamage() == p_146027_2_))
            {
                j += itemstack.stackSize;
                this.mainInventory[k] = null;
            }
        }

        for (k = 0; k < this.armorInventory.length; ++k)
        {
            itemstack = this.armorInventory[k];

            if (itemstack != null && (p_146027_1_ == null || itemstack.getItem() == p_146027_1_) && (p_146027_2_ <= -1 || itemstack.getItemDamage() == p_146027_2_))
            {
                j += itemstack.stackSize;
                this.armorInventory[k] = null;
            }
        }

        if (this.itemStack != null)
        {
            if (p_146027_1_ != null && this.itemStack.getItem() != p_146027_1_)
            {
                return j;
            }

            if (p_146027_2_ > -1 && this.itemStack.getItemDamage() != p_146027_2_)
            {
                return j;
            }

            j += this.itemStack.stackSize;
            this.setItemStack((ItemStack)null);
        }

        return j;
    }

    @Overwrite
    public ItemStack decrStackSize(int slot, int value) {

        ItemStack[] aitemstack = this.mainInventory;

        int invSumm = this.mainInventory.length + this.armorInventory.length;
        System.out.println("PUUUUUUUUUUUUUUUUUUUUUk");
        if (slot >= this.mainInventory.length)
        {
            aitemstack = this.equip;
            slot -= invSumm;
        }

        else if (slot >= this.mainInventory.length)
        {
            aitemstack = this.armorInventory;
            slot -= this.mainInventory.length;
        }

        if (aitemstack[slot] != null)
        {
            ItemStack itemstack;

            if (aitemstack[slot].stackSize <= value)
            {
                itemstack = aitemstack[slot];
                aitemstack[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = aitemstack[slot].splitStack(value);

                if (aitemstack[slot].stackSize == 0)
                {
                    aitemstack[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /*@Inject(method = "decrStackSize", at = @At("HEAD"), cancellable = true)
    private void onDecrStackSize(int slot, int value, CallbackInfoReturnable<ItemStack> cir) {

        int invsumm = this.mainInventory.length + this.armorInventory.length;

        if (slot >= invsumm) {

            slot -= invsumm;

            if (this.equip[slot] != null)
            {
                ItemStack itemstack;

                if (this.equip[slot].stackSize <= value)
                {
                    itemstack = this.equip[slot];
                    this.equip[slot] = null;
                    cir.setReturnValue(itemstack);
                }
                else
                {
                    itemstack = this.equip[slot].splitStack(value);

                    if (this.equip[slot].stackSize == 0)
                    {
                        this.equip[slot] = null;
                    }

                    cir.setReturnValue(itemstack);
                }
            }
            else
            {
                cir.setReturnValue(null);
            }

        }
    }*/


    /*@ModifyVariable(method = "decrStackSize", at = @At(value = "HEAD"), ordinal = 0)
    private ItemStack[] fixSlotIndexing(int slot, int value, ItemStack[] is) {

        if (slot >= this.armorInventory.length + this.mainInventory.length) {
            is = this.equip;
        }
        return is;
    }

    @ModifyVariable(method = "decrStackSize", at = @At(value = "JUMP", ordinal = 1), ordinal = 0)
    private int fixSlotIndexing2(int slot) {

        if (slot >= this.armorInventory.length) {
            slot -= this.armorInventory.length;
        }
        return slot;
    }*/

   /* @Inject(method = "getStackInSlotOnClosing", at = @At(value = "HEAD"), cancellable = true)
    private void modifyGettingItemOnClosing(int slot, CallbackInfoReturnable<ItemStack> cir) {

        int invsumm = this.mainInventory.length + this.armorInventory.length;

        if (slot >= invsumm) {
            slot -= invsumm;
            ItemStack stack = this.equip[slot];
            this.equip[slot] = null;
            cir.setReturnValue(stack);
        }


    }

    @Inject(method = "setInventorySlotContents", at = @At("HEAD"), cancellable = true)
    private void onSetInventorySlotContents(int slot, ItemStack stack, CallbackInfo ci) {

        int invsumm = this.mainInventory.length + this.armorInventory.length;

        if (slot >= invsumm) {

            slot -= invsumm;
            this.equip[slot] = stack;
            ci.cancel();
        }
    }

    @Inject(method = "writeToNBT", at = @At("TAIL"))
    private void onWriteToNBT(NBTTagList list, CallbackInfoReturnable<NBTTagList> cir) {

        // int i = 200;
        //int j =

        NBTTagCompound nbttagcompound;

        for (int i = 0; i >  this.equip.length; ++i) {
            if (this.equip[i] != null) {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) (-i));
                this.equip[i].writeToNBT(nbttagcompound);
                list.appendTag(nbttagcompound);
            }
        }
    }

    @Inject(method = "readFromNBT", at = @At(value = "JUMP", ordinal = 2))
    private void inReadFromNBT(NBTTagList list, CallbackInfo ci) {


    }

    @Shadow public abstract int getSizeInventory();

    @Inject(method = "getSizeInventory", at = @At("TAIL"), cancellable = true)
    private void onGetSizeInventory(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() + this.equip.length);
    }

    @Inject(method = "getStackInSlot", at = @At("HEAD"), cancellable = true)
    private void onGetStackInSlot(int slot, CallbackInfoReturnable<ItemStack> cir) {

        int invsumm = this.mainInventory.length + this.armorInventory.length;

        if (slot >= invsumm) {
            slot -= invsumm;
            cir.setReturnValue(this.equip[slot]);
        }
    }

    @Inject(method = "dropAllItems", at = @At("TAIL"))
    private void onDropAllItems(CallbackInfo ci) {

        for(int i =0; i< this.equip.length; ++i) {
            if (this.equip[i] != null) {
                this.player.func_146097_a(this.equip[i], true, false);
                this.equip[i] = null;
            }
        }
    }

    @Inject(method = "hasItemStack", at = @At("TAIL"), cancellable = true)
    private void modifyHasItem(ItemStack is, CallbackInfoReturnable<Boolean> cir) {

        for (int i = 0; i < this.equip.length; ++i) {

            if (this.equip[i] != null && this.equip[i].equals(is)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Shadow public abstract void setItemStack(ItemStack p_70437_1_);

    @Inject(method = "copyInventory", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", ordinal = 0))
    private void onCopyInventory(InventoryPlayer ip, CallbackInfo ci) {

        for (int i = 0; i < this.equip.length; ++i) {

            //this.equip[i] = ItemStack.copyItemStack(((IGGMPlayerEquipmentAnimationFightStance) ip).getGGMEquipmentInSlot(i));
            System.out.println("HHHHHHHHHHHHHH");
            this.equip[i] = null;
            System.out.println("LLLLLLLLLLLLLL");
            ((IGGMPlayerEquipmentAnimationFightStance) ip).getGGMEquipmentInSlot(i);
        }
    }*/

}
