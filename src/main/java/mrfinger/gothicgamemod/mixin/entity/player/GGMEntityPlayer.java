package mrfinger.gothicgamemod.mixin.entity.player;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrfinger.gothicgamemod.battle.UseSpendings;
import mrfinger.gothicgamemod.entity.capability.GGMExp;
import mrfinger.gothicgamemod.entity.capability.IGGMExp;
import mrfinger.gothicgamemod.entity.capability.attributes.*;
import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.entity.inventory.GGMContainerPlayer;
import mrfinger.gothicgamemod.entity.player.IGGMEntityPlayer;
import mrfinger.gothicgamemod.entity.player.GGMPlayerEquipment;
import mrfinger.gothicgamemod.entity.player.IGGMInventoryPlayer;
import mrfinger.gothicgamemod.init.GGMBattleSystem;
import mrfinger.gothicgamemod.init.GGMCapabilities;
import mrfinger.gothicgamemod.item.IItemBlocker;
import mrfinger.gothicgamemod.item.IItemEquip;
import mrfinger.gothicgamemod.item.IItemMeleeWeapon;
import mrfinger.gothicgamemod.item.equipment.IItemGGMEquip;
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
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(EntityPlayer.class)
public abstract class GGMEntityPlayer extends GGMEntityLivingBase implements IGGMEntityPlayer {


    @Shadow public PlayerCapabilities       capabilities;


    private IGGMExp exp;

    protected IGGMDynamicAttributeInstance  health;

    protected IGGMDynamicAttributeInstance  stamina;

    protected IGGMDynamicAttributeInstance  mana;


    private byte magicCircle;

    @Shadow private ItemStack itemInUse;
    @Shadow private int itemInUseCount;

    @Shadow public InventoryPlayer inventory;


    @Shadow protected abstract void onItemUseFinish();


    @Shadow protected abstract void updateItemUse(ItemStack p_71010_1_, int p_71010_2_);

    protected IGGMInventoryPlayer ggmEqupment;

    protected Container ggmContainerEquipment;

    protected boolean isUsingLH;


    protected byte attackCooldown;

    protected boolean inFightStance;

    protected boolean changeStance;

    protected short attackTicksLeft;

    protected short lastAttackDuration;

    protected byte attackSeries;

    protected byte prevCurItem;


