package mrfinger.gothicgamemod.item.magic;

import cpw.mods.fml.common.Mod.EventHandler;
import mrfinger.gothicgamemod.GCreativeTabs;
import mrfinger.gothicgamemod.GothicMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemMagicCast extends Item {
	
	public ItemMagicCast () {
		this.setCreativeTab(GCreativeTabs.tabGothicMagic);
		if (this.rune()) this.setFull3D();
		this.setUnlocalizedName(this.name());
		setTextureName(GothicMain.MODID + ":" + this.name());
	}
	
	@EventHandler
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		
		/*if (player.capabilities.isCreativeMode || IGothicPlayer.get(player).getStat(EntityAttributes.mana).getDynamicParametr().getCurrentValue() >= this.manaTakeValue())
			this.castSpell(item, world, player);	*/
		
		return item;
	}
	
	public String name() {
		return null;
	}
	
	protected boolean rune() {
		return false;
	}
	
	public boolean expendable() {
		return false;
	}
	
	protected float manaTakeValue() {
		return 5.0F;
	}
	
	protected int maxUseDuration() {
		return 10;
	}
	
	public void castSpell(ItemStack item, World world, EntityPlayer player) {
	}
	
}
