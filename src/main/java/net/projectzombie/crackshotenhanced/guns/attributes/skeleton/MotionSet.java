/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.skeleton;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

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
    private final double totalMovingBulletSpreadMultiplier;

    private final double speedMultiplier;
    private final double crouchingBulletSpreadMultiplier;
    private final double standingBulletSpreadMultiplier;
    private final double movingBulletSpreadMultiplier;

    public MotionSet(final GunModifier[] gunMods,
                    final double skeletonRunningSpeedMultiplier,
                    final double skeletonCrouchingBulletSpreadMultiplier,
                    final double skeletonStandingBulletSpreadMultiplier,
                    final double skeletonMovingBulletSpreadMultiplier)
    {
        super("Motion", gunMods, MotionAttributes.class);
        this.speedMultiplier = super.getMultiplierSum(MotionAttributes::getSpeedMultiplier);
        this.crouchingBulletSpreadMultiplier = super.getMultiplierSum(MotionAttributes::getCrouchingBulletSpreadMultiplier);
        this.standingBulletSpreadMultiplier = super.getMultiplierSum(MotionAttributes::getStandingBulletSpreadMultiplier);
        this.movingBulletSpreadMultiplier = super.getMultiplierSum(MotionAttributes::getRunningBulletSpreadMultiplier);

        this.totalRunningSpeedMultiplier = Math.max(MIN_RUNNING_SPEED_MULTIPLIER, skeletonRunningSpeedMultiplier + speedMultiplier);
        this.totalCrouchingBulletSpreadMultiplier = Math.max(MIN_STANDING_BS_MULTIPLIER, skeletonCrouchingBulletSpreadMultiplier + crouchingBulletSpreadMultiplier);
        this.totalStandingBulletSpreadMultiplier = Math.max(skeletonStandingBulletSpreadMultiplier, skeletonStandingBulletSpreadMultiplier + standingBulletSpreadMultiplier);
        this.totalMovingBulletSpreadMultiplier = Math.max(MIN_RUNNING_BS_MULTIPLIER, skeletonMovingBulletSpreadMultiplier + movingBulletSpreadMultiplier);
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
    public double getTotalMovingBulletSpreadMultiplier()   { return totalMovingBulletSpreadMultiplier;  }

    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addMultiplierStatIfValid(totalRunningSpeedMultiplier, "movement speed");
        stats.addMultiplierStatIfValid(totalCrouchingBulletSpreadMultiplier, "bullet spread when crouching");
        stats.addMultiplierStatIfValid(totalStandingBulletSpreadMultiplier, "bullet spread when standing");
        stats.addMultiplierStatIfValid(totalMovingBulletSpreadMultiplier, "bullet spread when moving");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addMultiplierStatIfValid(speedMultiplier, "movement speed");
        stats.addMultiplierStatIfValid(crouchingBulletSpreadMultiplier, "bullet spread when crouching");
        stats.addMultiplierStatIfValid(standingBulletSpreadMultiplier, "bullet spread when standing");
        stats.addMultiplierStatIfValid(movingBulletSpreadMultiplier, "bullet spread when moving");
        return stats.toArrayList();
    }
}
