package mrfinger.gothicgamemod.client.gui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import mrfinger.gothicgamemod.client.entity.IGGMAbstractClientPlayer;
import mrfinger.gothicgamemod.client.gui.button.GGMButton;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMIncreasableAttributeInstance;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.network.PacketDispatcher;
import mrfinger.gothicgamemod.network.client.CPacketAttributesToUpgrade;
import mrfinger.gothicgamemod.util.Packet;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.client.GGMAttributeHelper;
import mrfinger.gothicgamemod.init.GGMKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GGMGuiCharachterMenu extends GuiScreen  {

	
	public static final ResourceLocation menuTexture = new ResourceLocation(GothicMain.MODID, "textures/gui/charachter_menu.png");

	//public static final Map<IAttribute, GGMAttributeHelper> statHelpersMap = new LinkedHashMap<>(6, 1.0F);

	public static GGMAttributeHelper[] statHelpersArray;

	private int guiWidth = 220,
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

		statHelpersArray = new GGMAttributeHelper[] {

				new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.maxHealth), GGMCapabilities.maxHealthS),
				new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.maxStamina), GGMCapabilities.maxStaminaS),
				new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.maxMana), GGMCapabilities.maxManaS),
				new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(SharedMonsterAttributes.attackDamage), GGMCapabilities.strenghtS),
				new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.dexterity), GGMCapabilities.dexterityS),
				new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.intelligence), GGMCapabilities.intelligenceS)
		};
		/*statHelpersMap.put(SharedMonsterAttributes.maxHealth, new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(SharedMonsterAttributes.maxHealth), "Health"));
		statHelpersMap.put(GGMCapabilities.maxStamina, new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.maxStamina), "Stamina"));
		statHelpersMap.put(GGMCapabilities.maxMana, new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.maxMana), "Mana"));
		statHelpersMap.put(SharedMonsterAttributes.attackDamage, new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(SharedMonsterAttributes.attackDamage), "Strenght"));
		statHelpersMap.put(GGMCapabilities.dexterity, new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.dexterity), "Dexterity"));
		statHelpersMap.put(GGMCapabilities.intelligence, new GGMAttributeHelper((IGGMIncreasableAttributeInstance) player.getEntityAttribute(GGMCapabilities.intelligence), "Intelligence"));

		for (GGMAttributeHelper ah : statHelpersMap.values()) {
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
		fr.drawString(Long.toString(player.getExpCap().getExp()) + "/" + Long.toString(player.getExpCap().getAllNextLvlExp()), xx, y += 10, 0xFFFFFF);
		fr.drawString(Integer.toString(player.getExpCap().getLP() + lp), xx,  y += 10, 0xFFFFFF);


		y += 30;


		for (GGMAttributeHelper si : statHelpersArray) {

			fr.drawString(si.name, x, y, 0xFFFFFF);
			fr.drawString(si.toString(), xx, y, 0xFFFFFF);
			y += 10;
		}

		super.drawScreen(mouseX, mouseY, particalTicks);
	}
	
		
	@Override
	public void initGui() {

		super.initGui();

		this.guiOffsetX = (width - guiWidth) / 2;
		this.guiOffsetY = (height - guiHeight) / 2 - 5;

		System.out.print("Debug in: " + this.getClass() + " InitGui ");
		this.buttonList = new ArrayList<GGMButton>();

		System.out.print("X: " + guiOffsetX + " Y: " + guiOffsetY);
		System.out.println();
		int x = guiOffsetX + guiWidth - 30;
		int y = guiOffsetY + 105;
		
		boolean active = this.player.getExpCap().getLP() > 0;
		
		for (GGMAttributeHelper ah : statHelpersArray) {

			this.buttonList.add(new GGMButton.PlusButton(ah.id, x, y, active));
			y += 10;
		}
		
		x += 10;
		int i = statHelpersArray.length;
		y -= i * 10;

		for (GGMAttributeHelper ah : statHelpersArray) {

			this.buttonList.add(new GGMButton.MinusButton(ah.id + i, x, y, false));
			y += 10;
		}
		
		buttonList.add(new GGMButton.ControlButton(50, guiOffsetX + 45, guiOffsetY + guiHeight - 20, "Close"));
		buttonList.add(new GGMButton.ControlButton(51, guiOffsetX + 80, guiOffsetY + guiHeight - 20, "Reset"));
		buttonList.add(new GGMButton.ControlButton(52, guiOffsetX + 115, guiOffsetY + guiHeight - 20, "Apply"));
	}
	
	
	
	@Override
	public boolean doesGuiPauseGame() {
        return false;
    }


	public void lStatNull() {

		this.lp = 0;

		for(int i = 0; i < statHelpersArray.length; ++i) {

			if (statHelpersArray[i].upgradeAmounts > 0) {
				((GGMButton) this.buttonList.get(i + statHelpersArray.length)).enabled = false;
			}

			statHelpersArray[i].upgradeAmounts = 0;
			statHelpersArray[i].addedBefore = 0.0F;
			statHelpersArray[i].addedValue = 0.0F;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {		
		
		int id = button.id;
		int length = statHelpersArray.length;
		GGMAttributeHelper helper;
		IGGMIncreasableAttributeInstance stat;
		int needLP;
		
		
		if (id >= 0 && id < length) {

			helper = statHelpersArray[id];
			stat = helper.attributeInstance;

			if((player.getExpCap().getLP() + lp) > 0) {
				
				helper.upgradeAmounts++;
				helper.addedBefore = (float) stat.calculateIncreasingValueWithAdded(helper.addedValue, stat.getIncreasingValue());
				helper.addedValue += helper.addedBefore;
				
				--this.lp;
				
				((GGMButton) this.buttonList.get(id + length)).enabled = true;
				
				if ((player.getExpCap().getLP() + lp) <= 0) {

					for (int i = 0; i < length; ++i) {
						((GGMButton) this.buttonList.get(i)).enabled = false;
					}
				}
				else if (helper.addedValue >= stat.getMaxValue() - 0.001D) {

					helper.addedValue = (float) stat.getMaxValue();
					button.enabled = false;
				}
			}			
		}
		
		else if (id >= length && id < length * 2) {

			id -= length;
			helper = statHelpersArray[id];
			stat = helper.attributeInstance;
			
			if (helper.upgradeAmounts > 0) {
				
				helper.upgradeAmounts--;
				helper.addedBefore = (float) stat.calculateIncreasingValueWithAdded(helper.addedValue - helper.addedBefore, stat.getIncreasingValue());
				helper.addedValue -= helper.addedBefore;
				++this.lp;
				
				if ((player.getExpCap().getLP() + lp) > 0) {

					for (int i = 0; i < length; ++i) {
						GGMButton ggmButton = (GGMButton) this.buttonList.get(i);
						if (helper.addedValue < stat.getMaxValue()) ggmButton.enabled = true;
					}
				}
				if (helper.upgradeAmounts <= 0) ((GGMButton) this.buttonList.get(id + length)).enabled = false;
			}
		}
		
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
