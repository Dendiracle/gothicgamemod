package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.battle.DamageType;

public interface IGGMEntityCreature extends IGGMEntityLiving {


    DamageType getStandartMeleeDamageType();


    boolean isAnimedAttack();

    int currentAttackDuration();

    boolean chargeAttack();

    boolean isChargingAttack();

    int getAttackTime();

    int getAttackTick();

}
