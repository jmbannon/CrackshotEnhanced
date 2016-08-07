/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import csv.CSVReader;
import cs.guns.components.Barrels.Barrel;
import csv.CSVInput;
import cs.guns.modifiers.BaseDamageAttributes;
import cs.guns.modifiers.BulletSpreadAttributes;
import cs.guns.modifiers.HeadshotAttributes;
import cs.guns.modifiers.FireDamageAttributes;
import cs.guns.modifiers.ShrapnelDamageAttributes;
import cs.guns.components.modifiers.ProjectileAttributes;
import cs.guns.components.modifiers.SilencerAttributes;

/**
 *
 * @author jesse
 */
public class Barrels extends CSVInput<Barrel>
{
    static private Barrels singleton = null;
    static public Barrels getInstance()
    {
        if (singleton == null)
            singleton = new Barrels();
        return singleton;
    }
    
    static private final String BARREL_CSV_NAME = "Barrels.csv";
    static private final String[] BARREL_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Is Silencer (T/F)",
        "BulletSpreadModifier (DBL)",
        "Base Damage Value (DBL)",
        "Base Damage Multiplier (DBL)",
        "Shrapnel Damage Value (DBL)",
        "Shrapnel Damage Multiplier (DBL)",
        "Fire Damage Value (DBL)",
        "Fire Damage Multiplier (DBL)",
        "Headshot Modifier (DBL)",
        "Headshot Multiplier (DBL)",
        "AdditionalProjectiles (INT)",
        "Projectile Speed Multiplier (DBL)",
        "Projectile Range Modifier (INT)",
        "Projectile Range Multiplier (DBL)"
    };

    private Barrels()
    {
        super(BARREL_CSV_NAME, buildBarrels(), BARREL_VALUES);
    }
    
    @Override
    public Barrel getNullValue()
    {
        return new Barrel();
    }
    
    /**
    * Builds all barrels, if any. Allowed to not have any in CSV.
    * @return Array of all barrels (including null barrel).
    */
    static private Barrel[] buildBarrels()
    {
        final CSVReader csv = new CSVReader(BARREL_CSV_NAME, BARREL_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new Barrel[] { new Barrel() };
        }
 
        int j = 0;
        final Barrel[] toReturn                      = new Barrel[rowCount + 1];
        final String[] displayName                   = csv.getColumnString(j++);
        final String[] material                      = csv.getColumnString(j++);
        final int[]    materialByte                  = csv.getColumnInt(j++);
        final int[]    price                         = csv.getColumnInt(j++);
        final String[] color                         = csv.getColumnString(j++);
        final boolean[] silencer                     = csv.getColumnBoolean(j++);
        final double[] bulletSpreadModifier          = csv.getColumnDouble(j++);
        final double[] baseDamageValue               = csv.getColumnDouble(j++);
        final double[] baseDamageMultiplier          = csv.getColumnDouble(j++);
        final double[] shrapnelDamageValue           = csv.getColumnDouble(j++);
        final double[] shrapnelDamageMultiplier      = csv.getColumnDouble(j++);
        final double[] fireDamageValue               = csv.getColumnDouble(j++);
        final double[] fireDamageMultiplier          = csv.getColumnDouble(j++);
        final double[] headshotValue                 = csv.getColumnDouble(j++);
        final double[] headshotMultiplier            = csv.getColumnDouble(j++);
        final int[]    additionalProjectiles         = csv.getColumnInt(j++);
        final double[] projectileSpeedMultiplier     = csv.getColumnDouble(j++);
        final int[]    projectileRangeValue          = csv.getColumnInt(j++);
        final double[] projectileRangeMultiplier     = csv.getColumnDouble(j++);
        
 
        toReturn[0] = new Barrel();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new Barrel(
                i+1,
                displayName[i],              
                material[i],                     
                materialByte[i],                 
                price[i],                        
                color[i],
                silencer[i],
                bulletSpreadModifier[i],         
                baseDamageValue[i],                  
                baseDamageMultiplier[i],
                shrapnelDamageValue[i],
                shrapnelDamageMultiplier[i],
                fireDamageValue[i],
                fireDamageMultiplier[i],
                headshotValue[i],                
                headshotMultiplier[i],           
                additionalProjectiles[i],        
                projectileSpeedMultiplier[i],    
                projectileRangeValue[i],         
                projectileRangeMultiplier[i]
            );
        }
        return toReturn;
    }
    

    static public class Barrel extends GunModifier implements BulletSpreadAttributes,
                                                              BaseDamageAttributes,
                                                              HeadshotAttributes,
                                                              ProjectileAttributes,
                                                              SilencerAttributes,
                                                              FireDamageAttributes,
                                                              ShrapnelDamageAttributes
    {
        private final double bulletSpreadModifier;
        private final double baseDamageValue;
        private final double baseDamageMultiplier;
        private final double shrapnelDamageValue;
        private final double shrapnelDamageMultiplier;
        private final double fireDamageValue;
        private final double fireDamageMultiplier;
        private final double headshotValue;
        private final double headshotMultiplier;
        private final int    additionalProjectiles;
        private final double projectileSpeedMultiplier;
        private final int    projectileRangeValue;
        private final double projectileRangeMultiplier;
        private final boolean isSilencer;
        

        private Barrel(final int     uniqueID,
                        final String  displayName,
                        final String material,
                        final int    materialByte,
                        final int    price,
                        final String color,
                        final boolean isSilencer,
                        final double bulletSpreadModifier,
                        final double baseDamageValue,
                        final double baseDamageMultiplier,
                        final double shrapnelDamageValue,
                        final double shrapnelDamageMultiplier,
                        final double fireDamageValue,
                        final double fireDamageMultiplier,
                        final double headshotValue,
                        final double headshotMultiplier,
                        final int    additionalProjectiles,
                        final double projectileSpeedMultiplier,
                        final int    projectileRangeValue,
                        final double projectileRangeMultiplier)
        {
            super(uniqueID, displayName, material, materialByte, price, color);
            this.isSilencer = isSilencer;
            this.bulletSpreadModifier = bulletSpreadModifier;
            this.baseDamageValue = baseDamageValue;
            this.baseDamageMultiplier = baseDamageMultiplier;
            this.shrapnelDamageValue = shrapnelDamageValue;
            this.shrapnelDamageMultiplier = shrapnelDamageMultiplier;
            this.fireDamageValue = fireDamageValue;
            this.fireDamageMultiplier = fireDamageMultiplier;
            this.headshotValue = headshotValue;
            this.headshotMultiplier = headshotMultiplier;
            this.additionalProjectiles = additionalProjectiles;
            this.projectileSpeedMultiplier = projectileSpeedMultiplier;
            this.projectileRangeValue = projectileRangeValue;
            this.projectileRangeMultiplier = projectileRangeMultiplier;
        }

        public Barrel()
        {
            this(0, null, null, 0, 0, null, false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        @Override public boolean isSilencer()                { return isSilencer; }
        @Override public double getDamageValue()             { return baseDamageValue; }
        @Override public double getDamageMultiplier()        { return baseDamageMultiplier; }
        @Override public int getProjectileAmount()            { return additionalProjectiles; }
        @Override public double getBulletSpreadMultiplier()  { return bulletSpreadModifier; }
        @Override public Barrel getNullModifier()            { return singleton.getNullValue(); }
        @Override public double getHeadshotDamageModifier()  { return headshotValue; }
        @Override public double getHeadshotDamageMultiplier() { return headshotMultiplier; }
        @Override public double getProjectileSpeedMultiplier() { return projectileSpeedMultiplier; }
        @Override public int getProjectileRangeValue()         { return projectileRangeValue; }
        @Override public double getProjectileRangeMultiplier() { return projectileRangeMultiplier; }
        @Override public double getFireDamageValue()           { return fireDamageValue; }
        @Override public double getFireDamageMultiplier()      { return fireDamageMultiplier; }
        @Override public double getShrapnelDamageValue()       { return shrapnelDamageValue; }
        @Override public double getShrapnelDamageMultiplier()       { return shrapnelDamageMultiplier; }
    }
}
