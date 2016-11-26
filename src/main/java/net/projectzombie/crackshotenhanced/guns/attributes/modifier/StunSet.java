/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.attributes.attributeproperties.TimedEvent;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.entities.CSELivingEntity;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;
import net.projectzombie.crackshotenhanced.guns.utilities.Constants;

/**
 *
 * @author jb
 */
public class StunSet extends Chance<StunSet.StunAttributes> implements TimedEvent
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

    @Override public int getDurationInTicks() { return (int)(durationInSeconds / Constants.TPS); }

    @Override public void onStart(final CSELivingEntity entity) { entity.setSpeed(0.0); }
    @Override public void onEnd(final CSELivingEntity entity) { entity.setSpeed(entity.getDefaultSpeed()); }

    @Override public boolean canStop(final CSELivingEntity victim) { return false; }
    
    @Override
    public ArrayList<String> getGunStats() {
        final StatBuilder stats = new StatBuilder();
        stats.addPercentageStat(super.getChance(), "stun chance");
        stats.addValueStat(durationInSeconds, "stun duration");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addPercentageStatIfValid(super.getChance(), "stun chance");
        stats.addValueStatIfValid(durationInSeconds, "stun duration");
        return stats.toArrayList();
    }
    
}
