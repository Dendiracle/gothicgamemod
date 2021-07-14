package mrfinger.gothicgamemod.entity.animations.episodes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import net.minecraft.client.model.ModelBase;

public interface IAnimationEpisode<Entity extends IGGMEntityLivingBase, Model extends ModelBase>
{

    String getUnlocalizedName();

    int getStandartDuration();

    float getCulminationTickMultiplier();


    default void onUpdate(Entity entity, int duration, int count) {}

    default void onCulmination(Entity entity, int duration, int count, byte series) {}


    @SideOnly(Side.CLIENT)
    void updateModel(Entity entity, Model model, float progress);

}
