/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.skeleton;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;

/**
 *
 * @author jb
 */
public class MotionSet extends AttributeSet<MotionSet.MotionAttributes>
{
    public interface MotionAttributes extends SkeletonAttributes
    {
        double getSpeedMultiplier();

        double getCrouchingBulletSpreadMultiplier();
        double getStandingBulletSpreadMultiplier();
        double getRunningBulletSpreadMultiplier();
    }

    private static final double MIN_RUNNING_SPEED_MULTIPLIER = 0.3;

    private static final double MIN_STANDING_BS_MULTIPLIER = 0.05;
    private static final double MIN_RUNNING_BS_MULTIPLIER = 0.05;

    private final double totalRunningSpeedMultiplier;
    private final double totalCrouchingBulletSpreadMultiplier;
    private final double totalStandingBulletSpreadMultiplier;
    private final double totalRunningBulletSpreadMultiplier;

    private final double skeletonSpeedMultiplier;
    private final double skeletonCrouchingBulletSpreadMultiplier;
    private final double skeletonStandingBulletSpreadMultiplier;
    private final double skeletonRunningBulletSpreadMultiplier;

    public MotionSet(final GunModifier[] gunMods,
                    final double skeletonRunningSpeedMultiplier,
                    final double skeletonCrouchingBulletSpreadMultiplier,
                    final double skeletonStandingBulletSpreadMultiplier,
                    final double skeletonRunningBulletSpreadMultiplier)
    {
        super("Motion", gunMods, MotionAttributes.class);
        this.skeletonSpeedMultiplier = skeletonRunningSpeedMultiplier;
        this.skeletonCrouchingBulletSpreadMultiplier = skeletonCrouchingBulletSpreadMultiplier;
        this.skeletonStandingBulletSpreadMultiplier = skeletonStandingBulletSpreadMultiplier;
        this.skeletonRunningBulletSpreadMultiplier = skeletonRunningBulletSpreadMultiplier;

        this.totalRunningSpeedMultiplier = Math.max(MIN_RUNNING_SPEED_MULTIPLIER, skeletonRunningSpeedMultiplier + super.getMultiplierSum(MotionAttributes::getSpeedMultiplier));
        this.totalCrouchingBulletSpreadMultiplier = Math.max(MIN_STANDING_BS_MULTIPLIER, skeletonCrouchingBulletSpreadMultiplier + super.getMultiplierSum(MotionAttributes::getCrouchingBulletSpreadMultiplier));
        this.totalStandingBulletSpreadMultiplier = Math.max(skeletonStandingBulletSpreadMultiplier, super.getMultiplierSum(MotionAttributes::getStandingBulletSpreadMultiplier));
        this.totalRunningBulletSpreadMultiplier = Math.max(MIN_RUNNING_BS_MULTIPLIER, super.getMultiplierSum(MotionAttributes::getRunningBulletSpreadMultiplier));
    }
    
    public MotionSet(GunModifier mod)
    {
        /* WARNING
           This is a bit hacky - the total multipliers are actually just the 
           multipliers if we start them all at 0.
        */
        this(new GunModifier[] { mod }, 0, 0, 0, 0);
    }
    
    public double getTotalRunningSpeedMultiplier()          { return totalRunningSpeedMultiplier;  }
    public double getTotalCrouchingBulletSpreadMultiplier() { return totalCrouchingBulletSpreadMultiplier;  }
    public double getTotalStandingBulletSpreadMultiplier()  { return totalStandingBulletSpreadMultiplier;  }
    public double getTotalRunningBulletSpreadMultiplier()   { return totalRunningBulletSpreadMultiplier;  }

    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.addAll(getStat());
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonSpeedMultiplier, "stock running speed"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonCrouchingBulletSpreadMultiplier, "stock bullet spread when crouching"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonStandingBulletSpreadMultiplier, "stock bullet spread when standing"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonRunningBulletSpreadMultiplier, "stock bullet spread when running"));

        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalRunningSpeedMultiplier, "speed"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalCrouchingBulletSpreadMultiplier, "bullet spread when crouching"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalStandingBulletSpreadMultiplier, "bullet spread when standing"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalRunningBulletSpreadMultiplier, "bullet spread when running"));
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true; // Every gun has motion stats
    }
}
