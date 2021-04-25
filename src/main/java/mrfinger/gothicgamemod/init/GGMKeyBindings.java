package mrfinger.gothicgamemod.init;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;


public class GGMKeyBindings {
	
	public static final KeyBinding 	GKeyBinding = new KeyBinding("key.charachtermenu", Keyboard.KEY_C, "category.gothicgamemod");
	public static final KeyBinding BreakSpeedBoostAbility = new KeyBinding("key.speedboostability", Keyboard.KEY_V, "category.gothicgamemod");
	public static final KeyBinding changeStance = new KeyBinding("key.changestance", Keyboard.KEY_R, "category.gothicgamemod");
	public static final KeyBinding GGMInventory = new KeyBinding("key.ggminventory", Keyboard.KEY_I, "category.gothicgamemod");


	public static void registerKeys() {
		ClientRegistry.registerKeyBinding(GKeyBinding);
		ClientRegistry.registerKeyBinding(BreakSpeedBoostAbility);
		ClientRegistry.registerKeyBinding(changeStance);
		ClientRegistry.registerKeyBinding(GGMInventory);
	}

}
