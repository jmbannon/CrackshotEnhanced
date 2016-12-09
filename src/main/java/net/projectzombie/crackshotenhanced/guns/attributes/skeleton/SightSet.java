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
public class SightSet extends AttributeSet<SightSet.SightAttributes>
{
    public interface SightAttributes extends SkeletonAttributes
    {
        int getZoomAmount();
        double getZoomBulletSpreadMultiplier();
    }

    private final int zoomAmount;
    private final double zoomBulletSpreadModifier;
    
    public SightSet(final GunModifier[] gunMods)
    {
        super("Sight", gunMods, SightAttributes.class);
        this.zoomAmount = super.getIntSum(0, 0, SightAttributes::getZoomAmount);
        this.zoomBulletSpreadModifier = super.getMultiplierSum(SightAttributes::getZoomBulletSpreadMultiplier);
    }
    
    public SightSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    public int getZoomAmount()   { return zoomAmount; }
    public double getZoomBulletSpreadMultiplier() { return zoomBulletSpreadModifier;  }
    
    @Override
    public ArrayList<String> getGunStats(){
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(zoomAmount, "zoom amount");
        stats.addMultiplierStat(zoomBulletSpreadModifier, "bullet spread while zoomed");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(zoomAmount, "zoom amount");
        stats.addMultiplierStatIfValid(zoomBulletSpreadModifier, "bullet spread while zoomed");
        return stats.toArrayList();
    }

    @Override
    public boolean hasStats()
    {
        return zoomAmount > 0 || zoomBulletSpreadModifier > 0;
    }
}
