package mrfinger.gothicgamemod.client.render.entity;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.model.ModelAnimal;
import mrfinger.gothicgamemod.entity.packentities.IEntityGothicAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGGMAnimal extends RenderLiving
{
		
	public final ResourceLocation textureLocation;
		
	protected ModelAnimal modelAnimal;

	
	public RenderGGMAnimal(ModelAnimal model, String texture, float shadowSize)
    {

		super(model, shadowSize);
		this.textureLocation = new ResourceLocation(GothicMain.MODID + ":textures/entity/" + texture);
		this.modelAnimal = model;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}
	
	@Override
	protected void renderModel(EntityLivingBase entity, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float size)
    {
		
        this.bindEntityTexture(entity);
        this.modelAnimal.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, size, entity);

        if (!entity.isInvisible())
        {
            if (entity.width != ((IEntityGothicAnimal) entity).getStandartWidth())
            {
                float f0 =  entity.width / ((IEntityGothicAnimal) entity).getStandartWidth();
                GL11.glPushMatrix();
                GL11.glScalef(f0, f0, f0);
                float f1 = (24F - this.modelAnimal.corpusHeight()) * (1F - f0) * size;
                GL11.glTranslatef(0F, f1, 0F);
                this.modelAnimal.render(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, size);
                GL11.glPopMatrix();
            }
            else
            {
                this.modelAnimal.render(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, size);
            }
        }
        else if (!entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
        {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            if (entity.width != ((IEntityGothicAnimal) entity).getStandartWidth())
            {
                float f0 =  entity.width / ((IEntityGothicAnimal) entity).getStandartWidth();
                GL11.glScalef(f0, f0, f0);
                GL11.glTranslatef(0F, (24F - this.modelAnimal.corpusHeight()) * (1F - f0) * size, 0F);
            }
            this.modelAnimal.render(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, size);
            this.modelAnimal.render(entity, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, size);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        }
    }

}
