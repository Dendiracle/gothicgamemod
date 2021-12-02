package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityHumanoid;
import net.minecraft.item.ItemStack;

public interface IAnimationHelperHumanoidFightStance<Entity extends IGGMEntityHumanoid, Episode extends IAnimationHit> extends IAnimationHelperFightStance<Entity, Episode>
{

    boolean isUsingLH();

    ItemStack getBlockItem();


    void setUseItem();

    void endUseItem();

}
