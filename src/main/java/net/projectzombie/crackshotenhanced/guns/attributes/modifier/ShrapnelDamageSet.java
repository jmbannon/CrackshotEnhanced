/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

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
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(super.getTotal(), "shrapnel damage");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(super.getValue(), "shrapnel damage");
        stats.addMultiplierStatIfValid(super.getMultiplier(), "shrapnel damage");
        return stats.toArrayList();
    }
    
}
