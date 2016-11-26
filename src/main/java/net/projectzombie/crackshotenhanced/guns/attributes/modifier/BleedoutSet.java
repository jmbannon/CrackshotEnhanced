/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.entities.CSELivingEntity;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

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

    /** TODO: Apply bandage */
    @Override
    public boolean canStop(final CSELivingEntity victim) {
        return false;
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(super.getTotalDPS(), "bleed damage p/sec");
        stats.addValueStat(super.getTotalDurationInSeconds(), "bleed duration");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(super.getDamagePerSecond(), "bleed damage p/sec");
        stats.addMultiplierStatIfValid(super.getDamagePerSecondMultiplier(), "bleed damage p/sec");
        stats.addMultiplierStatIfValid(super.getAdditionalValueMultiplierAppliedToDPS(), "bleed damage dealt from shrapnel damage p/sec");
        stats.addValueStatIfValid(super.getDurationValue(), "bleed duration");
        stats.addMultiplierStatIfValid(super.getDurationMultiplier(), "bleed duration");
        return stats.toArrayList();
    }

}
