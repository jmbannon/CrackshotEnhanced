/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import csv.CSVReader;
import cs.guns.components.FireModes.FireMode;
import csv.CSVInput;
import cs.guns.components.modifiers.FireModeAttributes;

/**
 *
 * @author jbannon
 */
public class FireModes extends CSVInput<FireMode>
{
    static private FireModes singleton = null;
    static public FireModes getInstance()
    {
        if (singleton == null)
            singleton = new FireModes();
        return singleton;
    }
    
    static private final String FIREMODE_CSV_NAME = "FireModes.csv";
    static private final String[] FIREMODE_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Is Burst Fire (T/F)",
        "Shots Per Burst (INT)",
        "Is Automatic (T/F)"
    };
    
    private FireModes()
    {
        super(FIREMODE_CSV_NAME, buildFireModes(), FIREMODE_VALUES);
    }
    
    @Override
    public FireMode getNullValue()
    {
        return null;
    }
    
    /**
     * Builds all FireModes, if any. Must have at least one in CSV.
     * @return Array of all FireModes. Null otherwise.
     */
    static private FireMode[] buildFireModes()
    {
        final CSVReader csv = new CSVReader(FIREMODE_CSV_NAME, FIREMODE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final FireMode[] toReturn           = new FireMode[rowCount];
        final String[] displayNames          = csv.getColumnString(j++);
        final String[] materialNames         = csv.getColumnString(j++);
        final int[]    materialBytes         = csv.getColumnInt(j++);
        final int[]    price                 = csv.getColumnInt(j++);
        final String[] colors                = csv.getColumnString(j++);
        final boolean[] isBurstFire          = csv.getColumnBoolean(j++);
        final int[]     shotsPerBurst        = csv.getColumnInt(j++);
        final boolean[] isAutomatic          = csv.getColumnBoolean(j++);
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new FireMode(
                    i,
                    displayNames[i],
                    materialNames[i],
                    materialBytes[i],
                    price[i],
                    colors[i],
                    isBurstFire[i],
                    shotsPerBurst[i],
                    isAutomatic[i]);
        }
        return toReturn;
    }
    
    
    static public class FireMode extends GunModifier implements FireModeAttributes
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
        
        @Override public boolean isBurstFire()       { return isBurstFire; }
        @Override public boolean isAutomatic()       { return isAutomatic; }
        @Override public int     getShotsPerBurst()  { return shotsPerBurst; }
        @Override public int price()                 { return 40;       }
        @Override public FireMode getNullModifier() { return singleton.getNullValue(); }
        
        static
        public String getTitle()
        {
            return TITLE;
        }
    }
}
