/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.Bolts.Bolt;
import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

/**
 *
 * @author jesse
 */
public class Bolts extends ModifierConfig<Bolt>
{
    static private Bolts singleton = null;
    static public Bolts getInstance()
    {
        if (singleton == null) {
            singleton = new Bolts();
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
        defaultValues.put("Bolt Duration Multiplier", 0.0);
        return defaultValues;
    }
    
    static private final String YML_NAME = "Bolts.yml";
    static private final String MODULE_NAME = "Bolts";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Quality" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private Bolts() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public Bolt buildModule(final String key, final int uniqueID, final ModifierMap values) {
        try {
            return new Bolt(
                    key,
                    uniqueID,
                    values.getString("Display Name"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    Qualities.getInstance().get(values.getString("Quality")),
                    values.getDouble("Bolt Duration Multiplier")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add bolt " + values.getString("Display Name"));
            return null;
        }
    }
    
    @Override
    public Bolt getNullValue()
    {
        return new Bolt();
    }
    
    static public class Bolt extends QualityGunModifier
    {
        private final double durationMultiplier;

        private Bolt(final String key,
                     final int uniqueID,
                     final String displayName,
                     final int price,
                     final String color,
                     final Qualities.Quality quality,
                      final double durationMultiplier)
        {
            super(key, uniqueID, displayName, price, color, quality, CraftableType.BOLT);
            this.durationMultiplier = durationMultiplier;
        }

        private Bolt()
        {
            this(null, 0, null, 0, null, null, 0);
        }

        public double getBoltDurationMultiplier()           { return durationMultiplier; }
        @Override public Bolt getNullModifier()             { return new Bolt(); }
    }
}
