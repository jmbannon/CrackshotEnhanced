/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.modifiers;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.Modifier;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class DamageOnHit<T extends Modifier> extends GunModifierSet<T>
{
    private final double totalDamage;
    private final double damageValue;
    private final double damageMultiplier;
    
    public DamageOnHit(final String name,
                       final double damageValue,
                       final double damageMultiplier)
    {
        super(name);
        this.damageValue = damageValue;
        this.damageMultiplier = damageMultiplier;
        this.totalDamage = Math.max(0.0, damageValue * damageMultiplier);
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
