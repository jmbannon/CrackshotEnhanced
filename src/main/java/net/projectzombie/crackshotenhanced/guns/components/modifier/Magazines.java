/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.Magazines.Magazine;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.MagazineSet;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

/**
 *
 * @author jesse
 */
public class Magazines extends ModifierConfig<Magazine>
{
    static private Magazines singleton = null;
    static public Magazines getInstance()
    {
        if (singleton == null)
            singleton = new Magazines();
        return singleton;
    }

    static private final ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Material", 4);
        defaultValues.put("Material Data", 0);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Magazine Modifier", 0);
        defaultValues.put("Magazine Multiplier", 0.0);
        defaultValues.put("Reload Speed Multiplier", 0.0);
        return defaultValues;
    }

    static private final String YML_NAME = "Magazines.yml";
    static private final String MODULE_NAME = "Magazines";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Quality" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private Magazines() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public Magazine buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new Magazine(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getString("Material"),
                    values.getInt("Material Data"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    Qualities.getInstance().get(values.getString("Quality")),
                    values.getInt("Magazine Modifier"),
                    values.getDouble("Magazine Multiplier"),
                    values.getDouble("Reload Speed Multiplier")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add magazine " + values.getString("Display Name"));
            return null;
        }
    }

    @Override
    public Magazine getNullValue()
    {
        return new Magazine();
    }
    
    static public class Magazine extends QualityGunModifier implements MagazineSet.MagazineAttributes
    {
        private final int magazineBoost;
        private final double magazineMultiplier;
        private final double reloadSpeedMultiplier;

        private Magazine(final int uniqueID,
                         final String displayName,
                         final String material,
                         final int materialByte,
                         final int price,
                         final String color,
                         final Qualities.Quality quality,
                          final int magazineBoost,
                         final double magazineMultiplier,
                         final double reloadSpeedMultiplier)
        {
            super(uniqueID, displayName, material, materialByte, price, color, quality);
            this.magazineBoost = magazineBoost;
            this.magazineMultiplier = magazineMultiplier;
            this.reloadSpeedMultiplier = reloadSpeedMultiplier;
        }

        private Magazine()
        {
            this(0, null, null, 0, 0, null, null, 0, 0, 0);
        }

        @Override public int getMagazineSizeModifier()          { return magazineBoost; }
        @Override public double getReloadSpeedMultiplier()  { return reloadSpeedMultiplier; }
        @Override public GunModifier getNullModifier()      { return singleton.getNullValue(); }
        @Override public double getMagazineSizeMultiplier() { return magazineMultiplier; }

    }
}
