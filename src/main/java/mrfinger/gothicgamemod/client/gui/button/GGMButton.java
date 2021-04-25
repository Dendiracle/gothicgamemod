package mrfinger.gothicgamemod.client.gui.button;

import org.lwjgl.opengl.GL11;

import mrfinger.gothicgamemod.GothicMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GGMButton extends GuiButton
{
	
	static ResourceLocation buttonTexture = new ResourceLocation(GothicMain.MODID, "textures/gui/buttons.png");
	
	public GGMButton(int id, int x, int y, int width, int height, String text) {
    	super(id, x, y, width, height, text);    	
	}
	
	public static class ControlButton extends GGMButton {
	
		private String text;
	
		
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
	
		public ControlButton(int id, int x, int y, String text) {
    	super(id, x, y, 30, 10, text);
    	this.text = text;    	
		}

		@Override
		public void drawButton(Minecraft mc, int par1, int par2)
		{
            super.drawButton(mc, par1, par2);

			if (this.visible) {            
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(buttonTexture);            
				this.drawTexturedModalRect(xPosition, yPosition, 0, 0, 30, 10);
				fr.drawString(text, xPosition + 2, yPosition + 1,  0xFFFFFF);
			}
      	}
    }
    
    public static class PlusButton extends GGMButton {
    	
    	public PlusButton(int id, int x, int y, boolean enabled) {
        	super(id, x, y, 9, 9, "");

        	this.enabled = enabled;
        }
    	
    	

        @Override
        public void drawButton(Minecraft mc, int par1, int par2)
        {
            super.drawButton(mc, par1, par2);

            if (this.visible) {            
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(buttonTexture); 
                int x = this.enabled ? (this.field_146123_n ? 8 : 0) : 16;
                this.drawTexturedModalRect(xPosition, yPosition, x, 9, 9, 9); 
            }
        }
    }
    
    public static class MinusButton extends GGMButton
    {
    	
    	
    	
    	public MinusButton(int id, int x, int y, boolean enabled) {
        	super(id, x, y, 9, 9, "");
        	this.enabled = enabled;
        }
    	
    	

        @Override
        public void drawButton(Minecraft mc, int par1, int par2)
        {
            super.drawButton(mc, par1, par2);

            if (this.visible) {            
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(buttonTexture);
                int x = this.enabled ? (this.field_146123_n ? 8 : 0) : 16;
                this.drawTexturedModalRect(xPosition, yPosition, x, 17, 9, 9); 
            }
        }
    }
}



