package mrfinger.gothicgamemod.mixin.entity.player;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.animations.episodes.IAnimationEpisode;
import mrfinger.gothicgamemod.entity.IGGMEntity;
import mrfinger.gothicgamemod.entity.IGGMEntityLivingBase;
import mrfinger.gothicgamemod.entity.animations.IAnimation;
import mrfinger.gothicgamemod.entity.capability.GGMExp;
import mrfinger.gothicgamemod.entity.capability.IGGMExp;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMAttribute;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMBaseAttributeMap;
import mrfinger.gothicgamemod.entity.capability.attributes.IGGMDynamicAttributeInstance;
import mrfinger.gothicgamemod.entity.capability.effects.IGGMEffectInstance;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMPlayerEquipmentAnimationFightStance;
import mrfinger.gothicgamemod.fractions.Fraction;
import mrfinger.gothicgamemod.init.GEntities;
import mrfinger.gothicgamemod.init.GGMBattleSystem;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.init.GGMFractions;
import mrfinger.gothicgamemod.item.IItemBlocker;
import mrfinger.gothicgamemod.item.IItemEquip;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import mrfinger.gothicgamemod.mixin.entity.GGMEntityLivingBase;
import mrfinger.gothicgamemod.util.GGMTicks;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Mixin(EntityPlayer.class)
public abstract class GGMEntityPlayer extends GGMEntityLivingBase implements IGGMEntityPlayer {


    @Shadow public PlayerCapabilities       capabilities;


    private IGGMExp exp;

    protected IGGMDynamicAttributeInstance  health;

    protected IGGMDynamicAttributeInstance  stamina;

    protected IGGMDynamicAttributeInstance  mana;

    //protected Map<IGGMEffect, IGGMEffectInstance> useItemEffectsMap;

    @Shadow private ItemStack itemInUse;
    @Shadow private int itemInUseCount;

    @Shadow public InventoryPlayer inventory;


    @Shadow protected abstract void updateItemUse(ItemStack p_71010_1_, int p_71010_2_);

    @Shadow public abstract void stopUsingItem();

    protected IGGMPlayerEquipmentAnimationFightStance equpmentAndFightAnim;

    protected Container ggmContainerEquipment;

    protected byte attackCooldown;


    @Inject(method = "<init>*", at = @At(value = "RETURN"))
    private void init(CallbackInfo ci)
    {
        this.exp = new GGMExp(this, 500, 10);


        //this.useItemEffectsMap 	= new HashMap<>();

    }


    @Override
    public int initialLevel() {
        return 0;
    }


    @Override
    public void setLvl(int lvl) {
        super.setLvl(lvl);

        this.getExpCap().setExpFromLvl(this.getLvl());
    }

