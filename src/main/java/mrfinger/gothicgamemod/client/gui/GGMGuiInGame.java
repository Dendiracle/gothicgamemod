package mrfinger.gothicgamemod.client.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.multiplayer.IGGMPlayerControllerMP;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

public class GGMGuiInGame extends Gui {
	
	private final ResourceLocation texture = new ResourceLocation(GothicMain.MODID, "textures/gui/in_game.png");

	Minecraft mc;
	FontRenderer fr;
	IGGMEntityPlayer player;
	IGGMPlayerControllerMP pcontroller;

	protected static final RenderItem itemRenderer = new RenderItem();
	
	private int fWidth = 91, 
				fHeight = 7, 
				bWidth = 89, 
				bHeight = 5,
				maxRenderTimer = 100;
	
	private static final List<Integer[]>		expValues = new ArrayList();

	private int[][] exp = new int[2][5];

	public GGMGuiInGame() {

		this.mc = Minecraft.getMinecraft();

	}
		
	@SubscribeEvent
	public void onScreenRenderPost(RenderGameOverlayEvent.Pre event) {
	    switch(event.type) {
	        case HEALTH:
	            event.setCanceled(true);
	            break;
	        default:
	            break;
	    }
	}
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent re) {
		
		if (re.type.equals(ElementType.TEXT)) {

			int w = re.resolution.getScaledWidth();
			int h = re.resolution.getScaledHeight();

			this.fr = mc.fontRenderer;
			this.player = (IGGMEntityPlayer) mc.thePlayer;
			this.pcontroller = (IGGMPlayerControllerMP) mc.playerController;

			int hpOffxetX = 2;
			int hpOffsetY = re.resolution.getScaledHeight() - 12;
			

			int yOffset = 100;
			//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			if (this.expValues.size() > 5) for (int i = this.expValues.size() - 1; i >= 5 ; --i) {
				this.expValues.remove(i);
			}
			
			for (int i = this.expValues.size() - 1; i >= 0 ; --i) {					
				
				fr.drawString("Experience  " + this.expValues.get(i)[0], 20, yOffset, 0xFFFFFF);
				yOffset -= 10;
				this.expValues.get(i)[1]--;
				if (this.expValues.get(i)[1] <= 0) this.expValues.remove(i);
				
			}

			mc.renderEngine.bindTexture(texture);

			GL11.glEnable(GL11.GL_BLEND);

			this.drawTexturedModalRect(w - 42, h - 62, 92, 0, 42, 62);

			if (player.inFightStance()) {

				int k = player.getGGMEquipment().getCurrentItemIndex() * 20;
				this.drawTexturedModalRect(w - 43, h - 63 + k, 92, 62, 44, 24);

				int attackDuration = player.getCurrentAttackDuration();

				if (attackDuration > 0)
				{
					if (pcontroller.getAttackPenalty() > 0)
					{
						k = pcontroller.getAttackPenalty() * 16 / player.getCurrentAttackDuration();
					}
					else
					{
						k = player.getAttackCountdown() * 16 / player.getCurrentAttackDuration();
					}
				}
				else
				{
					k = 0;
				}

				k = k < 0 ? 0 : k;
				this.drawTexturedModalRect(w / 2 + 10, h / 2 - 7, 32, 28, 4, 16);
				this.drawTexturedModalRect(w / 2 + 10, h / 2 - 7, 28, 28, 4, k);

				if (player.isChangingStance()) this.drawTexturedModalRect(w / 2 + 94, h - 14, 17, 28, 11, 11);
				else this.drawTexturedModalRect(w / 2 + 94, h - 20, 0, 28, 17, 17);
			}

			if (mc.playerController.gameIsSurvivalOrAdventure()) {

				float playerHealth = (float) player.getHealthAttribute().getDynamicValue();
				float playerStamina = (float) player.getStaminaAttribute().getDynamicValue();
				float playerMana = (float) player.getManaAttribute().getDynamicValue();
							
				int healthBarcurrWidth = (int)((float)bWidth / mc.thePlayer.getMaxHealth() * playerHealth);
				//if (playerHealth > 4999998)	healthBarcurrWidth++;
				
				int staminaBarCurrWidth = (int)((float)bWidth / player.getStaminaAttribute().getAttributeValue() * playerStamina);
				
				int manaBarCurrWidth = (int)((float)bWidth / player.getManaAttribute().getAttributeValue() * playerMana);

				drawTexturedModalRect(hpOffxetX + 1, hpOffsetY - 14, 0, 0, bWidth, bHeight);
				drawTexturedModalRect(hpOffxetX + 1, hpOffsetY - 14, 0, 12, healthBarcurrWidth, bHeight);
				drawTexturedModalRect(hpOffxetX, hpOffsetY - 15, 0, bHeight, fWidth, fHeight);
		
				drawTexturedModalRect(hpOffxetX + 1, hpOffsetY - 5, 0, 0, bWidth, bHeight);
				drawTexturedModalRect(hpOffxetX + 1, hpOffsetY - 5, 0, 17, staminaBarCurrWidth, bHeight);
				drawTexturedModalRect(hpOffxetX, hpOffsetY - 6, 0, bHeight, fWidth, fHeight);
				
				drawTexturedModalRect(hpOffxetX + 1, hpOffsetY + 4, 0, 0, bWidth, bHeight);
				drawTexturedModalRect(hpOffxetX + 1, hpOffsetY + 4, 0, 22, manaBarCurrWidth, bHeight);
				drawTexturedModalRect(hpOffxetX, hpOffsetY + 3, 0, bHeight, fWidth, fHeight);
				
				fr.drawStringWithShadow(String.format("%.1f", playerHealth), hpOffxetX + 93, hpOffsetY - 15, 0xFFFFFF);
				fr.drawStringWithShadow(String.format("%.1f", playerStamina), hpOffxetX + 93, hpOffsetY - 6, 0xFFFFFF);
				fr.drawStringWithShadow(String.format("%.1f", playerMana), hpOffxetX + 93, hpOffsetY + 3, 0xFFFFFF);
				
			}

			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.enableGUIStandardItemLighting();

			for (int i = 0; i < 2; ++i) {

				int x = w - 39 + i * 20;

				for (int ii = 0; ii < 3; ++ii) {

					int y = h - 59 + ii * 20;
					this.renderInventorySlot(ii * 2 + i, x, y, re.partialTicks);
				}
			}

			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			this.mc.mcProfiler.endSection();
			GL11.glDisable(GL11.GL_BLEND);
		}				
	}

	protected void renderInventorySlot(int slot, int x, int y, float p_73832_4_) {

		ItemStack itemstack = this.player.getGGMEquipment().getStackInSlot(slot);

		if (itemstack != null)
		{
			float f1 = (float)itemstack.animationsToGo - p_73832_4_;

			if (f1 > 0.0F)
			{
				GL11.glPushMatrix();
				float f2 = 1.0F + f1 / 5.0F;
				GL11.glTranslatef((float)(x + 8), (float)(y + 12), 0.0F);
				GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
			}

			itemRenderer.renderItemAndEffectIntoGUI(this.fr, this.mc.getTextureManager(), itemstack, x, y);

			if (f1 > 0.0F)
			{
				GL11.glPopMatrix();
			}

			itemRenderer.renderItemOverlayIntoGUI(this.fr, this.mc.getTextureManager(), itemstack, x, y);
		}
	}

	public void addGainedExp(int value) {

		int exp[][] = this.exp;
		int l = exp[0].length;

		for (int i = 0; i < l; ++i) {

			if (exp[0][i] <= 0) {

				exp[0][i] = this.maxRenderTimer;
				exp[1][i] = value;
			}
			else if (i == --l) {
				exp[0][l] = value;
			}
		}
	}

	private void drawGainedEXP() {

	}
	
	private static String multiplicator(double value) {
		String j = null;
		if (value < 1000) {

			j = String.format("%.1s", value);
		}
		else if (value < 1000000) {			
			j = String.format("%.4s", value / 1000);
			if (value >=100000)	j = String.format("%.4s", (int)value / 1000);
			j += "K";
		}		
		else if (value < 1000000000) {
			j = String.format("%.4s", value / 1000000);
			if (value >=100000000)	j = String.format("%.4s", (int)value / 1000000);
			j += "M";
		}
		else {
			j = String.format("%.4s", value / 1000000000);
			if (value >=100000000000L)	j = String.format("%.4s", (int)value / 1000000000);
			j += "B";
		}
		return j;
	}
	
	
	public static void addExpGainNotice(int gainValue) {		
		expValues.add(new Integer[] {gainValue, 1000});
	}

}
