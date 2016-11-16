/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components;

import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.MotionAttributes;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.BulletSpreadAttributes;
import net.projectzombie.crackshotenhanced.cs.guns.components.Stocks.Stock;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

import java.util.HashMap;

/**
 *
 * @author jesse
 */
public class Stocks extends ModifierConfig<Stock>
{
    static private Stocks singleton = null;
    static public Stocks getInstance()
    {
        if (singleton == null)
            singleton = new Stocks();
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Material", 4);
        defaultValues.put("Material Data", 0);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Bullet Spread Multiplier", 0.0);
        defaultValues.put("Speed Running Multiplier", 0.0);
        defaultValues.put("Speed Sprinting Multiplier", 0.0);
        defaultValues.put("Bullet Spread Multiplier Crouching", 0.0);
        defaultValues.put("Bullet Spread Multiplier Standing", 0.0);
        defaultValues.put("Bullet Spread Multiplier Running", 0.0);
        defaultValues.put("Bullet Spread Multiplier Sprinting", 0.0);
        return defaultValues;
    }

    static private final String YML_NAME = "Stocks.yml";
    static private final String MODULE_NAME = "Stocks";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private Stocks() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public Stock buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new Stock(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getString("Material"),
                    values.getInt("Material Data"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    values.getDouble("Bullet Spread Multiplier"),
                    values.getDouble("Speed Running Multiplier"),
                    values.getDouble("Speed Sprinting Multiplier"),
                    values.getDouble("Bullet Spread Multiplier Crouching"),
                    values.getDouble("Bullet Spread Multiplier Standing"),
                    values.getDouble("Bullet Spread Multiplier Running"),
                    values.getDouble("Bullet Spread Multiplier Sprinting")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add stock " + values.getString("Display Name"));
            return null;
        }
    }

    @Override
    public Stock getNullValue()
    {
        return new Stock();
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
