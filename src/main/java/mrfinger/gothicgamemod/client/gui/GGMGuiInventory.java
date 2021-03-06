package mrfinger.gothicgamemod.client.gui;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.entity.inventory.GGMContainerPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.init.GGMKeyBindings;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GGMGuiInventory extends InventoryEffectRenderer {


    public static final ResourceLocation GGMInventoryTexture = new ResourceLocation(GothicMain.MODID, "textures/gui/container/inventory.png");
    public static final ResourceLocation GGMInventoryIcons = new ResourceLocation(GothicMain.MODID, "textures/gui/container/empty_slots.png");

    private float xSizeFloat;
    private float ySizeFloat;


    public GGMGuiInventory(IGGMEntityPlayer player) {
        super(player.getGGMContainer());

        this.allowUserInput = true;
    }

    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

        this.xSizeFloat = (float)p_73863_1_;
        this.ySizeFloat = (float)p_73863_2_;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GGMInventoryTexture);
        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        this.renderGGMSlotIcons();
        k += 96;
        l += 75;
        func_147046_a(k, l, 30, (float)(k) - this.xSizeFloat, (float)(l - 50) - this.ySizeFloat, this.mc.thePlayer);
    }

    public static void func_147046_a(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_147046_0_, (float)p_147046_1_, 50.0F);
        GL11.glScalef((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = p_147046_5_.renderYawOffset;
        float f3 = p_147046_5_.rotationYaw;
        float f4 = p_147046_5_.rotationPitch;
        float f5 = p_147046_5_.prevRotationYawHead;
        float f6 = p_147046_5_.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GL11.glTranslatef(0.0F, p_147046_5_.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        p_147046_5_.renderYawOffset = f2;
        p_147046_5_.rotationYaw = f3;
        p_147046_5_.rotationPitch = f4;
        p_147046_5_.prevRotationYawHead = f5;
        p_147046_5_.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void keyTyped(char p_73869_1_, int key) {
        super.keyTyped(p_73869_1_, key);

        if (key == GGMKeyBindings.GGMInventory.getKeyCode()) {

            this.mc.thePlayer.closeScreen();
        }
    }

    protected void renderGGMSlotIcons() {

        for (GGMContainerPlayer.GGMSlot slot : ((GGMContainerPlayer) this.inventorySlots).ggmSlots) {

            this.mc.getTextureManager().bindTexture(GGMInventoryIcons);

            if (slot.getStack() == null) {

                this.drawTexturedModalRect(slot.xDisplayPosition + this.guiLeft, slot.yDisplayPosition + this.guiTop, slot.getIconX(), slot.getIconY(), 16, 16);
            }
        }
    }

}
