/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components.modifiers;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;

/**
 *
 * @author jb
 */
public class FireModeSet extends GunModifierSet<FireModeAttributes>
{    
    private final boolean isAuto;
    private final boolean isBurst;
    private final int shotsPerBurst;
    
    public FireModeSet(final GunModifier[] gunMods)
    {
        super("Fire Mode", FireModeAttributes.class);
        this.isAuto = isAutomatic(gunMods);
        this.isBurst = isAuto ? false : isBurst(gunMods);
        this.shotsPerBurst = isBurst ? getShotsPerBurst(gunMods) : 0;
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
    
    static
    private boolean isBurst(final GunModifier[] gunMods)
    {
        boolean isBurst = false;
        for (FireModeAttributes att : getFireModeModifiers(gunMods))
        {
            if (att.isBurstFire())
                isBurst = true;
        }
        return isBurst;
    }
    
    static
    private boolean isAutomatic(final GunModifier[] gunMods)
    {
        boolean isAuto = false;
        for (FireModeAttributes att : getFireModeModifiers(gunMods))
        {
            if (att.isAutomatic())
                isAuto = true;
        }
        return isAuto;
    }
    
    static
    private int getShotsPerBurst(final GunModifier[] gunMods)
    {
        int shots = 0;
        for (FireModeAttributes att : getFireModeModifiers(gunMods))
        {
            shots += att.getShotsPerBurst();
        }
        return shots;
    }
    
    static
    private ArrayList<FireModeAttributes> getFireModeModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<FireModeAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof MagazineAttributes)
                mods.add((FireModeAttributes)mod);
        }
        return mods;
    }
}
