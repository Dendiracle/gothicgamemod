package mrfinger.gothicgamemod.entity.capability.attributes;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.*;

public class GGMDynamicAttributeInstance extends GGMIncreasableAttributeInstance implements IGGMDynamicAttributeInstance {


    protected double 	currentValue;

    protected float     naturalRegen;

    protected float     cachedRegenValue;

    protected boolean   needRegenUpdate;

    protected SortedMap<Integer, RegenModifier> regenModifiersMap = new TreeMap<>();


    public GGMDynamicAttributeInstance(IGGMBaseAttributeMap map, IGGMAttribute attribute, GGMDPAttributeInfo attributeInfo) {
        super(map, attribute, attributeInfo);

        this.setNaturalRegen(attributeInfo.defaultNaturalRegenValue);
        this.needRegenUpdate = true;
    }


    @Override
    public GGMDPAttributeInfo getAttributeInfo() {
        return (GGMDPAttributeInfo) super.getAttributeInfo();
    }

    @Override
    protected void flagForUpdate() {
        super.flagForUpdate();

        this.flagForRegenUpdate();
    }

    protected void flagForRegenUpdate() {

        this.needRegenUpdate = true;
    }


    @Override
    public double getCurrentValue() {
        return this.currentValue;
    }

    @Override
    public float getNaturalRegen() {
        return this.naturalRegen;
    }

    /*
     * Returns regular regeneration value per half second.
     */
    @Override
    public float getCachedRegenValue() {

        if (this.needRegenUpdate) {

            this.updateRegen();
            this.needRegenUpdate = false;
        }

        return this.cachedRegenValue;
    }


    @Override
    public float getDefaultNaturalRegenValue() {
        return this.getAttributeInfo().defaultNaturalRegenValue;
    }

    @Override
    public float getMaxNaturalRegenValue() {
        return this.getAttributeInfo().maxNaturalRegenValue;
    }

    @Override
    public float getNaturalRegenIncreasingValue() {
        return this.getAttributeInfo().naturalRegenIncreasingValue;
    }


    public float getValueAfterNaturalRegenIncreasingValueDecreases() {
        return this.getAttributeInfo().valueAfterNaturalRegenIncreasingValueDecreases;
    }

    public byte getMaxNaturalRegenIncreasesNeedLP() {
        return this.getAttributeInfo().maxNaturalRegenIncreasesNeedLP;
    }


    @Override
    public void applyModifier(AttributeModifier attributeModifier) {

        double pre = this.getAttributeValue();
        super.applyModifier(attributeModifier);

        this.setCurrentValue(this.currentValue * (this.getAttributeValue() / pre));
    }

    @Override
    public void removeModifier(AttributeModifier p_111124_1_) {

        double pre = this.getAttributeValue();
        super.removeModifier(p_111124_1_);

        this.setCurrentValue(this.currentValue * (this.getAttributeValue() / pre));
    }


    @Override
    public void setCurrentValue(double value) {

        if (this.currentValue != value) {

            double attributeValue = this.getAttributeValue();
            this.currentValue = value < 0.0D ? 0.0D : ((value >= attributeValue) ? attributeValue : value);
            ((IGGMBaseAttributeMap) this.attributeMap).addDPToUpdate(this);
        }
    }

    @Override
    public void changeCurrentValue (double changeValue) {
        this.setCurrentValue(this.currentValue + changeValue);
    }

    @Override
    public void restore() {
        this.setCurrentValue(this.getAttributeValue());
    }

    @Override
    public void setNaturalRegen(float value) {

        if (this.naturalRegen != value) {
            this.naturalRegen = value < 0.0F ? 0.0F : value;
            this.flagForRegenUpdate();
            ((IGGMBaseAttributeMap) this.attributeMap).addDPToUpdate(this);
        }
    }

    @Override
    public void changeNaturalRegen(float value) {
        this.setNaturalRegen(this.naturalRegen + value);
    }


    @Override
    public float increaseNaturalRegen(float increasingValue) {

        final float mv = this.getBaseValue() >= this.getMaxValue() ? this.getMaxNaturalRegenValue() * (float) this.getMaxValue() : this.getMaxNaturalRegenValue() * (float) this.getBaseValue();
        float bv = this.getNaturalRegen();

        if (bv >= mv) return 0.0F;

        increasingValue = this.calculateIncreasingRegenValue(increasingValue);
        {
            float difference = mv - bv;

            if ((difference - increasingValue) < increasingValue / 2.0D) {

                this.setNaturalRegen(mv);
                return difference;
            }
        }

        this.changeNaturalRegen(increasingValue);
        return increasingValue;
    }

