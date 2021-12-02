package mrfinger.gothicgamemod.battle;

public class DamageType {


    private final String unlocalizedName;


    public DamageType(String unlocalizedName) {

        this.unlocalizedName = unlocalizedName;
    }


    public String getUnlocalizedName() {

        return this.unlocalizedName;
    }


    @Override
    public String toString() {
        return this.getClass() + ": " + this.unlocalizedName;
    }
}
