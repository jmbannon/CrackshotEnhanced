/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes.modifier;

import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.attributes.AttributeSet;

/**
 *
 * @author jb
 */
public class BulletSpreadSet extends AttributeSet<BulletSpreadSet.BulletSpreadAttributes>
{
    public interface BulletSpreadAttributes extends ModifierAttributes {
        /**
         * Multiplier - percentage to modifiy the bullet spread.
         * @return Percentage to modifiy the bullet spread (0, inf).
         */
        double getBulletSpreadMultiplier();
    }

    static private final double MIN_BULLET_SPREAD = 0.05;
    private final double totalBulletSpread;
    private final double bulletSpreadMultiplier;
    private final double skeletonBulletSpread;
    
    public BulletSpreadSet(final GunModifier[] gunMods,
                           final double skeletonBulletSpread)
    {
        super("Bullet Spread", gunMods, BulletSpreadAttributes.class);
        this.bulletSpreadMultiplier = super.getMultiplierSum(BulletSpreadAttributes::getBulletSpreadMultiplier);
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
}
