package mrfinger.gothicgamemod.mixin.item;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public abstract class GGMItem {


    @Shadow public abstract int getMaxDamage();
}
