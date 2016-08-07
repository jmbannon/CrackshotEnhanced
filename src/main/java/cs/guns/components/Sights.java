/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import csv.CSVReader;
import cs.guns.components.Sights.Scope;
import csv.CSVInput;
import cs.guns.modifiers.BulletSpreadAttributes;
import cs.guns.components.modifiers.ScopeAttributes;

/**
 *
 * @author jbannon
 */
public class Sights extends CSVInput<Scope>
{
    static private Sights singleton = null;
    static public Sights getInstance()
    {
        if (singleton == null)
            singleton = new Sights();
        return singleton;
    }
    
    static private final String SCOPE_CSV_NAME = "Scopes.csv";
    static private final String[] SCOPE_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Crackshot Zoom Amount (INT)",
        "Bullet Spread Multiplier (DBL)",
        "Zoom Bulletspread Multiplier (DBL)"
    };

    private Sights()
    {
        super(SCOPE_CSV_NAME, buildScopes(), SCOPE_VALUES);
    }
    
    @Override
    public Scope getNullValue()
    {
        return new Scope();
    }
    
    /**
     * Builds all Scopes, if any. Allowed to not have any in CSV.
     * @return Array of all Scopes. Null otherwise.
     */
    static private Scope[] buildScopes()
    {
        final CSVReader csv = new CSVReader(SCOPE_CSV_NAME, SCOPE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final Scope[] toReturn             = new Scope[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final int[] crackshotZoomAmount     = csv.getColumnInt(j++);
        final double[] bulletSpreadModifier = csv.getColumnDouble(j++);
        final double[] zoomBulletSpreadMultiplier = csv.getColumnDouble(j++);
        toReturn[0] = new Scope();
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new Scope(
                    i+1,
                    displayNames[i],
                    materialNames[i],
                    materialBytes[i],
                    price[i],
                    colors[i],
                    crackshotZoomAmount[i],
                    bulletSpreadModifier[i],
                    zoomBulletSpreadMultiplier[i]);
        }
        return toReturn;
    }
    
    static public class Scope extends GunModifier implements BulletSpreadAttributes,
                                                             ScopeAttributes
    {
        private final int zoomAmount;
        private final double bulletSpreadModifier;
        private final double zoomBulletSpreadMultiplier;
        
        private Scope(final int uniqueID,
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

        private Scope()
        {
            this(0, null, null, 0, 0, null, 0, 0, 0);
        }

        @Override public int getZoomAmount()                    { return zoomAmount;   }
        @Override public double getZoomBulletSpreadMultiplier() { return zoomBulletSpreadMultiplier; }
        @Override public double getBulletSpreadMultiplier()     { return bulletSpreadModifier; }
        @Override public Scope getNullModifier()                { return singleton.getNullValue(); }
    }
}
