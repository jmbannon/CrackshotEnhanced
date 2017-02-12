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
    private static final double MIN_CROUCHING_BS_MULTIPLIER = 0.05;

    private final double runningSpeedMultiplier;
    private final double crouchingBulletSpreadMultiplier;
    private final double standingBulletSpreadMultiplier;
    private final double movingBulletSpreadMultiplier;


    public MotionSet(final GunModifier[] gunMods)
    {
        super("Motion", gunMods, MotionAttributes.class);

        this.runningSpeedMultiplier = Math.max(MIN_RUNNING_SPEED_MULTIPLIER, super.getMultiplierSum(MotionAttributes::getSpeedMultiplier));
        this.crouchingBulletSpreadMultiplier = Math.max(MIN_CROUCHING_BS_MULTIPLIER, super.getMultiplierSum(MotionAttributes::getCrouchingBulletSpreadMultiplier));
        this.standingBulletSpreadMultiplier = Math.max(MIN_STANDING_BS_MULTIPLIER, super.getMultiplierSum(MotionAttributes::getStandingBulletSpreadMultiplier));
        this.movingBulletSpreadMultiplier = Math.max(MIN_RUNNING_BS_MULTIPLIER, super.getMultiplierSum(MotionAttributes::getRunningBulletSpreadMultiplier));
    }
    
    public MotionSet(GunModifier mod)
    {
        /* WARNING
           This is a bit hacky - the total multipliers are actually just the 
           multipliers if we start them all at 0.
        */
        this(new GunModifier[] { mod });
    }
    
    public double getRunningSpeedMultiplier()          { return runningSpeedMultiplier;  }
    public double getCrouchingBulletSpreadMultiplier() { return crouchingBulletSpreadMultiplier;  }
    public double getStandingBulletSpreadMultiplier()  { return standingBulletSpreadMultiplier;  }
    public double getMovingBulletSpreadMultiplier()   { return movingBulletSpreadMultiplier;  }

    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addMultiplierStatIfValid(runningSpeedMultiplier, "movement speed");
        stats.addMultiplierStatIfValid(crouchingBulletSpreadMultiplier, "bullet spread when crouching");
        stats.addMultiplierStatIfValid(standingBulletSpreadMultiplier, "bullet spread when standing");
        stats.addMultiplierStatIfValid(movingBulletSpreadMultiplier, "bullet spread when moving");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        return this.getGunStats();
    }
}
