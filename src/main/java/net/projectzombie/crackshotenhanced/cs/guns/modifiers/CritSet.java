/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.modifiers;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;

/**
 *
 * @author jb
 */
public class CritSet extends Chance<CritAttributes>
{
    private final double critStrikeMultiplier;
    private final double critStrikeDamage;
    
    public CritSet(final GunModifier[] mods,
                    final double baseDamage)
    {
        super("Crit",
              getCritChance(mods),
                CritAttributes.class);
        
        this.critStrikeMultiplier = getCritMultiplier(mods);
        this.critStrikeDamage = critStrikeMultiplier * baseDamage;
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
    
    static
    private double getCritMultiplier(final GunModifier gunMods[])
    {
        double critMultiplier = 0;
        for (CritAttributes mod : getCritModifiers(gunMods))
        {
            critMultiplier += mod.getCritStrike();
        }
        return Math.max(0, critMultiplier);
    }

    static
    private double getCritChance(final GunModifier gunMods[])
    {
        double critChance = 0;
        for (CritAttributes mod : getCritModifiers(gunMods))
        {
            critChance += mod.getCritChance();
        }
        return Math.max(0, critChance);
    }
    
    /**
     * @param gunMods
     * @return Returns all IncendiaryModifiers on the gun.
     */
    static
    private ArrayList<CritAttributes> getCritModifiers(final GunModifier gunMods[])
    {
        final ArrayList<CritAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof CritAttributes)
                mods.add((CritAttributes)mod);
        }
        return mods;
    }
}
