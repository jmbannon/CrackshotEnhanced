/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import csv.CSVReader;
import cs.guns.components.Bolts.Bolt;
import csv.CSVInput;

/**
 *
 * @author jesse
 */
public class Bolts extends CSVInput<Bolt>
{
    static private Bolts singleton = null;
    static public Bolts getInstance()
    {
        if (singleton == null)
            singleton = new Bolts();
        return singleton;
    }
    
    static private final String BOLTS_CSV_NAME = "Bolts.csv";
    static private final String[] BOLT_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Bolt Duration Multiplier (DBL)"
    };
    
    private Bolts()
    {
        super(BOLTS_CSV_NAME, buildBolts(), BOLT_VALUES);
    }
    
    @Override
    public Bolt getNullValue()
    {
        return new Bolt();
    }
    
    /**
    * Builds all bolts, if any. Allowed to not have any in CSV.
    * @return Array of all bolts (including null bolt).
    */
    static private Bolt[] buildBolts()
    {
        final CSVReader csv = new CSVReader(BOLTS_CSV_NAME, BOLT_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new Bolt[] { new Bolt() };
        }
 
        int j = 0;
        final Bolt[]   toReturn             = new Bolt[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final double[] durationMultiplier   = csv.getColumnDouble(j++);
 
        toReturn[0] = new Bolt();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new Bolt(
                i+1,
                displayNames[i],
                materialNames[i],
                materialBytes[i],
                price[i],
                colors[i],
                durationMultiplier[i]);
        }
        return toReturn;
    }
    
    static public class Bolt extends GunModifier
    {
        private final double durationMultiplier;

        private Bolt(final int uniqueID,
                      final String displayName,
                      final String material,
                      final int materialByte,
                      final int price,
                      final String color,
                      final double durationMultiplier)
        {
            super(uniqueID, displayName, material, materialByte, price, color);
            this.durationMultiplier = durationMultiplier;
        }

        private Bolt()
        {
            this(0, null, null, 0, 0, null, 0);
        }

        public double getBoltDurationMultiplier() { return durationMultiplier; }
        @Override public Bolt getNullModifier()             { return new Bolt(); }
    }
}
