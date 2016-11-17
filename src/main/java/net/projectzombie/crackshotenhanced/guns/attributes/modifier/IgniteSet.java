/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;

import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.STAT_SEPERATOR;
import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.getMultiplierStat;
import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.getValueStat;
import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class IgniteSet extends Chance<IgniteSet.IgniteAttributes>
{
    public interface IgniteAttributes extends ModifierAttributes {
        double getIgniteChance();
        double getIgniteDuration();
        double getIgniteDamageMultiplierFromFireDamage();
    }

    private final double igniteDurationInSeconds;
    private final double igniteFireDmgMultiplier;
    private final double igniteDPS;
    
    public IgniteSet(final GunModifier[] mods,
                     final double totalFireDamage)
    {
        super("Ignite Damage", mods, IgniteAttributes::getIgniteChance, IgniteAttributes.class);
        this.igniteDurationInSeconds = super.getDoubleSum(IgniteAttributes::getIgniteDuration);
        this.igniteFireDmgMultiplier = super.getDoubleSum(IgniteAttributes::getIgniteDamageMultiplierFromFireDamage);
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
}