    @Inject(method = "<init>*", at = @At(value = "RETURN"))
    private void init(CallbackInfo ci) {
        this.exp = new GGMExp(this, 500, 10);
        this.ggmEqupment = new GGMPlayerEquipment(this);
        this.ggmContainerEquipment = new GGMContainerPlayer(this);
        this.attackTicksLeft = (short) -20;
        this.lastAttackDuration = (short) this.getNewAttackDuration();
        System.out.println("Debug in GGMEntityPlayer " + this.stepHeight);
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
    private void fixStrengtRegistry(IAttributeInstance attributeInstance, double value) {

    }


    @Override
    public void restoreCurrentValuesFull() {

        for (IGGMDynamicAttributeInstance ai : ((IGGMBaseAttributeMap) this.getAttributeMap()).getDPIColl()) {

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
    private void onOnLivingUpdate(CallbackInfo ci) {

        if (!thisEntity().worldObj.isRemote) {

            if (!this.isDead) {

                if (GGMTicks.gTicks % 10 == 0) {

                    for (IGGMDynamicAttributeInstance dpi : ((IGGMBaseAttributeMap) this.getAttributeMap()).getDPIColl()) {

                        dpi.onUpdate();
                    }
                }
            }
        }

        this.updateAttack();
        this.controlCurrentItem();
    }


    /*@SideOnly(Side.CLIENT)
    @Inject(method = "getItemInUse", at = @At(value = "HEAD"), cancellable = true)
    private void fixItemUseGetter(CallbackInfoReturnable<ItemStack> cir)
    {
        if (this.inFightStance) cir.setReturnValue(this.ggmItemInUse);
    }

    @Inject(method = "isUsingItem", at = @At(value = "HEAD"), cancellable = true)
    private void fixItemUseBool(CallbackInfoReturnable<Boolean> cir)
    {
        if (this.inFightStance) cir.setReturnValue(this.ggmItemInUse != null);
    }

    @SideOnly(Side.CLIENT)
    @Inject(method = "getItemInUseDuration", at = @At(value = "HEAD"), cancellable = true)
    private void fixItemUseDurationGetter(CallbackInfoReturnable<Integer> cir)
    {
        if (this.inFightStance) cir.setReturnValue(this.ggmItemInUse != null ? this.ggmItemInUse.getMaxItemUseDuration() - this.itemInUseCount : 0);
    }

    @Inject(method = "stopUsingItem", at = @At("HEAD"), cancellable = true)
    private void fixStopUsingItem(CallbackInfo ci)
    {
        if (this.inFightStance())
        {
            if (this.ggmItemInUse != null)
            {
                this.ggmItemInUse.onPlayerStoppedUsing(this.worldObj, thisEntity(), this.itemInUseCount);
            }

            ci.cancel();
        }
    }

    @Redirect(method = "clearItemInUse", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;itemInUse:Lnet/minecraft/item/ItemStack;", opcode = Opcodes.GETFIELD))
    private ItemStack fixClearItemInUse(EntityPlayer player)
    {
        if (this.inFightStance()) return this.ggmItemInUse;
        return this.itemInUse;
    }

    @Inject(method = "getItemInUseDuration", at = @At(value = "HEAD"), cancellable = true)
    private void fixIsBlocking(CallbackInfoReturnable<Boolean> cir)
    {
        if (this.inFightStance) cir.setReturnValue(this.ggmItemInUse != null && this.ggmItemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.block);
    }


    @Inject(method = "onUpdate", at = @At(value = "JUMP", opcode = 0))
    private void onGGMItemInUseUpdate(CallbackInfo ci)
    {
        if (this.inFightStance() && this.ggmItemInUse != null)
        {
            ItemStack item = this.ggmEqupment.getSecHeldItem() != null ? this.ggmEqupment.getSecHeldItem() : this.ggmEqupment.getHeldItem();

            if (item == this.ggmItemInUse)
            {
                item.getItem().onUsingTick(item, thisEntity(), itemInUseCount);

                if (--this.itemInUseCount <= 0 && !this.worldObj.isRemote)
                {
                    this.onItemUseFinish();
                }
            }
            else
            {
                this.clearItemInUse();
            }
        }
    }

    @Inject(method = "onItemUseFinish", at = @At("HEAD"), cancellable = true)
    private void fixItemUseFinish(CallbackInfo ci)
    {
        if (this.inFightStance())
        {
            if (this.ggmItemInUse != null)
            {
                byte inLH = this.ggmEqupment.getSecHeldItem() == this.ggmItemInUse ? (byte) 1 : (byte) 0;
                int i = this.ggmItemInUse.stackSize;
                ItemStack itemstack = this.ggmItemInUse.onFoodEaten(this.worldObj, thisEntity());

                if (itemstack != null && itemstack.stackSize > 0) {
                    if (itemstack != this.ggmItemInUse || itemstack.stackSize != i) {
                        this.ggmEqupment.setInventorySlotContents(this.ggmEqupment.getCurrentItemIndex() * 2 + inLH, itemstack);

                    }
                }
                else
                {
                    this.ggmEqupment.setInventorySlotContents(this.ggmEqupment.getCurrentItemIndex() * 2 + inLH, null);
                }

                this.clearItemInUse();

            }
            ci.cancel();
        }
    }

    @Inject(method = "setItemInUse", at = @At("HEAD"), cancellable = true)
    private void fixItemInUseSetter(ItemStack itemStack, int duration, CallbackInfo ci)
    {
        if (this.inFightStance())
        {
            if (this.ggmItemInUse != itemStack)
            {
                if (duration <= 0) ci.cancel();
                this.ggmItemInUse = itemStack;
                this.itemInUseCount = duration;

                if (!this.worldObj.isRemote)
                {
                    this.setEating(true);
                }
            }
            ci.cancel();
        }
    }

    @Redirect(method = "getItemIcon", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;itemInUse:Lnet/minecraft/item/ItemStack;", opcode = Opcodes.GETFIELD))
    private ItemStack fixItemIconGetter(EntityPlayer player)
    {
        if (this.inFightStance()) return this.ggmItemInUse;
        return this.itemInUse;
    }*/


    @Override
    public InventoryPlayer getInventoryPlayer() {
        return this.inventory;
    }


    @Override
    public IGGMInventoryPlayer getGGMEquipment() {
        return this.ggmEqupment;
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

    @Inject(method = "jump", at = @At(value = "HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci) {

        if (!this.capabilities.isCreativeMode && !this.canJump()) {

            ci.cancel();
        }
    }

    @Override
    public float getStaminaSpendingOnSprint() {
        return 0.1F;
    }

    @Inject(method = "addMovementStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addExhaustion(F)V", ordinal = 2))
    private void onSprinting(CallbackInfo ci) {

        this.sprintUpdate();
    }

    @Override
    public boolean isUsingLH() {
        return isUsingLH;
    }

    @Inject(method = "setItemInUse", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;itemInUse:Lnet/minecraft/item/ItemStack;", ordinal = 1))
    private void onSetItemInUse(ItemStack itemStack, int count, CallbackInfo ci) {

        if (this.inFightStance && itemStack == this.getGGMEquipment().getSecHeldItem()) {

            this.isUsingLH = true;
        }
        else {

            this.isUsingLH = false;
        }
    }

    @Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;getCurrentItem()Lnet/minecraft/item/ItemStack;", ordinal = 0))
    private ItemStack fixUsingItem1(InventoryPlayer inventoryPlayer) {

        if (this.inFightStance && this.isUsingLH) {

            return this.ggmEqupment.getSecHeldItem();
        }

        return inventory.getCurrentItem();
    }

    @Inject(method = "onItemUseFinish", at = @At(value = "HEAD"), cancellable = true)
    private void fixItemUseFinish(CallbackInfo ci)
    {
        if (this.itemInUse != null && this.inFightStance())
        {
            System.out.println("Debug in GGMEntityPlayer: fixItemUseFinish");

            this.updateItemUse(this.itemInUse, 16);
            int i = this.itemInUse.stackSize;
            int index = this.ggmEqupment.getCurrentItemIndex() * 2;

            if (this.isUsingLH)
            {
                ++index;
            }

            ItemStack itemstack = this.itemInUse.onFoodEaten(this.worldObj, thisEntity());

            itemstack = ForgeEventFactory.onItemUseFinish(thisEntity(), itemInUse, itemInUseCount, itemstack);

            if (itemstack != this.itemInUse || itemstack != null && itemstack.stackSize != i)
            {
                this.getGGMEquipment().setInventorySlotContents(index, itemstack);

                if (itemstack != null && itemstack.stackSize == 0)
                {
                    this.getGGMEquipment().setInventorySlotContents(index, null);
                }
            }

            this.clearItemInUse();

            ci.cancel();
        }
    }




    @Redirect(method = "damageEntity", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/common/ISpecialArmor$ArmorProperties;ApplyArmor(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/DamageSource;D)F"))
    private float onCalculateArmorAbsorption(EntityLivingBase entity, ItemStack[] inventory, DamageSource source, double damage)
    {
        IGGMDamageSource gds = (IGGMDamageSource) source;
        float f;

        if (gds.isSettedValues())
        {
            f = 0.0F;
            int a = this.getTotalArmorValue();
            boolean isBlocking = this.isBlocking();
            IItemBlocker blocker = isBlocking ? (IItemBlocker) this.itemInUse.getItem() : null;
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
    public int getNewAttackDuration() {
        return 20;
    }

    @Override
    public int getNewAttackCooldown() {
        return 10;
    }

    public int getAttackCooldown() {
        return this.attackCooldown;
    }

    @Override
    public int getLastAttackDuration() {
        return this.lastAttackDuration;
    }

    @Override
    public int getAttackTick() {
        return 16;
    }

    @Override
    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
    }

    @Override
    public float getStaminaSpendingFromAttack()
    {
        System.out.println("Debug in GGMEntityPlayer: held item " + this.getGGMEquipment().getHeldItem());
        if (this.getGGMEquipment().getHeldItem() != null) System.out.println("weight " + ((IItemEquip) this.getGGMEquipment().getHeldItem().getItem()).getWeight());
        return 1.0F + (this.inFightStance ? (this.getGGMEquipment().getHeldItem() == null ? 0.0F : ((IItemEquip) this.getGGMEquipment().getHeldItem().getItem()).getWeight()) : 0.0F);
    }

    @Override
    public boolean inFightStance() {
        if (this.attackTicksLeft > 0) this.inFightStance = true;
        return this.inFightStance;
    }

    @Override
    public boolean isChangingStance() {
        return this.changeStance;
    }

    @Override
    public void repeatAttack() {}

    @Override
    public void startAttack() {

        if (!this.inFightStance()) {
            this.setInFightStance(true);
        }

        if (this.attackCooldown <= 0 && this.inFightStance()) {

            if (this.attackTicksLeft <= 1 && (this.capabilities.isCreativeMode || this.canAttack())) {

                this.attackTicksLeft = (short) this.getNewAttackDuration();
                this.lastAttackDuration = this.attackTicksLeft;
            } else {
                this.repeatAttack();
            }
        }

    }

    @Override
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
    }

    @Override
    public void setInFightStance(boolean inFightStance) {
        this.inFightStance = this.attackTicksLeft > 0 ? true : inFightStance;
        if (this.inFightStance != inFightStance) this.changeStance = this.changeStance ? false : true;
    }

    @Override
    public void changeStance() {
        this.setInFightStance(this.inFightStance ? false : true);
    }

    @Redirect(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    private boolean onAttackEntity(Entity entity, DamageSource ds, float damage)
    {
        Map<DamageType, Float> map;
        if (this.inFightStance() && this.ggmEqupment.getHeldItem() != null && this.ggmEqupment.getHeldItem().getItem() instanceof IItemMeleeWeapon)
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

            damage = (float) thisEntity().getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            map = new HashMap<>(1, 1.0F);
            map.put(GGMBattleSystem.crushing, damage);
            this.attackCooldown = 10;
        }
        else {

            return false;
        }

        ((IGGMDamageSource) ds).setDamageValues(map);
        return entity.attackEntityFrom(ds, damage);
    }

    private void controlCurrentItem() {

        if (this.inFightStance && this.prevCurItem != this.inventory.currentItem) {

            this.ggmEqupment.setCurrentItem(this.inventory.currentItem);
            this.inventory.currentItem = this.prevCurItem;
        }
        else {
            this.prevCurItem = (byte) this.inventory.currentItem;
        }
    }


    @Inject(method = "readEntityFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;readEntityFromNBT(Lnet/minecraft/nbt/NBTTagCompound;)V"))
    private void onReadEntityFromNBT(NBTTagCompound compound, CallbackInfo ci)
    {
        this.ggmEqupment.readFromNBT(compound.getTagList("GGMEquipment", 10));
    }


    @Inject(method = "writeEntityToNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setTag(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V", ordinal = 0))
    private void onWriteEntityToNBT(NBTTagCompound compound, CallbackInfo ci) {
        compound.setTag("GGMEquipment", this.ggmEqupment.writeToNBT(new NBTTagList()));
    }


    @ModifyVariable(method = "dropOneItem", at = @At(value = "LOAD", ordinal = 0))
    private ItemStack fixDroppingItem(ItemStack itemStack) {

        if (this.inFightStance() && itemStack == null)
        {
            itemStack = this.ggmEqupment.getSecHeldItem();
        }
        System.out.println("Debug in " + this.getClass() + " Ondropitem Item is: " + itemStack);
        return itemStack;
    }

    @Redirect(method = "dropOneItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;decrStackSize(II)Lnet/minecraft/item/ItemStack;"))
    private ItemStack fixDroppingItem2(InventoryPlayer inventoryPlayer, int index, int count) {

        if (this.inFightStance()) {

            return this.ggmEqupment.decrStackSize(ggmEqupment.getCurrentItemIndex(), count);
        }

        return inventoryPlayer.decrStackSize(index, count);
    }


    @ModifyVariable(method = "getBreakSpeed(Lnet/minecraft/block/Block;ZIIII)F", at = @At(value = "LOAD", ordinal = 0), ordinal = 0)
    private float modifyBreakSpeed(float speedFromTool)
    {
        return speedFromTool + speedFromTool * (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() / 200.0F;
    }


    public EntityPlayer thisEntity() {
        return (EntityPlayer) (Object) this;
    }

}
