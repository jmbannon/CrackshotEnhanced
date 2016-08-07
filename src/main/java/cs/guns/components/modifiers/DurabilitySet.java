/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components.modifiers;

import java.util.ArrayList;
import cs.guns.components.GunModifier;
import cs.guns.components.GunModifierSet;
import static cs.guns.components.ModifierLoreBuilder.STAT_SEPERATOR;
import static cs.guns.components.ModifierLoreBuilder.getMultiplierStat;
import static cs.guns.components.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class DurabilitySet extends GunModifierSet<DurabilityAttributes>
{
    private static final int MAX_DURABILITY = 5;
    
    private final int extraDurability;
    private final double durabilityMultiplier;
    private final int skeletonDurability;
    private final int totalDurability;
    
    public DurabilitySet(final GunModifier[] gunMods,
                         final int skeletonMaxDurability)
    {
        super("Durability");
        this.durabilityMultiplier = getDurabilityMultiplier(gunMods);
        this.extraDurability = getDurabilityModifier(gunMods);
        this.skeletonDurability = skeletonMaxDurability;
        this.totalDurability = (int)Math.max(MAX_DURABILITY, Math.round((skeletonMaxDurability + extraDurability) * durabilityMultiplier));
    }
    
    public DurabilitySet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0);
    }
    
    public double getMaxDurability() { return totalDurability;  }
    
    /**
     * TODO, may need to add current durability.
     * @return 
     */
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(getValueStat(totalDurability, "total max durability"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(skeletonDurability, "max stock durability"));
        stats.addAll(getStat());
        
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(extraDurability, "durability"));
        stats.add(getMultiplierStat(durabilityMultiplier, "durability"));
        
        return stats;
    }
    
    @Override
    public boolean hasStats()
    {
        return true; // Every gun has durability
    }
    
    static
    private double getDurabilityMultiplier(final GunModifier[] gunMods)
    {
        double boltDurationMultiplier = 1.0;
        for (DurabilityAttributes mod : getDurabilityModifiers(gunMods))
        {
            boltDurationMultiplier += mod.getDurabilityMultiplier();
        }
        return Math.max(0.0, boltDurationMultiplier);
    }
    
    static
    private int getDurabilityModifier(final GunModifier[] gunMods)
    {
        int durabilityModifier = 0;
        for (DurabilityAttributes mod : getDurabilityModifiers(gunMods))
        {
            durabilityModifier += mod.getDurabilityModifier();
        }
        return Math.max(0, durabilityModifier);
    }
    
    static
    private ArrayList<DurabilityAttributes> getDurabilityModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<DurabilityAttributes> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof DurabilityAttributes)
                mods.add((DurabilityAttributes)mod);
        }
        return mods;
    }
}
