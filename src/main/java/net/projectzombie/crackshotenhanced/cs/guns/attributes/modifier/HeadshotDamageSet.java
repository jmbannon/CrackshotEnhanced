/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;

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
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getTotal(), "total headshot damage"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getValue(), "head shot damage"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(super.getMultiplier(), "head shot damage"));
        return stats;
    }
}
