/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.Stocks.Stock;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.MotionSet;
import net.projectzombie.crackshotenhanced.guns.attributes.modifier.BulletSpreadSet;
import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

public class Stocks extends ModifierConfig<Stock>
{
    static private Stocks singleton = null;
    static public Stocks getInstance()
    {
        if (singleton == null) {
            singleton = new Stocks();
            singleton.postInitialize();
        }
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
        defaultValues.put("Bullet Spread Multiplier Crouching", 0.0);
        defaultValues.put("Bullet Spread Multiplier Standing", 0.0);
        defaultValues.put("Bullet Spread Multiplier Running", 0.0);
        return defaultValues;
    }

    static private final String YML_NAME = "Stocks.yml";
    static private final String MODULE_NAME = "Stocks";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Quality" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private Stocks() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public Stock buildModule(final String key, final int uniqueID, final ModifierMap values) {
        try {
            return new Stock(
                    key,
                    uniqueID,
                    values.getString("Display Name"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    Qualities.getInstance().get(values.getString("Quality")),
                    values.getDouble("Bullet Spread Multiplier"),
                    values.getDouble("Speed Running Multiplier"),
                    values.getDouble("Bullet Spread Multiplier Crouching"),
                    values.getDouble("Bullet Spread Multiplier Standing"),
                    values.getDouble("Bullet Spread Multiplier Running")
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
    
    static public class Stock extends QualityGunModifier implements
            BulletSpreadSet.BulletSpreadAttributes,
            MotionSet.MotionAttributes
    {
        private final double bulletSpreadMultiplier;
        
        private final double runningSpeedMultiplier;

        private final double crouchingBulletSpreadMultiplier;
        private final double standingBulletSpreadMultiplier;
        private final double runningBulletSpreadMultiplier;


        private Stock(final String key,
                      final int uniqueID,
                      final String displayName,
                      final int price,
                      final String color,
                      final Qualities.Quality quality,
                       final double bulletSpreadMultiplier,

                      final double runningSpeedMultiplier,

                      final double crouchingBulletSpreadMultiplier,
                      final double standingBulletSpreadMultiplier,
                      final double runningBulletSpreadMultiplier)
        {
            super(key, uniqueID, displayName, price, color, quality, CraftableType.STOCK);
            this.bulletSpreadMultiplier = bulletSpreadMultiplier;
            this.runningSpeedMultiplier = runningSpeedMultiplier;
            this.crouchingBulletSpreadMultiplier = crouchingBulletSpreadMultiplier;
            this.standingBulletSpreadMultiplier = standingBulletSpreadMultiplier;
            this.runningBulletSpreadMultiplier = runningBulletSpreadMultiplier;
        }

        private Stock() { this(null, 0, null, 0, null, null, 0, 0, 0, 0, 0); }
        @Override public Stock getNullModifier() { return new Stock(); }
        
        @Override public double getBulletSpreadMultiplier()          { return bulletSpreadMultiplier; }
        @Override public double getSpeedMultiplier()                 { return runningSpeedMultiplier; }
        @Override public double getCrouchingBulletSpreadMultiplier() { return crouchingBulletSpreadMultiplier; }
        @Override public double getStandingBulletSpreadMultiplier()  { return standingBulletSpreadMultiplier; }
        @Override public double getRunningBulletSpreadMultiplier()   { return runningBulletSpreadMultiplier; }
    }
}
