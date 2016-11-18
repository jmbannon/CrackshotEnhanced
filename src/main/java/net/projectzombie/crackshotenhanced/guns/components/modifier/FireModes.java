/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.FireModes.FireMode;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.FireModeSet;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

import static net.projectzombie.crackshotenhanced.main.Main.getPlugin;

/**
 *
 * @author jbannon
 */
public class FireModes extends ModifierConfig<FireMode>
{
    static private FireModes singleton = null;
    static public FireModes getInstance()
    {
        if (singleton == null)
            singleton = new FireModes();
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Material", 4);
        defaultValues.put("Material Data", 0);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Burst Fire", false);
        defaultValues.put("Burst Shots Per Fire", 0);
        defaultValues.put("Automatic", false);
        return defaultValues;
    }

    static private final String YML_NAME = "FireModes.yml";
    static private final String MODULE_NAME = "FireModes";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private FireModes() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public FireMode buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new FireMode(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getString("Material"),
                    values.getInt("Material Data"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    values.getBoolean("Burst Fire"),
                    values.getInt("Burst Shots Per Fire"),
                    values.getBoolean("Automatic")
            );
        } catch (Exception e) {
            getPlugin().getLogger().warning("Cannot add firemode " + values.getString("Display Name"));
            return null;
        }
    }
    
    @Override
    public FireMode getNullValue()
    {
        return new FireMode();
    }
    
    
    static public class FireMode extends GunModifier implements FireModeSet.FireModeAttributes
    {
        private static final String TITLE = "Fire Mode: ";

        private final boolean isBurstFire;
        private final boolean isAutomatic;
        private final int shotsPerBurst;

        private FireMode(final int uniqueID,
                        final String displayName,
                          final String material,
                          final int materialByte,
                          final int price,
                          final String color,
                          final boolean isBurstFire,
                          final int shotsPerBurst,
                          final boolean isAutomatic) 
        {
            super(uniqueID, displayName, material, materialByte, price, color);
            this.isBurstFire = isBurstFire;
            this.shotsPerBurst = shotsPerBurst;
            this.isAutomatic = isAutomatic;
        }

        private FireMode()
        {
            this(0, null, null, 0, 0, null, false, 0, false);
        }
        
        @Override public boolean isBurstFire()       { return isBurstFire; }
        @Override public boolean isAutomatic()       { return isAutomatic; }
        @Override public int     getShotsPerBurst()  { return shotsPerBurst; }
        @Override public int price()                 { return 40;       }
        @Override public FireMode getNullModifier() { return new FireMode(); }
    }
}
