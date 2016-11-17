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
        
        this.critStrikeMultiplier = super.getMultiplierSum(CritAttributes::getCritStrike);
        this.critStrikeDamage = Math.max(0, critStrikeMultiplier * baseDamage);
    }
    
    public CritSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    public double getCritStrikeDamage() { return critStrikeDamage; }
    public double getCritStrikeMultiplier() { return critStrikeMultiplier; }
    
    @Override
    public boolean hasStats()
    {
        return super.getChance() > 0 && critStrikeMultiplier > 0;
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(ModifierLoreBuilder.getValueStat(critStrikeDamage, "total damage on critical hit"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.addAll(getStat());        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getMultiplierStat(critStrikeMultiplier, "critical strike from base damage"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(super.getChance(), "critical hit chance"));
        return stats;
    }
}
