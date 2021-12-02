package mrfinger.gothicgamemod.init;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.data.entity.EntityCapabilitiesData;
import mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo.*;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.GGMDynamicRangedAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import java.util.*;

public class GGMCapabilities
{

	//public static final Map<Class<? extends EntityLivingBase>, EntityData> entityDataMap = new HashMap<Class<? extends EntityLivingBase>, EntityData>();

	public static final Map<IAttribute, Float> expGainFromAttributesMap = new HashMap<>();

	private static final Map<IAttribute, IAttributeInfo> defaultAttributeInfoMap = new HashMap<>();


	public static final IGGMAttribute maxHealthDynamic = ((IGGMAttribute) new GGMDynamicRangedAttribute(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), SharedMonsterAttributes.maxHealth.getDefaultValue(), 0.0D, Double.MAX_VALUE, 50).setDescription(((RangedAttribute) SharedMonsterAttributes.maxHealth).getDescription()).setShouldWatch(true));
	public static final IGGMAttribute maxStamina 		= ((IGGMAttribute) new GGMDynamicRangedAttribute("generic.maxStamina", 20.0D, 0.0D, Double.MAX_VALUE, 50).setDescription("Max Stamina").setShouldWatch(true));
	public static final IGGMAttribute maxMana 			= ((IGGMAttribute) new GGMDynamicRangedAttribute("generic.maxMana", 20.0D, 0.0D, Double.MAX_VALUE, 10).setDescription("Max Mana").setShouldWatch(true));
	public static final IGGMAttribute dexterity 		= ((IGGMAttribute) new RangedAttribute("generic.dexterity", 2.0D, 0.0D, Double.MAX_VALUE).setDescription("Dexterity").setShouldWatch(true));
	public static final IGGMAttribute intelligence 		= ((IGGMAttribute) new RangedAttribute("generic.intelligence", 2.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Stamina").setShouldWatch(true));

	public static final Set<IAttribute> set = new HashSet<>();


	public static final IAttributeInfoDynamic maxHealthAIPlayer = new DynamicIncreasableAttributeInfo(20.0F, 10000.0D, 10.0F, 500.0F, (byte) 4, 0.0F, 0.02F, 1.0F, 10.0F, (byte) 5);
	public static final IAttributeInfoDynamic maxStaminaAIPlayer = new DynamicIncreasableAttributeInfo(20.0F, 10000.0D, 10.0F, 500.0F, (byte) 4, 1.0F, 0.1F, 5.0F, 50.0F, (byte) 5);
	public static final IAttributeInfoDynamic maxManaAIPlayer = new DynamicIncreasableAttributeInfo(20.0F, 10000.0D, 10.0F, 500.0F, (byte) 4, 0.1F, 0.05F, 1.0F, 10.0F, (byte) 5);
	public static final IAttributeInfo StrenghtAIPlayer = new IncreasableAttributeInfo(5.0F, 1000.0D, 1.0F, 50.0F, (byte) 4).addBonus(SharedMonsterAttributes.knockbackResistance, 0.0005F);
	public static final IAttributeInfo DexterityAIPlayer = new IncreasableAttributeInfo(5.0F, 1000.0D, 1.0F, 50.0F, (byte) 4).addBonus(SharedMonsterAttributes.movementSpeed, 0.0005F);
	public static final IAttributeInfo IntelligenceAIPlayer = new IncreasableAttributeInfo(5.0F, 1000.0D, 1.0F, 50.0F, (byte) 4);

	public static final DynamicAttributeInfo maxStaminaAIScavenger = new DynamicAttributeInfo(50.0F, 1.5F);
	public static final DynamicAttributeInfo maxStaminaAIGiantRat = new DynamicAttributeInfo(30.0F, 3.0F);


	public static final String maxHealthS = "Health";
	public static final String maxStaminaS = "Stamina";
	public static final String maxManaS = "Mana";
	public static final String strenghtS = "Strenght";
	public static final String dexterityS = "Dexterity";
	public static final String intelligenceS = "Intelligence";

	public static final Map<String, IGGMAttribute> AttributesMapByString = new HashMap<>(6, 1.0F);
	//public static final List<String> attributesCommandNamesList = new LinkedList<String>();


	public static void preLoad(FMLPreInitializationEvent e)
	{
		loadGGMAttributes();
		EntityCapabilitiesData.loadData();
	}

	private static void loadGGMAttributes()
	{
		((IGGMAttribute) SharedMonsterAttributes.knockbackResistance).setMaxValue(Double.MAX_VALUE);

		IGGMAttribute maxHealth = (IGGMAttribute) SharedMonsterAttributes.maxHealth;

		IGGMAttribute attackDamage = (IGGMAttribute) SharedMonsterAttributes.attackDamage;
		((RangedAttribute) attackDamage).setShouldWatch(true);


		set.add(maxHealth);
		set.add(maxStamina);
		set.add(maxMana);
		set.add(attackDamage);
		set.add(dexterity);
		set.add(intelligence);

		registerAttribute((IGGMAttribute) SharedMonsterAttributes.maxHealth, maxHealthS, 12F);
		registerAttribute((IGGMAttribute) SharedMonsterAttributes.followRange, SharedMonsterAttributes.followRange.getAttributeUnlocalizedName(), 0F);
		registerAttribute((IGGMAttribute) SharedMonsterAttributes.knockbackResistance, SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName(), 1F);
		registerAttribute((IGGMAttribute) SharedMonsterAttributes.movementSpeed, SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), 0.5F);
		registerAttribute((IGGMAttribute) SharedMonsterAttributes.attackDamage, strenghtS, 2F);
		registerAttribute(maxStamina, maxStaminaS, 12F);
		registerAttribute(maxMana, maxManaS, 12F);
		registerAttribute(dexterity, dexterityS, 2F);
		registerAttribute(intelligence, intelligenceS, 2F);

	}

	private static IAttributeInfo registerAttribute(IGGMAttribute attribute, String commandName, Float valueForLevel)
	{
		IAttributeInfo attributeInfo = new BaseAttributeInfo(attribute.getDefaultValue(), attribute.getMaxValue());
		defaultAttributeInfoMap.put(attribute, attributeInfo);
		AttributesMapByString.put(commandName, attribute);
		//attributesCommandNamesList.add(commandName);
		if (valueForLevel > 0F)
		{
			expGainFromAttributesMap.put(attribute, 1F / valueForLevel);
		}
		else
		{
			expGainFromAttributesMap.put(attribute, 0F);
		}
		return attributeInfo;
	}

	@SideOnly(Side.CLIENT)
	public static void loadClient()
	{

	}

	public static void postLoad(FMLPostInitializationEvent e)
	{

	}


	public static IAttributeInfo getDefaulAttributeInfo(IGGMAttribute attribute)
	{
		IAttributeInfo attributeInfo = defaultAttributeInfoMap.get(attribute);
		return attributeInfo != null ? attributeInfo : registerAttribute(attribute, attribute.getAttributeUnlocalizedName(), 0F);
	}


	/*public static void registerNewData(Class<? extends EntityLivingBase> entityClass, int lvl, IncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap) {
		entityDataMap.put(entityClass, new EntityData(lvl, statsMap, skillsMap));
	}

	public static void registerNewData(Class<? extends EntityLivingBase> entityClass, int lvl, IncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap, int expToNewLvl, int lPFromNewLvl) {
		entityDataMap.put(entityClass, new EntityData(lvl, statsMap, skillsMap, true, expToNewLvl, lPFromNewLvl));
	}


	public static EntityData getEntityData(Class<? extends EntityLivingBase> entityClass) {
		return entityDataMap.get(entityClass);
	}

	public static class EntityData {


		public boolean 		isHaveExp;

		public int			lvl, exp, lP;

		public IncreasableAttributeInfo[] 	statsMap;
		public GothicSkill[]	skillsMap;


		public EntityData(int lvl, IncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap) {

			this(lvl, statsMap, skillsMap, false, 0, 0);
		}

		public EntityData(int lvl, IncreasableAttributeInfo[] statsMap, GothicSkill[] skillsMap, boolean isHaveExp, int expToNewLvl, int lPFromNewLvl) {
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

		public int getExpValue() {
			return exp;
		}

		public int getlP() {
			return lP;
		}

		public IncreasableAttributeInfo[] getStatsMap() {
			return statsMap;
		}

		public GothicSkill[] getSkillsMap() {
			return skillsMap;
		}
	}*/
}
