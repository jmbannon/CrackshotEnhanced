/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import csv.CSVReader;
import csv.CSVInput;
import cs.guns.components.AOEAttachments.AOEAttachment;
import cs.guns.components.Attachments.Attachment;
import cs.aoe.modifiers.AOEModifier;
import cs.aoe.modifiers.CombustModifier;
import cs.aoe.modifiers.ElectricityModifier;
import cs.aoe.modifiers.ExplosiveAOEModifier;
import cs.aoe.modifiers.FlameAOE;
import cs.aoe.modifiers.PoisonAOEModifier;
import cs.aoe.modifiers.RadiationAOEModifier;
import cs.aoe.modifiers.ShockModifier;
/**
 *
 * @author jbannon
 */
public class AOEAttachments extends CSVInput<AOEAttachment>
{
    static private AOEAttachments singleton = null;
            
    static public AOEAttachments getInstance()
    {
        if (singleton == null)
            singleton = new AOEAttachments(ATTATCHMENT_ONE_CSV_NAME);
        return singleton;
    }
    
    static private final String ATTATCHMENT_ONE_CSV_NAME = "AOEAttatchments.csv";
    
    static private final String[] ATTATCHMENT_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Electric AOE Radius Value (DBL)",
        "Electric AOE Radius Multiplier (DBL)",
        "Electric AOE Duration Value (DBL)",
        "Electric AOE Duration Multiplier (DBL)",
        "Electric AOE DPS Value (DBL)",
        "Electric AOE DPS Multiplier (DBL)",
        "Concurrent Electricity Count (DBL)",
        
        "Shock Chance (DBL)",
        "Shock Damage Value (DBL)",
        "Shock Damage Multiplier From Electricity (DBL)",
        
        "Flame AOE Radius Value (DBL)",
        "Flame AOE Radius Multiplier (DBL)",
        "Flame AOE Duration Value (DBL)",
        "Flame AOE Duration Multiplier (DBL)",
        "Flame AOE DPS Value (DBL)",
        "Flame AOE DPS Multiplier (DBL)",
        
        "Combust Chance (DBL)",
        "Combust Damage Value (DBL)",
        "Combust Damage Multiplier From Electricity (DBL)",
        
        "Poison AOE Radius Value (DBL)",
        "Poison AOE Radius Multiplier (DBL)",
        "Poison AOE Duration Value (DBL)",
        "Poison AOE Duration Multiplier (DBL)",
        "Poison AOE DPS Value (DBL)",
        "Poison AOE DPS Multiplier (DBL)",
        
        "Radiation AOE Radius Value (DBL)",
        "Radiation AOE Radius Multiplier (DBL)",
        "Radiation AOE Duration Value (DBL)",
        "Radiation AOE Duration Multiplier (DBL)",
        "Radiation AOE DPS Value (DBL)",
        "Radiation AOE DPS Multiplier (DBL)",
        
