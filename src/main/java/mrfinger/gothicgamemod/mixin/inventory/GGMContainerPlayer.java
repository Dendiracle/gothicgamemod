package mrfinger.gothicgamemod.mixin.inventory;

//unused class
/*@Mixin(ContainerPlayer.class)
public abstract class GGMContainerPlayer implements IGGMContainer {


    @Shadow @Final private EntityPlayer thePlayer;

    /*@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Slot;<init>(Lnet/minecraft/inventory/IInventory;III)V", ordinal = 1), index = 1)
    private int fixArmorSlotsInit(IInventory iInventory, int slotIndex, int x, int y, InventoryPlayer ip, boolean p_i1819_2_, EntityPlayer p_i1819_3_) {
        return slotIndex - ip.getSizeInventory() + 4 + ip.mainInventory.length;
    }*/
/*
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Slot;<init>(Lnet/minecraft/inventory/IInventory;III)V", ordinal = 1), index = 1)
    private int fixArmorSlotsInit(IInventory iInventory, int slotIndex, int x, int y) {
        return slotIndex - this.thePlayer.inventory.getSizeInventory() + 4 + this.thePlayer.inventory.mainInventory.length;
    }

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/inventory/SlotCrafting"))
    private SlotCrafting fixCraftingSlots1(EntityPlayer player, IInventory matrix, IInventory result, int index, int x, int y) {
        return new SlotCrafting(player, matrix, result, index, 154, 55);
    }

    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/inventory/Slot", ordinal = 0))
    private Slot fixCraftingSlots2(IInventory matrix, int index, int x, int y) {
        return new Slot(matrix, index, x +10, y + 19);
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {


        for (int i = 0; i < 3; ++i) {

            for (int ii = 0; ii < 2; ++ii) {
                this.addSlotToContainer(new Slot(((IGGMEntityPlayer) this.thePlayer).getGGMEquipment(), i * 2 + ii, 117 + i * 18, 8 + ii * 18));
            }
        }

        for (int i = 0; i < 3; ++i) {

            this.addSlotToContainer(new Slot(((IGGMEntityPlayer) this.thePlayer).getGGMEquipment(), i + 6 , 77, 8 + i * 18));
        }

        this.addSlotToContainer(new Slot(((IGGMEntityPlayer) this.thePlayer).getGGMEquipment(), 9, 95, 24));
        this.addSlotToContainer(new Slot(((IGGMEntityPlayer) this.thePlayer).getGGMEquipment(), 10, 77, 62));
    }



}*/
