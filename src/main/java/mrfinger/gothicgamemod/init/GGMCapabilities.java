package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.entity.capability.attributes.GGMDPAttributeInfo;
import mrfinger.gothicgamemod.entity.capability.attributes.GGMIncreasableAttributeInfo;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.data.GothicSkill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GGMCapabilities {


	public static final Map<Class<? extends EntityLivingBase>, EntityData> entityDataMap = new HashMap<Class<? extends EntityLivingBase>, EntityData>();

	public static final Map<IAttribute, Float> expGainFromAttributesMap = new HashMap<>();


	public static final IGGMAttribute maxHealth 		= ((IGGMAttribute) new RangedAttribute(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), SharedMonsterAttributes.maxHealth.getDefaultValue(), 0.0D, Double.MAX_VALUE).setDescription(((RangedAttribute) SharedMonsterAttributes.maxHealth).getDescription()).setShouldWatch(true)).setHaveDP(true);
	public static final IGGMAttribute maxStamina 		= ((IGGMAttribute) new RangedAttribute("generic.maxStamina", 20.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Stamina").setShouldWatch(true)).setHaveDP(true);
	public static final IGGMAttribute maxMana 			= ((IGGMAttribute) new RangedAttribute("generic.maxMana", 20.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Mana").setShouldWatch(true)).setHaveDP(true);
	public static final IGGMAttribute dexterity 		= ((IGGMAttribute) new RangedAttribute("generic.dexterity", 2.0D, 0.0D, Double.MAX_VALUE).setDescription("Dexterity").setShouldWatch(true));
	public static final IGGMAttribute intelligence 		= ((IGGMAttribute) new RangedAttribute("generic.intelligence", 2.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Stamina").setShouldWatch(true));

	public static final Set<IAttribute> set = new HashSet<>();


	public static final GGMDPAttributeInfo maxHealthAIPlayer = new GGMDPAttributeInfo(20.0F, 10000.0D, 10.0F, 500.0F, (byte) 4, 0.0F, 0.02F, 1.0F, 10.0F, (byte) 5);
	public static final GGMDPAttributeInfo maxStaminaAIPlayer = new GGMDPAttributeInfo(20.0F, 10000.0D, 10.0F, 500.0F, (byte) 4, 1.0F, 0.1F, 5.0F, 50.0F, (byte) 5);
	public static final GGMDPAttributeInfo maxManaAIPlayer = new GGMDPAttributeInfo(20.0F, 10000.0D, 10.0F, 500.0F, (byte) 4, 0.1F, 0.05F, 1.0F, 10.0F, (byte) 5);
	public static final GGMIncreasableAttributeInfo AIPlayer = new GGMIncreasableAttributeInfo(5.0F, 1000.0D, 1.0F, 50.0F, (byte) 4);

	public static final GGMDPAttributeInfo maxStaminaAIScavenger = new GGMDPAttributeInfo(20.0F, 1.5F);



	public static final String maxHealthS = "Health";
	public static final String maxStaminaS = "Stamina";
	public static final String maxManaS = "Mana";
	public static final String strenghtS = "Strenght";
	public static final String dexterityS = "Dexterity";
	public static final String intelligenceS = "Intelligence";

	public static final Map<String, IGGMAttribute> AttributesMapByString = new HashMap<>(6, 1.0F);
	static {
		AttributesMapByString.put(maxHealthS, maxHealth);
		AttributesMapByString.put(maxStaminaS, maxStamina);
		AttributesMapByString.put(maxManaS, maxMana);
		AttributesMapByString.put(strenghtS, (IGGMAttribute) SharedMonsterAttributes.attackDamage);
		AttributesMapByString.put(dexterityS, dexterity);
		AttributesMapByString.put(intelligenceS, intelligence);
	}


	public static void load(FMLPreInitializationEvent e) {

		expGainFromAttributesMap.put(SharedMonsterAttributes.maxHealth, 12.0F);
		expGainFromAttributesMap.put(SharedMonsterAttributes.movementSpeed, 0.5F);
		expGainFromAttributesMap.put(SharedMonsterAttributes.attackDamage, 2.0F);
		expGainFromAttributesMap.put(SharedMonsterAttributes.knockbackResistance, 1.0F);
		expGainFromAttributesMap.put(dexterity, 2.0F);
		expGainFromAttributesMap.put(intelligence, 2.0F);
		expGainFromAttributesMap.put(maxStamina, 12.0F);
		expGainFromAttributesMap.put(maxMana, 12.0F);
		loadGGMAttributes();

	}

	@SideOnly(Side.CLIENT)
	public static void loadClient() {

	}

	public static void postLoad(FMLPostInitializationEvent e) {


	}


	public static void registerNewData(Class<? extends EntityLivingBase> entityClass, int lvl, GGMIncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap) {
		entityDataMap.put(entityClass, new EntityData(lvl, statsMap, skillsMap));
	}

	public static void registerNewData(Class<? extends EntityLivingBase> entityClass, int lvl, GGMIncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap, int expToNewLvl, int lPFromNewLvl) {
		entityDataMap.put(entityClass, new EntityData(lvl, statsMap, skillsMap, true, expToNewLvl, lPFromNewLvl));
	}


	public static EntityData getEntityData(Class<? extends EntityLivingBase> entityClass) {
		return entityDataMap.get(entityClass);
	}

	public static void loadGGMAttributes() {

		((RangedAttribute) SharedMonsterAttributes.knockbackResistance).maximumValue = Double.MAX_VALUE;

		IGGMAttribute maxHealth = (IGGMAttribute) SharedMonsterAttributes.maxHealth;
		maxHealth.setHaveDP(true);

		IGGMAttribute attackDamage = (IGGMAttribute) SharedMonsterAttributes.attackDamage;
		((RangedAttribute) attackDamage).setShouldWatch(true);
		attackDamage.setBonus(SharedMonsterAttributes.knockbackResistance, 0.0005F);

		dexterity.setBonus(SharedMonsterAttributes.movementSpeed, 0.0005F);

		set.add(maxHealth);
		set.add(maxStamina);
		set.add(maxMana);
		set.add(attackDamage);
		set.add(dexterity);
		set.add(intelligence);
	}

	public static class EntityData {


		public boolean 		isHaveExp;

		public int			lvl, exp, lP;

		public GGMIncreasableAttributeInfo[] 	statsMap;
		public GothicSkill[]	skillsMap;


		public EntityData(int lvl, GGMIncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap) {

			this(lvl, statsMap, skillsMap, false, 0, 0);
		}

		public EntityData(int lvl, GGMIncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap, boolean isHaveExp, int expToNewLvl, int lPFromNewLvl) {
			this.lvl = lvl;
			this.statsMap = statsMap;
			this.skillsMap = skillsMap;
			this.isHaveExp = isHaveExp;
			this.exp = expToNewLvl;
			this.lP = lPFromNewLvl;
		}

		public boolean isHaveExp() {
			return isHaveExp;
		}

		public int getLvl() {
			return lvl;
		}

		public int getExp() {
			return exp;
		}

		public int getlP() {
			return lP;
		}

		public GGMIncreasableAttributeInfo[] getStatsMap() {
			return statsMap;
		}

		public GothicSkill[] getSkillsMap() {
			return skillsMap;
		}
	}
}
