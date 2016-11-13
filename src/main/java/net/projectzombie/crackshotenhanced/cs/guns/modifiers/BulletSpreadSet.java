/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.modifiers;

import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;
import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;

/**
 *
 * @author jb
 */
public class BulletSpreadSet extends GunModifierSet<BulletSpreadAttributes>
{
    static private final double MIN_BULLET_SPREAD = 0.05;
    private final double totalBulletSpread;
    private final double bulletSpreadMultiplier;
    private final double skeletonBulletSpread;
    
    public BulletSpreadSet(final GunModifier[] gunMods,
                           final double skeletonBulletSpread)
    {
        super("Bullet Spread");
        this.bulletSpreadMultiplier = getBulletSpreadMultiplier(gunMods);
        this.totalBulletSpread = Math.max(MIN_BULLET_SPREAD, bulletSpreadMultiplier * skeletonBulletSpread);
        this.skeletonBulletSpread = skeletonBulletSpread;
    }
    
    public BulletSpreadSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }
    
    public double getBulletSpread() { return totalBulletSpread; }
    
    @Override
    public boolean hasStats()
    {
        return true;
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(ModifierLoreBuilder.getValueStat(totalBulletSpread, "total bullet spread"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.add(ModifierLoreBuilder.getValueStat(skeletonBulletSpread, "stock bullet spread"));
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getMultiplierStat(bulletSpreadMultiplier, "bullet spread"));
        return stats;
    }
    
    static
    private double getBulletSpreadMultiplier(final GunModifier[] gunMods)
    {
        double bulletSpreadMult = 1.0;
        for (BulletSpreadAttributes mod : getBulletSpreadModifiers(gunMods))
        {
            bulletSpreadMult += mod.getBulletSpreadMultiplier();
        }
        return Math.max(0, bulletSpreadMult);
    }
    
    /**
     * @return Returns all BulletSpreadModifiers on the gun.
     */
    static
    private ArrayList<BulletSpreadAttributes> getBulletSpreadModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<BulletSpreadAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof BulletSpreadAttributes)
                mods.add((BulletSpreadAttributes)mod);
        }
        return mods;
   }
}
