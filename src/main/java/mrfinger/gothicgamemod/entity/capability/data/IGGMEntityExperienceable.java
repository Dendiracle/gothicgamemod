package mrfinger.gothicgamemod.entity.capability.data;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.capability.IGGMExp;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;

import java.util.HashMap;
import java.util.Map;

public interface IGGMEntityExperienceable extends IGGMEntityLivingBase
{

    IGGMExp getExpCap();


    default long getExpValue()
    {
        return this.getExpCap().getExpValue();
    }

    default long getExpValueForNewLvl()
    {
        return this.getExpCap().getExpValueForNewLvl();
    }

    default int getLPValue()
    {
        return this.getExpCap().getLPValue();
    }


    @Override
    default void onSetLvl()
    {
        this.getExpCap().setExpValueFromLvl(this.getLvl());
    }

    default void setExpValue(long value)
    {
        this.getExpCap().setExpValue(value);
    }

    default void changeExpValue(long value)
    {
        this.getExpCap().changeExpValue(value);
    }

    default int gainExp(int value)
    {
        return this.getExpCap().gainExp(value);
    }

    default long setExpValueFromLvl(int lvl)
    {
        return this.getExpCap().setExpValueFromLvl(lvl);
    }

    default void setLP(int lp)
    {
        this.getExpCap().setLP(lp);
    }

    default void changeLP(int changeValue)
    {
        this.getExpCap().changeLP(changeValue);
    }


    @Override
    default void onKilledEntity(EntityLivingBase entity)
    {
        this.onKilledEntityBase(entity);
        this.experienceOnKillEntity(entity);
    }

    default void experienceOnKillEntity(EntityLivingBase entity)
    {
        int gainExp = ((IGGMEntityLivingBase) entity).getGainingExperience();

        if (gainExp > 0)
        {
            this.gainExp(gainExp);
        }
        System.out.println(this.getExpValue() + " " + this.getExpValueForNewLvl());
    }


    default boolean inreaseAttributeForLP(IAttribute attribute, int amounts)
    {
        IGGMAttributeInstance ai = (IGGMAttributeInstance) this.getEntityAttribute(attribute);

        if (this.getExpCap().getLPValue() >= amounts)
        {
            //this.getExpCap().changeLP(-ai.increaseAttribute(amounts));
            return true;
        }

        return false;
    }

    default boolean inreaseAttributeForLP(String attributeName, int amounts)
    {
        IGGMAttributeInstance ai = this.getEntityAttribute(attributeName);

        if (this.getExpCap().getLPValue() >= amounts)
        {
            //this.getExpCap().changeLP(-ai.increaseAttribute(amounts));
            return true;
        }

        return false;
    }

    default boolean increaseAttributesByName(Map<String, Integer> mapByNames)
    {
        Map<IGGMAttributeInstance, Integer> map = new HashMap<>(mapByNames.size(), 1F);

        int needLP = 0;

        for (Map.Entry<String, Integer> e : mapByNames.entrySet())
        {
            IGGMAttributeInstance attributeInstance = this.getEntityAttribute(e.getKey());

            if (attributeInstance == null)
            {
                return false;
            }

            map.put(attributeInstance, e.getValue());
            needLP += e.getValue();
        }

        if (this.getExpCap().getLPValue() < needLP)
        {
            return false;
        }

        needLP = 0;

        for (Map.Entry<IGGMAttributeInstance, Integer> entry : map.entrySet())
        {
            //needLP += entry.getKey().increaseAttribute(entry.getValue());
        }

        this.getExpCap().changeLP(-needLP);

        return true;
    }

}
