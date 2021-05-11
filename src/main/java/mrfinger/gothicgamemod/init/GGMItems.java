package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import mrfinger.gothicgamemod.debug.ItemDebug;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.item.IItemBlocker;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import mrfinger.gothicgamemod.item.Keks;
import mrfinger.gothicgamemod.item.equipment.ItemGGMEquipAttributeBonus;
import mrfinger.gothicgamemod.item.magic.ItemFireBolt;
import mrfinger.gothicgamemod.item.magic.ItemMagicCast;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GGMItems {
	
	public static List<ItemMagicCast> runesList = new ArrayList();

	public static final ItemDebug itemDebug = new ItemDebug(Item.ToolMaterial.EMERALD);

	public static final ItemGGMEquipAttributeBonus AmuletFranko = new ItemGGMEquipAttributeBonus("amulet_franko", "amulet_franko",(byte) 0, new HashMap<IAttribute, Float>()
	{
		{
			put(SharedMonsterAttributes.maxHealth, 100.0F);
		}
	}, new HashMap<IAttribute, Float>()
	{
		{
			put(SharedMonsterAttributes.maxHealth, 40.0F);
			put(SharedMonsterAttributes.attackDamage, 4.0F);
			put(GGMCapabilities.dexterity, 4.0F);
		}
	});

	public static final ItemGGMEquipAttributeBonus RingStrenght = new ItemGGMEquipAttributeBonus("ring_strenght", "amulet_franko",(byte) 1, new HashMap<IAttribute, Float>()
	{

	}, new HashMap<IAttribute, Float>(){
		{
			put(SharedMonsterAttributes.attackDamage, 5.0F);
		}
	});
	
	public static ItemMagicCast runeFireBolt = new ItemFireBolt.Rune();
	public static ItemMagicCast scrollFireBolt = new ItemFireBolt.Scroll();

	public static void load(FMLPreInitializationEvent e) {

		((IItemBlocker) itemDebug).getBlockersMapToRed().get(GGMBattleSystem.crushing).remove((IGGMAttribute) SharedMonsterAttributes.attackDamage);
		((IItemBlocker) itemDebug).setDamageBlocker(GGMBattleSystem.crushing, GGMCapabilities.dexterity, GGMCapabilities.maxMana, 1.0F, 1.0F);
		registerItem(itemDebug);
		registerItem(AmuletFranko);
		registerItem(RingStrenght);
		registerRune(runeFireBolt);
		registerItem(scrollFireBolt);
		registerItem(new Keks());

		IItemMeleeWeapon diamondSword = (IItemMeleeWeapon) GameData.getItemRegistry().getObjectById(276);
		Map<IAttribute, Float> requiredsMap = new HashMap<>();
		requiredsMap.put(SharedMonsterAttributes.attackDamage, 50.0F);
		diamondSword.setRequireds(requiredsMap);
		diamondSword.setExtraDamage(GGMBattleSystem.fire, (IGGMAttribute) SharedMonsterAttributes.maxHealth,0.4F, 1.0F);
    }
    
    public static void registerItem(Item item) {    	
        GameRegistry.registerItem(item, item.getUnlocalizedName());
    }
    
    public static void registerRune(ItemMagicCast rune) {
    	registerItem(rune);
    	runesList.add(rune);
    }
    
    /*private static void registerRestorePotions() {
    	staminaRestorePotion = new ItemRestorePotion("small_stamina_potion", new RestoreValues().setRestoreValues(EntityAttributes.stamina, 50.0F, 0.0F));
    	registerItem(staminaRestorePotion);    	
    	
    	manaRestorePotion = new ItemRestorePotion("small_mana_potion",  new RestoreValues().setRestoreValues(EntityAttributes.mana, 50.0F, 0.0F));
    	registerItem(manaRestorePotion);
    }*/
    
}
