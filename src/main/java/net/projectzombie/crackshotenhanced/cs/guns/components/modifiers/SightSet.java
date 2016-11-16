/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components.modifiers;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;

/**
 *
 * @author jb
 */
public class SightSet extends GunModifierSet<SightAttributes>
{
    
    private final int zoomAmount;
    private final double zoomBulletSpreadModifier;
    
    public SightSet(final GunModifier[] gunMods)
    {
        super("Sight", SightAttributes.class);
        this.zoomAmount = getZoomAmount(gunMods);
        this.zoomBulletSpreadModifier = getZoomBulletSpreadMultiplier(gunMods);
    }
    
    public SightSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    public SightSet(final SightAttributes mod)
    {
        super("Sight", SightAttributes.class);
        this.zoomAmount = mod.getZoomAmount();
        this.zoomBulletSpreadModifier = mod.getZoomBulletSpreadMultiplier();
    }
    
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
    
    static
    private double getZoomBulletSpreadMultiplier(final GunModifier[] gunMods)
    {
        double zoomBulletSpreadMultiplier = 1.0;
        for (SightAttributes mod : getScopeModifiers(gunMods))
        {
            zoomBulletSpreadMultiplier += mod.getZoomBulletSpreadMultiplier();
        }
        return Math.max(0, zoomBulletSpreadMultiplier);
    }
    
    static
    private int getZoomAmount(final GunModifier[] gunMods)
    {
        int zoomAmount = 0;
        for (SightAttributes mod : getScopeModifiers(gunMods))
        {
            zoomAmount += mod.getZoomAmount();
        }
        return Math.max(0, zoomAmount);
    }
    
    static
    private ArrayList<SightAttributes> getScopeModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<SightAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof SightAttributes)
                mods.add((SightAttributes)mod);
        }
        return mods;
    }
}
