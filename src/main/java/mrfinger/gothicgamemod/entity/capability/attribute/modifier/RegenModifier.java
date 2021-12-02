package mrfinger.gothicgamemod.entity.capability.attribute.modifier;

public class RegenModifier implements Comparable
{

    protected final String unlocolizedName;

    protected final int operationType;


    public RegenModifier(String unlocolizedName, int operationType)
    {
        this.unlocolizedName = unlocolizedName;
        this.operationType = operationType;
    }


    public String getUnlocolizedName()
    {
        return unlocolizedName;
    }

    public int getOperationType()
    {
        return operationType;
    }


    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof RegenModifier && this.unlocolizedName.equals(((RegenModifier) obj).getUnlocolizedName());
    }


    @Override
    public int compareTo(Object o)
    {
        return o instanceof RegenModifier ? (this.operationType - ((RegenModifier) o).getOperationType()) : o instanceof Integer ? this.operationType - (Integer) o : 0;
    }
}
