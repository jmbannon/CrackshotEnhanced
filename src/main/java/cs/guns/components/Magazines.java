/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import csv.CSVReader;
import cs.guns.components.Magazines.Magazine;
import csv.CSVInput;
import cs.guns.components.modifiers.MagazineAttributes;

/**
 *
 * @author jesse
 */
public class Magazines extends CSVInput<Magazine>
{
    static private Magazines singleton = null;
    static public Magazines getInstance()
    {
        if (singleton == null)
            singleton = new Magazines();
        return singleton;
    }
    
    static private final String MAGAZINES_CSV_NAME = "Magazines.csv";
    static private final String[] MAGAZINE_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Magazine Modifier (INT)",
        "Magazine Multiplier (DBL)",
        "Reload Speed Multiplier (DBL)"
    };
    
    private Magazines()
    {
        super(MAGAZINES_CSV_NAME,  buildMagazines(), MAGAZINE_VALUES);
    }
    
    @Override
    public Magazine getNullValue()
    {
        return new Magazine();
    }
    
    /**
     * Builds all magazines, if any. Allowed to not have any in CSV.
     * @return Array of all magazines (including null magazine).
     */
    static private Magazine[] buildMagazines()
    {
        final CSVReader csv = new CSVReader(MAGAZINES_CSV_NAME, MAGAZINE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Magazine[] { new Magazine() };
        }
        
        int j = 0;
        final Magazine[] toReturn          = new Magazine[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final int[] magazineBoost           = csv.getColumnInt(j++);
        final double[] magazineMultiplier   = csv.getColumnDouble(j++);
        final double[] reloadSpeedMultiplier= csv.getColumnDouble(j++);
        
        toReturn[0] = new Magazine();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new Magazine(
                    i+1,
                    displayNames[i],
                    materialNames[i],
                    materialBytes[i],
                    price[i],
                    colors[i],
                    magazineBoost[i],
                    magazineMultiplier[i],
                    reloadSpeedMultiplier[i]);
        }
        return toReturn;
    }
    
    static public class Magazine extends GunModifier implements MagazineAttributes
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
                          final int magazineBoost,
                          final double magazineMultiplier,
                          final double reloadSpeedMultiplier)
        {
            super(uniqueID, displayName, material, materialByte, price, color);
            this.magazineBoost = magazineBoost;
            this.magazineMultiplier = magazineMultiplier;
            this.reloadSpeedMultiplier = reloadSpeedMultiplier;
        }

        private Magazine()
        {
            this(0, null, null, 0, 0, null, 0, 0, 0);
        }

        @Override public int getMagazineSizeModifier()          { return magazineBoost; }
        @Override public double getReloadSpeedMultiplier()  { return reloadSpeedMultiplier; }
        @Override public GunModifier getNullModifier()      { return singleton.getNullValue(); }
        @Override public double getMagazineSizeMultiplier() { return magazineMultiplier; }

    }
}
