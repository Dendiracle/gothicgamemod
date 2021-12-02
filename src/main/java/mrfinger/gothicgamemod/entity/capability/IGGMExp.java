package mrfinger.gothicgamemod.entity.capability;

import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityExperienceable;

public interface IGGMExp extends ICapabilitySaveable
{

    IGGMEntityExperienceable getEntity();


    long getExpValue();

    long getExpValueForNewLvl();

    int getLPValue();


    void setExpValue(long value);

    void changeExpValue(long changeValue);

    int gainExp(int exp);

    long setExpValueFromLvl(int lvl);

    void setLP(int lp);

    void changeLP(int changeValue);

}