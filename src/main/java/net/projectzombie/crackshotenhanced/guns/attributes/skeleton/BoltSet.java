/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.skeleton;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.TPS;

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
    private final double totalBoltDurationInTicks;

    public BoltSet(final GunModifier[] gunMods,
                   final int skeletonBoltActionDurationInTicks)
    {
        super("Bolt Action", gunMods, BoltAttributes.class);
        this.boltDurationMultiplier = super.getMultiplierSum(BoltAttributes::getBoltDurationMultiplier);
        this.totalBoltDurationInTicks = skeletonBoltActionDurationInTicks * boltDurationMultiplier;
    }

    public double getBoltDurationMultiplier() { return boltDurationMultiplier; }
    public double getTotalBoltDurationInTicks() { return totalBoltDurationInTicks; }
    
    public BoltSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0);
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(totalBoltDurationInTicks / TPS, "bolt action duration in seconds");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addMultiplierStatIfValid(boltDurationMultiplier, "bolt action duration");
        return stats.toArrayList();
    }
    
    @Override
    public boolean hasStats()
    {
        return totalBoltDurationInTicks > 0;
    }
}
