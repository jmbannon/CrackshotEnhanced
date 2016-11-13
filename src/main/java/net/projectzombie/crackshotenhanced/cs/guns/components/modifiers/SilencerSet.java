/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.getBooleanStat;

/**
 *
 * @author jb
 */
public class SilencerSet extends GunModifierSet<SilencerAttributes>
{    
    private final boolean isSilenced;
    
    public SilencerSet(final GunModifier[] gunMods)
    {
        super("Silencer");
        this.isSilenced = isSilenced(gunMods);
    }
    
    public SilencerSet(GunModifier mod)
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
        if (isSilenced)
        {
            stats.add(getBooleanStat(isSilenced, "has silencer"));
        }
        else
        {
            stats.add(getBooleanStat(true, "no silencer"));
        }
        
        return stats;
    }

    @Override
    public boolean hasStats()
    {
        return true;
    }
    
    static
    private boolean isSilenced(final GunModifier[] gunMods)
    {
        boolean isSilenced = false;
        for (SilencerAttributes att : getFireModeModifiers(gunMods))
        {
            if (att.isSilencer())
                isSilenced = true;
        }
        return isSilenced;
    }
    
    static
    private ArrayList<SilencerAttributes> getFireModeModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<SilencerAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof SilencerAttributes)
                mods.add((SilencerAttributes)mod);
        }
        return mods;
    }
}
