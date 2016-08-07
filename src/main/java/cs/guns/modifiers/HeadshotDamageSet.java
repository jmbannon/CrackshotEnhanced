/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.modifiers;

import java.util.ArrayList;
import cs.guns.components.GunModifier;
import static cs.guns.components.ModifierLoreBuilder.STAT_SEPERATOR;
import static cs.guns.components.ModifierLoreBuilder.getMultiplierStat;
import static cs.guns.components.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class HeadshotDamageSet extends DamageOnHit<HeadshotAttributes>
{
    
    public HeadshotDamageSet(final GunModifier[] modifiers)
    {
        super("Headshot Damage",
              headshotDamageValueSum(modifiers),
              headshotDamageMultiplierSum(modifiers));
    }
    
    public HeadshotDamageSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(super.getTotal(), "total headshot damage"));
        stats.add(STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(super.getValue(), "head shot damage"));
        stats.add(getMultiplierStat(super.getMultiplier(), "head shot damage"));
        return stats;
    }
    
    static private double headshotDamageValueSum(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (HeadshotAttributes mod : getHeadshotModifiers(modSet))
        {
            baseDamageValueSum += mod.getHeadshotDamageModifier();
        }
        return baseDamageValueSum;
    }
    
    static private double headshotDamageMultiplierSum(final GunModifier[] modSet)
    {
        double baseDamageMultiplierSum = 1.0;

        for (HeadshotAttributes mod : getHeadshotModifiers(modSet))
        {
            baseDamageMultiplierSum += mod.getHeadshotDamageMultiplier();
        }
        
        return baseDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static private ArrayList<HeadshotAttributes> getHeadshotModifiers(final GunModifier[] modSet)
    {
        final ArrayList<HeadshotAttributes> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof BaseDamageAttributes)
                mods.add((HeadshotAttributes)mod);
        }
        return mods;
    }
}
