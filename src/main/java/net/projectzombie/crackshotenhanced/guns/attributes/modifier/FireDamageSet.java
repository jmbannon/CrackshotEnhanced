/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;

/**
 *
 * @author jb
 */
public class FireDamageSet extends DamageOnHit<FireDamageSet.FireDamageAttributes>
{
    public interface FireDamageAttributes extends ModifierAttributes {
        double getFireDamageValue();
        double getFireDamageMultiplier();
    }

    public FireDamageSet(GunModifier[] modifiers)
    {
        super("Fire Damage",
                modifiers,
              FireDamageAttributes::getFireDamageValue,
              FireDamageAttributes::getFireDamageMultiplier,
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
}
