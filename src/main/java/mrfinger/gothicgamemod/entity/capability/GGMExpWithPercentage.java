package mrfinger.gothicgamemod.entity.capability;
/*
import net.minecraft.nbt.NBTTagCompound;

public class GGMExpWithPercentage implements IGGMExp
{

    protected final IGGMEntityExperienceable entity;

    protected int currentExp;
    protected int needsExpForNewLvl;
    protected int lp;


    public GGMExpWithPercentage(IGGMEntityExperienceable entity)
    {
        this.entity = entity;

        this.setExpValueFromLvl(entity.getLvl());
        this.lp = 0;
    }


    @Override
    public IGGMEntityExperienceable getEntity()
    {
        return this.entity;
    }


    @Override
    public long getExpValue()
    {
        return this.currentExp;
    }

    @Override
    public long getExpValueForNewLvl()
    {
        return this.needsExpForNewLvl;
    }

    @Override
    public int getLPValue()
    {
        return this.lp;
    }


    @Override
    public void setExpValue(int value)
    {
        if (value < 0)
        {

            int needsExpInreasesFromLvl = this.getNeedsExpInreasesFromLvl();
            int needsExpForNewLvl = this.needsExpForNewLvl;
            int decreaseLvl = 0;

            do
            {
                value += needsExpForNewLvl;
                needsExpForNewLvl -= needsExpInreasesFromLvl;
                ++decreaseLvl;
            }
            while (value < 0);

            this.currentExp = value;
            this.needsExpForNewLvl = needsExpForNewLvl + needsExpInreasesFromLvl;
            this.entity.setLvl(this.entity.getLvl() - decreaseLvl);
        }
        else if (value != this.currentExp)
        {
            if (value == 0)
            {
                this.currentExp = 0;
                return;
            }

            int needsExpInreasesFromLvl = this.getNeedsExpInreasesFromLvl();
            int needsExpForNewLvl = this.needsExpForNewLvl;

            if (value >= needsExpForNewLvl)
            {
                int addLvl = 0;

                do
                {
                    value -= needsExpForNewLvl;
                    needsExpForNewLvl += needsExpInreasesFromLvl;
                    ++addLvl;
                }
                while (value >= needsExpForNewLvl);

                this.entity.setLvl(this.entity.getLvl() + addLvl);
            }


            this.currentExp = value;
            this.needsExpForNewLvl = needsExpForNewLvl;
        }
    }

    protected int getMinimalEntityLvl()
    {
        return 0;
    }

    protected int getNeedsExpInreasesFromLvl()
    {
        return 500;
    }

    @Override
    public void changeExpValue(int changeValue)
    {
        this.setExpValue(this.currentExp + changeValue);
    }

    @Override
    public int gainExp(int exp)
    {
        if (exp < 0)
        {
            throw new IllegalArgumentException("Gaining experience cannot be less than 0");
        }
        else
        {
            int simpleLvl = this.entity.getLvl();
            this.changeExpValue(exp);
            if (this.entity.getLvl() > simpleLvl)
            {
                simpleLvl = this.entity.getLvl() - simpleLvl;
                this.changeLP(simpleLvl * this.getGainingLPFromLvl());
                return simpleLvl;
            }
        }

        return 0;
    }

    protected int getGainingLPFromLvl()
    {
        return 10;
    }

    @Override
    public long setExpValueFromLvl(int lvl, double exp)
    {
        return 0;
    }




    @Override
    public void setExpValueFromLvl(int lvl)
    {
        long toNL = this.forNewLvl * (lvl + 1);

        this.currentExp = (toNL * lvl) / 2;

        this.needsExpForNewLvl = this.currentExp + toNL;
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
    public void changeExpValue(long changeValue)
    {
        this.setExpValue(this.currentExp + changeValue);
    }

    @Override
    public void changeLP(int changeValue) {
        this.setLP(this.lp + changeValue);
    }

}*/