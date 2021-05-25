package mrfinger.gothicgamemod.entity.animations;

import net.minecraft.item.ItemStack;

public interface IAnimationHumanoidFightStance extends IAnimationFightStance {


    public boolean isUsingLH();

    ItemStack getBlockItem();


    void setUseItem();

    void endUseItem();
}
