/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

/**
 *
 * @author jb
 */
public class IncendiaryDamageSet extends DamageOnHit<IncendiaryDamageSet.IncendiaryDamageAttributes>
{
    public interface IncendiaryDamageAttributes extends ModifierAttributes {
        double getIncendiaryDamageValue();
        double getIncendiaryDamageMultiplier();
    }

    public IncendiaryDamageSet(GunModifier[] modifiers)
    {
        super("Incendiary Damage",
                modifiers,
                IncendiaryDamageAttributes::getIncendiaryDamageValue,
                IncendiaryDamageAttributes::getIncendiaryDamageMultiplier,
                IncendiaryDamageAttributes.class);
    }
    
    public IncendiaryDamageSet(GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(super.getTotal(), "incendiary damage");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(super.getValue(), "incendiary damage");
        stats.addMultiplierStatIfValid(super.getMultiplier(), "incendiary damage");
        return stats.toArrayList();
    }
}
