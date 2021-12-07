package mrfinger.gothicgamemod.mixin.common.entity.player;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationHit;
import mrfinger.gothicgamemod.entity.capability.attribute.generic.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attribute.map.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attribute.instance.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.effect.instance.GGMEffectInstanceDynamicAttributeController;
import mrfinger.gothicgamemod.entity.effect.instance.IGGMEffectInstance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMPlayerEquipmentAnimationHelperFightStance;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMEffects;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.item.IItemBlocker;
import mrfinger.gothicgamemod.item.IItemEquip;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import mrfinger.gothicgamemod.mixin.common.entity.GGMEntityLivingBase;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityPlayer.class)
public abstract class GGMEntityPlayer extends GGMEntityLivingBase implements IGGMEntityPlayer
{

    @Shadow
    public PlayerCapabilities capabilities;

    protected long currentExp;
    protected long toNextLvl;
    protected int lp;

    protected IGGMDynamicAttributeInstance health;
    protected IGGMDynamicAttributeInstance stamina;
    protected IGGMDynamicAttributeInstance mana;

    //protected Map<IGGMEffect, IGGMEffectInstance> useItemEffectsMap;

    @Shadow
    private ItemStack itemInUse;
    @Shadow
    private int itemInUseCount;

    @Shadow
    public InventoryPlayer inventory;


    @Shadow
    protected abstract void updateItemUse(ItemStack p_71010_1_, int p_71010_2_);

    @Shadow
    public abstract void stopUsingItem();

    @Shadow
    public abstract boolean isPlayerSleeping();

    protected IGGMPlayerEquipmentAnimationHelperFightStance equpmentAndFightAnim;

    protected Container ggmContainerEquipment;

    protected byte attackCooldown;


    @Inject(method = "<init>*", at = @At(value = "RETURN"))
    private void init(CallbackInfo ci)
    {
        this.toNextLvl = this.needToNextLvlIncreases();
        //this.useItemEffectsMap 	= new HashMap<>();
    }


    @Override
    public int initialLevel()
    {
        return 0;
    }


    @Override
    public void setLvl(int lvl)
    {
        super.setLvl(lvl);

        this.setExpValueFromLvl(this.getLvl());
    }


    @Override
    public long getExpValue()
    {
        return currentExp;
    }

    @Override
    public long getExpValueForNewLvl()
    {
        return this.toNextLvl;
    }

    @Override
    public int getLPValue()
    {
        return this.lp;
    }


    public void setExpValue(long value)
    {
        if (value != this.currentExp)
        {

            if (value <= 0)
            {
                this.currentExp = 0;
                this.toNextLvl = this.needToNextLvlIncreases();
                this.setLvl(0);
                this.flagForLvlUpdate();
                return;
            }

            final int needToNextLvlIncreases = this.needToNextLvlIncreases();
            int lvl = this.lvl;
            long toCurrentLvl = this.toNextLvl - (1 + lvl) * needToNextLvlIncreases;
            if (value >= this.toNextLvl)
            {
                toCurrentLvl = this.toNextLvl;

                do
                {
                    ++lvl;
                    toCurrentLvl += (lvl + 1) * needToNextLvlIncreases;
                }
                while (value >= toCurrentLvl);

                this.toNextLvl = toCurrentLvl;
                this.setLvl(lvl);
            } else if (value < toCurrentLvl)
            {
                do
                {
                    toCurrentLvl -= lvl * needToNextLvlIncreases;
                    --lvl;
                }
                while (value < toCurrentLvl);

                this.toNextLvl = toCurrentLvl + (1 + lvl) * needToNextLvlIncreases;
                this.setLvl(lvl);
            }

            this.currentExp = value;
            this.flagForLvlUpdate();
        }
    }

    protected int needToNextLvlIncreases()
    {
        return 500;
    }

    protected int lpFromNextLvl()
    {
        return 10;
    }

    @Override
    public void changeExpValue(long changeValue)
    {
        this.setExpValue(this.currentExp + changeValue);
    }

