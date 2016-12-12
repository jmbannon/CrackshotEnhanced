/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.skeleton;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;
import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

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
    private final int totalDurability;
    
    public DurabilitySet(final GunModifier[] gunMods,
                         final int skeletonMaxDurability)
    {
        super("Durability", gunMods, DurabilityAttributes.class);
        this.durabilityMultiplier = super.getMultiplierSum(DurabilityAttributes::getDurabilityMultiplier);
        this.extraDurability = super.getIntSum(0, 0, DurabilityAttributes::getDurabilityModifier);
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
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStat(totalDurability, "max durability");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addValueStatIfValid(extraDurability, "durability");
        stats.addMultiplierStatIfValid(durabilityMultiplier, "durability");
        return stats.toArrayList();
    }
}
