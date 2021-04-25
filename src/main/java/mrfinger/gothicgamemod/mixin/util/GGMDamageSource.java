package mrfinger.gothicgamemod.mixin.util;

import mrfinger.gothicgamemod.battle.DamageType;
import mrfinger.gothicgamemod.util.IGGMDamageSource;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Collections;
import java.util.Map;

@Mixin(DamageSource.class)
public class GGMDamageSource implements IGGMDamageSource {


    Map<DamageType, Float> damageValuesMap;

    @Override
    public void setDamageValues(Map<DamageType, Float> map) {

        this.damageValuesMap = Collections.unmodifiableMap(map);
    }

    @Override
    public boolean isSettedValues() {
        return this.damageValuesMap != null;
    }

    @Override
    public Map<DamageType, Float> getDamageValuesMap() {
        return this.damageValuesMap;
    }

}
