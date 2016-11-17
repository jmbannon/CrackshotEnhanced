/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;

import java.util.function.ToDoubleFunction;

import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class DamageOverTime<T extends ModifierAttributes> extends AttributeSet<T>
{

    private final double damagePerSecond;
    private final double damagePerSecondMultiplier;
    private final double durationValue;
    private final double durationMultiplier;
    private final double additionalValueMultiplierAppliedToDPS;

    private final double totalDPS;
    private final double totalDuration;

    public DamageOverTime(final String name,
                          final GunModifier[] gunMods,
                          final ToDoubleFunction<? super T> damagePerSecondValue,
                          final ToDoubleFunction<? super T> damagePerSecondMultiplier,
                          final ToDoubleFunction<? super T> durationValue,
                          final ToDoubleFunction<? super T> durationMultiplier,
                          final double totalAdditionalValue,
                          final ToDoubleFunction<? super T> additionalValueMultiplierAppliedToDPS,
                          final Class<T> t)
    {
        super(name, gunMods, t);

        this.damagePerSecond = super.getDoubleSum(0, 0, damagePerSecondValue);
        this.damagePerSecondMultiplier = super.getMultiplierSum(damagePerSecondMultiplier);
        this.durationValue = super.getDoubleSum(0, 0, durationValue);
        this.durationMultiplier = this.getMultiplierSum(durationMultiplier);
        this.additionalValueMultiplierAppliedToDPS = (additionalValueMultiplierAppliedToDPS == null) ? 0 : this.getDoubleSum(0, 0, additionalValueMultiplierAppliedToDPS);

        this.totalDPS = Math.max(0, (this.damagePerSecond * this.damagePerSecondMultiplier) + (totalAdditionalValue * this.additionalValueMultiplierAppliedToDPS));
        this.totalDuration = Math.max(0, this.durationValue * this.durationMultiplier);
    }

    public DamageOverTime(final String name,
                          final GunModifier[] gunMods,
                          final ToDoubleFunction<? super T> damagePerSecondValue,
                          final ToDoubleFunction<? super T> damagePerSecondMultiplier,
                          final ToDoubleFunction<? super T> durationValue,
                          final ToDoubleFunction<? super T> durationMultiplier,
                          final Class<T> t)
    {
        this(name, gunMods, damagePerSecondValue, damagePerSecondMultiplier, durationValue, durationMultiplier, 0.0, null, t);
    }
    
    @Override
    public boolean hasStats()
    {
        return totalDPS > 0 && totalDuration > 0;
    }

    public double getTotalDPS() { return totalDPS;       }
    public double getTotalDPT() { return totalDPS / TPS; }
    
    public double getTotalDurationInSeconds() { return totalDuration;       }
    public double getTotalDurationInTicks()   { return totalDuration / TPS; }
    public double getDurationValue()          { return durationValue;       }
    public double getDurationMultiplier()     { return durationMultiplier;  }
    public double getDamagePerSecond()        { return damagePerSecond;     }
    public double getDamagePerSecondMultiplier() { return damagePerSecondMultiplier; }
    public double getAdditionalValueMultiplierAppliedToDPS() { return additionalValueMultiplierAppliedToDPS; }
    
}
