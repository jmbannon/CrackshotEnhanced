/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes.skeleton;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.attributes.AttributeSet;

/**
 *
 * @author jb
 */
public class MagazineSet extends AttributeSet<MagazineSet.MagazineAttributes>
{
    public interface MagazineAttributes extends SkeletonAttributes
    {
        int getMagazineSizeModifier();
        double getMagazineSizeMultiplier();

        double getReloadSpeedMultiplier();
    }

    static private final double MIN_RELOAD_SPEED = 1.0;
    static private final int MIN_MAG_SIZE = 1;
    
    private final int magSizeModifier;
    private final double magSizeMultiplier;
    private final int skeletonMagSize;
    private final int totalMagSize;
    private final double reloadSpeedMultiplier;
    private final double totalReloadSpeed;
    
    public MagazineSet(final GunModifier[] gunMods,
                       final int skeletonMagSize,
                       final double skeletonReloadDuration)
    {
        super("Magazine", gunMods, MagazineAttributes.class);
        this.skeletonMagSize = skeletonMagSize;
        this.magSizeModifier = super.getIntSum(MagazineAttributes::getMagazineSizeModifier);
        this.magSizeMultiplier = super.getMultiplierSum(MagazineAttributes::getMagazineSizeMultiplier);
        this.reloadSpeedMultiplier = super.getMultiplierSum(MagazineAttributes::getReloadSpeedMultiplier);
        this.totalMagSize = Math.max(MIN_MAG_SIZE, (int)((skeletonMagSize + magSizeModifier) * magSizeMultiplier));
        this.totalReloadSpeed = Math.max(MIN_RELOAD_SPEED, skeletonReloadDuration * reloadSpeedMultiplier);
    }
    
    public MagazineSet(GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0, 0);
    }
    
    public int getMagazineSize()   { return magSizeModifier; }
    public double getReloadDuration() { return reloadSpeedMultiplier;  }
    
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
        stats.add(ModifierLoreBuilder.getValueStat(magSizeModifier, "extra mag size"));
        return stats;
    }

    @Override
    public boolean hasStats()
    {
        return magSizeModifier > 0 && reloadSpeedMultiplier > 0;
    }
}
