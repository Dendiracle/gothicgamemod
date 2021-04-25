package mrfinger.gothicgamemod.entity.capability.attributes;

import mrfinger.gothicgamemod.entity.capability.ICapabilitySaveable;
import net.minecraft.nbt.NBTTagCompound;

public class RegenModifier implements Comparable<RegenModifier>, ICapabilitySaveable {


    private static int idCount = 1;


    float amount;

    int operations;


    public RegenModifier(int id, int operation, boolean isSaved, float amount) {

        this.amount = amount;
        if (id >= 16777215 || id < 0) throw new IllegalArgumentException("Id of Regen Modifier can't be higher than 8388608 or less than 0");
        if (operation > 63 || operation < 0) throw new IllegalArgumentException("Operaion of Regen Modifier can't be higher than 63 or less than 0");
        operation <<= 1;
        if (isSaved) operation += 1;
        this.operations = (operation << 24) + id;
    }

    public RegenModifier(int operation, boolean isSaved, float amount) {
        this(idCount++, operation, isSaved, amount);
    }

    public RegenModifier(int operation, float amount) {
        this(operation, true, amount);
    }

    public RegenModifier(NBTTagCompound nbt) {

        this.loadNBTData(nbt);
    }


    public int getID() {
        return this.operations & 16777215;
    }

    public int getFullID() {
        return this.operations;
    }

    public int getOperation() {
        return (this.operations >> 25 ) & 63;
    }

    public boolean isSaved() {
        return (this.operations & 16777216) == 1;
    }

    public float getAmount() {
        return amount;
    }


    @Override
    public int compareTo(RegenModifier o) {
        return Integer.compare(this.getFullID(), o.getFullID());
    }


    @Override
    public String toString() {
        return this.getClass() + "/" + this.getFullID() + "/" + this.getAmount();
    }


    @Override
    public void saveNBTData(NBTTagCompound nbt) {

        if (this.isSaved()) {
            nbt.setInteger("Ops", this.operations);
            nbt.setFloat("Amount", this.amount);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {

        this.operations = nbt.getInteger("Ops");
        this.amount = nbt.getFloat("Amount");
    }

}
