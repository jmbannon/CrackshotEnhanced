/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.AOEAttachments.AOEAttachment;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.AOEAttributes;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.CombustAttributes;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.ElectricityAttributes;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.ExplosiveAttributes;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.FlameAttributes;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.PoisonAttributes;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.RadiationAttributes;
import net.projectzombie.crackshotenhanced.guns.attributes.aoe.ShockAttributes;
import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

/**
 *
 * @author jbannon
 */
public class AOEAttachments extends ModifierConfig<AOEAttachment>
{
    static private AOEAttachments singleton = null;
    static public AOEAttachments getInstance()
    {
        if (singleton == null)
            singleton = new AOEAttachments();
        return singleton;
    }
    
    @Override
    public AOEAttachment getNullValue()
    {
        return new AOEAttachment();
    }


    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Material", 4);
        defaultValues.put("Material Data", 0);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Electric AOE Radius Value", 0.0);
        defaultValues.put("Electric AOE Radius Multiplier", 0.0);
        defaultValues.put("Electric AOE Duration Value", 0.0);
        defaultValues.put("Electric AOE Duration Multiplier", 0.0);
        defaultValues.put("Electric AOE DPS Value", 0.0);
        defaultValues.put("Electric AOE DPS Multiplier", 0.0);
        defaultValues.put("Electricity Concurrent Count", 0.0);
        defaultValues.put("Shock Chance", 0.0);
        defaultValues.put("Shock Damage Value", 0.0);
        defaultValues.put("Shock Damage Multiplier From Electricity", 0.0);
        defaultValues.put("Flame AOE Radius Value", 0.0);
        defaultValues.put("Flame AOE Radius Multiplier", 0.0);
        defaultValues.put("Flame AOE Duration Value", 0.0);
        defaultValues.put("Flame AOE Duration Multiplier", 0.0);
        defaultValues.put("Flame AOE DPS Value", 0.0);
        defaultValues.put("Flame AOE DPS Multiplier", 0.0);
        defaultValues.put("Combust Chance", 0.0);
        defaultValues.put("Combust Damage Value", 0.0);
        defaultValues.put("Combust Damage Multiplier From Electricity", 0.0);
        defaultValues.put("Poison AOE Radius Value", 0.0);
        defaultValues.put("Poison AOE Radius Multiplier", 0.0);
        defaultValues.put("Poison AOE Duration Value", 0.0);
        defaultValues.put("Poison AOE Duration Multiplier", 0.0);
        defaultValues.put("Poison AOE DPS Value", 0.0);
        defaultValues.put("Poison AOE DPS Multiplier", 0.0);
        defaultValues.put("Radiation AOE Radius Value", 0.0);
        defaultValues.put("Radiation AOE Radius Multiplier", 0.0);
        defaultValues.put("Radiation AOE Duration Value", 0.0);
        defaultValues.put("Radiation AOE Duration Multiplier", 0.0);
        defaultValues.put("Radiation AOE DPS Value", 0.0);
        defaultValues.put("Radiation AOE DPS Multiplier", 0.0);
        defaultValues.put("Explosive AOE Radius Value", 0.0);
        defaultValues.put("Explosive AOE Radius Multiplier", 0.0);
        defaultValues.put("Explosive AOE Duration Value", 0.0);
        defaultValues.put("Explosive AOE Duration Multiplier", 0.0);
        defaultValues.put("Explosive AOE DPS Value", 0.0);
        defaultValues.put("Explosive AOE DPS Multiplier", 0.0);
        return defaultValues;
    }

    static private final String YML_NAME = "AOEAttachments.yml";
    static private final String MODULE_NAME = "AOEAttachments";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Quality" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private AOEAttachments() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public AOEAttachment buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new AOEAttachment(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    Qualities.getInstance().get(values.getString("Quality")),
                    values.getDouble("Electric AOE Radius Value"),
                    values.getDouble("Electric AOE Radius Multiplier"),
                    values.getDouble("Electric AOE Duration Value"),
                    values.getDouble("Electric AOE Duration Multiplier"),
                    values.getDouble("Electric AOE DPS Value"),
                    values.getDouble("Electric AOE DPS Multiplier"),
                    values.getInt("Electricity Concurrent Count"),
                    values.getDouble("Shock Chance"),
                    values.getDouble("Shock Damage Value"),
                    values.getDouble("Shock Damage Multiplier From Electricity"),
                    values.getDouble("Flame AOE Radius Value"),
                    values.getDouble("Flame AOE Radius Multiplier"),
                    values.getDouble("Flame AOE Duration Value"),
                    values.getDouble("Flame AOE Duration Multiplier"),
                    values.getDouble("Flame AOE DPS Value"),
                    values.getDouble("Flame AOE DPS Multiplier"),
                    values.getDouble("Combust Chance"),
                    values.getDouble("Combust Damage Value"),
                    values.getDouble("Combust Damage Multiplier From Electricity"),
                    values.getDouble("Poison AOE Radius Value"),
                    values.getDouble("Poison AOE Radius Multiplier"),
                    values.getDouble("Poison AOE Duration Value"),
                    values.getDouble("Poison AOE Duration Multiplier"),
                    values.getDouble("Poison AOE DPS Value"),
                    values.getDouble("Poison AOE DPS Multiplier"),
                    values.getDouble("Radiation AOE Radius Value"),
                    values.getDouble("Radiation AOE Radius Multiplier"),
                    values.getDouble("Radiation AOE Duration Value"),
                    values.getDouble("Radiation AOE Duration Multiplier"),
                    values.getDouble("Radiation AOE DPS Value"),
                    values.getDouble("Radiation AOE DPS Multiplier"),
                    values.getDouble("Explosive AOE Radius Value"),
                    values.getDouble("Explosive AOE Radius Multiplier"),
                    values.getDouble("Explosive AOE Duration Value"),
                    values.getDouble("Explosive AOE Duration Multiplier"),
                    values.getDouble("Explosive AOE DPS Value"),
                    values.getDouble("Explosive AOE DPS Multiplier")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add AOE attachment " + values.getString("Display Name"));
            return null;
        }
    }

    static public class AOEAttachment extends QualityGunModifier implements AOEAttributes,
            CombustAttributes,
            ElectricityAttributes,
            ExplosiveAttributes,
            FlameAttributes,
            PoisonAttributes,
            RadiationAttributes,
            ShockAttributes
    {
        private final double electricAOERadiusValue;
        private final double electricAOERadiusMultiplier;
        private final double electricAOEDurationValue;
        private final double electricAOEDurationMultiplier;
        private final double electricAOEDamageValue;
        private final double electricAOEDamageMultiplier;
        private final int    concurrentElectricityCount;
        
        private final double shockChance;
        private final double shockDamageValue;
        private final double shockDamageMultiplierFromElectricity;
        
        private final double flameAOERadiusValue;
        private final double flameAOERadiusMultiplier;
        private final double flameAOEDurationValue;
        private final double flameAOEDurationMultiplier;
        private final double flameAOEDamageValue;
        private final double flameAOEDamageMultiplier;
        
        private final double combustChance;
        private final double combustDamageValue;
        private final double combustDamageMultiplierFromElectricity;
        
        private final double poisonAOERadiusValue;
        private final double poisonAOERadiusMultiplier;
        private final double poisonAOEDurationValue;
        private final double poisonAOEDurationMultiplier;
        private final double poisonAOEDamageValue;
        private final double poisonAOEDamageMultiplier;
        
        private final double radiationAOERadiusValue;
        private final double radiationAOERadiusMultiplier;
        private final double radiationAOEDurationValue;
        private final double radiationAOEDurationMultiplier;
        private final double radiationAOEDamagePerSecond;
        private final double radiationAOEDamagePerSecondMultiplier;
        
        private final double explosiveAOERadiusValue;
        private final double explosiveAOERadiusMultiplier;
        private final double explosiveAOEDurationValue;
        private final double explosiveAOEDurationMultiplier;
        private final double explosiveAOEDamageValue;
        private final double explosiveAOEDamageMultiplier;

        
        private AOEAttachment(final int uniqueID,
                              final String displayname,
                              final int price,
                              final String color,
                              final Qualities.Quality quality,
                            
                            final double electricAOERadiusValue,
                              final double electricAOERadiusMultiplier,
                              final double electricAOEDurationValue,
                              final double electricAOEDurationMultiplier,
                              final double electricAOEDamageValue,
                              final double electricAOEDamageMultiplier,
                              final int concurrentElectricityCount,

                              final double shockChance,
                              final double shockDamageValue,
                              final double shockDamageMultiplierFromElectricity,

                              final double flameAOERadiusValue,
                              final double flameAOERadiusMultiplier,
                              final double flameAOEDurationValue,
                              final double flameAOEDurationMultiplier,
                              final double flameAOEDamageValue,
                              final double flameAOEDamageMultiplier,

                              final double combustChance,
                              final double combustDamageValue,
                              final double combustDamageMultiplierFromElectricity,

                              final double poisonAOERadiusValue,
                              final double poisonAOERadiusMultiplier,
                              final double poisonAOEDurationValue,
                              final double poisonAOEDurationMultiplier,
                              final double poisonAOEDamageValue,
                              final double poisonAOEDamageMultiplier,

                              final double radiationAOERadiusValue,
                              final double radiationAOERadiusMultiplier,
                              final double radiationAOEDurationValue,
                              final double radiationAOEDurationMultiplier,
                              final double radiationAOEDamageValue,
                              final double radiationAOEDamageMultiplier,

                              final double explosiveAOERadiusValue,
                              final double explosiveAOERadiusMultiplier,
                              final double explosiveAOEDurationValue,
                              final double explosiveAOEDurationMultiplier,
                              final double explosiveAOEDamageValue,
                              final double explosiveAOEDamageMultiplier)
        {        
            super(uniqueID, displayname, price, color, quality, CraftableType.SLOT_ONE_ATTACHMENT);
            this.electricAOERadiusValue                 = electricAOERadiusValue;
            this.electricAOERadiusMultiplier            = electricAOERadiusMultiplier;
            this.electricAOEDurationValue               = electricAOEDurationValue;
            this.electricAOEDurationMultiplier          = electricAOEDurationMultiplier;
            this.electricAOEDamageValue                 = electricAOEDamageValue;
            this.electricAOEDamageMultiplier            = electricAOEDamageMultiplier;
            this.concurrentElectricityCount             = concurrentElectricityCount;
            this.shockChance                            = shockChance;
            this.shockDamageValue                       = shockDamageValue;
            this.shockDamageMultiplierFromElectricity   = shockDamageMultiplierFromElectricity;
            this.flameAOERadiusValue                    = flameAOERadiusValue;
            this.flameAOERadiusMultiplier               = flameAOERadiusMultiplier;
            this.flameAOEDurationValue                  = flameAOEDurationValue;
            this.flameAOEDurationMultiplier             = flameAOEDurationMultiplier;
            this.flameAOEDamageValue                    = flameAOEDamageValue;
            this.flameAOEDamageMultiplier               = flameAOEDamageMultiplier;
            this.combustChance                          = combustChance;
            this.combustDamageValue                     = combustDamageValue;
            this.combustDamageMultiplierFromElectricity = combustDamageMultiplierFromElectricity;
            this.poisonAOERadiusValue                   = poisonAOERadiusValue;
            this.poisonAOERadiusMultiplier              = poisonAOERadiusMultiplier;
            this.poisonAOEDurationValue                 = poisonAOEDurationValue;
            this.poisonAOEDurationMultiplier            = poisonAOEDurationMultiplier;
            this.poisonAOEDamageValue                   = poisonAOEDamageValue;
            this.poisonAOEDamageMultiplier              = poisonAOEDamageMultiplier;
            this.radiationAOERadiusValue                = radiationAOERadiusValue;
            this.radiationAOERadiusMultiplier           = radiationAOERadiusMultiplier;
            this.radiationAOEDurationValue              = radiationAOEDurationValue;
            this.radiationAOEDurationMultiplier         = radiationAOEDurationMultiplier;
            this.radiationAOEDamagePerSecond            = radiationAOEDamageValue;
            this.radiationAOEDamagePerSecondMultiplier  = radiationAOEDamageMultiplier;
            this.explosiveAOERadiusValue                = explosiveAOERadiusValue;
            this.explosiveAOERadiusMultiplier           = explosiveAOERadiusMultiplier;
            this.explosiveAOEDurationValue              = explosiveAOEDurationValue;
            this.explosiveAOEDurationMultiplier         = explosiveAOEDurationMultiplier;
            this.explosiveAOEDamageValue                = explosiveAOEDamageValue;
            this.explosiveAOEDamageMultiplier           = explosiveAOEDamageMultiplier;
        }

        /**
         * Constructs the null Attatchment.
         */
        private AOEAttachment()
        {
            this(0, null, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0);
        }


        @Override public GunModifier getNullModifier() { return new AOEAttachment(); }

        @Override public double getElectricityAOERadiusValue()        { return electricAOERadiusValue; }
        @Override public double getElectricityAOERadiusMultiplier()   { return electricAOERadiusMultiplier; }
        @Override public double getElectricityAOEDurationValue()      { return electricAOEDurationValue; }
        @Override public double getElectricityAOEDurationMultiplier() { return electricAOEDurationMultiplier; }
        @Override public double getElectricityDamageValue()           { return electricAOEDamageValue; }
        @Override public double getElectricityDamageMultiplier()      { return electricAOEDamageMultiplier; }
        @Override public int    getConcurrentElectricityCount()       { return concurrentElectricityCount; }
        
        @Override public double getShockChance()                          { return shockChance; }
        @Override public double getShockDamageValue()                     { return shockDamageValue; }
        @Override public double getShockDamageMultiplierFromElectricity() { return shockDamageMultiplierFromElectricity; }
        
        @Override public double getFlameAOERadiusValue()               { return flameAOERadiusValue; }
        @Override public double getFlameAOERadiusMultiplier()          { return flameAOERadiusMultiplier; }
        @Override public double getFlameAOEDurationValue()             { return flameAOEDurationValue; }
        @Override public double getFlameAOEDurationMultiplier()        { return flameAOEDurationMultiplier; }
        @Override public double getFlameAOEDamagePerSecond()           { return flameAOEDamageValue; }
        @Override public double getFlameAOEDamagePerSecondMultiplier() { return flameAOEDamageMultiplier; }
        
        @Override public double getCombustChance()                    { return combustChance; }
        @Override public double getCombustDamageValue()               { return combustDamageValue; }
        @Override public double getCombustDamageMultiplierFromFlame() { return combustDamageMultiplierFromElectricity; }
        
        @Override public double getPoisonAOERadiusValue()               { return poisonAOERadiusValue; }
        @Override public double getPoisonAOERadiusMultiplier()          { return poisonAOERadiusMultiplier; }
        @Override public double getPoisonAOEDurationValue()             { return poisonAOEDurationValue; }
        @Override public double getPoisonAOEDurationMultiplier()        { return poisonAOEDurationMultiplier; }
        @Override public double getPoisonAOEDamagePerSecond()           { return poisonAOEDamageValue; }
        @Override public double getPoisonAOEDamagePerSecondMultiplier() { return poisonAOEDamageMultiplier; }
        
        @Override public double getRadiationAOERadiusValue()               { return radiationAOERadiusValue; }
        @Override public double getRadiationAOERadiusMultiplier()          { return radiationAOERadiusMultiplier; }
        @Override public double getRadiationAOEDurationValue()             { return radiationAOEDurationValue; }
        @Override public double getRadiationAOEDurationMultiplier()        { return radiationAOEDurationMultiplier; }
        @Override public double getRadiationAOEDamagePerSecond()           { return radiationAOEDamagePerSecond; }
        @Override public double getRadiationAOEDamagePerSecondMultiplier() { return radiationAOEDamagePerSecondMultiplier; }

        @Override public double getExplosiveAOERadiusValue()        { return explosiveAOERadiusValue; }
        @Override public double getExplosiveAOERadiusMultiplier()   { return explosiveAOERadiusMultiplier; }
        @Override public double getExplosiveAOEDamageValue()        { return explosiveAOEDurationValue; }
        @Override public double getExplosiveAOEDamageMultiplier()   { return explosiveAOEDurationMultiplier; }
        @Override public double getExplosiveAOEDurationValue()      { return explosiveAOEDamageValue; }
        @Override public double getExplosiveAOEDurationMultiplier() { return explosiveAOEDamageMultiplier; } 
    }
}
