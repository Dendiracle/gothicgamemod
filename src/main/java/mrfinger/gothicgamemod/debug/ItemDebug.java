package mrfinger.gothicgamemod.debug;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemDebug extends ItemSword {

    public ItemDebug(ToolMaterial p_i45356_1_) {
        super(p_i45356_1_);
        setUnlocalizedName("itemdebug");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        System.out.println("Debug in ItemDebug");
        return super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
    }
}
