package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.capability.IGGMExp;

public interface IGGMEntityExperienceableWithEXPInside extends IGGMEntityExperienceable, IGGMExp
{

    @Override
    default IGGMEntityExperienceable getEntity()
    {
        return this;
    }

    @Override
    default IGGMExp getExpCap()
    {
        return this;
    }


    @Override
    long getExpValue();

    @Override
    long getExpValueForNewLvl();

    int getLPValue();


    void setExpValue(long value);

    void changeExpValue(long changeValue);

    int gainExp(int exp);

    long setExpValueFromLvl(int lvl);

    void setLP(int lp);

    void changeLP(int changeValue);

}