    @Redirect(method = "applyEntityAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/BaseAttributeMap;registerAttribute(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;"))
    private IAttributeInstance onApplyAttributes(BaseAttributeMap map, IAttribute attribute) {

        IGGMBaseAttributeMap gmap = (IGGMBaseAttributeMap) map;
        {
            this.health = (IGGMDynamicAttributeInstance) gmap.registerAttribute(GGMCapabilities.maxHealth, GGMCapabilities.maxHealthAIPlayer);
            //this.health.applyRegenModifier(new RegenModifier(0, 0, false, 0.001F));
            //this.health.applyRegenModifier(new RegenModifier(0, 1, false, 0.001F));
            this.stamina = (IGGMDynamicAttributeInstance) gmap.registerAttribute(GGMCapabilities.maxStamina, GGMCapabilities.maxStaminaAIPlayer);
            //this.stamina.applyRegenModifier(new RegenModifier(0, 0, false, 0.1F));
            //this.stamina.applyRegenModifier(new RegenModifier(0, 1, false, 0.05F));
            this.mana = (IGGMDynamicAttributeInstance) gmap.registerAttribute(GGMCapabilities.maxMana, GGMCapabilities.maxManaAIPlayer);
            // this.mana.applyRegenModifier(new RegenModifier(0, 0, false, 0.001F));
            //this.mana.applyRegenModifier(new RegenModifier(0, 1, false, 0.001F));
        }

        IAttributeInstance strenght = gmap.registerAttribute((IGGMAttribute) SharedMonsterAttributes.attackDamage, GGMCapabilities.AIPlayer);
        gmap.registerAttribute(GGMCapabilities.dexterity, GGMCapabilities.AIPlayer);
        gmap.registerAttribute(GGMCapabilities.intelligence, GGMCapabilities.AIPlayer);
        gmap.resizeCollections();
        return strenght;
    }

    @Redirect(method = "applyEntityAttributes", at = @At(value = "INVOKE", target ="Lnet/minecraft/entity/ai/attributes/IAttributeInstance;setBaseValue(D)V"))
    private void fixStrengtRegistry(IAttributeInstance attributeInstance, double value)
    {

    }


    @Override
    public Fraction getStandartFraction() {
        return GGMFractions.humans;
    }

    @Override
    public void restoreCurrentValuesFull()
    {
        Collection<IGGMDynamicAttributeInstance> dpiColl = ((IGGMBaseAttributeMap) this.getAttributeMap()).getDPIColl();

        for (IGGMDynamicAttributeInstance ai : dpiColl)
        {
            ai.restore();
        }
    }

    @Override
    public IGGMExp getExpCap() {
        return this.exp;
    }

    @Override
    public void saveExp(NBTTagCompound nbt) {
        this.getExpCap().saveNBTData(nbt);
    }

    @Override
    public void loadExp(NBTTagCompound nbt) {
        this.getExpCap().loadNBTData(nbt);
    }


    @Override
    public IGGMDynamicAttributeInstance getHealthAttribute() {
        return this.health;
    }

    @Override
    public float getHealth() {
        return (float) this.health.getCurrentValue();
    }

    @Override
    public void setHealth(float value) {
        this.getHealthAttribute().setCurrentValue(value);
    }

    @Override
    public float getMaxHealth() {
        return (float) this.health.getAttributeValue();
    }


    @Override
    public IGGMDynamicAttributeInstance getStaminaAttribute() {
        return this.stamina;
    }


    @Override
    public IGGMDynamicAttributeInstance getManaAttribute() {
        return this.mana;
    }


    @Inject(method = "onLivingUpdate", at = @At("TAIL"))
    private void onOnLivingUpdate(CallbackInfo ci)
    {
        if (!thisEntity().worldObj.isRemote) {

            if (!this.isDead) {

                if (GGMTicks.gTicks % 10 == 0) {

                    for (IGGMDynamicAttributeInstance dpi : ((IGGMBaseAttributeMap) this.getAttributeMap()).getDPIColl()) {

                        dpi.onUpdate();
                    }
                }
            }
        }
    }


    @Override
    public void setAnimation(IAnimation animation)
    {
        super.setAnimation(animation);

        if (this.itemInUse != null && this.currentAnimation.denyUsingItems()) this.stopUsingItem();
    }

    @Override
    public boolean setAnimation(String animationName)
    {
        if (animationName.equals(this.equpmentAndFightAnim.getUnlocalizedName()))
        {
            if (this.currentAnimation != this.equpmentAndFightAnim)
            {
                this.animationToSet = this.equpmentAndFightAnim;
                this.clearAnimation();
            }

            return true;
        }

        return super.setAnimation(animationName);
    }

    @Override
    public void clearAnimation()
    {
        super.clearAnimation();

        if (this.itemInUse != null && this.currentAnimation.denyUsingItems()) this.stopUsingItem();
    }


    @Override
    public InventoryPlayer getInventoryPlayer() {
        return this.inventory;
    }


    @Override
    public IGGMPlayerEquipmentAnimationFightStance getGGMEquipment() {
        return this.equpmentAndFightAnim;
    }

    @Override
    public Container getGGMContainer() {
        return this.ggmContainerEquipment;
    }


    @Override
    public int getMaxAir() {
        return super.getMaxAir() + (int) this.stamina.getAttributeValue();
    }


    @Override
    public float getStaminaSpendingFromJump() {
        return this.getStaminaSpendingOnSprint() * 12.0F;
    }

    @Override
    public double getJumpHeight() {
        return 0.42D + this.getEntityAttribute(GGMCapabilities.dexterity).getAttributeValue() * 0.0005D;
    }

    @Inject(method = "jump", at = @At(value = "HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci) {

        if (!this.capabilities.isCreativeMode && (this.currentAnimation.denyJump() || !this.canJump()))
        {
            ci.cancel();
        }
    }

    @Override
    public float getStaminaSpendingOnSprint() {
        return 0.1F;
    }

    @Inject(method = "addMovementStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V", ordinal = 2))
    private void onSprinting(CallbackInfo ci)
    {
        this.sprintUpdate();
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
        if (this.currentAnimation.denySetItemInUse(itemStack, duration)) ci.cancel();
    }

    @Inject(method = "setItemInUse", at = @At(value = "JUMP", ordinal = 1))
    private void animationOnSetUseItemEvent(ItemStack itemStack, int duration, CallbackInfo ci)
    {
        this.currentAnimation.onItemUseSetted(itemStack, duration);
        System.out.println("Debug in GGMEntityPlayer currentAnimation.onItemUseSetted");
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/Item;onUsingTick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;I)V"))
    private void animationOnItemUsingTick(CallbackInfo ci)
    {
        this.currentAnimation.onUsingItem(this.itemInUse, this.itemInUseCount);
    }


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
                            double curr = ((IGGMDynamicAttributeInstance) spendAttribute).getCurrentValue();
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
        }
        else {

            f = ISpecialArmor.ArmorProperties.ApplyArmor(entity, inventory, source, damage);
        }

        return f;
    }


    @Override
    public short getNewAttackDuration(IAnimationEpisode hitType) {
        return 20;
    }

    @Override
    public IAnimationEpisode getLastAttackHitTYpe()
    {
        return this.equpmentAndFightAnim.getEpisode();
    }

    @Override
    public short getLastAttackDuration()
    {
        return (short) this.equpmentAndFightAnim.getEpisodeDuration();
    }

    @Override
    public short getAttackCount()
    {
        return (short) this.equpmentAndFightAnim.getEpisodeCount();
    }

    @Override
    public short getAttackTick()
    {
        return this.equpmentAndFightAnim.getAttackTick();
    }

    @Override
    public byte getAttackSeries()
    {
        return this.equpmentAndFightAnim.getAttackSeries();
    }


    @Override
    public float getStaminaSpendingFromAttack()
    {
        return 1.0F + (this.inFightStance() ? (this.getGGMEquipment().getHeldItem() == null ? 0.0F : ((IItemEquip) this.getGGMEquipment().getHeldItem().getItem()).getWeight()) : 0.0F);
    }

    @Override
    public boolean inFightStance()
    {
        return this.currentAnimation == this.equpmentAndFightAnim;
    }

    @Override
    public boolean isChangingStance()
    {
        return (this.inFightStance() && this.needEndAnimation) || this.animationToSet == this.equpmentAndFightAnim;
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
                this.tryEndAnimation(this.equpmentAndFightAnim);
            }
        }
        else
        {
            if (this.inFightStance())
            {
                this.tryEndAnimation();
            }
        }
    }

    @Override
    public void changeStance()
    {
        this.setInFightStance(!this.inFightStance());
    }


    @Override
    public void startAttack(IAnimationEpisode hitType)
    {
        this.equpmentAndFightAnim.setAnimationEpisode(hitType, this.getNewAttackDuration(hitType));
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

                if (ai != null) {
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

                        double curr = (float) ((IGGMDynamicAttributeInstance) spendAttribute).getCurrentValue();
                        if (curr <= 0.0D && ee.getValue().getSpendsFromD() > 0.0D) continue;
                        double dam = mainAttribute.getAttributeValue() * ee.getValue().getAttributeMultiplier();
                        double toSpend = dam * ee.getValue().getSpendsFromD();

                        if (curr < toSpend) {

                            dam *= curr / toSpend;
                            toSpend = curr;
                        }

                        if (toSpend > 0.0D) ((IGGMDynamicAttributeInstance) spendAttribute).changeCurrentValue(-toSpend);
                        damage1 += (float) dam;
                    }

                    damage += damage1;
                    map.put(e.getKey(), damage1);
                }
            }
            ((IGGMDamageSource) ds).setDamageValues(map);*/
        }

        else if (this.attackCooldown <= 10) {

            damage = (float) thisEntity().getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 0.5F;
            map = new HashMap<>(1, 1.0F);
            map.put(GGMBattleSystem.crushing, damage);
            this.attackCooldown = 10;
        }
        else {

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
    private void onKillingEntity(EntityLivingBase entity, CallbackInfo ci)
    {
        for (IGGMEffectInstance effect : this.attackEffectsMap.values())
        {
            effect.onKillEntity((IGGMEntityLivingBase) entity);
        }

        int gainExp = ((IGGMEntityLivingBase) entity).getLvl() * GEntities.EXPModifier;
        if (entity instanceof EntityPlayer) gainExp += 10 * GEntities.EXPModifier;
        this.gainExp(gainExp);
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
    private void onWriteEntityToNBT(NBTTagCompound compound, CallbackInfo ci) {
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
    private ItemStack fixDroppingItem2(InventoryPlayer inventoryPlayer, int index, int count) {

        if (this.inFightStance()) {

            return this.equpmentAndFightAnim.decrStackSize(equpmentAndFightAnim.getCurrentItemIndex(), count);
        }

        return inventoryPlayer.decrStackSize(index, count);
    }*/

    @ModifyVariable(method = "getBreakSpeed(Lnet/minecraft/block/Block;ZIIII)F", at = @At(value = "LOAD", ordinal = 0), ordinal = 0, remap = false)
    private float modifyBreakSpeed(float speedFromTool)
    {
        float newSpeed = speedFromTool + speedFromTool * (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 0.005F;

        return newSpeed;
    }


    public EntityPlayer thisEntity() {
        return (EntityPlayer) (Object) this;
    }

}