        "Explosive AOE Radius Value (DBL)",
        "Explosive AOE Radius Multiplier (DBL)",
        "Explosive AOE Duration Value (DBL)",
        "Explosive AOE Duration Multiplier (DBL)",
        "Explosive AOE DPS Value (DBL)", 
        "Explosive AOE DPS Multiplier (DBL)"
    };
    
    private AOEAttachments(final String csvName)
    {
        super(csvName, buildAttatchments(csvName), ATTATCHMENT_VALUES);
    }
    
    @Override
    public AOEAttachment getNullValue()
    {
        return new AOEAttachment();
    }
    
    /**
     * Builds all attatchments, if any. Allowed to not have any in CSV.
     * @return Array of all attatchments (including null attatchment).
     */
    static private AOEAttachment[] buildAttatchments(final String csvName)
    {
        final CSVReader csv = new CSVReader(csvName, ATTATCHMENT_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new AOEAttachment[] { new AOEAttachment() };
        }
        int j = 0;
        final AOEAttachment[] toReturn    = new AOEAttachment[rowCount + 1];
        final String[] displayNames                   = csv.getColumnString(j++);
        final String[] materialNames                  = csv.getColumnString(j++);
        final int[]    materialBytes                  = csv.getColumnInt(j++);
        final int[]    price                          = csv.getColumnInt(j++);
        final String[] colors                         = csv.getColumnString(j++);
        final double[] electricAOERadiusValue                 = csv.getColumnDouble(j++);
        final double[] electricAOERadiusMultiplier            = csv.getColumnDouble(j++);
        final double[] electricAOEDurationValue               = csv.getColumnDouble(j++);
        final double[] electricAOEDurationMultiplier          = csv.getColumnDouble(j++);
        final double[] electricAOEDamageValue                 = csv.getColumnDouble(j++);
        final double[] electricAOEDamageMultiplier            = csv.getColumnDouble(j++);
        final int[]    concurrentElectricityCount             = csv.getColumnInt(j++);
        final double[] shockChance                            = csv.getColumnDouble(j++);
        final double[] shockDamageValue                       = csv.getColumnDouble(j++);
        final double[] shockDamageMultiplierFromElectricity   = csv.getColumnDouble(j++);
        final double[] flameAOERadiusValue                    = csv.getColumnDouble(j++);
        final double[] flameAOERadiusMultiplier               = csv.getColumnDouble(j++);
        final double[] flameAOEDurationValue                  = csv.getColumnDouble(j++);
        final double[] flameAOEDurationMultiplier             = csv.getColumnDouble(j++);
        final double[] flameAOEDamageValue                    = csv.getColumnDouble(j++);
        final double[] flameAOEDamageMultiplier               = csv.getColumnDouble(j++);
        final double[] combustChance                          = csv.getColumnDouble(j++);
        final double[] combustDamageValue                     = csv.getColumnDouble(j++);
        final double[] combustDamageMultiplierFromElectricity = csv.getColumnDouble(j++);
        final double[] poisonAOERadiusValue                   = csv.getColumnDouble(j++);
        final double[] poisonAOERadiusMultiplier              = csv.getColumnDouble(j++);
        final double[] poisonAOEDurationValue                 = csv.getColumnDouble(j++);
        final double[] poisonAOEDurationMultiplier            = csv.getColumnDouble(j++);
        final double[] poisonAOEDamageValue                   = csv.getColumnDouble(j++);
        final double[] poisonAOEDamageMultiplier              = csv.getColumnDouble(j++);
        final double[] radiationAOERadiusValue                = csv.getColumnDouble(j++);
        final double[] radiationAOERadiusMultiplier           = csv.getColumnDouble(j++);
        final double[] radiationAOEDurationValue              = csv.getColumnDouble(j++);
        final double[] radiationAOEDurationMultiplier         = csv.getColumnDouble(j++);
        final double[] radiationAOEDamagePerSecond            = csv.getColumnDouble(j++);
        final double[] radiationAOEDamagePerSecondMultiplier  = csv.getColumnDouble(j++);
        final double[] explosiveAOERadiusValue                = csv.getColumnDouble(j++);
        final double[] explosiveAOERadiusMultiplier           = csv.getColumnDouble(j++);
        final double[] explosiveAOEDurationValue              = csv.getColumnDouble(j++);
        final double[] explosiveAOEDurationMultiplier         = csv.getColumnDouble(j++);
        final double[] explosiveAOEDamageValue                = csv.getColumnDouble(j++);
        final double[] explosiveAOEDamageMultiplier           = csv.getColumnDouble(j++);
 
        toReturn[0] = new AOEAttachment();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new AOEAttachment(
                    i+1,
                    displayNames[i],
                    materialNames[i],
                    materialBytes[i],
                    price[i],
                    colors[i],
                    electricAOERadiusValue[i],                
                    electricAOERadiusMultiplier[i],           
                    electricAOEDurationValue[i],              
                    electricAOEDurationMultiplier[i],         
                    electricAOEDamageValue[i],                
                    electricAOEDamageMultiplier[i],           
                    concurrentElectricityCount[i],            
                    shockChance[i],                           
                    shockDamageValue[i],                      
                    shockDamageMultiplierFromElectricity[i],  
                    flameAOERadiusValue[i],                   
                    flameAOERadiusMultiplier[i],              
                    flameAOEDurationValue[i],                 
                    flameAOEDurationMultiplier[i],            
                    flameAOEDamageValue[i],                   
                    flameAOEDamageMultiplier[i],              
                    combustChance[i],                         
                    combustDamageValue[i],                    
                    combustDamageMultiplierFromElectricity[i],
                    poisonAOERadiusValue[i],                  
                    poisonAOERadiusMultiplier[i],             
                    poisonAOEDurationValue[i],                
                    poisonAOEDurationMultiplier[i],           
                    poisonAOEDamageValue[i],                  
                    poisonAOEDamageMultiplier[i],             
                    radiationAOERadiusValue[i],               
                    radiationAOERadiusMultiplier[i],          
                    radiationAOEDurationValue[i],             
                    radiationAOEDurationMultiplier[i],        
                    radiationAOEDamagePerSecond[i],           
                    radiationAOEDamagePerSecondMultiplier[i], 
                    explosiveAOERadiusValue[i],               
                    explosiveAOERadiusMultiplier[i],          
                    explosiveAOEDurationValue[i],             
                    explosiveAOEDurationMultiplier[i],        
                    explosiveAOEDamageValue[i],               
                    explosiveAOEDamageMultiplier[i]);
        }
        return toReturn;
    }

    static public class AOEAttachment extends Attachment implements AOEModifier,
            CombustModifier,
            ElectricityModifier,
            ExplosiveAOEModifier,
            FlameAOE,
            PoisonAOEModifier,
            RadiationAOEModifier,
            ShockModifier
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
                            final String materialName,
                            final int materialByte,
                            final int price,
                            final String color,
                            
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
            super(uniqueID, displayname, materialName, materialByte, price, color);
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
            this(0, null, null, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0);
        }


        @Override public GunModifier getNullModifier() { return new AOEAttachment(); }
        @Override public boolean isAOE() { return true; }
        
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
