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

import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.TPS;

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
    private final int totalMagSize;
    private final double reloadSpeedMultiplier;
    private final double totalReloadDurationInTicks;
    
    public MagazineSet(final GunModifier[] gunMods,
                       final int skeletonMagSize,
                       final double skeletonReloadDurationInTicks)
    {
        super("Magazine", gunMods, MagazineAttributes.class);
        this.magSizeModifier = super.getIntSum(MagazineAttributes::getMagazineSizeModifier);
        this.magSizeMultiplier = super.getMultiplierSum(MagazineAttributes::getMagazineSizeMultiplier);
        this.reloadSpeedMultiplier = super.getMultiplierSum(MagazineAttributes::getReloadSpeedMultiplier);
        this.totalMagSize = Math.max(MIN_MAG_SIZE, (int)((skeletonMagSize + magSizeModifier) * magSizeMultiplier));
        this.totalReloadDurationInTicks = Math.max(MIN_RELOAD_SPEED, skeletonReloadDurationInTicks * reloadSpeedMultiplier);
    }
    
    public MagazineSet(GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0, 0);
    }
    
    public int getTotalMagazineSize()   { return totalMagSize; }
    public double getTotalReloadDurationInTicks() { return totalReloadDurationInTicks;  }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(totalMagSize, "magazine size");
        stats.addValueStat(totalReloadDurationInTicks / TPS, "second reload duration");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(magSizeModifier, "magazine size");
        stats.addMultiplierStatIfValid(magSizeMultiplier, "magazine size");
        stats.addMultiplierStatIfValid(reloadSpeedMultiplier, "reload duration");
        return stats.toArrayList();
    }

    @Override
    public boolean hasStats()
    {
        return super.hasStats() && (magSizeModifier > 0 || magSizeMultiplier != 1.0 || reloadSpeedMultiplier != 1.0);
    }
}
