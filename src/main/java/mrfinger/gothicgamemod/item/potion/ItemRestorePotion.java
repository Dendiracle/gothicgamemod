package mrfinger.gothicgamemod.item.potion;

import net.minecraft.item.Item;

public class ItemRestorePotion extends Item {
	
	/*private final RestoreValues restore;
	
	public ItemRestorePotion (String name, RestoreValues restore) {
		this.setUnlocalizedName(name);
		setTextureName(GothicMain.MODID + ":" + name);
		this.restore = restore;
	}
	
	@EventHandler
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		player.setItemInUse(item, 16);
		return item;
	}
	
	@Override
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
		if (!player.capability.isCreativeMode) player.getCurrentEquippedItem().stackSize--;
		IGGMExp statsPlayer = IGGMExp.get(player);
		//statsPlayer.restoreCurrentValues(this.restore);
		return item;
	}*/

}
