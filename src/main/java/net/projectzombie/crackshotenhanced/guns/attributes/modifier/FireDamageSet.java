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
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(super.getTotal(), "fire damage");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(super.getValue(), "fire damage");
        stats.addMultiplierStatIfValid(super.getMultiplier(), "fire damage");
        return stats.toArrayList();
    }
}
