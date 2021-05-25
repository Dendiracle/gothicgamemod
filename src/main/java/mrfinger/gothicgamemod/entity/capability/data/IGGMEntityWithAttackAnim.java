package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.battle.hittypes.IHitType;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public interface IGGMEntityWithAttackAnim extends IGGMEntityLivingBase {


    short getNewAttackDuration(IHitType hitType);

    IHitType getLastAttackHitTYpe();

    short getLastAttackDuration();

    short getAttackCount();

    short getAttackTick();

    byte getAttackSeries();


    void startAttack(IHitType hitType);

}
