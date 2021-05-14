package mrfinger.gothicgamemod.mixin.entity;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.AnimationEntityLiving;
import mrfinger.gothicgamemod.entity.animations.IAnimation;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.effects.IGGMEffect;
import mrfinger.gothicgamemod.entity.capability.effects.IGGMEffectInstance;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityLivingBase.class)
public abstract class GGMEntityLivingBase extends GGMEntity implements IGGMEntityLivingBase {


    private int 							lvl;
    private boolean							needExpUpdate;

	private int 							disSprintTimer;

    @Shadow private BaseAttributeMap 		attributeMap;


    protected Map<IGGMEffect, IGGMEffectInstance> tickingEffectsMap;
	protected Map<IGGMEffect, IGGMEffectInstance> attackEffectsMap;
	protected Map<IGGMEffect, IGGMEffectInstance> otherEffectsMap;


    protected IAnimation currentAnimation;
    protected IAnimation defaulAnimation;


	@Shadow protected float 				lastDamage;


    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setHealth(F)V"))
	private void deleteHealthing(EntityLivingBase entity, float value)
	{
		this.restoreCurrentValuesFull();
	}

	@Inject(method = "<init>*", at = @At(value = "RETURN"))
	private void init(CallbackInfo ci)
	{
		this.lvl = this.initialLevel();
		this.flagForLvlUpdate();

		this.tickingEffectsMap 	= new HashMap<>();
		this.attackEffectsMap 	= new HashMap<>();
		this.otherEffectsMap	= new HashMap<>();

		this.defaulAnimation = new AnimationEntityLiving(this);
		this.currentAnimation = this.getDefaultAnimation();
	}


	@Inject(method = "writeEntityToNBT", at = @At("TAIL"))
	private void onWriteEntityToNBT(NBTTagCompound nbt, CallbackInfo ci)
	{
		this.saveExp(nbt);
	}

	@Inject(method = "readEntityFromNBT", at = @At("TAIL"))
	private void onReadEntityToNBT(NBTTagCompound nbt, CallbackInfo ci)
	{
		this.loadExp(nbt);
	}


	@Override
	public int getLvl() {
		return lvl;
	}

	@Override
	public void setLvl(int lvl)
	{
		if (lvl >= 0) {

			if (lvl != this.lvl) {

				this.flagForLvlUpdate();
				this.lvl = lvl;
			}
		}
		else {
			throw new IllegalArgumentException("Level of entity cannot be less than 0");
		}
	}

	@Override
	public void saveExp(NBTTagCompound nbt)
	{
		nbt.setInteger("LVL", this.lvl);
	}

	@Override
	public void loadExp(NBTTagCompound nbt)
	{
		this.setLvl(nbt.getInteger("LVL"));
	}


	@Override
	public void restoreCurrentValuesFull()
	{
    	this.setHealth(this.getMaxHealth());
	}


	@Override
	public IGGMDynamicAttributeInstance getDP(IGGMAttribute attribute) {
		return ((IGGMBaseAttributeMap) this.attributeMap).getDPI(attribute);
	}


	@Override
	public IAttributeInstance getHealthAttribute() {
		return this.attributeMap.getAttributeInstance(SharedMonsterAttributes.maxHealth);
	}


	@Inject(method = "onLivingUpdate", at = @At("HEAD"))
	private void onOnLivingUpdate(CallbackInfo ci)
	{
		for (IGGMEffectInstance effect : this.tickingEffectsMap.values())
		{
			effect.onEntityUpdate();
		}

		if (this.currentAnimation != null) this.currentAnimation.onUpdate();

    	if (this.disSprintTimer > 0 ) --this.disSprintTimer;
	}

