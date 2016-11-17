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
    public ArrayList<String> getStats()
    {
        return getStat();
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        if (isAuto)
        {
            stats.add(ModifierLoreBuilder.getBooleanStat(isAuto, "is automatic"));
        }
        else if (isBurst)
        {
            stats.add(ModifierLoreBuilder.getBooleanStat(isBurst, "is burst-fire"));
            stats.add(ModifierLoreBuilder.getValueStat(shotsPerBurst, "shots per burst"));
        }
        else
        {
            stats.add(ModifierLoreBuilder.getBooleanStat(true, "is semi-automatic"));
        }
        
        return stats;
    }

    @Override
    public boolean hasStats()
    {
        return true;
    }
}
