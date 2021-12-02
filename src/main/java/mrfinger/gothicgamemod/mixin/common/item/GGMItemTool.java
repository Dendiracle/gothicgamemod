package mrfinger.gothicgamemod.mixin.common.item;

import mrfinger.gothicgamemod.item.IItemTool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemTool.class)
public abstract class GGMItemTool implements IItemTool {


    @Shadow private float damageVsEntity;

    @Shadow protected Item.ToolMaterial toolMaterial;


    @Override
    public float getDamageVsEntity() {
        return this.damageVsEntity;
    }


    @Override
    public Item.ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }
}