	@ModifyArg(method = "onEntityUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setAir(I)V", ordinal = 2))
	private int modifyMaxAir(int air) {
		return this.getMaxAir();
	}


	@Override
	public Map<IGGMEffect, IGGMEffectInstance> getEffectsMap()
	{
		return this.tickingEffectsMap;
	}

	@Override
	public Map<IGGMEffect, IGGMEffectInstance> getAttackEffectsMap()
	{
		return attackEffectsMap;
	}

	@Override
	public Map<IGGMEffect, IGGMEffectInstance> getOtherEffectsMap()
	{
		return otherEffectsMap;
	}


	@Override
	public IAnimation getCurrentAnimation() {
		return this.currentAnimation;
	}

	@Override
	public IAnimation getDefaultAnimation()
	{
		return this.defaulAnimation;
	}

	@Override
	public void setAnimation(IAnimation animation)
	{
		this.currentAnimation = this.currentAnimation.onSetNewAnimation(animation);

		if (this.currentAnimation == null) this.currentAnimation = this.getDefaultAnimation();

		this.currentAnimation.setEntity(this);
	}

	@Override
	public boolean endAnimation()
	{
		IAnimation old = this.getCurrentAnimation();

		if (old.canEndAnimation())
		{
			this.currentAnimation = this.getDefaultAnimation();
			old.onEndAnimation();
			return true;
		}

		return false;
	}

	@Override
	public int getMaxAir() {
		return 300;
	}


	public void onKillEntity(EntityLivingBase entity)
	{
		for (IGGMEffectInstance effect : this.attackEffectsMap.values())
		{
			effect.onKillEntity((IGGMEntityLivingBase) entity);
		}
	}


	@Inject(method = "attackEntityFrom", at = @At(value = "JUMP", ordinal = 1), cancellable = true)
	private void cancelCancel(DamageSource ds, float damage, CallbackInfoReturnable<Boolean> ci)
	{
		boolean cancel = false;

		for (IGGMEffectInstance effect : this.attackEffectsMap.values())
		{
			if (effect.onAttackEntityFrom((IGGMDamageSource) ds, damage)) cancel = true;
		}

		if (cancel) ci.setReturnValue(true);

    	if (((IGGMDamageSource) ds).isSettedValues()) {
    		this.lastDamage = 0.0F;
		}
	}

	@Inject(method = "onDeath", at = @At(value = "JUMP", ordinal = 1))
	private void onDeathOfEntity(DamageSource damageSource, CallbackInfo ci)
	{
		for (IGGMEffectInstance effect : this.attackEffectsMap.values())
		{
			effect.onDeath((IGGMDamageSource) damageSource);
		}
	}


	@Inject(method = "applyArmorCalculations", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/EntityLivingBase;damageArmor(F)V"), cancellable = true)
	private void onApplyArmorCalculations(DamageSource ds, float damage, CallbackInfoReturnable<Float> cir) {

    	IGGMDamageSource gds = (IGGMDamageSource) ds;

    	if (gds.isSettedValues()) {

    		damage = 0.0F;
    		int a = this.getTotalArmorValue();
    		Map<DamageType, Float> map = gds.getDamageValuesMap();

    		for (Map.Entry<DamageType, Float> e : map.entrySet()) {

    			float f = e.getValue() - a;
    			if (f > 0.0F) damage += a;
			}

    		cir.setReturnValue(damage);
		}
	}

	/*@Override
	public IGGMSkillInstance getSkill(EntitySkills name) {
		return this.skillsMap.getValue(name);
	}*/


	@Inject(method = "jump", at = @At(value = "HEAD"), cancellable = true)
	private void onJumpHead(CallbackInfo ci)
	{
		if (this.currentAnimation.denyJump()) ci.cancel();
	}


	@Redirect(method = "jump", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;motionY:D", ordinal = 0))
	private void jumpFix(EntityLivingBase entity, double motionY)
	{
		double origin = this.jumpHeight();
		double newMotion = origin;

		for (IGGMEffectInstance effect : this.otherEffectsMap.values())
		{
			newMotion = effect.onJump(origin, newMotion);
		}

		entity.motionY = newMotion;
	}


	@Override
	public void setDisallowSprintTimer(int timer) {
		this.disSprintTimer = timer;
		if (this.isSprinting() && timer > 0) {
			this.setSprinting(false);
		}
	}

	@Override
	public int getDisallowSprintTimer() {
		return this.disSprintTimer;
	}

	@ModifyVariable(method = "setSprinting", at = @At(value = "HEAD", ordinal = 0))//"INVOKE", target = "Lnet/minecraft/entity/Entity;setSprinting(Z)V"))
	private boolean modifySprinting(boolean b) {

		return b && this.isCanSprint();
	}

	/*@Inject(method = "setSprinting", at = @At("RETURN"))//value = "JUMP", ordinal = 1), cancellable = true)
	private void fixSprinting(boolean b, CallbackInfo ci) {

		//if (thisEntity().worldObj.isRemote) ci.cancel();
		if (!thisEntity().isSprinting() && thisEntity().getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D")) != null) throw (new NullPointerException("AAAAAAAAAAAAAAAAAAA"));
	}*/


	@Override
	public void justAttack(Entity entity, float distance) {}

	@Shadow @Override public abstract void setSprinting(boolean sprinting);

	@Shadow public abstract BaseAttributeMap getAttributeMap();

    @Shadow public abstract IAttributeInstance getEntityAttribute(IAttribute p_110148_1_);

	@Shadow public abstract void setHealth(float p_70606_1_);

	@Shadow public abstract float getMaxHealth();

    @Shadow public int attackTime;

    @Inject(method = "knockBack", at = @At("HEAD"), cancellable = true)
	private void onKnockback(Entity knockbacker, float power, double x, double y, CallbackInfo ci) {


	}

	@Override
	public int attackDuration() {
		return 20;
	}

	@Override
	public float attackDistance() {
		return 2.0F;
	}

	@Override
	public void flagForLvlUpdate() {
		this.needExpUpdate = true;
	}

	@Override
	public boolean isNeedExpUpdate()
	{
		if (this.needExpUpdate)
		{
			this.needExpUpdate = false;
			return true;
		}
		return false;
	}


	@ModifyVariable(method = "fall", at = @At(value = "TAIL"), ordinal = 0)
	private int fixFallDamage(int i)
	{
		if (i >= 0 && !this.worldObj.isRemote)
		{
			System.out.println("Debug in GGMENtityLivingBase fall " + this.motionX + " " + this.motionY + " " + this.motionZ);
			//throw new IllegalArgumentException("Just im farting");
		}

		return i;
	}


	@Override
	protected boolean cancelMount(IGGMEntity entity)
	{
		boolean flag = false;

		for (IGGMEffectInstance effect : this.otherEffectsMap.values())
		{
			if (effect.onMountEntity(entity)) flag = true;
		}

		flag = this.currentAnimation.denyMount(entity, flag);

		return flag;
	}



	/*@Override
	public void immobilize() {
		if (this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(this.immobilizeModifierID) == null)
			this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(this.immobilizeModifier);
	}

	@Override
	public void returnMobility() {
		this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(this.immobilizeModifier);		
	}*/
	
	
	
	/*public void setCastDuration(Spell spell, ItemStack item, int time, float takeMana) {
		if (this.castDuration <= 0) {
			if(!entity.worldObj.isRemote) {
				this.castingSpell = spell;
				this.item = item;
				this.castDuration = time;
				this.takingManaAfterSpell = takeMana;
				this.immobilize();
			}
		}
	}
	
	public void nullifyCastingSpell() {
		this.castingSpell = null;
		this.item = null;
		this.castDuration = 0;
		this.takingManaAfterSpell = 0.0F;
		this.returnMobility();
	}
	
	public void finishCast(ItemStack item) {
		this.statsMap.get(EntityAttributes.mana);
		this.castingSpell.createSpell();		
		if ((item != null && ((ItemMagicCast) this.item.getItem()).expendable()) && !(this.entity instanceof EntityPlayer && ((EntityPlayer)this.entity).capabilities.isCreativeMode)) item.stackSize--;
		this.nullifyCastingSpell();
	}
	
	public ItemStack getUsingItemStack() {
		return this.item;
	}*/




	/*@Override
	public boolean isCastingSpell() {
		return this.castDuration > 0;
	}*/


}
