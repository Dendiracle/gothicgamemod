package mrfinger.gothicgamemod.entity.effect;

import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.effect.instance.IGGMEffectInstance;

import java.lang.reflect.Type;

public abstract class AbstractGGMEffectManager<Entity extends IGGMEntityLivingBase> implements IGGMEffectManager<Entity>
{

    protected final int id;
    protected final String unlocalizedName;


    public AbstractGGMEffectManager(int id, String unlocalizedName)
    {
        this.id = id;
        this.unlocalizedName = unlocalizedName;
    }


    @Override
    public int getID()
    {
        return this.id;
    }

    @Override
    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }


    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof IGGMEffectManager &&(this.id == ((IGGMEffectManager) obj).getID() || this.unlocalizedName.equals(((IGGMEffectManager) obj).getUnlocalizedName()));
    }


    public abstract class AbstractGGMEffectInstance<Manager extends AbstractGGMEffectManager<Entity>, Entity extends IGGMEntityLivingBase> implements IGGMEffectInstance<Manager, Entity>
    {

        protected final Entity entity;


        public AbstractGGMEffectInstance(Entity entity)
        {
            this.entity = entity;

        }


        @Override
        public Manager getGenericEffect()
        {
            return (Manager) AbstractGGMEffectManager.this;
        }

        @Override
        public Entity getEntity()
        {
            return this.entity;
        }

    }


    public abstract class AbstractGGMEffectSnapshot<Effect extends IGGMEffectInstance> implements IGGMEffectSnapshot<Effect>
    {

    }

}