    @Override
    public int gainExp(int exp)
    {
        if (exp < 0)
        {
            throw new IllegalArgumentException("Entity cannot gains experience less than 0");
        }

        int simpleLvl = this.lvl;
        System.out.println("debug in GGMEntityPlayer " + this.lvl + " " + lp);
        this.changeExpValue(exp);
        if (this.lvl > simpleLvl)
        {
            simpleLvl = this.lvl - simpleLvl;
            this.changeLP((simpleLvl) * this.lpFromNextLvl());
            System.out.println(this.lvl + " " + lp);
            return simpleLvl;
        }
        return 0;
    }


    @Override
    public long setExpValueFromLvl(int lvl)
    {
        if (this.lvl == lvl)
        {
            return this.currentExp;
        }

        long toNL = this.needToNextLvlIncreases() * (lvl + 1);
        this.currentExp = (toNL * lvl) / 2;
        this.toNextLvl = this.currentExp + toNL;
        this.flagForLvlUpdate();

        return this.currentExp;
    }

    @Override
    public void setLP(int lp)
    {
        if (lp != this.lp)
        {
            this.lp = lp > 0 ? lp : 0;
            this.flagForLvlUpdate();
        }
    }

    @Override
    public void changeLP(int changeValue)
    {
        this.setLP(this.lp + changeValue);
    }


    public void saveNBTData(NBTTagCompound nbt)
    {
        nbt.setLong("EXP", this.currentExp);
        nbt.setInteger("LP", this.lp);
    }

    public void loadNBTData(NBTTagCompound nbt)
    {
        this.setExpValue(nbt.getLong("EXP"));
        this.setLP(nbt.getInteger("LP"));
    }


