/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;

import java.util.function.ToDoubleFunction;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class DamageOnHit<T extends ModifierAttributes> extends AttributeSet<T>
{
    private final double totalDamage;
    private final double damageValue;
    private final double damageMultiplier;

    public DamageOnHit(final String name,
                       final GunModifier[] gunMods,
                       final double damageValueOffset,
                       ToDoubleFunction<? super T> damageValueFunction,
                       ToDoubleFunction<? super T> damageMultiplierFunction,
                       final Class<T> t)
    {
        super(name, gunMods, t);
        this.damageValue = super.getDoubleSum(damageValueOffset, 0.0, damageValueFunction);
        this.damageMultiplier = super.getMultiplierSum(damageMultiplierFunction);
        this.totalDamage = Math.max(0.0, damageValue * damageMultiplier);
    }

    public DamageOnHit(final String name,
                       final GunModifier[] gunMods,
                       ToDoubleFunction<? super T> damageValueFunction,
                       ToDoubleFunction<? super T> damageMultiplierFunction,
                       final Class<T> t)
    {
        this(name, gunMods, 0.0, damageValueFunction, damageMultiplierFunction, t);
    }


    
    @Override
    public boolean hasStats()
    {
        return totalDamage > 0;
    }
    public double getValue()      { return damageValue; }
    public double getMultiplier() { return damageMultiplier; }
    public double getTotal()      { return totalDamage; }
}
