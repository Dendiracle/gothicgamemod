package mrfinger.gothicgamemod.mixin.common.item;

import mrfinger.gothicgamemod.item.IItemTool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemSword.class)
public abstract class GGMItemSword implements IItemTool {


    @Shadow private float field_150934_a;

    @Shadow @Final private Item.ToolMaterial repairMaterial;


    @Override
    public float getDamageVsEntity()
    {
        return this.field_150934_a;
    }

    @Override
    public Item.ToolMaterial getToolMaterial()
    {
        return this.repairMaterial;
    }
}
