package mrfinger.gothicgamemod.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GGMControlButton extends GuiButton
{


    FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

    public GGMControlButton(int id, int x, int y, String text) {
        super(id, x, y, 30, 10, text);
    }

    @Override
    public void drawButton(Minecraft mc, int par1, int par2)
    {
        super.drawButton(mc, par1, par2);

        if (this.visible) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(GGMButton.buttonTexture);
            this.drawTexturedModalRect(xPosition, yPosition, 0, 0, 30, 10);
            fr.drawString(displayString, xPosition + 2, yPosition + 1,  0xFFFFFF);
        }
    }

}
