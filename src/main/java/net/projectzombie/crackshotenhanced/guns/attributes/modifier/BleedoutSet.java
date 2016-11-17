/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;

import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.STAT_SEPERATOR;
import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.getMultiplierStat;
import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class BleedoutSet extends DamageOverTime<BleedoutSet.BleedoutAttributes>
{
    public interface BleedoutAttributes extends ModifierAttributes {
        double getBleedoutDurationValue();
        double getBleedoutDurationMultiplier();

        double getBleedoutDamageValuePerSecond();
        double getBleedoutDamageValuePerSecondMultiplier();
        double getBleedoutDamageMultiplerFromShrapnel();
    }
    
    public BleedoutSet(final GunModifier[] modSet,
                       final double totalShrapnelDamage)
    {
        super("Bleedout Damage",
                modSet,
                BleedoutAttributes::getBleedoutDamageValuePerSecond,
                BleedoutAttributes::getBleedoutDamageValuePerSecondMultiplier,
                BleedoutAttributes::getBleedoutDurationValue,
                BleedoutAttributes::getBleedoutDurationMultiplier,
                totalShrapnelDamage,
                BleedoutAttributes::getBleedoutDamageMultiplerFromShrapnel,
                BleedoutAttributes.class);
    }
    
    public BleedoutSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(getValueStat(super.getTotalDPS(), "total bleed damage p/sec"));
        stats.add(getValueStat(super.getTotalDurationInSeconds(), "total bleed duration"));
        stats.add(STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(super.getDamagePerSecond(), "bleed damage p/sec"));
        stats.add(getMultiplierStat(super.getAdditionalValueMultiplierAppliedToDPS(), "bleed damage dealt from shrapnel damage p/sec"));
        stats.add(getValueStat(super.getDurationValue(), "bleed duration"));
        stats.add(getMultiplierStat(super.getDurationMultiplier(), "bleed duration"));
        return stats;
    }

}
