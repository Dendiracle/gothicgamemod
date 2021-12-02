package mrfinger.gothicgamemod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class GCreativeTabs {
	
	public static CreativeTabs[] gothicCreativetabsArray = new CreativeTabs[2];

	public static void registerTabs() {
		/*final CreativeTabs gothicWeapons = new CreativeTabs("Gothic_Weapons") {
			public Item getTabIconItem() {
				return Item.getItemFromBlock(Blocks.flowing_lava);
			}
		};
		
		final CreativeTabs gothicMobs = new CreativeTabs("Gothic_Mobs") {
			public Item getTabIconItem() {
				return Item.getItemFromBlock(Blocks.soul_sand);
			}
		};*/
	}
	
	public static final CreativeTabs tabGothicWeapons = (new CreativeTabs("Gothic_Weapons") {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Items.golden_sword;
        }
    });
	
	public static final CreativeTabs tabGothicMagic = (new CreativeTabs("Gothic_Magic") {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Items.golden_sword;
        }
    });
}
