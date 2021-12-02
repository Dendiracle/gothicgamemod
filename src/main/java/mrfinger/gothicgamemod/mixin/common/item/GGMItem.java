package mrfinger.gothicgamemod.mixin.common.item;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class GGMItem {


    @Shadow public abstract int getMaxDurability();
}
