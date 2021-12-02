package mrfinger.gothicgamemod.network.play.server;

public class EntityDynamicProperties {


    private final String unlName;

    private final double currentValue;

    private final float regenValue;


    public EntityDynamicProperties(String unlocalizedName, double currentValue, float regenValue) {
        this.unlName = unlocalizedName;
        this.currentValue = currentValue;
        this.regenValue = regenValue;
    }


    public String getUnlocalizedName() {
        return unlName;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public float getRegenValue() {
        return regenValue;
    }
}
