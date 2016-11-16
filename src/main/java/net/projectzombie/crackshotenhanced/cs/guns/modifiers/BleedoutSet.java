/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.STAT_SEPERATOR;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.getMultiplierStat;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class BleedoutSet extends DamageOverTime<BleedoutAttributes>
{   
    private final double shrapnelMultiplier;
    private final double bleedoutDPSValue;
    
    public BleedoutSet(final GunModifier[] modSet,
                       final double totalShrapnelDamage)
    {
        super("Bleedout Damage",
              getTotalDPS(modSet, totalShrapnelDamage),
              getBleedoutDurationValue(modSet),
              getBleedoutDurationMultiplier(modSet),
                BleedoutAttributes.class);
        
        this.shrapnelMultiplier = getBleedoutShrapnelMultiplier(modSet);
        this.bleedoutDPSValue = getBleedoutDPSValue(modSet);
    }
    
    public BleedoutSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(getValueStat(super.totalDPS, "total bleed damage p/sec"));
        stats.add(getValueStat(super.totalDuration, "total bleed duration"));
        stats.add(STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(bleedoutDPSValue, "bleed damage p/sec"));
        stats.add(getMultiplierStat(shrapnelMultiplier, "bleed damage dealt from shrapnel damage p/sec"));
        stats.add(getValueStat(super.durationValue, "bleed duration"));
        stats.add(getMultiplierStat(super.durationMultiplier, "bleed duration"));
        return stats;
    }
    
    
    static private double getTotalDPS(final GunModifier[] modifierSet,
                                      final double totalShrapnelDamage)
    {            
        return Math.max(0, getBleedoutDPSValue(modifierSet)
                + (getBleedoutShrapnelMultiplier(modifierSet) * totalShrapnelDamage));
    }
    
    /**
    * @return Returns all BleedoutModifiers on the gun.
    */
    private static ArrayList<BleedoutAttributes> getBleedoutModifiers(final GunModifier[] modifierSet)
    {
        final ArrayList<BleedoutAttributes> mods = new ArrayList<>();
        for (GunModifier mod : modifierSet)
        {
            if (mod instanceof BleedoutAttributes)
                mods.add((BleedoutAttributes)mod);
        }
        return mods;
    }
   
    
    static private double getBleedoutShrapnelMultiplier(final GunModifier[] modifierSet)
    {            
        final ArrayList<BleedoutAttributes> bleedoutMods = getBleedoutModifiers(modifierSet);
        double bleedoutDamageShrapMultiplier = 0;

        for (BleedoutAttributes mod : bleedoutMods)
        {
            bleedoutDamageShrapMultiplier += mod.getBleedoutDamageMultiplerFromShrapnel();
        }
        return Math.max(0, bleedoutDamageShrapMultiplier);
    }
    
    static private double getBleedoutDPSValue(final GunModifier[] modifierSet)
    {            
        final ArrayList<BleedoutAttributes> bleedoutMods = getBleedoutModifiers(modifierSet);
        double bleedoutDamageValue = 0;

        for (BleedoutAttributes mod : bleedoutMods)
        {
            bleedoutDamageValue += mod.getBleedoutDamageValuePerSecond();
        }
        return Math.max(0, bleedoutDamageValue);
    }

    static private double getBleedoutDurationValue(final GunModifier[] modifierSet)
    {
        final ArrayList<BleedoutAttributes> bleedoutMods = getBleedoutModifiers(modifierSet);
        double bleedoutDurationValue = 0;

        for (BleedoutAttributes mod : bleedoutMods)
        {
            bleedoutDurationValue += mod.getBleedoutDurationValue();
        }
        return Math.max(0, bleedoutDurationValue);
    }
    
    static private double getBleedoutDurationMultiplier(final GunModifier[] modifierSet)
    {
        final ArrayList<BleedoutAttributes> bleedoutMods = getBleedoutModifiers(modifierSet);
        double bleedoutDurationMultiplier = 1.0;

        for (BleedoutAttributes mod : bleedoutMods)
        {
            bleedoutDurationMultiplier += mod.getBleedoutDurationMultiplier();
        }
        return Math.max(0, bleedoutDurationMultiplier);
    }

}
