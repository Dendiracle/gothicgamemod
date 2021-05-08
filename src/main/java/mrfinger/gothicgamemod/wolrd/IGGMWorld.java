package mrfinger.gothicgamemod.wolrd;

import mrfinger.gothicgamemod.entity.packentities.PackEntity;
import mrfinger.gothicgamemod.entity.packentities.IEntityHerd;
import mrfinger.gothicgamemod.fractions.PackFraction;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public interface IGGMWorld {


    boolean isRemote();


    PackEntity createNewPack();

    PackEntity createNewPack(PackFraction fraction);

    PackEntity findRightPack(IEntityHerd entity);

    void addPack(PackEntity entity);


    List getEntitiesWithinAABB(Class clas, AxisAlignedBB aabb);

}
