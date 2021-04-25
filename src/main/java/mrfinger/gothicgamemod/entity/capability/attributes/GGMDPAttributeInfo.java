package mrfinger.gothicgamemod.entity.capability.attributes;

public class GGMDPAttributeInfo extends GGMIncreasableAttributeInfo {


    protected final float   defaultNaturalRegenValue;

    protected final float        maxNaturalRegenValue;

    protected final float 	naturalRegenIncreasingValue;

    protected final float	valueAfterNaturalRegenIncreasingValueDecreases;

    protected final byte 	maxNaturalRegenIncreasesNeedLP;


    public GGMDPAttributeInfo(float defaultValue, double maxValue, float increasingValue, float valueAfterIncreasingValueDecreases, byte maxIncreasesNeedLP, float defaultNaturalRegenValue, float maxNaturalRegenValue, float naturalRegenIncreasingValue, float valueAfterNaturalRegenIncreasingValueDecreases, byte maxNaturalRegenIncreasesNeedLP) {
        super(defaultValue, maxValue, increasingValue, valueAfterIncreasingValueDecreases, maxIncreasesNeedLP);

        this.defaultNaturalRegenValue = defaultNaturalRegenValue;
        this.maxNaturalRegenValue = maxNaturalRegenValue;
        this.naturalRegenIncreasingValue = naturalRegenIncreasingValue;
        this.valueAfterNaturalRegenIncreasingValueDecreases = valueAfterNaturalRegenIncreasingValueDecreases;
        this.maxNaturalRegenIncreasesNeedLP = maxNaturalRegenIncreasesNeedLP;
    }


    public float getDefaultNaturalRegenValue() {
        return defaultNaturalRegenValue;
    }

    public float getMaxNaturalRegenValue() {
        return maxNaturalRegenValue;
    }

    public float getNaturalRegenIncreasingValue() {
        return naturalRegenIncreasingValue;
    }

    public float getValueAfterNaturalRegenIncreasingValueDecreases() {
        return valueAfterNaturalRegenIncreasingValueDecreases;
    }

    public byte getMaxNaturalRegenIncreasesNeedLP() {
        return maxNaturalRegenIncreasesNeedLP;
    }

}
