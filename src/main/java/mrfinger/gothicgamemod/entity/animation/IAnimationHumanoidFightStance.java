package mrfinger.gothicgamemod.entity.animation;

import mrfinger.gothicgamemod.entity.animation.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityHumanoid;
import net.minecraft.item.ItemStack;

public interface IAnimationHumanoidFightStance<Entity extends IGGMEntityHumanoid, Episode extends IAnimationHit> extends IAnimationFightStance<Entity, Episode>
{

    boolean isUsingLH();

    ItemStack getBlockItem();


    void setUseItem();

    void endUseItem();

}
