package mrfinger.gothicgamemod.client.render.item.magic;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.model.item.ModelRune3;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RuneItemRenderer implements IItemRenderer {
	
	protected ModelRune3 model = new ModelRune3();
	
	public final ResourceLocation texture;

	public RuneItemRenderer(String texture) {
		this.texture = new ResourceLocation(GothicMain.MODID, "textures/items/runes/" + texture + ".png");
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		switch (type) {
		
		case EQUIPPED:


			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-70.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.1F, 0.28F, -0.015F);
			GL11.glScalef(0.3F, 0.3F, 0.3F);
			
			model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-140.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-1.0F, -0.2F, 0.3F);
			GL11.glScalef(0.5F, 0.75F, 0.5F);
			
			model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			GL11.glPopMatrix();
			break;
		/*case ENTITY:
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, 0.0F, 0.0F);
			GL11.glScalef(0.5F, 0.75F, 0.5F);
			
			model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			GL11.glPopMatrix();
			
			break;
		case INVENTORY:
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, 0.0F, 0.0F);
			GL11.glScalef(0.5F, 0.75F, 0.5F);
			
			model.render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			
			GL11.glPopMatrix();
			
			break;*/
		default:
			break;
		}
	}
	
	private void renderFirstPerson (ItemStack item, Object... data) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		//GL11.glRotatef(angle, x, y, z);
		//GL11.glScalef(x, y, z);
		
		model.render((Entity) data[1], 0F, 0F, 0F, 0F, 0F, 0.0625F);
		
		GL11.glPopMatrix();
	}
	
	private void renderItem (ItemStack item) {
		
	}

	private void renderDrop (ItemStack item) {
	
	}

	private void renderThirdPerson (ItemRenderType type, ItemStack item) {
	
	}

}
