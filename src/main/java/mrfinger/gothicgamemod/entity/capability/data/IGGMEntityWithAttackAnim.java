package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;

public interface IGGMEntityWithAttackAnim extends IGGMEntityLivingBase {


    int getNewAttackDuration();

    int getLastAttackDuration();

    int getAttackTick();

    int getAttackTicksLeft();

    void startAttack();

    void updateAttack();

}
