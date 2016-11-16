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
public class MagazineSet extends GunModifierSet<MagazineAttributes>
{
    static private final double MIN_RELOAD_SPEED = 1.0;
    
    private final int extraMagSize;
    private final int skeletonMagSize;
    private final int totalMagSize;
    private final double reloadSpeed;
    
    public MagazineSet(final GunModifier[] gunMods,
                       final int skeletonMagSize,
                       final double skeletonReloadDuration)
    {
        super("Magazine", MagazineAttributes.class);
        this.skeletonMagSize = skeletonMagSize;
        this.extraMagSize = getMagazineSize(gunMods, skeletonMagSize);
        this.reloadSpeed = getReloadSpeed(gunMods, skeletonReloadDuration);
        this.totalMagSize = skeletonMagSize + extraMagSize;
    }
    
    public MagazineSet(GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0, 0);
    }
    
    public int getMagazineSize()   { return extraMagSize; }
    public double getReloadDuration() { return reloadSpeed;  }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(totalMagSize, "total magazine size"));
        stats.add(ModifierLoreBuilder.STAT_SEPERATOR);
        stats.add(ModifierLoreBuilder.getValueStat(skeletonMagSize, "stock mag size"));
        stats.addAll(getStat());
        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getValueStat(extraMagSize, "extra mag size"));
        return stats;
    }

    @Override
    public boolean hasStats()
    {
        return extraMagSize > 0 && reloadSpeed > 0;
    }
    
    static
    private double getReloadSpeed(final GunModifier[] gunMods,
                                  double skeletonReloadDuration)
    {
        double reloadDurationMultiplier = 1.0;
        for (MagazineAttributes mod : getMagazineModifiers(gunMods))
        {
            reloadDurationMultiplier += mod.getReloadSpeedMultiplier();
        }
        return Math.max(MIN_RELOAD_SPEED, skeletonReloadDuration * reloadDurationMultiplier);
    }
    
    static
    private int getMagazineSize(final GunModifier[] gunMods,
                                      int skeletonMagSize)
    {
        double magSizeMultiplier = 1.0;
        for (MagazineAttributes mod : getMagazineModifiers(gunMods))
        {
            skeletonMagSize += mod.getMagazineSizeModifier();
            magSizeMultiplier += mod.getMagazineSizeMultiplier();
        }
        return Math.max(1, (int)Math.round((double)skeletonMagSize * magSizeMultiplier));
    }
    
    static
    private ArrayList<MagazineAttributes> getMagazineModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<MagazineAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MagazineAttributes)
                mods.add((MagazineAttributes)mod);
        }
        return mods;
    }
}
