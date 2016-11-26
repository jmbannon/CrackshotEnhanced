/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

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

    public BulletSpreadSet(final GunModifier[] gunMods,
                           final double skeletonBulletSpread)
    {
        super("Bullet Spread", gunMods, BulletSpreadAttributes.class);
        this.bulletSpreadMultiplier = super.getMultiplierSum(BulletSpreadAttributes::getBulletSpreadMultiplier);
        this.totalBulletSpread = Math.max(MIN_BULLET_SPREAD, bulletSpreadMultiplier * skeletonBulletSpread);
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
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(totalBulletSpread, "bullet spread");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addMultiplierStatIfValid(bulletSpreadMultiplier, "bullet spread");
        return stats.toArrayList();
    }
}
