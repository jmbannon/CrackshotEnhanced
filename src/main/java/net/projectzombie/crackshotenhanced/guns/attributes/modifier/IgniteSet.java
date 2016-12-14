/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.attributes.attributeproperties.TimedEventPerTick;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.entities.CSELivingEntity;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;
import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;

import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.getValueStat;
import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class IgniteSet extends Chance<IgniteSet.IgniteAttributes> implements TimedEventPerTick
{
    public interface IgniteAttributes extends ModifierAttributes {
        double getIgniteChance();
        double getIgniteDuration();
        double getIgniteDamageMultiplierFromFireDamage();
    }

    private final double igniteDurationInSeconds;
    private final double igniteIncendiaryDmgMultiplier;
    private final double igniteDPS;
    
    public IgniteSet(final GunModifier[] mods,
                     final double totalIncendiaryDamage)
    {
        super("Ignite Damage", mods, IgniteAttributes::getIgniteChance, IgniteAttributes.class);
        this.igniteDurationInSeconds = super.getDoubleSum(IgniteAttributes::getIgniteDuration);
        this.igniteIncendiaryDmgMultiplier = super.getDoubleSum(IgniteAttributes::getIgniteDamageMultiplierFromFireDamage);
        this.igniteDPS = this.igniteIncendiaryDmgMultiplier * totalIncendiaryDamage;
    }
    
    public IgniteSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod }, 0.0);
    }

    public double getDamagePerTick() { return igniteDPS / TPS; }

    @Override public int getDurationInTicks() { return (int)(igniteDurationInSeconds * TPS); }

    @Override public void onStart(final CSELivingEntity entity) {}
    @Override public void onEnd(final CSELivingEntity entity) {}
    @Override public boolean canStop(final CSELivingEntity victim) {
        return victim.isInWater();
    }
    @Override public void applyEventPerTick(final CSELivingEntity victim) {
        victim.toBukkit().getWorld().playEffect(victim.toBukkit().getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
        victim.toBukkit().damage(getDamagePerTick());
    }


    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addPercentageStat(super.getChance(), "ignite chance");
        stats.addValueStat(igniteDurationInSeconds, "ignite duration in seconds");
        stats.addValueStat(igniteDPS, "ignite damage p/sec");
        return stats.toArrayList();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addPercentageStatIfValid(super.getChance(), "ignite chance");
        stats.addValueStatIfValid(igniteDurationInSeconds, "ignite duration in seconds");
        stats.addMultiplierStatIfValid(igniteIncendiaryDmgMultiplier, "ignite damage dealt from incendiary damage p/sec");
        return stats.toArrayList();
    }
}
