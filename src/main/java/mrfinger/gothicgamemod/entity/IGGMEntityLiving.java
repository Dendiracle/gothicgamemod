package mrfinger.gothicgamemod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

public interface IGGMEntityLiving extends IGGMEntityLivingBase
{

    EntityLookHelper getLookHelper();

    EntityMoveHelper getMoveHelper();

    EntityJumpHelper getJumpHelper();

    PathNavigate getNavigator();


    default void setPath(PathEntity path)
    {
        this.getNavigator().setPath(path, 1.0D);
    }

    default void setPath(int x, int y, int z)
    {
        this.getNavigator().tryMoveToXYZ(x, y, z, 1.0D);
    }

    default void setPathToEntity(IGGMEntity entity)
    {
        this.getNavigator().tryMoveToEntityLiving((Entity) entity, 1.0D);
    }

    default void cleartPath()
    {
        this.getNavigator().clearPathEntity();
    }


    default ItemStack getHelDItem()
    {
        return null;
    }


    EntityLivingBase getAttackTarget();

    void setAttackTarget(EntityLivingBase entity);


    int getVerticalFaceSpeed();


    default boolean isNeedWakeUp()
    {
        return false;
    }

    default boolean isNeedGoToSleep()
    {
        return false;
    }

    default void goToSleep() {}

}
