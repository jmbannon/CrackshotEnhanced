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
public class FireModeSet extends AttributeSet<FireModeSet.FireModeAttributes>
{
    public interface FireModeAttributes extends SkeletonAttributes
    {
        boolean isBurstFire();
        boolean isAutomatic();
        int     getShotsPerBurst();
    }

    private static final int MIN_SHOTS_PER_BURST = 1;

    private final boolean isAuto;
    private final boolean isBurst;
    private final int shotsPerBurst;
    
    public FireModeSet(final GunModifier[] gunMods)
    {
        super("Fire Mode", gunMods, FireModeAttributes.class);
        this.isAuto = super.getBoolean(FireModeAttributes::isAutomatic);
        this.isBurst = !isAuto && super.getBoolean(FireModeAttributes::isBurstFire);
        this.shotsPerBurst = isBurst ? super.getIntSum(0, MIN_SHOTS_PER_BURST, FireModeAttributes::getShotsPerBurst) : 0;
    }
    
    public FireModeSet(GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        return getIndividualStats();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        if (isAuto)
        {
            stats.addBooleanStatIfTrue(isAuto, "is automatic");
        }
        else if (isBurst)
        {
            stats.addBooleanStatIfTrue(isBurst, "is burst-fire");
            stats.addValueStat(shotsPerBurst, "shots per burst");
        }
        else
        {
            stats.addBooleanStatIfTrue(true, "is semi-automatic");
        }
        return stats.toArrayList();
    }

    @Override
    public boolean hasStats()
    {
        return true;
    }
}
