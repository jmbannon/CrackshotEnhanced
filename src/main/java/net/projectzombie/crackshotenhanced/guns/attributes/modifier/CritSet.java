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
public class CritSet extends Chance<CritSet.CritAttributes>
{
    public interface CritAttributes extends ModifierAttributes {
        /**
         * Returns the crit chance percentage modifier.
         * @return Percent modifier of crit chance (0, inf).
         */
        double getCritChance();

        /**
         * Returns the crit strike percentage modifier from base damage.
         * @return Percent modifier of crit strike (0, inf).
         */
        double getCritStrike();
    }

    private final double critStrikeMultiplier;
    private final double critStrikeDamage;
    
    public CritSet(final GunModifier[] mods,
                    final double baseDamage)
    {
        super("Crit", mods, CritAttributes::getCritChance, CritAttributes.class);
        // Only want sum because crit strike damage is an additional amount of base damage.
        this.critStrikeMultiplier = super.getDoubleSum(CritAttributes::getCritStrike);
        this.critStrikeDamage = Math.max(0, critStrikeMultiplier * baseDamage);
    }
    
    public CritSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    public double getTotalDamageOnCrit() { return critStrikeDamage; }

    @Override
    public boolean hasStats()
    {
        return super.hasStats() && critStrikeMultiplier > 0;
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addPercentageStat(super.getChance(), "critical hit chance");
        stats.addValueStat(critStrikeDamage, "damage on critical hit");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addPercentageStatIfValid(super.getChance(), "critical hit chance");
        stats.addMultiplierStatIfValid(critStrikeMultiplier, "critical strike from base damage");
        return stats.toArrayList();
    }
}
