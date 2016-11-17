/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.Barrels.Barrel;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.ProjectileSet;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.SilencerSet;
import net.projectzombie.crackshotenhanced.guns.attributes.modifier.*;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

/**
 *
 * @author jesse
 */
public class Barrels extends ModifierConfig<Barrel>
{
    static private Barrels singleton = null;
    static public Barrels getInstance()
    {
        if (singleton == null)
            singleton = new Barrels();
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Material", 4);
        defaultValues.put("Material Data", 0);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Silencer", false);
        defaultValues.put("Bullet Spread Modifier", 0.0);
        defaultValues.put("Base Damage Value", 0.0);
        defaultValues.put("Base Damage Multiplier", 0.0);
        defaultValues.put("Shrapnel Damage Value", 0.0);
        defaultValues.put("Shrapnel Damage Multiplier", 0.0);
        defaultValues.put("Fire Damage Value", 0.0);
        defaultValues.put("Fire Damage Multiplier", 0.0);
        defaultValues.put("Headshot Modifier", 0.0);
        defaultValues.put("Headshot Multiplier", 0.0);
        defaultValues.put("Projectile Additional Per Shot", 0);
        defaultValues.put("Projectile Speed Multiplier", 0.0);
        defaultValues.put("Projectile Range Modifier", 0);
        defaultValues.put("Projectile Range Multiplier", 0.0);
        return defaultValues;
    }
    
    static private final String YML_NAME = "Barrels.yml";
    static private final String MODULE_NAME = "Barrels";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private Barrels() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public Barrel buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new Barrel(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getString("Material"),
                    values.getInt("Material Data"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    values.getBoolean("Silencer"),
                    values.getDouble("Bullet Spread Modifier"),
                    values.getDouble("Base Damage Value"),
                    values.getDouble("Base Damage Multiplier"),
                    values.getDouble("Shrapnel Damage Value"),
                    values.getDouble("Shrapnel Damage Multiplier"),
                    values.getDouble("Fire Damage Value"),
                    values.getDouble("Fire Damage Multiplier"),
                    values.getDouble("Headshot Modifier"),
                    values.getDouble("Headshot Multiplier"),
                    values.getInt("Projectile Additional Per Shot"),
                    values.getDouble("Projectile Speed Multiplier"),
                    values.getInt("Projectile Range Modifier"),
                    values.getDouble("Projectile Range Multiplier")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add barrel " + values.getString("Display Name"));
            return null;
        }
    }

    @Override
    public Barrel getNullValue()
    {
        return new Barrel();
    }

    static public class Barrel extends GunModifier implements
            BulletSpreadSet.BulletSpreadAttributes,
            BaseDamageSet.BaseDamageAttributes,
            HeadshotDamageSet.HeadshotAttributes,
            ProjectileSet.ProjectileAttributes,
            SilencerSet.SilencerAttributes,
            FireDamageSet.FireDamageAttributes,
            ShrapnelDamageSet.ShrapnelDamageAttributes
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
