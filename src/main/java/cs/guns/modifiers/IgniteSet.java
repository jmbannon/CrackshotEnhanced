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
import static cs.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class IgniteSet extends Chance<IgniteAttributes>
{
    
    private final double igniteDurationInSeconds;
    private final double igniteFireDmgMultiplier;
    private final double igniteDPS;
    
    public IgniteSet(final GunModifier[] mods,
                     final double totalFireDamage)
    {
        super("Ignite Damage",
              getIgniteChance(mods));
        
        this.igniteDurationInSeconds = getIgniteDurationInSeconds(mods);
        this.igniteFireDmgMultiplier = getIgniteFireDamageMultiplierSum(mods);
        this.igniteDPS = this.igniteFireDmgMultiplier * totalFireDamage;
    }
    
    public IgniteSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    public double getIgniteDurationInSeconds()    { return igniteDurationInSeconds;       }
    public double getIgniteDurationInTicks()      { return igniteDurationInSeconds / TPS; }
    public double getIgniteFireDamageMultiplier() { return igniteFireDmgMultiplier;       }
    public double getIgniteDamagePerSecond()      { return igniteDPS;                     }
    public double getIgniteDamagePerTick()        { return igniteDPS / TPS;               }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();

        stats.add(getValueStat(igniteDPS, "total ignite damage p/sec"));
        stats.add(STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getMultiplierStat(super.getChance(), "ignite chance"));
        stats.add(getValueStat(igniteDurationInSeconds, "ignite duration in seconds"));
        stats.add(getMultiplierStat(igniteFireDmgMultiplier, "ignite damage dealt from fire damage p/sec"));
        return stats;
    }
    
    static
    private double getIgniteChance(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (IgniteAttributes mod : getIgniteModifiers(modSet))
        {
            baseDamageValueSum += mod.getIgniteChance();
        }
        return baseDamageValueSum;
    }
    
    static
    private double getIgniteDurationInSeconds(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (IgniteAttributes mod : getIgniteModifiers(modSet))
        {
            baseDamageValueSum += mod.getIgniteDuration();
        }
        return baseDamageValueSum;
    }
    
    static 
    private double getIgniteFireDamageMultiplierSum(final GunModifier[] modSet)
    {
        double fireDamageMultiplierSum = 0.0;

        for (IgniteAttributes mod : getIgniteModifiers(modSet))
        {
            fireDamageMultiplierSum += mod.getIgniteDamageMultiplierFromFireDamage();
        }
        return fireDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static 
    private ArrayList<IgniteAttributes> getIgniteModifiers(final GunModifier[] modSet)
    {
        final ArrayList<IgniteAttributes> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof IgniteAttributes)
                mods.add((IgniteAttributes)mod);
        }
        return mods;
    }
}
