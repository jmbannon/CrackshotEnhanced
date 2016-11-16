/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.modifiers;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.FireModeAttributes;

/**
 *
 * @author jb
 */
public class FireDamageSet extends DamageOnHit<FireDamageAttributes>
{
    
    public FireDamageSet(GunModifier[] modifiers)
    {
        super("Fire Damage",
              fireDamageValueSum(modifiers),
              fireDamageMultiplierSum(modifiers),
                FireDamageAttributes.class);
    }
    
    public FireDamageSet(GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getTotal(), "total fire damage"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getValue(), "fire damage"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(super.getMultiplier(), "fire damage"));
        return stats;
    }
    
    static private double fireDamageValueSum(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (FireDamageAttributes mod : getIncendiaryModifiers(modSet))
        {
            baseDamageValueSum += mod.getFireDamageValue();
        }
        return baseDamageValueSum;
    }
    
    static private double fireDamageMultiplierSum(final GunModifier[] modSet)
    {
        double baseDamageMultiplierSum = 1.0;

        for (FireDamageAttributes mod : getIncendiaryModifiers(modSet))
        {
            baseDamageMultiplierSum += mod.getFireDamageMultiplier();
        }
        return baseDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static private ArrayList<FireDamageAttributes> getIncendiaryModifiers(final GunModifier[] modSet)
    {
        final ArrayList<FireDamageAttributes> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof FireDamageAttributes)
                mods.add((FireDamageAttributes)mod);
        }
        return mods;
    }
}
