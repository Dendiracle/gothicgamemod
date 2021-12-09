package mrfinger.gothicgamemod.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.entity.IGGMAbstractClientPlayer;
import mrfinger.gothicgamemod.client.entity.capability.GGMDynamicAttributeHelper;
import mrfinger.gothicgamemod.client.entity.capability.GGMIncreasableAttributeHelper;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMKeyBindings;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketAttributesToUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class GGMGuiCharachterMenu extends GuiScreen  {

	
	public static final ResourceLocation menuTexture = new ResourceLocation(GothicMain.MODID, "textures/gui/charachter_menu.png");

	//public static final Map<IAttribute, GGMIncreasableAttributeHelper> statHelpersMap = new LinkedHashMap<>(6, 1.0F);

	public static GGMIncreasableAttributeHelper[] statHelpersArray;

	private int guiWidth = 256,
				guiHeight = 240;

	float GparticalTicks;

	public final Minecraft mc;
	public final IGGMAbstractClientPlayer player;
	public final FontRenderer fr;
		
	protected int guiOffsetX, guiOffsetY;
	
	private int lp;


	public GGMGuiCharachterMenu() {

		this.mc = Minecraft.getMinecraft();
		this.player = (IGGMAbstractClientPlayer) mc.thePlayer;
		this.fr = mc.fontRenderer;
	}


	public static void loadStatHelpers(IGGMAbstractClientPlayer player) {

		statHelpersArray = new GGMIncreasableAttributeHelper[] {

				new GGMDynamicAttributeHelper((IGGMDynamicAttributeInstance) ((EntityLivingBase) player).getEntityAttribute(GGMCapabilities.maxHealthDynamic), GGMCapabilities.maxHealthS),
				new GGMDynamicAttributeHelper((IGGMDynamicAttributeInstance) ((EntityLivingBase) player).getEntityAttribute(GGMCapabilities.maxStamina), GGMCapabilities.maxStaminaS),
				new GGMDynamicAttributeHelper((IGGMDynamicAttributeInstance) ((EntityLivingBase) player).getEntityAttribute(GGMCapabilities.maxMana), GGMCapabilities.maxManaS),
				new GGMIncreasableAttributeHelper((IGGMAttributeInstance) ((EntityLivingBase) player).getEntityAttribute(SharedMonsterAttributes.attackDamage), GGMCapabilities.strenghtS),
				new GGMIncreasableAttributeHelper((IGGMAttributeInstance) ((EntityLivingBase) player).getEntityAttribute(GGMCapabilities.dexterity), GGMCapabilities.dexterityS),
				new GGMIncreasableAttributeHelper((IGGMAttributeInstance) ((EntityLivingBase) player).getEntityAttribute(GGMCapabilities.intelligence), GGMCapabilities.intelligenceS)
		};
		/*statHelpersMap.put(SharedMonsterAttributes.maxHealthDynamic, new GGMIncreasableAttributeHelper((IGGMAttributeInstance) player.getEntityAttributeInstance(SharedMonsterAttributes.maxHealthDynamic), "Health"));
		statHelpersMap.put(GGMCapabilities.maxStamina, new GGMIncreasableAttributeHelper((IGGMAttributeInstance) player.getEntityAttributeInstance(GGMCapabilities.maxStamina), "Stamina"));
		statHelpersMap.put(GGMCapabilities.maxMana, new GGMIncreasableAttributeHelper((IGGMAttributeInstance) player.getEntityAttributeInstance(GGMCapabilities.maxMana), "Mana"));
		statHelpersMap.put(SharedMonsterAttributes.attackDamage, new GGMIncreasableAttributeHelper((IGGMAttributeInstance) player.getEntityAttributeInstance(SharedMonsterAttributes.attackDamage), "Strenght"));
		statHelpersMap.put(GGMCapabilities.dexterity, new GGMIncreasableAttributeHelper((IGGMAttributeInstance) player.getEntityAttributeInstance(GGMCapabilities.dexterity), "Dexterity"));
		statHelpersMap.put(GGMCapabilities.intelligence, new GGMIncreasableAttributeHelper((IGGMAttributeInstance) player.getEntityAttributeInstance(GGMCapabilities.intelligence), "Intelligence"));

		for (GGMIncreasableAttributeHelper ah : statHelpersMap.values()) {
			statHelpersArray[ah.id] = ah;
		}*/
	}

	
	@Override
	public void drawScreen(int mouseX, int mouseY, float particalTicks) {

		this.GparticalTicks = particalTicks;
		Minecraft.getMinecraft().renderEngine.bindTexture(menuTexture);
		drawDefaultBackground();
		drawTexturedModalRect(guiOffsetX, guiOffsetY, 0, 0, guiWidth, guiHeight);
		
		int x = this.guiOffsetX + 12;
		int y = guiOffsetY + 55;
				
		fr.drawString("Level", x, y, 0xFFFFFF);
		fr.drawString("Exp", x, y += 10, 0xFFFFFF);
		fr.drawString("LP", x, y += 10, 0xFFFFFF);
		
		int xx = x + 62;

		y = guiOffsetY + 55;
		
		fr.drawString(Integer.toString(player.getLvl()), xx, y, 0xFFFFFF);
		fr.drawString(Long.toString(player.getExpCap().getExpValue()) + "/" + Long.toString(player.getExpCap().getExpValueForNewLvl()), xx, y += 10, 0xFFFFFF);
		fr.drawString(Integer.toString(player.getExpCap().getLPValue() + lp), xx,  y += 10, 0xFFFFFF);


		y += 30;


		for (GGMIncreasableAttributeHelper si : statHelpersArray) {

			fr.drawString(si.name, x, y, 0xFFFFFF);
			fr.drawString(si.toDrawValue(), xx, y, 0xFFFFFF);
			y += 10;
		}

		super.drawScreen(mouseX, mouseY, particalTicks);
	}
	
		
	@Override
	public void initGui() {

		super.initGui();

		this.guiOffsetX = (width - guiWidth) / 2;
		this.guiOffsetY = (height - guiHeight) / 2 - 5;

		this.buttonList = new ArrayList<GGMButton>();

		int x = guiOffsetX + guiWidth - 40;
		int y = guiOffsetY + 105;
		
		boolean active = this.player.getExpCap().getLPValue() > 0;
		int length = statHelpersArray.length;
		
		for (GGMIncreasableAttributeHelper ah : statHelpersArray)
		{
			IGGMAttributeInstance ai = ah.attributeInstance;
			this.buttonList.add(new GGMButton.PlusButton(ah.id, x, y, ah, true));//active && ai.getBaseValue() < ai.getMaxIncreasableValue()));
			y += 10;
		}
		y = guiOffsetY + 105;
		x += 10;
		for (GGMIncreasableAttributeHelper ah : statHelpersArray)
		{
			IGGMAttributeInstance ai = ah.attributeInstance;
			this.buttonList.add(new GGMButton.PlusButton(ah.id + length, x, y, ah, true));// active && ai.getBaseValue() < ai.getMaxIncreasableValue()));
			y += 10;
		}
		y = guiOffsetY + 105;
		x += 10;
		for (GGMIncreasableAttributeHelper ah : statHelpersArray)
		{
			this.buttonList.add(new GGMButton.MinusButton(ah.id + length * 2, x , y, ah, false));
			y += 10;
		}

		buttonList.add(new GGMControlButton(50, guiOffsetX + (guiWidth / 2) - 50, guiOffsetY + guiHeight - 20, "Close"));
		buttonList.add(new GGMControlButton(51, guiOffsetX + (guiWidth / 2) - 15, guiOffsetY + guiHeight - 20, "Reset"));
		buttonList.add(new GGMControlButton(52, guiOffsetX + (guiWidth / 2) + 20, guiOffsetY + guiHeight - 20, "Apply"));

	}
	
	
	
	@Override
	public boolean doesGuiPauseGame()
	{
        return false;
    }


	public void lStatNull()
	{
		this.lp = 0;
		int length = statHelpersArray.length;
		for (int i = 0; i < length; ++i)
		{
			if (statHelpersArray[i].upgradeAmounts > 0.0F) {
				((GGMButton) this.buttonList.get(i + length * 2)).enabled = false;
			}
			statHelpersArray[i].nullify();
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		/*int id = button.id;
		int length = statHelpersArray.length;
		if (id < length * 3)
		{
			final int entityLP = player.getExpCap().getLPValue();
			GGMIncreasableAttributeHelper helper = ((GGMButton) button).attributeHelper;
			IGGMAttributeInstance stat = helper.getAttributeInstance();

			if (id >= 0 && id < length)
			{
				if ((entityLP + lp) > 0)
				{
					helper.increase();
					--this.lp;
					((GGMButton) this.buttonList.get(id + length * 2)).enabled = true;

					if ((entityLP + lp) <= 0) {
						for (int i = 0; i < length; ++i) {
							((GGMButton) this.buttonList.get(i)).enabled = false;
							((GGMButton) this.buttonList.get(i + length)).enabled = false;
						}
					}
					if (helper.addedValue + stat.getBaseValue() > stat.getMaxIncreasableValue() - stat.calculateIncreasingValueWithAdded(helper.addedValue, stat.getIncreasingValue()) / 2.0F) {
						helper.addedValue = (float) (stat.getMaxIncreasableValue() - stat.getBaseValue());
						button.enabled = false;
						((GGMButton) this.buttonList.get(id + length)).enabled = false;
					}
				}
			} else if (id >= length && id < length * 2) {
				int lpToSpend = entityLP + lp > 5 ? 5 : entityLP + lp;

				if ((lpToSpend) > 0) {
					helper.increase(lpToSpend);
					this.lp -= lpToSpend;

					((GGMButton) this.buttonList.get(id + length)).enabled = true;

					if ((entityLP + lp) <= 0) {
						for (int i = 0; i < length; ++i) {
							((GGMButton) this.buttonList.get(i)).enabled = false;
							((GGMButton) this.buttonList.get(i + length)).enabled = false;
						}
					}
					if (helper.addedValue + stat.getBaseValue() > stat.getMaxIncreasableValue() - stat.calculateIncreasingValueWithAdded(helper.addedValue, stat.getIncreasingValue()) / 2.0F) {
						helper.addedValue = (float) (stat.getMaxIncreasableValue() - stat.getBaseValue());
						button.enabled = false;
						((GGMButton) this.buttonList.get(id - length)).enabled = false;
					}
				}
			} else if (id >= length * 2 && id < length * 3) {
				if (helper.upgradeAmounts > 0.0F) {

					helper.decrease();
					++this.lp;

					for (int i = 0; i < length; ++i) {
						GGMButton ggmButton = (GGMButton) this.buttonList.get(i);
						GGMButton ggmButton2 = (GGMButton) this.buttonList.get(i + length);
						if (helper.addedValue < stat.getMaxIncreasableValue()) {
							ggmButton.enabled = true;
							ggmButton2.enabled = true;
						}
					}

					if (helper.upgradeAmounts <= 0.0F) button.enabled = false;
				}
			}
		}
		else {
			switch (id) {
				case 50:
					mc.thePlayer.closeScreen();
					this.lStatNull();
					break;
				case 51:
					this.lStatNull();
					break;
				case 52:
					PacketDispatcher.sendToServer(new CPacketAttributesToUpgrade(player.getEntityId(), statHelpersArray));
					break;
			}
		}*/

		super.actionPerformed(button);
	}

	
	@Override
	protected void keyTyped(char typeChar, int keyCode) {		
		if(keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E ||keyCode == GGMKeyBindings.GKeyBinding.getKeyCode()) {
			mc.thePlayer.closeScreen();
			this.lStatNull();
		}
		super.keyTyped(typeChar, keyCode);
	}
	
	

}
