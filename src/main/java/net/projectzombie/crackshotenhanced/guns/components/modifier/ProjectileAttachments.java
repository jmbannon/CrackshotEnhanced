/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.ProjectileAttachments.ProjectileAttachment;
import net.projectzombie.crackshotenhanced.guns.attributes.modifier.*;
import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

/**
 *
 * @author jbannon
 */
public class ProjectileAttachments extends ModifierConfig<ProjectileAttachment>
{
    static private ProjectileAttachments slotOneSingleton = null;
    static private ProjectileAttachments slotTwoSingleton = null;
    static private ProjectileAttachments slotThreeSingleton = null;
            
    static public ProjectileAttachments getSlotOneInstance()
    {
        if (slotOneSingleton == null) {
            final String ymlName = "SlotOneAttachments.yml";
            final String moduleName = "SlotOneAttachments";
            slotOneSingleton = new ProjectileAttachments(ymlName, moduleName, CraftableType.SLOT_ONE_ATTACHMENT);
            slotOneSingleton.postInitialize();
        }
        return slotOneSingleton;
    }

    static public ProjectileAttachments getSlotTwoInstance()
    {
        if (slotTwoSingleton == null) {
            final String ymlName = "SlotTwoAttachments.yml";
            final String moduleName = "SlotTwoAttachments";
            slotTwoSingleton = new ProjectileAttachments(ymlName, moduleName, CraftableType.SLOT_TWO_ATTACHMENT);
            slotTwoSingleton.postInitialize();
        }
        return slotTwoSingleton;
    }

    static public ProjectileAttachments getSlotThreeInstance()
    {
        if (slotThreeSingleton == null) {
            final String ymlName = "SlotThreeAttachments.yml";
            final String moduleName = "SlotThreeAttachments";
            slotThreeSingleton = new ProjectileAttachments(ymlName, moduleName, CraftableType.SLOT_THREE_ATTACHMENT);
            slotThreeSingleton.postInitialize();
        }
        return slotThreeSingleton;
    }




    static private ModifierMap buildDefaultValues(final String moduleName) {
        final ModifierMap defaultValues = new ModifierMap(moduleName);
        defaultValues.put("Material", 4);
        defaultValues.put("Material Data", 0);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Bulletspread Multiplier", 0.0);
        defaultValues.put("Damage Modifier", 0.0);
        defaultValues.put("Damage Multiplier", 0.0);
        defaultValues.put("Headshot Damage Modifier", 0.0);
        defaultValues.put("Headshot Damage Multiplier", 0.0);
        defaultValues.put("Crit Chance Modifier", 0.0);
        defaultValues.put("Crit Strike Multiplier", 0.0);
        defaultValues.put("Bleedout Duration Seconds", 0.0);
        defaultValues.put("Bleedout Duration Multiplier", 0.0);
        defaultValues.put("Bleedout Damage", 0.0);
        defaultValues.put("Bleedout Damage Multiplier", 0.0);
        defaultValues.put("Bleedout Damage Multiplier from Base Damage", 0.0);
        defaultValues.put("Bleedout Damage Multiplier from Shrapnel", 0.0);
        defaultValues.put("Incendiary Damage Modifier", 0.0);
        defaultValues.put("Incendiary Damage Multiplier", 0.0);
        defaultValues.put("Ignite Chance", 0.0);
        defaultValues.put("Ignite Duration", 0.0);
        defaultValues.put("Ignite Damage Multiplier From Incendiary Damage", 0.0);
        defaultValues.put("Ignite Damage Multiplier From Base Damage", 0.0);
        defaultValues.put("Shrapnel Damage Modifier", 0.0);
        defaultValues.put("Shrapnel Damage Multiplier", 0.0);
        defaultValues.put("Stun Chance", 0.0);
        defaultValues.put("Stun Duration", 0.0);
        return defaultValues;
    }


    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Quality" };
    private final CraftableType type;

    private ProjectileAttachments(final String yamlName,
                                  final String moduleName,
                                  final CraftableType type) {
        super(yamlName, moduleName, NECESSARY_VALUES, buildDefaultValues(moduleName));
        assert(type == CraftableType.SLOT_ONE_ATTACHMENT
                || type == CraftableType.SLOT_TWO_ATTACHMENT
                || type == CraftableType.SLOT_THREE_ATTACHMENT);
        this.type = type;
    }

