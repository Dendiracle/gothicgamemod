package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityHumanoid;
import net.minecraft.item.ItemStack;

public interface IAnimationHumanoidFightStance<Entity extends IGGMEntityHumanoid, Animation extends IAnimation<Entity>> extends IAnimationFightStance<Entity, Animation>
{

    boolean isUsingItem();

    boolean isBlocking();

    boolean isUsingLH();

    ItemStack getBlockItem();


    void setUseItem();

    void endUseItem();

}
