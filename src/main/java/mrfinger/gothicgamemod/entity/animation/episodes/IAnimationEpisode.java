package mrfinger.gothicgamemod.entity.animation.episodes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animation.instance.IAnimation;
import net.minecraft.client.model.ModelBase;

public interface IAnimationEpisode<Entity extends IGGMEntityLivingBase, Model extends ModelBase>
{

    String getUnlocalizedName();

    int getStandartDuration();

    float getCulminationTickMultiplier();


    default void onSet(Entity entity, IAnimation helper) {}

    default void onUpdate(Entity entity, IAnimation helper) {}

    default void onCulmination(Entity entity, IAnimation helper) {}

    default boolean isCanBreak(Entity entity, IAnimation helper)
    {
        return true;
    }

    default void onEnd(Entity entity, IAnimation helper) {}


    default void controlEntityMotion(Entity entity, IAnimation helper) {}


    @SideOnly(Side.CLIENT)
    void updateModel(Entity entity, Model model, float progress);

}
