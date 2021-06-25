package mrfinger.gothicgamemod.entity.animations.episodes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimation;
import net.minecraft.client.model.ModelBase;

public interface IAnimationEpisode
{

    String getUnlocalizedName();

    int getStandartDuration();

    float getCulminationTickMultiplier();


    default void onUpdate(IGGMEntityLivingBase entity, int duration, int count) {}

    default void onCulmination(IGGMEntityLivingBase entity, int duration, int count, byte series) {}


    @SideOnly(Side.CLIENT)
    void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress);

}
