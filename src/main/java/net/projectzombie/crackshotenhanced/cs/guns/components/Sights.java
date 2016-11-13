/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components;

import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.ScopeAttributes;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.BulletSpreadAttributes;
import net.projectzombie.crackshotenhanced.cs.guns.components.Sights.Sight;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;

import java.util.HashMap;

/**
 *
 * @author jbannon
 */
public class Sights extends ModifierConfig<Sight>
{
    static private Sights singleton = null;
    static public Sights getInstance()
    {
        if (singleton == null)
            singleton = new Sights();
        return singleton;
    }

    static private final ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap();
        defaultValues.put("Material", 4);
        defaultValues.put("Material Data", 0);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Crackshot Zoom Amount", 0);
        defaultValues.put("Bullet Spread Multiplier", 0.0);
        defaultValues.put("Zoom Bulletspread Multiplier", 0.0);
        return defaultValues;
    }

    static private final String YML_NAME = "Sights.yml";
    static private final String MODULE_NAME = "Sights";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private Sights() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public Sight buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new Sight(
                    uniqueID,
                    values.getString("Display Name"),
                    values.getString("Material"),
                    values.getInt("Material Data"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    values.getInt("Crackshot Zoom Amount"),
                    values.getDouble("Bullet Spread Multiplier"),
                    values.getDouble("Zoom Bulletspread Multiplier")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add sight " + values.getString("Display Name"));
            return null;
        }
    }


    @Override
    public Sight getNullValue()
    {
        return new Sight();
    }
    
    static public class Sight extends GunModifier implements BulletSpreadAttributes,
            ScopeAttributes
    {
        private final int zoomAmount;
        private final double bulletSpreadModifier;
        private final double zoomBulletSpreadMultiplier;
        
        private Sight(final int uniqueID,
                      final String displayName,
                      final String material,
                      final int materialByte,
                      final int price,
                      final String color,
                      final int crackshotZoomAmount,
                      final double bulletSpreadModifier,
                      final double zoomBulletSpreadModifier)
        {
            super(uniqueID, displayName, material, materialByte, price, color);
            this.zoomAmount = crackshotZoomAmount;
            this.bulletSpreadModifier = bulletSpreadModifier;
            this.zoomBulletSpreadMultiplier = zoomBulletSpreadModifier;
        }

        private Sight()
        {
            this(0, null, null, 0, 0, null, 0, 0, 0);
        }

        @Override public int getZoomAmount()                    { return zoomAmount;   }
        @Override public double getZoomBulletSpreadMultiplier() { return zoomBulletSpreadMultiplier; }
        @Override public double getBulletSpreadMultiplier()     { return bulletSpreadModifier; }
        @Override public Sight getNullModifier()                { return singleton.getNullValue(); }
    }
}
