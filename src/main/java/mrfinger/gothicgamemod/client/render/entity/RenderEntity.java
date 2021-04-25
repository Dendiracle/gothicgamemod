package mrfinger.gothicgamemod.client.render.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntity extends RenderLiving{
	
	public final ResourceLocation textureLocation;		
	
	public RenderEntity(ModelBase model, String texture, float shadowSize) {
		super(model, shadowSize);
		this.textureLocation = new ResourceLocation("gothicgame:textures/entity/" + texture);		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return textureLocation;
	}

}
