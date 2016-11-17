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

    private final double skeleBaseDamage;
    
    public BaseDamageSet(GunModifier[] modifiers,
                         final double skeletonBaseDamage)
    {
        super("Base Damage",
                modifiers,
                skeletonBaseDamage,
                BaseDamageAttributes::getDamageValue,
                BaseDamageAttributes::getDamageMultiplier,
                BaseDamageAttributes.class);
        this.skeleBaseDamage = skeletonBaseDamage;
    }
    
    public BaseDamageSet(GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getTotal(), "total base damage"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.add(ModifierLoreBuilder.getValueStat(skeleBaseDamage, "stock base damage"));
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(super.getValue(), "base damage"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(super.getMultiplier(), "base damage"));
        return stats;
    }
}
