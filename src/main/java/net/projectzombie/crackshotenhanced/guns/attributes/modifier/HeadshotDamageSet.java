/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

/**
 *
 * @author jb
 */
public class HeadshotDamageSet extends DamageOnHit<HeadshotDamageSet.HeadshotAttributes>
{
    public interface HeadshotAttributes extends ModifierAttributes {
        double getHeadshotDamageModifier();
        double getHeadshotDamageMultiplier();
    }

    public HeadshotDamageSet(final GunModifier[] modifiers)
    {
        super("Headshot Damage",
                modifiers,
                HeadshotAttributes::getHeadshotDamageModifier,
                HeadshotAttributes::getHeadshotDamageMultiplier,
                HeadshotAttributes.class);
    }
    
    public HeadshotDamageSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(super.getTotal(), "headshot damage");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(super.getValue(), "head shot damage");
        stats.addMultiplierStatIfValid(super.getMultiplier(), "head shot damage");
        return stats.toArrayList();
    }
}
