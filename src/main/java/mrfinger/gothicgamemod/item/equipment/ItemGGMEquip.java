package mrfinger.gothicgamemod.item.equipment;

import mrfinger.gothicgamemod.GothicMain;
import net.minecraft.item.Item;

public abstract class ItemGGMEquip extends Item implements IItemGGMEquip {


    protected final byte index;


    public ItemGGMEquip(String unlocalizedName, String texture, byte index) {

        this.setUnlocalizedName(unlocalizedName);
        this.setTextureName(GothicMain.MODID + ":" + texture);
        this.setMaxStackSize(1);
        this.index = index;
    }


    @Override
    public byte getIndex() {
        return this.index;
    }

}
