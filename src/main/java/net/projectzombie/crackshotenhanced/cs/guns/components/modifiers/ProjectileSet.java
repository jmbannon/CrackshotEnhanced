/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components.modifiers;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;

/**
 *
 * @author jb
 */
public class ProjectileSet extends GunModifierSet<ProjectileAttributes>
{
    static private final int MIN_PROJECTILE_SPEED = 1;
    
    private final int totalProjectileRange;
    private final int totalProjectileSpeed;
    private final int totalProjectileAmount;
    
    private final int skeletonProjectileRange;
    private final int skeletonProjectileSpeed;
    private final int skeletonProjectileAmount;
    
    public ProjectileSet(final GunModifier[] gunMods,
                       final int skeletonProjectileRange,
                       final int skeletonProjectileSpeed,
                       final int skeletonProjectileAmount)
    {
        super("Projectile", ProjectileAttributes.class);
        final ArrayList<ProjectileAttributes> attributes = super.getModifiers(gunMods);
        this.skeletonProjectileRange = skeletonProjectileRange;
        this.skeletonProjectileSpeed = skeletonProjectileSpeed;
        this.skeletonProjectileAmount = skeletonProjectileAmount;
        this.totalProjectileRange = getTotalProjectileRange(attributes, skeletonProjectileRange);
        this.totalProjectileSpeed = getTotalProjectileSpeed(attributes, skeletonProjectileSpeed);
        this.totalProjectileAmount = getTotalProjectileAmount(attributes, skeletonProjectileAmount);
    }
    
    public ProjectileSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0, 0, 0);
    }
    
    public int getProjectileRange()        { return totalProjectileRange;  }
    public int getProjectileSpeed()        { return totalProjectileSpeed;  }
    public int getTotalProjectileAmount()  { return totalProjectileAmount; }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.addAll(getStat());
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        if (skeletonProjectileAmount > 1) 
            stats.add(ModifierLoreBuilder.getValueStat(skeletonProjectileAmount, "stock projectiles per shot"));
        else
            stats.add(ModifierLoreBuilder.getValueStat(skeletonProjectileAmount, "stock projectile per shot"));
        stats.add(ModifierLoreBuilder.getValueStat(skeletonProjectileSpeed, "stock projectile speed"));
        stats.add(ModifierLoreBuilder.getValueStat(skeletonProjectileRange, "stock projectile range"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        if (totalProjectileAmount > 1)
            stats.add(ModifierLoreBuilder.getValueStat(totalProjectileAmount, "projectiles per shot"));
        else
            stats.add(ModifierLoreBuilder.getValueStat(totalProjectileAmount, "projectile per shot"));
        stats.add(ModifierLoreBuilder.getValueStat(totalProjectileSpeed, "projectile speed"));
        stats.add(ModifierLoreBuilder.getValueStat(totalProjectileRange, "projectile range"));
        
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true;
    }
    
    static
    private int getTotalProjectileSpeed(final ArrayList<ProjectileAttributes> gunMods,
                                   final int skeletonProjectileSpeed)
    {
        double projectileSpeedMultiplier = 1.0;
        for (ProjectileAttributes mod : gunMods)
        {
            projectileSpeedMultiplier += mod.getProjectileSpeedMultiplier();
        }
        return Math.max(MIN_PROJECTILE_SPEED, (int)Math.round((double)skeletonProjectileSpeed * projectileSpeedMultiplier));
    }
    
    static
    private int getTotalProjectileRange(final ArrayList<ProjectileAttributes> gunMods,
                                   int projectileRangeValue)
    {
        double projectileRangeMultiplier = 1.0;
        for (ProjectileAttributes mod : gunMods)
        {
            projectileRangeMultiplier += mod.getProjectileRangeMultiplier();
            projectileRangeValue += mod.getProjectileRangeValue();
        }
        return Math.max(1, (int)Math.round((double)projectileRangeValue * projectileRangeMultiplier));
    }
    
    static
    private int getTotalProjectileAmount(final ArrayList<ProjectileAttributes> gunMods,
                                         int skeletonProjectileAmount)
    {
        for (ProjectileAttributes mod : gunMods)
        {
            skeletonProjectileAmount += mod.getProjectileAmount();
        }
        return Math.max(0, skeletonProjectileAmount);
    }
}
