/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Mod;
import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

/**
 *
 * @author jb
 */
public class BaseDamageSet extends DamageOnHit<BaseDamageSet.BaseDamageAttributes>
{
    public interface BaseDamageAttributes extends ModifierAttributes {
        /**
         * Value - increase or decrease to base damage.
         * @return Double to be added to damage.
         */
        double getDamageValue();

        /**
         * Multiplier - percentage to modify base damage.
         * @return Percentage to be multiplied to base damage (0, inf).
         */
        double getDamageMultiplier();
    }

    public BaseDamageSet(GunModifier[] modifiers,
                         final double skeletonBaseDamage)
    {
        super("Base Damage",
                modifiers,
                skeletonBaseDamage,
                BaseDamageAttributes::getDamageValue,
                BaseDamageAttributes::getDamageMultiplier,
                BaseDamageAttributes.class);
    }
    
    public BaseDamageSet(GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(super.getTotal(), "base damage");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(super.getValue(), "base damage");
        stats.addMultiplierStatIfValid(super.getMultiplier(), "base damage");
        return stats.toArrayList();
    }
}
