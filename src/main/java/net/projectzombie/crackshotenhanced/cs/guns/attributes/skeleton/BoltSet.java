/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes.skeleton;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.attributes.AttributeSet;

import static net.projectzombie.crackshotenhanced.cs.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class BoltSet extends AttributeSet<BoltSet.BoltAttributes>
{
    public interface BoltAttributes extends SkeletonAttributes
    {
        double getBoltDurationMultiplier();
    }

    private final double boltDurationMultiplier;
    private final double skeletonBoltActionDurationInTicks;
    
    public BoltSet(final GunModifier[] gunMods,
                   final int skeletonBoltActionDurationInTicks)
    {
        super("Bolt Action", gunMods, BoltAttributes.class);
        this.boltDurationMultiplier = super.getMultiplierSum(BoltAttributes::getBoltDurationMultiplier);
        this.skeletonBoltActionDurationInTicks = skeletonBoltActionDurationInTicks;
    }
    
    public BoltSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0);
    }
    
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
}