    @Override
    public int increaseNaturalRegen(int amount)
    {
        final boolean flag = this.getBaseValue() < this.getMaxValue();
        final float mv = flag ? this.getMaxNaturalRegenValue() * (float) this.getBaseValue() : this.getMaxNaturalRegenValue() * (float) this.getMaxValue();
        float bv = this.getNaturalRegen();

        if (bv >= mv) return 0;

        float iv = this.getNaturalRegenIncreasingValue();
        float step = this.getValueAfterNaturalRegenIncreasingValueDecreases();
        int separator = 1;

        if (bv >= step) {

            while (bv >= step) {

                ++separator;
                step += this.getValueAfterNaturalRegenIncreasingValueDecreases();
            }

            iv = this.getNaturalRegenIncreasingValue() / separator;
        }

        if (mv - bv < iv / 2.0F) {
            this.setNaturalRegen(mv);
            return 0;
        }

        if (flag && bv + iv > mv) return 0;

        int i = 1;

        for (; i <= amount; ++i) {

            bv += iv;

            if (bv >= step) {

                while (bv >= step) {

                    ++separator;
                    bv -= (bv - step) / separator;
                    step += this.getValueAfterNaturalRegenIncreasingValueDecreases();
                }

                iv = this.getNaturalRegenIncreasingValue() / separator;
            }

            if (flag) {
                if (bv + iv > mv) {
                    break;
                }
            }
            else if (mv - bv < iv / 2.0F) {
                this.setNaturalRegen(mv);
                return i;
            }
        }

        this.setNaturalRegen(bv);
        return i;
    }


    @Override
    public float calculateIncreasingRegenValue(float value) {
        return (float) calculateIncreasingValueWithArgs(value, this.getNaturalRegen(), this.getValueAfterNaturalRegenIncreasingValueDecreases(), this.getMaxNaturalRegenIncreasesNeedLP());
    }

    @Override
    public float calculateIncreasingRegenValueWithAdded(float added, float value) {
        return (float) calculateIncreasingValueWithArgs(value, this.getNaturalRegen() + added, this.getValueAfterNaturalRegenIncreasingValueDecreases(), this.getMaxNaturalRegenIncreasesNeedLP());
    }

    @Override
    public RegenModifier getRegenModifier(int id, int operation, boolean isSaved) {

        operation <<= 1;
        if (isSaved) operation += 1;
        id = (operation << 24) + id;

        return this.getRegenModifierByFullID(id);
    }

    @Override
    public RegenModifier getRegenModifierByFullID(int id) {
        return this.regenModifiersMap.get(Integer.valueOf(id));
    }

    @Override
    public RegenModifier applyRegenModifier(RegenModifier modifier) {

        RegenModifier toReturn = this.regenModifiersMap.put(Integer.valueOf(modifier.getFullID()), modifier);
        this.flagForRegenUpdate();

        return toReturn;
    }

    @Override
    public RegenModifier removeRegenModifier(int id, int operation, boolean isSaved) {

        operation <<= 1;
        if (isSaved) operation += 1;
        id = (operation << 24) + id;

        return this.removeRegenModifierByFullID(id);
    }

    @Override
    public RegenModifier removeRegenModifierByFullID(int id) {

        RegenModifier toReturn = this.regenModifiersMap.remove(Integer.valueOf(id));
        this.flagForRegenUpdate();

        return toReturn;
    }

    @Override
    public RegenModifier removeRegenModifier(RegenModifier modifier) {
        return this.removeRegenModifierByFullID(modifier.getFullID());
    }

    @Override
    public void onUpdate() {
        this.changeCurrentValue(this.getCachedRegenValue());
    }


    @Override
    public void updateRegen() {

        float regen = this.getNaturalRegen();
        SortedMap<Integer, RegenModifier> operandMap = this.regenModifiersMap.headMap(1 << 25);

        for (RegenModifier regenModifier : operandMap.values()) {

            regen += regenModifier.getAmount();
        }

        operandMap = this.regenModifiersMap.subMap(1 << 25, 2 << 25);
        double attributeValue = this.getAttributeValue();

        for (RegenModifier regenModifier : operandMap.values())
        {
            regen += regenModifier.getAmount() * attributeValue;
        }

        operandMap = this.regenModifiersMap.tailMap(2 << 25);

        for (RegenModifier regenModifier : operandMap.values())
        {
            regen *= 1.0F + regenModifier.getAmount() ;
        }

        this.cachedRegenValue = regen / 2.0F;

        ((IGGMBaseAttributeMap) this.attributeMap).addDPToUpdate(this);
    }


    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        super.saveNBTData(nbt);

        nbt.setDouble("Curr", this.getCurrentValue());
        nbt.setFloat("NReg", this.getNaturalRegen());

        if (!this.regenModifiersMap.isEmpty()) {

            NBTTagList list = new NBTTagList();

            for (RegenModifier rm : this.regenModifiersMap.values()) {

                NBTTagCompound rmCompound = new NBTTagCompound();
                rm.saveNBTData(rmCompound);
                list.appendTag(rmCompound);
            }

            nbt.setTag("RegModifiers", list);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        super.loadNBTData(nbt);

        this.setCurrentValue(nbt.getDouble("Curr"));
        this.setNaturalRegen(nbt.getFloat("NReg"));

        if (nbt.hasKey("RegModifiers", 9)) {

            NBTTagList nbttaglist = nbt.getTagList("RegModifiers", 10);
            int size = nbttaglist.tagCount();

            for (int i = 0; i < size; ++i) {

                this.applyRegenModifier(new RegenModifier(nbt));
            }
        }
    }

}

