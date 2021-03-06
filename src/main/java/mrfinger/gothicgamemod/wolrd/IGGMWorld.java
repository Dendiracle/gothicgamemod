package mrfinger.gothicgamemod.wolrd;

import mrfinger.gothicgamemod.entity.HabitatsCollection;
import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.entity.packentities.IPackEntity;
import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.fractions.PackFraction;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IGGMWorld {


    boolean isRemote();


    HabitatsCollection getHabitatsCollection();


    List getEntitiesWithinAABB(Class clas, AxisAlignedBB aabb);


    PathEntity getEntityPathToXYZ(Entity p_72844_1_, int p_72844_2_, int p_72844_3_, int p_72844_4_, float p_72844_5_, boolean p_72844_6_, boolean p_72844_7_, boolean p_72844_8_, boolean p_72844_9_);


    Block getBlock(int x, int y, int z);

    boolean setBlock(int x, int y, int z, Block block, int metadata, int p_147465_6_);

    boolean setBlock(int x, int y, int z, Block block);
}
