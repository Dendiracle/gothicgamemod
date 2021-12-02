package mrfinger.gothicgamemod.entity;

import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.wolrd.IGGMWorld;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

import java.util.*;

public class HabitatsCollection extends WorldSavedData
{

    private IGGMWorld world;

    private int tickCounter;

    private int count;
    protected List<IPackEntity>[] packListsArray;
    protected Map<Fraction, Collection<IPackEntity>> fractionPackMap;
    protected Map<UUID, IPackEntity> packMapByID;


    public HabitatsCollection(String mapName)
    {
        super(mapName);

        this.packListsArray = new List[10];

        for (int i = 0; i < packListsArray.length; ++i)
        {
            packListsArray[i] = new ArrayList<>();
        }

        this.fractionPackMap = new HashMap<>(GGMFractions.fractionsMap.size(), 1F);

        for (Fraction fraction : GGMFractions.fractionsMap.values())
        {
            this.fractionPackMap.put(fraction, new LinkedList<>());
        }

        this.packMapByID = new HashMap<>();
    }

    public HabitatsCollection(IGGMWorld world)
    {
        this("gothic-habitats");

        this.world = world;
        this.markDirty();
    }


    public void setWorld(IGGMWorld world)
    {
        this.world = world;

        for (IPackEntity pack : this.packMapByID.values())
        {
            pack.setWorld(world);
        }
    }


    public void tick()
    {
        int index = this.tickCounter++ % 10;

        Iterator<IPackEntity> iterator = this.packListsArray[index].iterator();

        while (iterator.hasNext())
        {
            IPackEntity pack = iterator.next();
            pack.onUpdatePackAI();
            if (pack.isPackToRemove())
            {
                iterator.remove();
                this.fractionPackMap.get(pack.getFraction()).remove(pack);
                this.packMapByID.remove(pack.getId());
                this.markDirty();
            }
        }
    }


    public Map<UUID, IPackEntity> getPackMapByID()
    {
        return packMapByID;
    }

    public IPackEntity createNewPack(PackFraction fraction)
    {
        IPackEntity pack = fraction.getNewEntityPack(this.world);
        this.addPack(pack);
        return pack;
    }


    public void addPack(IPackEntity pack)
    {
        if (this.count >= packListsArray.length) this.count = 0;
        packListsArray[this.count++].add(pack);

        this.fractionPackMap.computeIfAbsent(pack.getFraction(), k -> new LinkedList<>()).add(pack);
        this.packMapByID.put(pack.getId(), pack);

        this.markDirty();
    }

    public void removePack(IPackEntity pack)
    {
        for (List l : packListsArray)
        {
            if (l.remove(pack)) break;
        }

        this.fractionPackMap.computeIfAbsent(pack.getFraction(), k -> new LinkedList<>()).remove(pack);
        this.packMapByID.remove(pack.getId());

        this.markDirty();
    }


    public IPackEntity findRightPack(IEntityHerd entity)
    {
        return this.findRightPack(entity.getCurrentFraction(), entity.getPosX(), entity.getPosY(), entity.getPosZ());
    }

    public IPackEntity findRightPack(PackFraction fraction, double posX, double posY, double posZ)
    {
        Collection<IPackEntity> collection = this.fractionPackMap.get(fraction);

        if (collection != null)
        {
            for (IPackEntity pack : collection)
            {
                float needDistanceSQ = pack.getMaxRangeToMembers();
                needDistanceSQ *= needDistanceSQ;

                if (pack.getDistanceSQ(posX, posY, posZ) <= needDistanceSQ)
                {
                    return pack;
                }
            }
        }

        IPackEntity pack = this.createNewPack(fraction);
        pack.setPos(posX, posY, posZ);
        return pack;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        this.tickCounter = compound.getInteger("Tick");
        NBTTagList habitatsList = compound.getTagList("Gothic_Habitats", 10);

        for (int i = 0; i < habitatsList.tagCount(); i++)
        {
            NBTTagCompound habCompound = habitatsList.getCompoundTagAt(i);
            Fraction fraction = GGMFractions.fractionsMap.get(habCompound.getString("Fraction"));

            if (fraction instanceof PackFraction)
            {
                this.addPack(((PackFraction) fraction).getPackEntityFromNBT(this.world, habCompound));
            }
            else
            {

            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Tick", this.tickCounter);

        NBTTagList habitatsList = new NBTTagList();

        for (IPackEntity pack : this.packMapByID.values())
        {
            NBTTagCompound habCompound = new NBTTagCompound();
            habCompound.setString("Fraction", pack.getFraction().getUnlocalizedName());
            pack.getFraction().writePackToNBT(pack, habCompound);
            habitatsList.appendTag(habCompound);
        }

        compound.setTag("Gothic_Habitats", habitatsList);
    }
}
