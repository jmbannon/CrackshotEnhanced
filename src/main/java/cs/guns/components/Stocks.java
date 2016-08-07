/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import csv.CSVReader;
import csv.CSVInput;
import cs.guns.components.Stocks.Stock;
import cs.guns.modifiers.BulletSpreadAttributes;
import cs.guns.components.modifiers.MotionAttributes;

/**
 *
 * @author jesse
 */
public class Stocks extends CSVInput<Stock>
{
    static private Stocks singleton = null;
    static public Stocks getInstance()
    {
        if (singleton == null)
            singleton = new Stocks();
        return singleton;
    }
    
    static private final String STOCK_CSV_NAME = "Stocks.csv";
    static private final String[] STOCK_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Bullet Spread Multiplier (DBL)",
        
        // UPDATE IN CSVs
        "Running Speed Multiplier (DBL)",
        "Sprinting Speed Multiplier (DBL)",
        
        "Crouching Bullet Spread Multiplier (DBL)",
        "Standing Bullet Spread Multiplier (DBL)",
        "Running Bullet Spread Multiplier (DBL)",
        "Sprinting Bullet Spread Multiplier (DBL)"
    };
    
    private Stocks()
    {
        super(STOCK_CSV_NAME, buildStocks(), STOCK_VALUES);
    }
    
    @Override
    public Stock getNullValue()
    {
        return new Stock();
    }
    
    /**
     * Builds all stocks, if any. Allowed to not have any in CSV.
     * @return Array of all stocks (including null stock).
     */
    static private Stock[] buildStocks()
    {
        final CSVReader csv = new CSVReader(STOCK_CSV_NAME, STOCK_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Stock[] { new Stock() };
        }
        
        int j = 0;
        final Stock[] toReturn             = new Stock[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final double[] bulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] runningSpeedMultiplier = csv.getColumnDouble(j++);
        final double[] sprintingSpeedMultiplier = csv.getColumnDouble(j++);
        final double[] crouchingBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] standingBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] runningBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] sprintingBulletSpreadMultiplier = csv.getColumnDouble(j++);
        
        
        toReturn[0] = new Stock();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new Stock(
                    i+1,
                    displayNames[i],
                    materialNames[i],
                    materialBytes[i],
                    price[i],
                    colors[i],
                    bulletSpreadMultiplier[i],
                    runningSpeedMultiplier[i],
                    sprintingSpeedMultiplier[i],
                    crouchingBulletSpreadMultiplier[i],
                    standingBulletSpreadMultiplier[i],
                    runningBulletSpreadMultiplier[i],
                    sprintingBulletSpreadMultiplier[i]);
        }
        return toReturn;
    }
    
    static public class Stock extends GunModifier implements BulletSpreadAttributes,
                                                             MotionAttributes
    {
        private final double bulletSpreadMultiplier;
        
        private final double runningSpeedMultiplier;
        private final double sprintingSpeedMultiplier;
        
        private final double crouchingBulletSpreadMultiplier;
        private final double standingBulletSpreadMultiplier;
        private final double runningBulletSpreadMultiplier;
        private final double sprintingBulletSpreadMultiplier;
        

        private Stock(final int uniqueID,
                       final String displayName,
                       final String material,
                       final int materialData,
                       final int price,
                       final String color,
                       final double bulletSpreadMultiplier,
                       
                       final double runningSpeedMultiplier,
                       final double sprintingSpeedMultiplier,
                       
                       final double crouchingBulletSpreadMultiplier,
                       final double standingBulletSpreadMultiplier,
                       final double runningBulletSpreadMultiplier,
                       final double sprintingBulletSpreadMultiplier)
        {
            super(uniqueID, displayName, material, materialData, price, color);
            this.bulletSpreadMultiplier = bulletSpreadMultiplier;
            
            this.runningSpeedMultiplier = runningSpeedMultiplier;
            this.sprintingSpeedMultiplier = sprintingSpeedMultiplier;
            
            this.crouchingBulletSpreadMultiplier = crouchingBulletSpreadMultiplier;
            this.standingBulletSpreadMultiplier = standingBulletSpreadMultiplier;
            this.runningBulletSpreadMultiplier = runningBulletSpreadMultiplier;
            this.sprintingBulletSpreadMultiplier = sprintingBulletSpreadMultiplier;
        }

        private Stock() { this(0, null, null, 0, 0, null, 0, 0, 0, 0, 0, 0, 0); }
        @Override public Stock getNullModifier() { return new Stock(); }
        
        @Override public double getBulletSpreadMultiplier()          { return bulletSpreadMultiplier; }
        @Override public double getRunningSpeedMultiplier()          { return runningSpeedMultiplier; }
        @Override public double getSprintingSpeedMultiplier()        { return sprintingSpeedMultiplier; }
        @Override public double getCrouchingBulletSpreadMultiplier() { return crouchingBulletSpreadMultiplier; }
        @Override public double getStandingBulletSpreadMultiplier()  { return standingBulletSpreadMultiplier; }
        @Override public double getRunningBulletSpreadMultiplier()   { return runningBulletSpreadMultiplier; }
        @Override public double getSprintingBulletSpreadMultiplier() { return sprintingBulletSpreadMultiplier; }
    }
}
