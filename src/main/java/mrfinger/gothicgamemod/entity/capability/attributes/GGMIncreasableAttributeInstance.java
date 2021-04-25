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

    /*@Override
    public int increaseAttribute(int amount) {

        double bv = this.getBaseValue();
        double mv = this.getMaxValue();

        if (bv >= mv) return 0;

        float iv = this.getIncreasingValue();
        double increasingValue = this.calculateIncreasingValueWithArgs(iv);

        if (mv - (bv + increasingValue) < increasingValue / 2.0D) {

            this.setBaseValue(mv);
            return 0;
        }

        float increaseValue = 0.0F;

        for (int i = 1; i <= amount; ++i) {

            increaseValue += this.calculateIncreasingValueWithAdded(increaseValue, iv);

            if (mv - (bv + increaseValue) < 0.001D) {
                this.setBaseValue(mv);
                return i;
            }
        }

        this.setBaseValue(bv + increaseValue);

        return amount;
    }*/

    /*public static double calculateIncreasingValueWithAdded(double added, double increaseValue, double baseValue, float step, int maxIncreasesNeedLP) {

        if (step <= 0.0F) return increaseValue;

        baseValue += added;

        if (baseValue >= step * maxIncreasesNeedLP) {
            increaseValue /= (this.getMaxIncreasesNeedLP() + 1);
            return increaseValue;
        }

        int separator = 1;
        while (valueWithAdded >= step) {
            separator++;
            step += this.getValueAfterIncreasingValueDecreases();
        }

        increaseValue /= separator;

        while ((valueWithAdded + increaseValue) > step) {
            separator++;
            increaseValue -= (((valueWithAdded + increaseValue) - step) / separator);
        }

        return increaseValue;
    }*/

    /*@Override
    public double calculateIncreasingValueWithArgs(double increaseValue) {

        float step = this.getValueAfterIncreasingValueDecreases();

        if (step == 0.0F) return increaseValue;

        if (this.getBaseValue() >= step * this.getMaxIncreasesNeedLP()) {
            increaseValue /= (this.getMaxIncreasesNeedLP() + 1);
            return increaseValue;
        }

        int separator = 1;
        while (this.getBaseValue() >= step) {
            separator++;
            step += this.getValueAfterIncreasingValueDecreases();
        }

        increaseValue /= separator;

        while ((this.getBaseValue() + increaseValue) > step) {
            separator++;
            increaseValue -= (((this.getBaseValue() + increaseValue) - step) / separator);
        }

        return increaseValue;
    }*/

    @Override
    public double calculateIncreasingValue(double value) {
        return calculateIncreasingValueWithArgs(value, this.getBaseValue(), this.getValueAfterIncreasingValueDecreases(), this.getMaxIncreasesNeedLP());
    }

    @Override
    public double calculateIncreasingValueWithAdded(double added, double value) {

        return calculateIncreasingValueWithArgs(value, this.getBaseValue() + added, this.getValueAfterIncreasingValueDecreases(), this.getMaxIncreasesNeedLP());
    }

    /*@Override
    public double calculateIncreasingValueWithAdded(double added, double value) {


        float step = this.getValueAfterIncreasingValueDecreases();

        if (step == 0.0F) return value;

        double valueWithAdded = this.getBaseValue() + added;

        if (valueWithAdded >= step * this.getMaxIncreasesNeedLP()) {
            value /= (this.getMaxIncreasesNeedLP() + 1);
            return value;
        }

        int separator = 1;
        while (valueWithAdded >= step) {
            separator++;
            step += this.getValueAfterIncreasingValueDecreases();
        }

        value /= separator;

        while ((valueWithAdded + value) > step) {
            separator++;
            value -= (((valueWithAdded + value) - step) / separator);
        }

        return value;
    }*/


    public static double calculateIncreasingValueWithArgs(double increaseValue, double baseValue, float step, int maxIncreasesNeedLP) {

        if (step <= 0.0F) return increaseValue;

        if (baseValue >= step * maxIncreasesNeedLP) {
            increaseValue /= maxIncreasesNeedLP + 1;
            return increaseValue;
        }

        int separator = 1;
        float valueAfterIncreasingValueDecreases = step;

        while (baseValue >= step) {
            ++separator;
            step += valueAfterIncreasingValueDecreases;
        }

        increaseValue /= separator;

        while ((baseValue + increaseValue) > step) {
            ++separator;
            increaseValue -= (((baseValue + increaseValue) - step) / separator);
        }

        return increaseValue;
    }


    @Override
    public void saveNBTData(NBTTagCompound nbt) {}

    @Override
    public void loadNBTData(NBTTagCompound nbt) {}

}