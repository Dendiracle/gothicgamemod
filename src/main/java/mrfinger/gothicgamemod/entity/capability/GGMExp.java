package mrfinger.gothicgamemod.entity.capability;

import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityExperienceable;
import net.minecraft.nbt.NBTTagCompound;

public class GGMExp implements IGGMExp
{

    protected final IGGMEntityExperienceable entity;

    protected long currentExp;
    protected long toNextLvl;

    protected int lp;


    public GGMExp(IGGMEntityExperienceable entity)
    {
        this.entity = entity;

        this.setExpValueFromLvl(entity.getLvl());
    }


    @Override
    public IGGMEntityExperienceable getEntity()

    {
        return this.entity;
    }


    @Override
    public long getExpValue()
    {
        return currentExp;
    }

    @Override
    public long getExpValueForNewLvl()
    {
        return this.toNextLvl;
    }

    @Override
    public int getLPValue()
    {
        return this.lp;
    }


    public void setExpValue(long value)
    {

        if (value != this.currentExp)
        {
            if (value <= 0)
            {
                this.currentExp = 0;
                this.toNextLvl = this.needToNextLvlIncreases();
                this.entity.setLvl(0);
                this.entity.flagForLvlUpdate();
                return;
            }

            final int needToNextLvlIncreases = this.needToNextLvlIncreases();
            int lvl = this.entity.getLvl();
            long toCurrentLvl = this.toNextLvl - (1 + lvl) * needToNextLvlIncreases;

            if (value >= this.toNextLvl)
            {
                toCurrentLvl = this.toNextLvl;

                do
                {
                    ++lvl;
                    toCurrentLvl += (lvl + 1) * needToNextLvlIncreases;
                }
                while (value >= toCurrentLvl);

                this.toNextLvl = toCurrentLvl;
                this.entity.setLvl(lvl);
            }
            else if (value < toCurrentLvl)
            {
                do
                {
                    toCurrentLvl -= lvl * needToNextLvlIncreases;
                    --lvl;
                }
                while (value < toCurrentLvl);

                this.toNextLvl = toCurrentLvl + (1 + lvl) * needToNextLvlIncreases;
                this.entity.setLvl(lvl);
            }

            this.currentExp = value;
            this.entity.flagForLvlUpdate();
        }
    }

    protected int needToNextLvlIncreases()
    {
        return 500;
    }

    protected int lpFromNextLvl()
    {
        return 10;
    }

    @Override
    public void changeExpValue(long changeValue)
    {
        this.setExpValue(this.currentExp + changeValue);
    }

    @Override
    public int gainExp(int exp)
    {
        if (exp < 0)
        {
            throw new IllegalArgumentException("Entity cannot gains experience less than 0");
        }

        int simpleLvl = this.entity.getLvl();
        this.changeExpValue(exp);
        if (this.entity.getLvl() > simpleLvl)
        {
            simpleLvl = this.entity.getLvl() - simpleLvl;
            this.changeLP((simpleLvl) * this.lpFromNextLvl());
            return simpleLvl;
        }

        return 0;
    }


    @Override
    public long setExpValueFromLvl(int lvl)
    {
        long toNL = this.needToNextLvlIncreases() * (lvl + 1);
        this.currentExp = (toNL * lvl) / 2;
        this.toNextLvl = this.currentExp + toNL;
        this.entity.flagForLvlUpdate();

        return this.currentExp;
    }

    @Override
    public void setLP(int lp)
    {
        if (lp != this.lp)
        {
            this.lp = lp > 0 ? lp : 0;
            this.entity.flagForLvlUpdate();
        }
    }

    @Override
    public void changeLP(int changeValue) {
        this.setLP(this.lp + changeValue);
    }


    public void saveNBTData(NBTTagCompound nbt)
    {
        nbt.setLong("EXP", this.currentExp);
        nbt.setInteger("LP", this.lp);
    }

    public void loadNBTData(NBTTagCompound nbt)
    {
        this.setExpValue(nbt.getLong("EXP"));
        this.setLP(nbt.getInteger("LP"));
    }

}