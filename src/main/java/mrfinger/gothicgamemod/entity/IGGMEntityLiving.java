package mrfinger.gothicgamemod.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.pathfinding.PathNavigate;

public interface IGGMEntityLiving extends IGGMEntityLivingBase
{

    EntityLookHelper getLookHelper();

    EntityMoveHelper getMoveHelper();

    EntityJumpHelper getJumpHelper();

    PathNavigate getNavigator();


    default void onNewAIFinishedPath() {}


    EntityLivingBase getAttackTarget();

    void setAttackTarget(EntityLivingBase entity);


    int getVerticalFaceSpeed();

}
