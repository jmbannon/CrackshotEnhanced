/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;

/**
 *
 * @author jb
 */
public class ShrapnelDamageSet extends DamageOnHit<ShrapnelDamageSet.ShrapnelDamageAttributes>
{
    public interface ShrapnelDamageAttributes extends ModifierAttributes {
        double getShrapnelDamageValue();
        double getShrapnelDamageMultiplier();
    }

    public ShrapnelDamageSet(GunModifier[] modifiers)
    {
        super("Shrapnel Damage",
                modifiers,
                ShrapnelDamageAttributes::getShrapnelDamageValue,
                ShrapnelDamageAttributes::getShrapnelDamageMultiplier,
                ShrapnelDamageAttributes.class);
    }
    
    public ShrapnelDamageSet(GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getTotal(), "total shrapnel damage"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getValue(), "shrapnel damage"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(super.getMultiplier(), "shrapnel damage"));
        return stats;
    }
    
}
