package mrfinger.gothicgamemod.entity;

public interface IGGMEntityCreature extends IGGMEntityLivingBase {


    boolean isAnimedAttack();

    int currentAttackDuration();

    boolean chargeAttack();

    boolean isChargingAttack();

    int getAttackTime();

    int getAttackTick();

}
