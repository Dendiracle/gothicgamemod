package mrfinger.gothicgamemod.client.gui;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.entity.capability.GGMIncreasableAttributeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class GGMButton extends GuiButton
{
	
	public static ResourceLocation buttonTexture = new ResourceLocation(GothicMain.MODID, "textures/gui/buttons.png");


	public final GGMIncreasableAttributeHelper attributeHelper;

	
	public GGMButton(int id, int x, int y, int width, int height, GGMIncreasableAttributeHelper attributeHelper, boolean enabled)
    {
    	super(id, x, y, width, height, "");

    	this.attributeHelper = attributeHelper;
    	this.enabled = enabled;
	}


	public abstract int getTexXPos();

	public abstract int getTexYPos();


	public abstract void action(int amount);


    @Override
    public void drawButton(Minecraft mc, int par1, int par2)
    {
        super.drawButton(mc, par1, par2);

        if (this.visible) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(buttonTexture);
            int x = this.getTexXPos();
            this.drawTexturedModalRect(xPosition, yPosition, this.getTexXPos(), this.getTexYPos(), 9, 9);
        }
    }

    
    public static class PlusButton extends GGMButton
    {
    	
    	public PlusButton(int id, int x, int y, GGMIncreasableAttributeHelper attributeHelper, boolean enabled)
        {
        	super(id, x, y, 9, 9, attributeHelper, enabled);
        }

        @Override
        public int getTexXPos()
        {
            return this.enabled ? (this.hovered ? 8 : 0) : 16;
        }

        @Override
        public int getTexYPos()
        {
            return 9;
        }


        @Override
        public void action(int amount)
        {
            this.attributeHelper.increase(amount);
        }

    }
    
    public static class MinusButton extends GGMButton
    {
    	
    	public MinusButton(int id, int x, int y, GGMIncreasableAttributeHelper attributeHelper, boolean enabled)
        {
            super(id, x, y, 9, 9, attributeHelper, enabled);
        }


        @Override
        public int getTexXPos()
        {
            return this.enabled ? (this.hovered ? 8 : 0) : 16;
        }

        @Override
        public int getTexYPos()
        {
            return 17;
        }


        @Override
        public void action(int amount)
        {
            this.attributeHelper.decrease();
        }

    }
}



