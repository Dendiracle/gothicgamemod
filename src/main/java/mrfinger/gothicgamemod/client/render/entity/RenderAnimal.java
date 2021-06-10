package mrfinger.gothicgamemod.client.render.entity;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.model.ModelAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderAnimal extends RenderLiving {
		
	public final ResourceLocation textureLocation;
		
	protected ModelAnimal modelAnimal;
	
	public RenderAnimal(ModelAnimal model, String texture, float shadowSize) {
		super(model, shadowSize);
		this.textureLocation = new ResourceLocation(GothicMain.MODID + ":textures/entity/" + texture);
		this.modelAnimal = model;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}
	
	@Override
	protected void renderModel(EntityLivingBase entity, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
    {
		
        this.bindEntityTexture(entity);
        this.modelAnimal.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, entity);

        if (!entity.isInvisible())
        {
            this.modelAnimal.render(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        }
        else if (!entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
        {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            this.modelAnimal.render(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        }

    }
	
	
	
}
