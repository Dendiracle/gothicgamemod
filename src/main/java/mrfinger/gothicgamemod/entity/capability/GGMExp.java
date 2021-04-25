package mrfinger.gothicgamemod.entity.capability;

import codechicken.lib.packet.PacketCustom;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.init.GGMGui;
import net.minecraft.nbt.NBTTagCompound;

public class GGMExp implements IGGMExp {


    protected final IGGMEntityExperienceable entity;

    protected final int                 forNewLvl;

    protected long                      curr, toNextLvl;

    protected int                       lpFLvl, lp;


    public GGMExp(IGGMEntityExperienceable entity, int forNewLvlExp, int lPFromLvl) {

        this.entity = entity;
        this.forNewLvl = forNewLvlExp;
        this.lpFLvl = lPFromLvl;

        this.setExpFromLvl(entity.getLvl());
        this.lp = 0;
    }


    @Override
    public void setExpFromLvl(int lvl) {

        long toNL = this.forNewLvl * (lvl + 1);

        this.curr = (toNL * lvl) / 2;

        this.toNextLvl = this.curr + toNL;
    }


    public void saveNBTData(NBTTagCompound nbt) {

        nbt.setLong("EXP", this.curr);
        nbt.setInteger("LP", this.lp);
    }

    public void loadNBTData(NBTTagCompound nbt) {

        this.setExp(nbt.getLong("EXP"));
        this.setLP(nbt.getInteger("LP"));
    }


    public void setExp(long value) {

        if (value != this.curr) {

            if (value <= 0) {
                this.curr = 0;
                this.toNextLvl = forNewLvl;
                this.entity.setLvl(0);
                return;
            }

            long fNL = this.forNewLvl;

            long tCL = 0;
            long tNL = fNL;

            int lvl = 0;

            while (value >= tCL + tNL) {

                tCL += tNL;
                tNL += fNL;
                ++lvl;
            }


            this.curr = value;

            this.entity.setLvl(lvl);

            this.toNextLvl = tCL + tNL;
        }
    }


    @Override
    public void setLP(int lp) {

        if (lp != this.lp) {

            this.lp = lp > 0 ? lp : 0;
            this.entity.flagForLvlUpdate();
        }
    }


    @Override
    public void changeExp(long changeValue) {

        this.setExp(this.curr + changeValue);
    }

    @Override
    public void changeLP(int changeValue) {
        this.setLP(this.lp + changeValue);
    }

    @Override
    public boolean gainExp(int exp) {

        if (exp > 0) {

            int simpleLvl = this.entity.getLvl();
            this.changeExp(exp);
            if (this.entity.getEntityWorld().isRemote) {

                GGMGui.guiInGame.addGainedExp(exp);
            }
            if (this.entity.getLvl() > simpleLvl) {

                this.changeLP((this.entity.getLvl() - simpleLvl) * this.lpFLvl);
                return true;
            }
        }

        return false;
    }

    @Override
    public long getExp() {
        return curr;
    }

    @Override
    public long getAllNextLvlExp() {
        return this.toNextLvl;
    }

    @Override
    public int getLP() {
        return this.lp;
    }

}