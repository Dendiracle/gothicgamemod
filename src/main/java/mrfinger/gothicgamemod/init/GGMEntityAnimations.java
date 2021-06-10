package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mrfinger.gothicgamemod.client.model.ModelScavenger;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animals.EntityScavenger;
import mrfinger.gothicgamemod.entity.animations.episodes.AbstractAnimationEpisode;
import net.minecraft.client.model.ModelBase;

public class GGMEntityAnimations
{

    public static AbstractAnimationEpisode ScavLiving0 = new AbstractAnimationEpisode("Scav_Living0", 100)
    {
        @Override
        public void updateModel(IGGMEntityLivingBase entity, ModelBase model, float progress)
        {
            ModelScavenger mdl = (ModelScavenger) model;

            while (progress >= 0.2F)
            {
                progress -= 0.2F;
            }

            if (progress >= 0.1F)
            {
                progress = 0.2F - progress;
            }

            //mdl.Corpus.rotateAngleX = progress * 18.0F;
            //mdl.Neck.rotateAngleX = progress * 18.0F;
            //mdl.Head.rotateAngleX = progress * 18.0F;

            //mdl.Neck.offsetY = mdl.Neck.rotationPointY;
            //mdl.Neck.offsetX = mdl.Neck.rotationPointX;
        }
    };



    public static void preLoad(FMLPreInitializationEvent event)
    {
        EntityScavenger.livingEpisodesMap.put(ScavLiving0.getUnlocalizedName(), ScavLiving0);
    }


}
