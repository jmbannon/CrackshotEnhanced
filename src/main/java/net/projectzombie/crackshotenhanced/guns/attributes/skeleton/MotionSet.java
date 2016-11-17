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
        double getRunningSpeedMultiplier();
        double getSprintingSpeedMultiplier();

        double getCrouchingBulletSpreadMultiplier();
        double getStandingBulletSpreadMultiplier();
        double getRunningBulletSpreadMultiplier();
        double getSprintingBulletSpreadMultiplier();
    }

    private static final double MIN_RUNNING_SPEED_MULTIPLIER = 0.3;
    private static final double MIN_SPRINTING_SPEED_MULTIPLIER = 0.3;
    
    private static final double MIN_STANDING_BS_MULTIPLIER = 0.05;
    private static final double MIN_RUNNING_BS_MULTIPLIER = 0.05;
    private static final double MIN_SPRINTING_BS_MULTIPLIER = 0.05;
    
    private final double totalRunningSpeedMultiplier;
    private final double totalSprintingSpeedMultiplier;
    private final double totalCrouchingBulletSpreadMultiplier;
    private final double totalStandingBulletSpreadMultiplier;
    private final double totalRunningBulletSpreadMultiplier;
    private final double totalSprintingBulletSpreadMultiplier;
    
    private final double skeletonRunningSpeedMultiplier;
    private final double skeletonSprintingSpeedMultiplier;
    private final double skeletonCrouchingBulletSpreadMultiplier;
    private final double skeletonStandingBulletSpreadMultiplier;
    private final double skeletonRunningBulletSpreadMultiplier;
    private final double skeletonSprintingBulletSpreadMultiplier;
    
    public MotionSet(final GunModifier[] gunMods,
                    final double skeletonRunningSpeedMultiplier,
                    final double skeletonSprintingSpeedMultiplier,
                    final double skeletonCrouchingBulletSpreadMultiplier,
                    final double skeletonStandingBulletSpreadMultiplier,
                    final double skeletonRunningBulletSpreadMultiplier,
                    final double skeletonSprintingBulletSpreadMultiplier)
    {
        super("Motion", gunMods, MotionAttributes.class);
        this.skeletonRunningSpeedMultiplier = skeletonRunningSpeedMultiplier;
        this.skeletonSprintingSpeedMultiplier = skeletonSprintingSpeedMultiplier;
        this.skeletonCrouchingBulletSpreadMultiplier = skeletonCrouchingBulletSpreadMultiplier;
        this.skeletonStandingBulletSpreadMultiplier = skeletonStandingBulletSpreadMultiplier;
        this.skeletonRunningBulletSpreadMultiplier = skeletonRunningBulletSpreadMultiplier;
        this.skeletonSprintingBulletSpreadMultiplier = skeletonSprintingBulletSpreadMultiplier;
        
        this.totalRunningSpeedMultiplier = super.getDoubleSum(skeletonRunningSpeedMultiplier, MIN_RUNNING_SPEED_MULTIPLIER, MotionAttributes::getRunningSpeedMultiplier);
        this.totalSprintingSpeedMultiplier = super.getDoubleSum(skeletonSprintingSpeedMultiplier, MIN_SPRINTING_SPEED_MULTIPLIER, MotionAttributes::getSprintingSpeedMultiplier);
        this.totalCrouchingBulletSpreadMultiplier = super.getDoubleSum(skeletonCrouchingBulletSpreadMultiplier, MIN_STANDING_BS_MULTIPLIER, MotionAttributes::getCrouchingBulletSpreadMultiplier);
        this.totalStandingBulletSpreadMultiplier = super.getDoubleSum(skeletonStandingBulletSpreadMultiplier, MIN_STANDING_BS_MULTIPLIER, MotionAttributes::getStandingBulletSpreadMultiplier);
        this.totalRunningBulletSpreadMultiplier = super.getDoubleSum(skeletonRunningBulletSpreadMultiplier, MIN_RUNNING_BS_MULTIPLIER, MotionAttributes::getRunningBulletSpreadMultiplier);
        this.totalSprintingBulletSpreadMultiplier = super.getDoubleSum(skeletonSprintingBulletSpreadMultiplier, MIN_SPRINTING_BS_MULTIPLIER, MotionAttributes::getSprintingBulletSpreadMultiplier);
    }
    
    public MotionSet(GunModifier mod)
    {
        /* WARNING
           This is a bit hacky - the total multipliers are actually just the 
           multipliers if we start them all at 0.
        */
        this(new GunModifier[] { mod }, 0, 0, 0, 0, 0, 0);
    }
    
    public double getTotalRunningSpeedMultiplier()          { return totalRunningSpeedMultiplier;  }
    public double getTotalSprintingSpeedMultiplier()        { return totalSprintingSpeedMultiplier;  }
    public double getTotalCrouchingBulletSpreadMultiplier() { return totalCrouchingBulletSpreadMultiplier;  }
    public double getTotalStandingBulletSpreadMultiplier()  { return totalStandingBulletSpreadMultiplier;  }
    public double getTotalRunningBulletSpreadMultiplier()   { return totalRunningBulletSpreadMultiplier;  }
    public double getTotalSprintingBulletSpreadMultiplier() { return totalSprintingBulletSpreadMultiplier;  }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.addAll(getStat());
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonRunningSpeedMultiplier, "stock running speed"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonSprintingSpeedMultiplier, "stock sprinting speed"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonCrouchingBulletSpreadMultiplier, "stock bullet spread when crouching"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonStandingBulletSpreadMultiplier, "stock bullet spread when standing"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonRunningBulletSpreadMultiplier, "stock bullet spread when running"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(skeletonSprintingBulletSpreadMultiplier, "stock bullet spread when sprinting"));
        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalRunningSpeedMultiplier, "running speed"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalSprintingSpeedMultiplier, "sprinting speed"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalCrouchingBulletSpreadMultiplier, "bullet spread when crouching"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalStandingBulletSpreadMultiplier, "bullet spread when standing"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalRunningBulletSpreadMultiplier, "bullet spread when running"));
        stats.add(ModifierLoreBuilder.getMultiplierStat(totalSprintingBulletSpreadMultiplier, "bullet spread when sprinting"));
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true; // Every gun has motion stats
    }
}
