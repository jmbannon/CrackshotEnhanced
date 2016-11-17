/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.cs.guns.utilities.Constants;

/**
 *
 * @author jb
 */
public class StunSet extends Chance<StunSet.StunAttributes>
{
    public interface StunAttributes extends ModifierAttributes {
        double getStunChance();
        double getStunDuration();
    }

    private final double durationInSeconds;

    public StunSet(final GunModifier[] mods)
    {
        super("Stun", mods, StunAttributes::getStunChance, StunAttributes.class);
        this.durationInSeconds = super.getDoubleSum(StunAttributes::getStunDuration);
    }
    
    public StunSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    public double getDurationInSeconds() { return durationInSeconds; }
    public double getDurationInTicks()   { return durationInSeconds / Constants.TPS; }
    
    @Override
    public ArrayList<String> getStats()
    {
        return getStat();
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(ModifierLoreBuilder.getMultiplierStat(super.getChance(), "stun chance"));
        stats.add(ModifierLoreBuilder.getValueStat(durationInSeconds, "stun duration"));
        return stats;
    }
    
}
