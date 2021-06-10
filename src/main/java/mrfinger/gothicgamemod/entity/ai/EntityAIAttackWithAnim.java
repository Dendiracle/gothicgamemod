package mrfinger.gothicgamemod.entity.ai;

import mrfinger.gothicgamemod.entity.capability.data.IGGMEntityWithAttackAnim;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIAttackWithAnim extends EntityAIBase
{

    IGGMEntityWithAttackAnim entity;


    public EntityAIAttackWithAnim(IGGMEntityWithAttackAnim entty)
    {
        this.entity = entty;
    }


    @Override
    public boolean shouldExecute()
    {
        if (this.entity.getEntityToAttack() != null && this.entity.getEntityToAttack().isEntityAlive())
        {
            return true;
        }

        return false;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
    }
}
