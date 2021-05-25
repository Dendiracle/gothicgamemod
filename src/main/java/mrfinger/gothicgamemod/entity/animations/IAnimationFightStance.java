package mrfinger.gothicgamemod.entity.animations;

import mrfinger.gothicgamemod.battle.hittypes.IHitType;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface IAnimationFightStance extends IAnimation
{

    IHitType getLastHitType();

    short getAttackDuration();

    short getAtackCount();

    short getAttackTick();

    byte getAttackSeries();

    boolean setAnimationHit(IHitType hitType, short count);

    void clearAnimationHit();


    IGGMEntity[] getTargets();

    void setTargets(IGGMEntity[] entities);

    void setTargets(Collection<IGGMEntity> entities);


    boolean isUsingItem();

    boolean isBlocking();

}
