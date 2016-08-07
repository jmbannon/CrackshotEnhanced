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
import static cs.guns.components.ModifierLoreBuilder.getValueStat;

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
        super("Projectile");
        this.skeletonProjectileRange = skeletonProjectileRange;
        this.skeletonProjectileSpeed = skeletonProjectileSpeed;
        this.skeletonProjectileAmount = skeletonProjectileAmount;
        this.totalProjectileRange = getTotalProjectileRange(gunMods, skeletonProjectileRange);
        this.totalProjectileSpeed = getTotalProjectileSpeed(gunMods, skeletonProjectileSpeed);
        this.totalProjectileAmount = getTotalProjectileAmount(gunMods, skeletonProjectileAmount);
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
        stats.add(STAT_SEPERATOR);
        if (skeletonProjectileAmount > 1) 
            stats.add(getValueStat(skeletonProjectileAmount, "stock projectiles per shot"));
        else
            stats.add(getValueStat(skeletonProjectileAmount, "stock projectile per shot"));
        stats.add(getValueStat(skeletonProjectileSpeed, "stock projectile speed"));
        stats.add(getValueStat(skeletonProjectileRange, "stock projectile range"));
        stats.add(STAT_SEPERATOR);
        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        if (totalProjectileAmount > 1)
            stats.add(getValueStat(totalProjectileAmount, "projectiles per shot"));
        else
            stats.add(getValueStat(totalProjectileAmount, "projectile per shot"));
        stats.add(getValueStat(totalProjectileSpeed, "projectile speed"));
        stats.add(getValueStat(totalProjectileRange, "projectile range"));
        
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true;
    }
    
    static
    private int getTotalProjectileSpeed(final GunModifier[] gunMods,
                                   final int skeletonProjectileSpeed)
    {
        double projectileSpeedMultiplier = 1.0;
        for (ProjectileAttributes mod : getProjectileModifiers(gunMods))
        {
            projectileSpeedMultiplier += mod.getProjectileSpeedMultiplier();
        }
        return Math.max(MIN_PROJECTILE_SPEED, (int)Math.round((double)skeletonProjectileSpeed * projectileSpeedMultiplier));
    }
    
    static
    private int getTotalProjectileRange(final GunModifier[] gunMods,
                                   int projectileRangeValue)
    {
        double projectileRangeMultiplier = 1.0;
        for (ProjectileAttributes mod : getProjectileModifiers(gunMods))
        {
            projectileRangeMultiplier += mod.getProjectileRangeMultiplier();
            projectileRangeValue += mod.getProjectileRangeValue();
        }
        return Math.max(1, (int)Math.round((double)projectileRangeValue * projectileRangeMultiplier));
    }
    
    static
    private int getTotalProjectileAmount(final GunModifier[] gunMods,
                                         int skeletonProjectileAmount)
    {
        for (ProjectileAttributes mod : getProjectileModifiers(gunMods))
        {
            skeletonProjectileAmount += mod.getProjectileAmount();
        }
        return Math.max(0, skeletonProjectileAmount);
    }
    
    static
    private ArrayList<ProjectileAttributes> getProjectileModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<ProjectileAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MagazineAttributes)
                mods.add((ProjectileAttributes)mod);
        }
        return mods;
    }

}
