/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.modifiers;

import java.util.ArrayList;
import cs.guns.components.GunModifier;
import static cs.guns.components.ModifierLoreBuilder.getMultiplierStat;
import static cs.guns.components.ModifierLoreBuilder.getValueStat;
import static cs.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class StunSet extends Chance<StunAttributes>
{
    private final double durationInSeconds;
    private final double durationInTicks;
    
    public StunSet(final GunModifier[] mods)
    {
        super("Stun",
              getStunChance(mods));
        
        this.durationInSeconds = getStunDurationInSeconds(mods);
        this.durationInTicks = getStunDurationInTicks(mods);
    }
    
    public StunSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    public double getDurationInSeconds() { return durationInSeconds; }
    public double getDurationInTicks()   { return durationInTicks / TPS; }
    
    @Override
    public ArrayList<String> getStats()
    {
        return getStat();
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getMultiplierStat(super.getChance(), "stun chance"));
        stats.add(getValueStat(durationInSeconds, "stun duration"));
        return stats;
    }
    
    static
    public double getStunChance(final GunModifier[] mods)
    {
        double stunChance = 0;
        for (StunAttributes mod : getStunModifiers(mods))
        {
            stunChance += mod.getStunChance();
        }
        return Math.max(0, stunChance);
    }
    
    
    static
    public double getStunDurationInSeconds(final GunModifier[] mods)
    {
        double stunDuration = 0;
        for (StunAttributes mod : getStunModifiers(mods))
        {
            stunDuration += mod.getStunDuration();
        }
        return stunDuration;
    }
    
    static
    public double getStunDurationInTicks(final GunModifier[] mods)
    {
        return getStunDurationInSeconds(mods) / TPS;
    }
        
    /**
     * @param gunMods
     * @return Returns all StunModifiers on the gun.
     */
    static 
    public ArrayList<StunAttributes> getStunModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<StunAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof StunAttributes)
                mods.add((StunAttributes)mod);
        }
        return mods;
    }
    
}
