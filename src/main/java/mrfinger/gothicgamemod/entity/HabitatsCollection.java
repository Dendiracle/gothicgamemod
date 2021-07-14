package mrfinger.gothicgamemod.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class HabitatsCollection extends WorldSavedData
{

    private World world;

    private int tickCounter;

    private int count;


    public HabitatsCollection(String mapName)
    {
        super(mapName);
    }

    public HabitatsCollection(World world)
    {
        super("gothic-habitats");

        this.world = world;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {

    }

    @Override
    public void writeToNBT(NBTTagCompound p_76187_1_) {

    }
}
