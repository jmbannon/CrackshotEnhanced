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
    
//    public SightSet(final SightAttributes mod)
//    {
//        super("Sight", new GunModifier[] { mod }, SightAttributes.class);
//        this.zoomAmount = mod.getZoomAmount();
//        this.zoomBulletSpreadModifier = mod.getZoomBulletSpreadMultiplier();
//    }
    
    public int getMagazineSize()   { return zoomAmount; }
    public double getReloadDuration() { return zoomBulletSpreadModifier;  }
    
    @Override
    public ArrayList<String> getStats()
    {
        return getStat();
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getMultiplierStat(zoomBulletSpreadModifier, "bullet spread while scoped"));
        stats.add(ModifierLoreBuilder.getValueStat(zoomAmount, "zoom amount"));
        return stats;
    }

    @Override
    public boolean hasStats()
    {
        return zoomAmount > 0 && zoomBulletSpreadModifier > 0;
    }
}
