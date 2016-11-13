/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components.modifiers;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;

import static net.projectzombie.crackshotenhanced.cs.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class BoltSet extends GunModifierSet<BoltAttributes>
{
    private final double boltDurationMultiplier;
    private final double skeletonBoltActionDurationInTicks;
    
    public BoltSet(final GunModifier[] gunMods,
                   final int skeletonBoltActionDurationInTicks)
    {
        super("Bolt Action");
        this.boltDurationMultiplier = getBoltDurationMultiplier(gunMods);
        this.skeletonBoltActionDurationInTicks = skeletonBoltActionDurationInTicks;
    }
    
    public BoltSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0);
    }
    
    public double getBoltDurationMultiplier() { return boltDurationMultiplier;  }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        final double newBoltSpeed = skeletonBoltActionDurationInTicks * boltDurationMultiplier;
        
        stats.add(ModifierLoreBuilder.getValueStat(newBoltSpeed / TPS, "bolt action duration in seconds"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.addAll(getStat());
        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getMultiplierStat(boltDurationMultiplier, "bolt action duration"));
        stats.add(ModifierLoreBuilder.getValueStat(skeletonBoltActionDurationInTicks / TPS, "stock bolt action duration"));
        
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return boltDurationMultiplier > 0 && skeletonBoltActionDurationInTicks > 0;
    }
    
    static
    private double getBoltDurationMultiplier(final GunModifier[] gunMods)
    {
        double boltDurationMultiplier = 1.0;
        for (BoltAttributes mod : getBoltModifiers(gunMods))
        {
            boltDurationMultiplier += mod.getBoltDurationMultiplier();
        }
        return Math.max(0.0, boltDurationMultiplier);
    }
    
    static
    private ArrayList<BoltAttributes> getBoltModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<BoltAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof BoltAttributes)
                mods.add((BoltAttributes)mod);
        }
        return mods;
    }
}
