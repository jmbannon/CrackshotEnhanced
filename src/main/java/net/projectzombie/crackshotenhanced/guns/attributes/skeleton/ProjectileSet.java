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
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

/**
 *
 * @author jb
 */

public class ProjectileSet extends AttributeSet<ProjectileSet.ProjectileAttributes>
{
    public interface ProjectileAttributes extends SkeletonAttributes
    {
        int getProjectileAmount();
        double getProjectileSpeedMultiplier();

        int getProjectileRangeValue();
        double getProjectileRangeMultiplier();
    }

    static private final int MIN_PROJECTILE_SPEED = 1;
    
    private final int totalProjectileRange;
    private final int totalProjectileSpeed;
    private final int totalProjectileAmount;

    private final int addedProjectileRangeValue;
    private final double addedProjectileRangeMultiplier;
    private final double addedProjectileSpeedMultiplier;
    private final int addedProjectileAmount;
    
    public ProjectileSet(final GunModifier[] gunMods,
                       final int skeletonProjectileRange,
                       final int skeletonProjectileSpeed,
                       final int skeletonProjectileAmount)
    {
        super("Projectile", gunMods, ProjectileAttributes.class);
        this.addedProjectileAmount = super.getIntSum(ProjectileAttributes::getProjectileAmount);
        this.addedProjectileSpeedMultiplier = super.getMultiplierSum(ProjectileAttributes::getProjectileSpeedMultiplier);
        this.addedProjectileRangeValue = super.getIntSum(ProjectileAttributes::getProjectileRangeValue);
        this.addedProjectileRangeMultiplier = super.getMultiplierSum(ProjectileAttributes::getProjectileRangeMultiplier);

        this.totalProjectileRange = Math.max(1, (int)((skeletonProjectileRange + addedProjectileRangeValue) * addedProjectileRangeMultiplier));
        this.totalProjectileSpeed = Math.max(MIN_PROJECTILE_SPEED, (int)(skeletonProjectileSpeed * addedProjectileSpeedMultiplier));
        this.totalProjectileAmount = Math.max(1, skeletonProjectileAmount + addedProjectileAmount);
    }
    
    public ProjectileSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0, 0, 0);
    }
    
    public int getProjectileRange()        { return totalProjectileRange;  }
    public int getProjectileSpeed()        { return totalProjectileSpeed;  }
    public int getTotalProjectileAmount()  { return totalProjectileAmount; }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        if (totalProjectileAmount != 1)
            stats.addValueStat(totalProjectileAmount, "projectiles per shot");
        else
            stats.addValueStat(totalProjectileAmount, "projectile per shot");
        stats.addValueStat(totalProjectileSpeed, "projectile speed");
        stats.addValueStat(totalProjectileRange, "projectile range");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        if (addedProjectileAmount > 1)
            stats.addValueStat(addedProjectileAmount, "additional projectiles per shot");
        else if (addedProjectileAmount == 1)
            stats.addValueStat(addedProjectileAmount, "additional projectile per shot");

        stats.addMultiplierStatIfValid(addedProjectileSpeedMultiplier, "stock projectile speed");
        stats.addValueStatIfValid(addedProjectileRangeValue, "range");
        stats.addMultiplierStatIfValid(addedProjectileRangeMultiplier, "range");

        return stats.toArrayList();
    }
    
    @Override
    public boolean hasStats()
    {
        return true;
    }
}
