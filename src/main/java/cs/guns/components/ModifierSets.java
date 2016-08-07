/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import java.util.ArrayList;
import csv.CSVReader;
import cs.guns.components.Barrels.Barrel;
import cs.guns.components.Bolts.Bolt;
import cs.guns.components.FireModes.FireMode;
import cs.guns.components.Magazines.Magazine;
import cs.guns.components.ModifierSets.ModifierSet;
import cs.guns.components.Sights.Scope;
import cs.guns.components.Stocks.Stock;
import csv.CSVInput;
import csv.CSVValue;
import cs.guns.components.Attachments.Attachment;

/**
 *
 * @author jesse
 */
public class ModifierSets extends CSVInput<ModifierSet>
{
    static private ModifierSets singleton = null;
    static public ModifierSets getInstance()
    {
        if (singleton == null)
            singleton = new ModifierSets();
        return singleton;
    }
    
    static private final String MODIFIER_SET_CSV_TEMPLATE = "Set Name:,Example Set\nAttatchments:\nBarrels:\nBolts:\nFireModes:\nMagazines:\nScopes:\nStocks\n---------";
    static private final String MODIFIER_SET_CSV_NAME = "ModifierSet.csv";
    static private final int LINES_PER_SET = 9;
    
    public ModifierSets()
    {
        super(MODIFIER_SET_CSV_NAME, initializeModifierSet(), MODIFIER_SET_CSV_TEMPLATE);
    }
    
    /**
     * Builds all ModifierSets, if any. Must have at least one in CSV.
     * @return Array of all ModifierSets. Null otherwise.
     */
    static private ModifierSet[] initializeModifierSet()
    {
        final CSVReader csv = new CSVReader(MODIFIER_SET_CSV_NAME, LINES_PER_SET, MODIFIER_SET_CSV_TEMPLATE);
        final int rowCount = csv.getRowCount();
        final ArrayList<ModifierSet> toReturn = new ArrayList<>();
        ModifierSet temp;
        
        String[]       name;
        ArrayList<Attachment> attachments;
        ArrayList<Barrel>      barrels;
        ArrayList<Bolt>        bolts;
        ArrayList<FireMode>    fireModes;
        ArrayList<Magazine>    magazines;
        ArrayList<Scope>       scopes;
        ArrayList<Stock>       stocks;
        
        if (rowCount <= 0 || rowCount % LINES_PER_SET != 0)
            return null;
        
        for (int i = 0; i < rowCount; i += LINES_PER_SET)
        {
            name               = csv.getRowString(i + 0);
            attachments        = Attachments.getInstance().get(csv.getRowString(i+1), true);
            barrels            = Barrels.getInstance().get(csv.getRowString(i+2), true);
            bolts              = Bolts.getInstance().get(csv.getRowString(i+3), true);
            fireModes          = FireModes.getInstance().get(csv.getRowString(i+4), false);
            magazines          = Magazines.getInstance().get(csv.getRowString(i+5), true);
            scopes             = Sights.getInstance().get(csv.getRowString(i+6), true);
            stocks             = Stocks.getInstance().get(csv.getRowString(i+7), true);
            
            temp = createModifierSet(i, name, attachments, barrels, bolts, fireModes, magazines, scopes, stocks);
            if (temp != null)
                toReturn.add(temp);
        }
        
        return toReturn.toArray(new ModifierSet[toReturn.size()]);
    }
    
    static private ModifierSet createModifierSet(int index,
                                           String[] nameArray,
                                           ArrayList<Attachment> attachments,
                                           ArrayList<Barrel> barrels,
                                           ArrayList<Bolt> bolts,
                                           ArrayList<FireMode> fireModes,
                                           ArrayList<Magazine> magazines,
                                           ArrayList<Scope> scopes,
                                           ArrayList<Stock> stocks)
    {
        if (nameArray.length < 1 || fireModes.size() < 1)
        {
            return null;
        }
        else
        {
            return new ModifierSet(
                    index,
                    nameArray[0],
                    attachments,
                    barrels,
                    bolts,
                    fireModes,
                    magazines,
                    scopes,
                    stocks);
        }
    }
    
    static public class ModifierSet extends CSVValue
    {
        private final ArrayList<Attachment> attachments;
        private final ArrayList<Barrel> barrels;
        private final ArrayList<Bolt> bolts;
        private final ArrayList<FireMode> fireModes;
        private final ArrayList<Magazine> magazines;
        private final ArrayList<Scope> scopes;
        private final ArrayList<Stock> stocks;
        private final GunModifier[] modifiers;

        private ModifierSet(final int index,
                            final String name,
                            final ArrayList<Attachment> attachments,
                            final ArrayList<Barrel> barrels,
                            final ArrayList<Bolt> bolts,
                            final ArrayList<FireMode> fireModes,
                            final ArrayList<Magazine> magazines,
                            final ArrayList<Scope> scopes,
                            final ArrayList<Stock> stocks)
        {
            super(index, name);
            this.attachments = attachments;
            this.barrels = barrels;
            this.bolts = bolts;
            this.fireModes = fireModes;
            this.magazines = magazines;
            this.scopes = scopes;
            this.stocks = stocks;
            this.modifiers = constructGunModifierArray();
        }

        public ArrayList<Attachment>  getAttachments()       { return attachments; }
        public ArrayList<Barrel>      getBarrels()           { return barrels;      }
        public ArrayList<Bolt>        getBolts()             { return bolts;        } 
        public ArrayList<FireMode>    getFireModes()         { return fireModes;    }
        public ArrayList<Magazine>    getMagazines()         { return magazines;    }
        public ArrayList<Scope>       getScopes()            { return scopes;       }
        public ArrayList<Stock>       getStocks()            { return stocks;       }
        public GunModifier[]          getModifiers()         { return modifiers;    }

        public int getCSCombinationCount()
        {
            return barrels.size() * bolts.size() * fireModes.size() * magazines.size() * scopes.size();
        }

        /**
         * Necessities every ModifierSet must have at least one of, otherwise can't create the Crackshot gun.
         * @return ModifierSet is valid to create a gun in Crackshot .yml
         */
        public boolean isValid()
        {
            return getCSCombinationCount() != 0;
        }

        /**
         * @return An array of all the modifiers within the set.
         */   
        private GunModifier[] constructGunModifierArray()
        {
            final ArrayList<GunModifier> mods = new ArrayList<>();
            mods.addAll(attachments);
            mods.addAll(barrels);
            mods.addAll(bolts);
            mods.addAll(fireModes);
            mods.addAll(magazines);
            mods.addAll(scopes);
            mods.addAll(stocks);

            return mods.toArray(new GunModifier[mods.size()]);
        }
    }
}
