/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components.modifiers;

import java.util.ArrayList;
import cs.guns.components.GunModifier;
import cs.guns.components.GunModifierSet;
import static cs.guns.components.ModifierLoreBuilder.STAT_SEPERATOR;
import static cs.guns.components.ModifierLoreBuilder.getMultiplierStat;

/**
 *
 * @author jb
 */
public class MotionSet extends GunModifierSet<MotionAttributes>
{
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
        super("Motion");
        
        this.skeletonRunningSpeedMultiplier = skeletonRunningSpeedMultiplier;
        this.skeletonSprintingSpeedMultiplier = skeletonSprintingSpeedMultiplier;
        this.skeletonCrouchingBulletSpreadMultiplier = skeletonCrouchingBulletSpreadMultiplier;
        this.skeletonStandingBulletSpreadMultiplier = skeletonStandingBulletSpreadMultiplier;
        this.skeletonRunningBulletSpreadMultiplier = skeletonRunningBulletSpreadMultiplier;
        this.skeletonSprintingBulletSpreadMultiplier = skeletonSprintingBulletSpreadMultiplier;
        
        this.totalRunningSpeedMultiplier = getTotalRunningSpeedMultiplier(gunMods, skeletonRunningSpeedMultiplier);
        this.totalSprintingSpeedMultiplier = getTotalSprintingSpeedMultiplier(gunMods, skeletonSprintingSpeedMultiplier);
        this.totalCrouchingBulletSpreadMultiplier = getCrouchingBulletSpreadMultiplier(gunMods, skeletonCrouchingBulletSpreadMultiplier);
        this.totalStandingBulletSpreadMultiplier = getStandingBulletSpreadMultiplier(gunMods, skeletonStandingBulletSpreadMultiplier);
        this.totalRunningBulletSpreadMultiplier = getRunningBulletSpreadMultiplier(gunMods, skeletonRunningBulletSpreadMultiplier);
        this.totalSprintingBulletSpreadMultiplier = getSprintingBulletSpreadMultiplier(gunMods, skeletonSprintingBulletSpreadMultiplier);
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
        stats.add(STAT_SEPERATOR);
        stats.add(getMultiplierStat(skeletonRunningSpeedMultiplier, "stock running speed"));
        stats.add(getMultiplierStat(skeletonSprintingSpeedMultiplier, "stock sprinting speed"));
        stats.add(getMultiplierStat(skeletonCrouchingBulletSpreadMultiplier, "stock bullet spread when crouching"));
        stats.add(getMultiplierStat(skeletonStandingBulletSpreadMultiplier, "stock bullet spread when standing"));
        stats.add(getMultiplierStat(skeletonRunningBulletSpreadMultiplier, "stock bullet spread when running"));
        stats.add(getMultiplierStat(skeletonSprintingBulletSpreadMultiplier, "stock bullet spread when sprinting"));
        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getMultiplierStat(totalRunningSpeedMultiplier, "running speed"));
        stats.add(getMultiplierStat(totalSprintingSpeedMultiplier, "sprinting speed"));
        stats.add(getMultiplierStat(totalCrouchingBulletSpreadMultiplier, "bullet spread when crouching"));
        stats.add(getMultiplierStat(totalStandingBulletSpreadMultiplier, "bullet spread when standing"));
        stats.add(getMultiplierStat(totalRunningBulletSpreadMultiplier, "bullet spread when running"));
        stats.add(getMultiplierStat(totalSprintingBulletSpreadMultiplier, "bullet spread when sprinting"));
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true; // Every gun has motion stats
    }
    
    static
    private double getTotalRunningSpeedMultiplier(final GunModifier[] gunMods,
                                                  double skeletonRunningSpeedMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonRunningSpeedMultiplier += mod.getRunningSpeedMultiplier();
        
        return Math.max(MIN_RUNNING_SPEED_MULTIPLIER, skeletonRunningSpeedMultiplier);
    }
    
    static
    private double getTotalSprintingSpeedMultiplier(final GunModifier[] gunMods,
                                                    double skeletonSprintingSpeedMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonSprintingSpeedMultiplier += mod.getSprintingSpeedMultiplier();
        
        return Math.max(MIN_SPRINTING_SPEED_MULTIPLIER, skeletonSprintingSpeedMultiplier);
    }
    
    static
    private double getCrouchingBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                     double skeletonCrouchingBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonCrouchingBSMultiplier += mod.getStandingBulletSpreadMultiplier();
        
        return Math.max(MIN_STANDING_BS_MULTIPLIER, skeletonCrouchingBSMultiplier);
    }
    
    static
    private double getStandingBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                     double skeletonStandingBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonStandingBSMultiplier += mod.getStandingBulletSpreadMultiplier();
        
        return Math.max(MIN_STANDING_BS_MULTIPLIER, skeletonStandingBSMultiplier);
    }
    
    static
    private double getRunningBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                    double skeletonRunningBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            skeletonRunningBSMultiplier += mod.getRunningBulletSpreadMultiplier();
        
        return Math.max(MIN_RUNNING_BS_MULTIPLIER, skeletonRunningBSMultiplier);
    }
    
    static
    private double getSprintingBulletSpreadMultiplier(final GunModifier[] gunMods,
                                                      double sprintingBSMultiplier)
    {
        for (MotionAttributes mod : getSpeedModifiers(gunMods))
            sprintingBSMultiplier += mod.getSprintingBulletSpreadMultiplier();
        
        return Math.max(MIN_SPRINTING_BS_MULTIPLIER, sprintingBSMultiplier);
    }
    
    static
    private ArrayList<MotionAttributes> getSpeedModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<MotionAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MotionAttributes)
                mods.add((MotionAttributes)mod);
        }
        return mods;
    }
}