    @Redirect(method = "applyEntityAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;registerAttribute(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;"))
    private IAttributeInstance onApplyAttributes(BaseAttributeMap map, IAttribute attribute)
    {
        IGGMBaseAttributeMap gmap = (IGGMBaseAttributeMap) map;

        this.health = (IGGMDynamicAttributeInstance) gmap.getAttributeInstance(GGMCapabilities.maxHealthDynamic);
        //this.health.applyRegenModifier(new RegenModifierInstanceSimple(0, 0, false, 0.001F));
        //this.health.applyRegenModifier(new RegenModifierInstanceSimple(0, 1, false, 0.001F));
        this.stamina = (IGGMDynamicAttributeInstance) gmap.registerAttribute(GGMCapabilities.maxStamina);
        //this.stamina.applyRegenModifier(new RegenModifierInstanceSimple(0, 0, false, 0.1F));
        //this.stamina.applyRegenModifier(new RegenModifierInstanceSimple(0, 1, false, 0.05F));
        this.mana = (IGGMDynamicAttributeInstance) gmap.registerAttribute(GGMCapabilities.maxMana);
        // this.mana.applyRegenModifier(new RegenModifierInstanceSimple(0, 0, false, 0.001F));
        //this.mana.applyRegenModifier(new RegenModifierInstanceSimple(0, 1, false, 0.001F));


        IAttributeInstance strenght = gmap.registerAttribute(SharedMonsterAttributes.attackDamage);
        gmap.registerAttribute(GGMCapabilities.dexterity);
        gmap.registerAttribute(GGMCapabilities.intelligence);
        gmap.resizeCollections();
        return strenght;
    }

    protected IAttribute getGenericMaxHealthAttribute()
    {
        return GGMCapabilities.maxHealthDynamic;
    }


    /*
    * Don't delete!
    */
    @Redirect(method = "applyEntityAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;setBaseValue(D)V"))
    private void fixStrengtRegistry(IAttributeInstance attributeInstance, double value)
    {

    }


    @Override
    public Fraction getStandartFraction()
    {
        return GGMFractions.humans;
    }

    @Override
    public void restoreCurrentValuesFull()
    {
        ((GGMEffectInstanceDynamicAttributeController) this.getEffect(GGMEffects.DynamicAttributeController)).restoreDP();
    }


    @Override
    public void saveExp(NBTTagCompound nbt)
    {
        this.saveNBTData(nbt);
    }

    @Override
    public void loadExp(NBTTagCompound nbt)
    {
        this.loadNBTData(nbt);
    }


    @Override
    public IGGMDynamicAttributeInstance getHealthAttribute()
    {
        return this.health;
    }

    @Override
    public float getHealth()
    {
        return (float) this.health.getDynamicValue();
    }

    @Override
    public void setHealth(float value)
    {
        this.getHealthAttribute().setDynamicValue(value);
    }

    @Override
    public float getMaxHealth()
    {
        return (float) this.health.getAttributeValue();
    }


    @Override
    public IGGMDynamicAttributeInstance getStaminaAttribute()
    {
        return this.stamina;
    }


    @Override
    public IGGMDynamicAttributeInstance getManaAttribute()
    {
        return this.mana;
    }


    /*@Inject(method = "onLivingUpdate", at = @At("TAIL"))
    private void onOnLivingUpdate(CallbackInfo ci)
    {
        if (!thisEntity().worldObj.isRemote) {

            if (!this.isDead) {

                if (this.ticksExisted % 10 == 0) {

                    for (IGGMDynamicAttributeInstance dpi : ((IGGMBaseAttributeMap) this.getAttributeMap()).getDPIColl()) {

                        dpi.onUpdate();
                    }
                }
            }
        }
    }*/

    @Override
    public void staminaMoveUpdate()
    {
        if (this.isSprinting())
        {
            IGGMDynamicAttributeInstance stamina = this.getStaminaAttribute();
            double d = this.getStaminaSpendingOnSprint();
            double dd = stamina.getDynamicValue();

            if (dd < d)
            {
                this.setSprinting(false);
            } else if (!thisEntity().worldObj.isRemote)
            {
                stamina.changeCurrentValue(-d);
            }
        }
    }


    @Override
    public boolean setActiveAnimationHelperDirectly(String animationName)
    {
        if (animationName.equals(this.equpmentAndFightAnim.getUnlocalizedName()))
        {
            if (this.activeAnimationHelper != this.equpmentAndFightAnim)
            {
                this.animationHelperToSet = this.equpmentAndFightAnim;
                this.directlyChangeAnimationHelper();
            }

            return true;
        }

        return super.setActiveAnimationHelperDirectly(animationName);
    }

    @Override
    protected void directlyChangeAnimationHelper()
    {
        super.directlyChangeAnimationHelper();

        if (this.itemInUse != null && !this.activeAnimationHelper.allowUsingItems()) this.stopUsingItem();
    }


    @Override
    public InventoryPlayer getInventoryPlayer()
    {
        return this.inventory;
    }


    @Override
    public IGGMPlayerEquipmentAnimationHelperFightStance getGGMEquipment()
    {
        return this.equpmentAndFightAnim;
    }

    @Override
    public Container getGGMContainer()
    {
        return this.ggmContainerEquipment;
    }


    @Override
    public int getMaxAir()
    {
        return super.getMaxAir() +
                (int) this.stamina.getAttributeValue();
    }


    @Override
    public float getStaminaSpendingFromJump()
    {
        return this.getStaminaSpendingOnSprint() * 12.0F;
    }


    @Override
    public boolean canJump()
    {
        return this.inCreative() || super.canJump();
    }

    @Override
    public double getJumpHeight()
    {
        return super.getJumpHeight() + this.getEntityAttribute(GGMCapabilities.dexterity).getAttributeValue() * 0.0005D;
    }

    @Inject(method = "jump", at = @At(value = "HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci)
    {
        if (!this.canJump())
        {
            ci.cancel();
        }
    }


    @Override
    public boolean isCanSprint()
    {
        return this.stamina.getDynamicValue() > this.getStaminaSpendingOnSprint() * 20F;
    }

    @Override
    public float getStaminaSpendingOnSprint()
    {
        return 0.1F;
    }

    /*@Inject(method = "addMovementStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V", ordinal = 2))
    private void onSprinting(CallbackInfo ci)
    {
        this.staminaMoveUpdate();
    }*/


    @Inject(method = "isMovementBlocked", at = @At("RETURN"), cancellable = true)
    private void fixIsMovementBlocked(CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(this.activeAnimationHelper.denyMovement() || cir.getReturnValue());
    }


    @Override
    public ItemStack getSecHeldItem()
    {
        return this.equpmentAndFightAnim.getSecHeldItem();
    }

    @Override
    public boolean isUsingLH()
    {
        return this.equpmentAndFightAnim.isUsingLH();
    }



    /*@ModifyVariable(method = "setItemInUse", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraftforge/event/ForgeEventFactory;onItemUseStart(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;I)I"), ordinal = 0)
    private int itemUseEffectModifyingOnStart(int origin)
    {
        int newValue = origin;
        for (IGGMEffectInstance effect : this.useItemEffectsMap.values())
        {
            effect.onItemUseSetted()
        }
    }*/


    @Inject(method = "setItemInUse", at = @At("HEAD"), cancellable = true)
    private void useItemSettingDenying(ItemStack itemStack, int duration, CallbackInfo ci)
    {
        if (this.activeAnimationHelper.denySetItemInUse(itemStack, duration)) ci.cancel();
    }

    @Inject(method = "setItemInUse", at = @At(value = "JUMP", ordinal = 1))
    private void animationOnSetUseItemEvent(ItemStack itemStack, int duration, CallbackInfo ci)
    {
        this.activeAnimationHelper.onItemUseSetted(itemStack, duration);
        System.out.println("Debug in GGMEntityPlayer activeAnimationHelper.onItemUseSetted");
    }

    /*@Inject(method = "onUpdate", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/Item;onUsingTick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;I)V"), remap = false)
    private void animationOnItemUsingTick(CallbackInfo ci)
    {
        this.activeAnimationHelper.onUsingItem(this.itemInUse, this.itemInUseCount);
    }*/


    @Redirect(method = "damageEntity", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ISpecialArmor$ArmorProperties;ApplyArmor(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;D)F", remap = false))
    private float onCalculateArmorAbsorption(EntityLivingBase entity, ItemStack[] inventory, DamageSource source, double damage)
    {
        IGGMDamageSource gds = (IGGMDamageSource) source;
        float f;

        if (gds.isSettedValues())
        {
            f = 0.0F;
            int a = this.getTotalArmorValue();
            boolean isBlocking = this.equpmentAndFightAnim.isBlocking();
            IItemBlocker blocker = isBlocking ? (IItemBlocker) this.equpmentAndFightAnim.getBlockItem().getItem() : null;
            Map<DamageType, Float> map = gds.getDamageValuesMap();

            for (Map.Entry<DamageType, Float> e : map.entrySet())
            {
                float damageSumm = e.getValue();
                if (isBlocking)
                {
                    for (Map.Entry<IGGMAttribute, UseSpendings> ee : blocker.getBlockersMap().get(e.getKey()).entrySet())
                    {
                        IAttributeInstance mainAttribute = this.getEntityAttribute(ee.getKey());
                        IAttributeInstance spendAttribute = this.getEntityAttribute(ee.getValue().getAttribute());

                        if (mainAttribute != null && spendAttribute instanceof IGGMDynamicAttributeInstance)
                        {
                            double curr = ((IGGMDynamicAttributeInstance) spendAttribute).getDynamicValue();
                            if (curr <= 0.0D && ee.getValue().getSpendsFromD() > 0.0F) continue;
                            double canBlock = mainAttribute.getAttributeValue() * ee.getValue().getAttributeMultiplier();
                            double toSpendCurr = damageSumm * ee.getValue().getSpendsFromD();

                            if (toSpendCurr > curr)
                            {
                                canBlock *= curr / toSpendCurr;
                                toSpendCurr = curr;
                            }
                            if (canBlock > damageSumm)
                            {
                                toSpendCurr *= damageSumm / canBlock;
                                canBlock = damageSumm;
                            }

                            damageSumm -= canBlock;
                            ((IGGMDynamicAttributeInstance) spendAttribute).changeCurrentValue(-toSpendCurr);
                            float damageItem = (float) canBlock - blocker.getSustainability();
                            if (damageItem > 0.0F) this.itemInUse.damageItem((int) damageItem, thisEntity());
                            if (this.itemInUse == null || this.itemInUse.getItemDamage() > this.itemInUse.getMaxDamage() || this.itemInUse.stackSize <= 0)
                            {
                                isBlocking = false;
                                this.clearItemInUse();
                                break;
                            }
                            if (damageSumm <= 0.0D) break;
                        }
                    }
                }
                damageSumm -= a;
                if (damageSumm > 0.0F) f += damageSumm;
            }
        } else
        {

            f = ISpecialArmor.ArmorProperties.ApplyArmor(entity, inventory, source, damage);
        }

        return f;
    }


    @Override
    public short getNewAttackDuration(IAnimationHit hitType)
    {
        return (short) hitType.getStandartDuration();
    }

    @Override
    public IAnimationHit getCurrentAttackHitTYpe()
    {
        return (IAnimationHit) this.equpmentAndFightAnim.getAnimationEpisode();
    }

    @Override
    public short getCurrentAttackDuration()
    {
        return (short) this.equpmentAndFightAnim.getEpisodeDuration();
    }

    @Override
    public short getAttackCountdown()
    {
        return (short) this.equpmentAndFightAnim.getEpisodeCountdown();
    }


    @Override
    public float getStaminaSpendingFromAnimatedAttack()
    {
        return 1.0F + (this.inFightStance() ? (this.getGGMEquipment().getHeldItem() == null ? 0.0F : ((IItemEquip) this.getGGMEquipment().getHeldItem().getItem()).getWeight()) : 0.0F);
    }


    @Override
    public boolean isEntitySleeping()
    {
        return this.isPlayerSleeping();
    }

    @Override
    public boolean inFightStance()
    {
        return this.activeAnimationHelper == this.equpmentAndFightAnim;
    }

    @Override
    public boolean isChangingStance()
    {
        return (this.inFightStance() && this.animationHelperToSet != null) || this.animationHelperToSet == this.equpmentAndFightAnim;
    }



    /*@Override
    public void updateAttack() {

        if (this.attackTicksLeft > -20) {

            --this.attackTicksLeft;

            if (this.changeStance && this.attackTicksLeft <= -2) {
                this.inFightStance = this.inFightStance ? false : true;
                this.changeStance = false;
            }
        }
        else {
            this.attackSeries = 0;
        }

        if (this.attackCooldown > 0) {

            --this.attackCooldown;
        }
    }*/

    @Override
    public void setInFightStance(boolean inFightStance)
    {
        if (inFightStance)
        {
            if (!this.inFightStance())
            {
                this.tryChangeAnimationHelper(this.equpmentAndFightAnim);
            }
        } else
        {
            if (this.inFightStance())
            {
                this.tryChangeAnimationHelperToDefault();
            }
        }
    }

    @Override
    public void changeStance()
    {
        this.setInFightStance(!this.inFightStance());
    }


    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        this.attackTargetEntityWithCurrentItem(entity);
        return false;
    }

    @Redirect(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    private boolean onAttackEntity(Entity entity, DamageSource ds, float damage)
    {
        Map<DamageType, Float> map;
        if (this.inFightStance() && this.equpmentAndFightAnim.getHeldItem() != null && this.equpmentAndFightAnim.getHeldItem().getItem() instanceof IItemMeleeWeapon)
        {
            IItemMeleeWeapon tool = (IItemMeleeWeapon) thisEntity().getHeldItem().getItem();
            Map<DamageType, UseSpendings> itemMap = tool.getDamageValuesMap();
            map = new HashMap<>(itemMap.size(), 1.0F);

            for (Map.Entry<DamageType, UseSpendings> e : itemMap.entrySet())
            {
                IAttributeInstance ai = thisEntity().getEntityAttribute(e.getValue().getAttribute());

                if (ai != null)
                {
                    float d = ((float) ai.getAttributeValue() * e.getValue().getAttributeMultiplier()) + e.getValue().getSpendsFromD();
                    map.put(e.getKey(), d);
                    damage += d;
                }
            }

            /*damage = 0.0F;
            IItemMeleeWeapon tool = (IItemMeleeWeapon) thisEntity().getHeldItem().getItem();
            Map<DamageType, Map<IGGMAttribute, UseSpendings>> itemMap = tool.getDamageValuesMap();
            Map<DamageType, Float> map = new HashMap<>(itemMap.size(), 1.0F);

            for (Map.Entry<DamageType, Map<IGGMAttribute, UseSpendings>> e : itemMap.entrySet()) {

                float damage1 = 0.0F;

                for(Map.Entry<IGGMAttribute, UseSpendings> ee : e.getValue().entrySet()) {

                    IAttributeInstance mainAttribute = this.getEntityAttribute(ee.getKey());
                    IAttributeInstance spendAttribute = this.getEntityAttribute(ee.getValue().getAttribute());

                    if (mainAttribute != null && (ee.getValue().getSpendsFromD() <= 0.0D || spendAttribute instanceof IGGMDynamicAttributeInstance)) {

                        double currentExp = (float) ((IGGMDynamicAttributeInstance) spendAttribute).getDynamicValue();
                        if (currentExp <= 0.0D && ee.getValue().getSpendsFromD() > 0.0D) continue;
                        double dam = mainAttribute.getAttributeValue() * ee.getValue().getAttributeMultiplier();
                        double toSpend = dam * ee.getValue().getSpendsFromD();

                        if (currentExp < toSpend) {

                            dam *= currentExp / toSpend;
                            toSpend = currentExp;
                        }

                        if (toSpend > 0.0D) ((IGGMDynamicAttributeInstance) spendAttribute).changeCurrentValue(-toSpend);
                        damage1 += (float) dam;
                    }

                    damage += damage1;
                    map.put(e.getKey(), damage1);
                }
            }
            ((IGGMDamageSource) ds).setDamageValues(map);*/
        } else if (this.attackCooldown <= 10)
        {

            damage = (float) thisEntity().getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 0.5F;
            map = new HashMap<>(1, 1.0F);
            this.attackCooldown = 10;
        } else
        {

            return false;
        }

        ((IGGMDamageSource) ds).setDamageValues(map);

        for (IGGMEffectInstance effect : this.attackEffectsMap.values())
        {
            ds = (DamageSource) effect.onAttackTarget((IGGMEntity) entity, (IGGMDamageSource) ds, damage);
        }

        return entity.attackEntityFrom(ds, damage);
    }

    @Inject(method = "onKillEntity", at = @At("HEAD"))
    private void onKillEntityHook(EntityLivingBase entity, CallbackInfo ci)
    {
        this.onKilledEntity(entity);
    }



    /*private void controlCurrentItem()
    {
        if (this.inFightStance && this.prevCurItem != this.inventory.currentItem) {

            this.equpmentAndFightAnim.setCurrentItem(this.inventory.currentItem);
            this.inventory.currentItem = this.prevCurItem;
        }
        else {
            this.prevCurItem = (byte) this.inventory.currentItem;
        }
    }*/


    @Inject(method = "readEntityFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;readEntityFromNBT(Lnet/minecraft/nbt/NBTTagCompound;)V"))
    private void onReadEntityFromNBT(NBTTagCompound compound, CallbackInfo ci)
    {
        this.equpmentAndFightAnim.readFromNBT(compound.getTagList("GGMEquipment", 10));
    }


    @Inject(method = "writeEntityToNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setTag(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V", ordinal = 0))
    private void onWriteEntityToNBT(NBTTagCompound compound, CallbackInfo ci)
    {
        compound.setTag("GGMEquipment", this.equpmentAndFightAnim.writeToNBT(new NBTTagList()));
    }


    /*@ModifyVariable(method = "dropOneItem", at = @At(value = "LOAD", ordinal = 0))
    private ItemStack fixDroppingItem(ItemStack itemStack) {

        if (this.inFightStance() && itemStack == null)
        {
            itemStack = this.equpmentAndFightAnim.getSecHeldItem();
        }
        System.out.println("Debug in " + this.getClass() + " Ondropitem Item is: " + itemStack);
        return itemStack;
    }

    @Redirect(method = "dropOneItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;decrStackSize(II)Lnet/minecraft/item/ItemStack;"))
    private ItemStack fixDroppingItem2(InventoryPlayer inventoryPlayer, int index, int episodeCountdown) {

        if (this.inFightStance()) {

            return this.equpmentAndFightAnim.decrStackSize(equpmentAndFightAnim.getCurrentItemIndex(), episodeCountdown);
        }

        return inventoryPlayer.decrStackSize(index, episodeCountdown);
    }*/

    @ModifyVariable(method = "getBreakSpeed(Lnet/minecraft/block/Block;ZIIII)F", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, remap = false)
    private float modifyBreakSpeed(float speedFromTool)
    {
        float newSpeed = speedFromTool + speedFromTool * (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 0.005F;

        return newSpeed;
    }


    @Override
    protected float correctFallDistance(float distance)
    {
        float dex = (float) this.getEntityAttribute(GGMCapabilities.dexterity).getAttributeValue();
        distance -= dex * 0.02f;

        return distance <= 0F ? 0F : distance;
    }


    public EntityPlayer thisEntity()
    {
        return (EntityPlayer) (Object) this;
    }

}
