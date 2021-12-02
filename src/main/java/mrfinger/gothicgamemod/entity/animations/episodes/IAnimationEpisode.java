package mrfinger.gothicgamemod.entity.animations.episodes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimationHelper;
import net.minecraft.client.model.ModelBase;

public interface IAnimationEpisode<Entity extends IGGMEntityLivingBase, Model extends ModelBase>
{

    String getUnlocalizedName();

    int getStandartDuration();

    float getCulminationTickMultiplier();


    default void onSet(Entity entity, IAnimationHelper helper) {}

    default void onUpdate(Entity entity, IAnimationHelper helper) {}

    default void onCulmination(Entity entity, IAnimationHelper helper) {}

    default boolean isCanBreak(Entity entity, IAnimationHelper helper)
    {
        return true;
    }

    default void onEnd(Entity entity, IAnimationHelper helper) {}


    default void controlEntityMotion(Entity entity, IAnimationHelper helper) {}


    @SideOnly(Side.CLIENT)
    void updateModel(Entity entity, Model model, float progress);

}
