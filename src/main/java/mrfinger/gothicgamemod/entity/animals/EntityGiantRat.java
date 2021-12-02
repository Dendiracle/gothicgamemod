package mrfinger.gothicgamemod.entity.animals;

import mrfinger.gothicgamemod.GothicMain;
import mrfinger.gothicgamemod.entity.capability.attribute.attributeinfo.IAttributeInfoDynamic;
import mrfinger.gothicgamemod.entity.packentities.EntityGothicAnimal;
import mrfinger.gothicgamemod.fractions.PackFraction;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMFractions;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityGiantRat extends EntityGothicAnimal
{

	public EntityGiantRat(World world)
	{
		super(world);
	}


	@Override
	public PackFraction getStandartFraction()
	{
		return GGMFractions.rats;
	}


	@Override
	protected IAttributeInfoDynamic getNewStaminaAI()
	{
		return GGMCapabilities.maxStaminaAIGiantRat;
	}


	@Override
	public double getWanderSpeedModifier()
	{
		return 0.5D;
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z)
	{
		Block block = this.worldObj.getBlock(x, y - 1, z);

		if (block == Blocks.grass)
		{
			return 10.0F;
		}
		else if (block == Blocks.dirt)
		{
			return 8.0F;
		}

		return -1.0F;
	}


	@Override
	protected String getLivingSound()
	{
		return GothicMain.MODID + ":rat_living";
	}

	@Override
	protected String getEatSound()
	{
		return null;
	}

	@Override
	protected String getWarnSound() {
		return null;
	}


	@Override
	public int getNewBornGrowthAge() {
		return -160;
	}

	@Override
	public int getMaxGrowth() {
		return 100;
	}

	@Override
	public int getChildBirthNeedsGrowth() {
		return 40;
	}

	@Override
	public boolean isViviparous() {
		return true;
	}

	@Override
	public float getStandartWidth() {
		return 0.35F;
	}

	@Override
	public float getStandartHeight() {
		return 0.35F;
	}
}
