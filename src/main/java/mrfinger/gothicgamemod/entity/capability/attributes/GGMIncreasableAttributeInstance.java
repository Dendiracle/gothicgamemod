package mrfinger.gothicgamemod.entity.capability.attributes;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;

public class GGMIncreasableAttributeInstance extends ModifiableAttributeInstance implements IGGMIncreasableAttributeInstance {


    protected final GGMIncreasableAttributeInfo attributeInfo;


    public GGMIncreasableAttributeInstance(IGGMBaseAttributeMap attributeMap, IGGMAttribute attribute, GGMIncreasableAttributeInfo attributeInfo) {
        super((BaseAttributeMap) attributeMap, attribute);

        this.attributeInfo = attributeInfo;
        this.setBaseValue(attributeInfo.defaultValue);

        if (this.isBonus()) {

            UUID id = ((IGGMAttribute) this.genericAttribute).getModifierID();
            Map<IGGMAttribute, Float> map = ((IGGMAttribute) this.genericAttribute).getAttributeModifiersMap();
            double thisValue = this.getAttributeValue();

            for (Map.Entry<IGGMAttribute, Float> e : map.entrySet()) {

                this.attributeMap.getAttributeInstance(e.getKey()).applyModifier(new AttributeModifier(id, "BonusFromStat", thisValue * e.getValue(), 0).setSaved(false));
            }
        }
    }


    @Override
    public GGMIncreasableAttributeInfo getAttributeInfo() {
        return attributeInfo;
    }

    @Override
    public boolean isBonus() {
        return ((IGGMAttribute) this.genericAttribute).getAttributeModifiersMap() != null;
    }

    @Override
    public boolean setBonuses() {

        if (this.isBonus()) {

            UUID id = ((IGGMAttribute) this.genericAttribute).getModifierID();
            Map<IGGMAttribute, Float> map = ((IGGMAttribute) this.genericAttribute).getAttributeModifiersMap();
            double thisValue = this.getAttributeValue();

            for (Map.Entry<IGGMAttribute, Float> e : map.entrySet()) {

                ((IGGMModifiableAttributeInstance) this.attributeMap.getAttributeInstance(e.getKey())).setModifierAmount(id,thisValue * e.getValue());
            }

            return true;
        }

        return false;
    }


    @Override
    protected void flagForUpdate() {
        super.flagForUpdate();

        this.setBonuses();
    }


    @Override
    public void changeBaseValue(double changeValue) {
        this.setBaseValue(this.getBaseValue() + changeValue);
    }


    @Override
    public float getDefaultValue() {
        return this.attributeInfo.defaultValue;
    }

    @Override
    public double getMaxValue() {
        return this.attributeInfo.maxValue;
    }

    @Override
    public float getIncreasingValue() {
        return this.attributeInfo.increasingValue;
    }

    @Override
    public float getValueAfterIncreasingValueDecreases() {
        return this.attributeInfo.valueAfterIncreasingValueDecreases;
    }

    @Override
    public byte getMaxIncreasesNeedLP() {
        return this.attributeInfo.maxIncreasesNeedLP;
    }


    @Override
    public double increaseAttribute(double increasingValue) {

        double mv = this.getMaxValue();
        double bv = this.getBaseValue();

        if (bv >= mv) return 0.0F;

        double difference = mv - bv;
        increasingValue = this.calculateIncreasingValue(increasingValue);

        if ((difference - increasingValue) < increasingValue / 2.0D) {

            this.setBaseValue(mv);
            return difference;
        }

        this.changeBaseValue(increasingValue);
        return increasingValue;
    }

    @Override
    public int increaseAttribute(int amount) {

        double bv = this.getBaseValue();
        final double mv = this.getMaxValue();

        if (bv >= mv) return 0;

        float iv = this.getIncreasingValue();
        float step = this.getValueAfterIncreasingValueDecreases();
        int separator = 1;

        if (bv >= step) {

            while (bv >= step) {

                ++separator;
                step += this.getValueAfterIncreasingValueDecreases();
            }

            iv = this.getIncreasingValue() / separator;
        }

        if (mv - bv < iv / 2.0F) {
            this.setBaseValue(mv);
            return 0;
        }

        for (int i = 1; i <= amount; ++i) {

            bv += iv;

            if (bv >= step) {

                while (bv >= step) {

                    ++separator;
                    bv -= (bv - step) / separator;
                    step += this.getValueAfterIncreasingValueDecreases();
                }

                iv = this.getIncreasingValue() / separator;
            }

            if (mv - bv < iv / 2.0F) {
                this.setBaseValue(mv);
                return i;
            }
        }

        this.setBaseValue(bv);
        return amount;
    }


    @Override
    public double calculateIncreasingValue(double value)
    {
        return calculateIncreasingValueWithArgs(value, this.getBaseValue(), this.getValueAfterIncreasingValueDecreases(), this.getMaxIncreasesNeedLP());
    }

    @Override
    public double calculateIncreasingValueWithAdded(double added, double value)
    {
        return calculateIncreasingValueWithArgs(value, this.getBaseValue() + added, this.getValueAfterIncreasingValueDecreases(), this.getMaxIncreasesNeedLP());
    }


    @Override
    public double calculateDecreasingValue(double value) {
        return calculateDecreasingValueWithArgs(value, this.getBaseValue(), this.getValueAfterIncreasingValueDecreases(), this.getMaxIncreasesNeedLP());
    }

    @Override
    public double calculateDecreasingValueWithAdded(double added, double value) {
        return calculateDecreasingValueWithArgs(value, this.getBaseValue() + added, this.getValueAfterIncreasingValueDecreases(), this.getMaxIncreasesNeedLP());
    }


    public static double calculateIncreasingValueWithArgs(double increaseValue, final double baseValue, float step, final int maxIncreasesNeedLP)
    {

        if (step <= 0.0F) return increaseValue;

        if (baseValue >= step * maxIncreasesNeedLP)
        {
            increaseValue /= maxIncreasesNeedLP + 1;
            return increaseValue;
        }

        int separator = 1;
        final float valueAfterIncreasingValueDecreases = step;

        while (baseValue >= step)
        {
            ++separator;
            step += valueAfterIncreasingValueDecreases;
        }

        increaseValue /= separator;

        while ((baseValue + increaseValue) > step)
        {
            ++separator;
            increaseValue -= (((baseValue + increaseValue) - step) / separator);
            step += valueAfterIncreasingValueDecreases;
        }

        return increaseValue;
    }

    public static double calculateDecreasingValueWithArgs(double increaseValue, final double baseValue, float step, final int maxIncreasesNeedLP)
    {
        if (step <= 0.0F || baseValue < step) return increaseValue;

        int separator = 1;
        final float valueAfterIncreasingValueDecreases = step;

        while (baseValue >= step)
        {
            ++separator;
            step += valueAfterIncreasingValueDecreases;
        }

        increaseValue /= separator;

        while ((baseValue - increaseValue) < step)
        {
            --separator;
            increaseValue += (step - baseValue) / separator;
            step -= valueAfterIncreasingValueDecreases;
        }

        return increaseValue;
    }


    @Override
    public void saveNBTData(NBTTagCompound nbt) {}

    @Override
    public void loadNBTData(NBTTagCompound nbt) {}

}