    public ProjectileAttachment buildModule(final String key, final int uniqueID, final ModifierMap values) {
        try {
            return new ProjectileAttachment(
                    key,
                    uniqueID,
                    values.getString("Display Name"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    Qualities.getInstance().get(values.getString("Quality")),
                    this.type,
                    values.getDouble("Bulletspread Multiplier"),
                    values.getDouble("Damage Modifier"),
                    values.getDouble("Damage Multiplier"),
                    values.getDouble("Headshot Damage Modifier"),
                    values.getDouble("Headshot Damage Multiplier"),
                    values.getDouble("Crit Chance Modifier"),
                    values.getDouble("Crit Strike Multiplier"),
                    values.getDouble("Bleedout Duration Seconds"),
                    values.getDouble("Bleedout Duration Multiplier"),
                    values.getDouble("Bleedout Damage"),
                    values.getDouble("Bleedout Damage Multiplier"),
                    values.getDouble("Bleedout Damage Multiplier from Base Damage"),
                    values.getDouble("Bleedout Damage Multiplier from Shrapnel"),
                    values.getDouble("Incendiary Damage Modifier"),
                    values.getDouble("Incendiary Damage Multiplier"),
                    values.getDouble("Ignite Chance"),
                    values.getDouble("Ignite Duration"),
                    values.getDouble("Ignite Damage Multiplier From Incendiary Damage"),
                    values.getDouble("Ignite Damage Multiplier From Base Damage"),
                    values.getDouble("Shrapnel Damage Modifier"),
                    values.getDouble("Shrapnel Damage Multiplier"),
                    values.getDouble("Stun Chance"),
                    values.getDouble("Stun Duration")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add projectile attachment " + values.getString("Display Name"));
            return null;
        }
    }
    
    @Override
    public ProjectileAttachment getNullValue()
    {
        return new ProjectileAttachment();
    }


    static public class ProjectileAttachment extends QualityGunModifier implements
            BulletSpreadSet.BulletSpreadAttributes,
            BaseDamageSet.BaseDamageAttributes,
            HeadshotDamageSet.HeadshotAttributes,
            CritSet.CritAttributes,
            BleedoutSet.BleedoutAttributes,
            IncendiaryDamageSet.IncendiaryDamageAttributes,
            IgniteSet.IgniteAttributes,
            ShrapnelDamageSet.ShrapnelDamageAttributes,
            StunSet.StunAttributes
    {
        private final double bulletSpreadMultiplier;
        private final double damageModifier;
        private final double damageMultiplier;
        private final double headshotDamageModifier;
        private final double headshotDamageMultiplier;
        private final double critChanceBoost;
        private final double critStrikeMultiplier;
        private final double bleedoutDurationSeconds;
        private final double bleedoutDurationMultiplier;
        private final double bleedoutDamageBoost;
        private final double bleedoutDamageMultiplier;
        private final double bleedoutDamageMultiplierFromBase;
        private final double bleedoutDamageMultiplierFromShrap;
        private final double incendiaryDamageModifier;
        private final double incendiaryDamageMultiplier;
        private final double igniteChance;
        private final double igniteDuration;
        private final double igniteDamageMultiplierFromIncendiary;
        private final double igniteDamageMultiplierFromBase;
        private final double shrapnelDamageModifier;
        private final double shrapnelDamageMultiplier;
        private final double stunChance;
        private final double stunDuration;

        private ProjectileAttachment(final String key,
                                     final int uniqueID,
                                     final String displayname,
                                     final int price,
                                     final String color,
                                     final Qualities.Quality quality,
                                     final CraftableType type,
                                     final double bulletSpreadMultiplier,
                                     final double damageModifier,
                                     final double damageMultiplier,
                                     final double headshotDamageModifier,
                                     final double headshotDamageMultiplier,
                                     final double critChanceBoost,
                                     final double critStrikeMultiplier,
                                     final double bleedoutDurationSeconds,
                                     final double bleedoutDurationMultiplier,
                                     final double bleedoutDamageBoost,
                                     final double bleedoutDamageMultiplier,
                                     final double bleedoutDamageMultiplierFromBase,
                                     final double bleedoutDamageMultiplierFromShrap,
                                     final double fireDamageModifier,
                                     final double fireDamageMultiplier,
                                     final double igniteChance,
                                     final double igniteDuration,
                                     final double igniteDamageMultiplierFromFire,
                                     final double igniteDamageMultiplierFromBase,
                                     final double shrapnelDamageModifier,
                                     final double shrapnelDamageMultiplier,
                                     final double stunChance,
                                     final double stunDuration)
        {        
            super(key, uniqueID, displayname, price, color, quality, type);
            this.bulletSpreadMultiplier = bulletSpreadMultiplier;
            this.damageModifier = damageModifier;
            this.damageMultiplier = damageMultiplier;
            this.headshotDamageModifier = headshotDamageModifier;
            this.headshotDamageMultiplier = headshotDamageMultiplier;
            this.critChanceBoost = critChanceBoost;
            this.critStrikeMultiplier = critStrikeMultiplier;
            this.bleedoutDurationSeconds = bleedoutDurationSeconds;
            this.bleedoutDurationMultiplier = bleedoutDurationMultiplier;
            this.bleedoutDamageBoost = bleedoutDamageBoost;
            this.bleedoutDamageMultiplier = bleedoutDamageMultiplier;
            this.bleedoutDamageMultiplierFromBase = bleedoutDamageMultiplierFromBase;
            this.bleedoutDamageMultiplierFromShrap = bleedoutDamageMultiplierFromShrap;
            this.incendiaryDamageModifier = fireDamageModifier;
            this.incendiaryDamageMultiplier = fireDamageMultiplier;
            this.igniteChance = igniteChance;
            this.igniteDuration = igniteDuration;
            this.igniteDamageMultiplierFromIncendiary = igniteDamageMultiplierFromFire;
            this.igniteDamageMultiplierFromBase = igniteDamageMultiplierFromBase;
            this.shrapnelDamageModifier = shrapnelDamageModifier;
            this.shrapnelDamageMultiplier = shrapnelDamageMultiplier;
            this.stunChance = stunChance;
            this.stunDuration = stunDuration;
        }

        /**
         * Constructs the null Attatchment.
         */
        private ProjectileAttachment()
        {
            this(null, 0, null, 0, null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        @Override public double getDamageValue()                         { return damageModifier; }
        @Override public double getDamageMultiplier()                    { return damageMultiplier; }
        @Override public double getBulletSpreadMultiplier()              { return bulletSpreadMultiplier; }
        @Override public double getCritChance()                          { return critChanceBoost; }
        @Override public double getCritStrike()                          { return critStrikeMultiplier; }
        @Override public double getBleedoutDurationValue()               { return bleedoutDurationSeconds; }
        @Override public double getBleedoutDamageValuePerSecond()        { return bleedoutDamageBoost;  }
        @Override public double getBleedoutDamageValuePerSecondMultiplier() { return bleedoutDamageMultiplier; }
        @Override public double getHeadshotDamageModifier()              { return headshotDamageModifier; }
        @Override public double getHeadshotDamageMultiplier()            { return headshotDamageMultiplier; }
        @Override public double getBleedoutDurationMultiplier()          { return bleedoutDurationMultiplier; }
        @Override public double getBleedoutDamageMultiplerFromShrapnel() { return bleedoutDamageMultiplierFromShrap; }
        @Override public double getIncendiaryDamageValue()                     { return incendiaryDamageModifier; }
        @Override public double getIncendiaryDamageMultiplier()                { return incendiaryDamageMultiplier; }
        @Override public double getIgniteChance()                        { return igniteChance; }
        @Override public double getIgniteDuration()                      { return igniteDuration; }
        @Override public double getIgniteDamageMultiplierFromFireDamage() { return igniteDamageMultiplierFromIncendiary; }
        @Override public double getShrapnelDamageValue()                 { return shrapnelDamageModifier; }
        @Override public double getShrapnelDamageMultiplier()            { return shrapnelDamageMultiplier; }
        @Override public double getStunChance()                          { return stunChance; }
        @Override public double getStunDuration()                        { return stunDuration; }
        @Override public ProjectileAttachment getNullModifier()          { return slotOneSingleton.getNullValue(); }
    }
}
