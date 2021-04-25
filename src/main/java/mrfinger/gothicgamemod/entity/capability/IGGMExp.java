package mrfinger.gothicgamemod.entity.capability;

public interface IGGMExp extends ICapabilitySaveable {


    void setExp(long value);

    void setExpFromLvl(int lvl);

    void setLP(int lP);


    void changeExp(long changeValue);

    void changeLP(int changeValue);

    boolean gainExp(int exp);


    long getExp();

    long getAllNextLvlExp();

    int getLP();

}