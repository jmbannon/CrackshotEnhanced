/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes.skeleton;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.attributes.AttributeSet;

import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.STAT_SEPERATOR;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.getMultiplierStat;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class DurabilitySet extends AttributeSet<DurabilitySet.DurabilityAttributes>
{
    public interface DurabilityAttributes extends SkeletonAttributes
    {
        int getDurabilityModifier();
        double getDurabilityMultiplier();
    }

    private static final int MAX_DURABILITY = 5;
    
    private final int extraDurability;
    private final double durabilityMultiplier;
    private final int skeletonDurability;
    private final int totalDurability;
    
    public DurabilitySet(final GunModifier[] gunMods,
                         final int skeletonMaxDurability)
    {
        super("Durability", gunMods, DurabilityAttributes.class);
        this.durabilityMultiplier = super.getMultiplierSum(DurabilityAttributes::getDurabilityMultiplier);
        this.extraDurability = super.getIntSum(0, 0, DurabilityAttributes::getDurabilityModifier);
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
}
