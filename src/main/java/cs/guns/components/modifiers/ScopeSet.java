/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components.modifiers;

import java.util.ArrayList;
import cs.guns.components.GunModifier;
import cs.guns.components.GunModifierSet;
import static cs.guns.components.ModifierLoreBuilder.getMultiplierStat;
import static cs.guns.components.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class ScopeSet extends GunModifierSet<ScopeAttributes>
{
    
    private final int zoomAmount;
    private final double zoomBulletSpreadModifier;
    
    public ScopeSet(final GunModifier[] gunMods)
    {
        super("Sight");
        this.zoomAmount = getZoomAmount(gunMods);
        this.zoomBulletSpreadModifier = getZoomBulletSpreadMultiplier(gunMods);
    }
    
    public ScopeSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    public ScopeSet(final ScopeAttributes mod)
    {
        super("Sight");
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
        stats.add(getMultiplierStat(zoomBulletSpreadModifier, "bullet spread while scoped"));
        stats.add(getValueStat(zoomAmount, "zoom amount"));
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
        for (ScopeAttributes mod : getScopeModifiers(gunMods))
        {
            zoomBulletSpreadMultiplier += mod.getZoomBulletSpreadMultiplier();
        }
        return Math.max(0, zoomBulletSpreadMultiplier);
    }
    
    static
    private int getZoomAmount(final GunModifier[] gunMods)
    {
        int zoomAmount = 0;
        for (ScopeAttributes mod : getScopeModifiers(gunMods))
        {
            zoomAmount += mod.getZoomAmount();
        }
        return Math.max(0, zoomAmount);
    }
    
    static
    private ArrayList<ScopeAttributes> getScopeModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<ScopeAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof ScopeAttributes)
                mods.add((ScopeAttributes)mod);
        }
        return mods;
    }
}
