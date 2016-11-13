/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components;

import java.util.ArrayList;
import java.util.HashMap;

import net.projectzombie.crackshotenhanced.cs.guns.components.Magazines.Magazine;
import net.projectzombie.crackshotenhanced.cs.guns.components.ModifierSets.ModifierSet;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;

/**
 *
 * @author jesse
 */
public class ModifierSets extends ModifierConfig<ModifierSet>
{
    static private ModifierSets singleton = null;
    static public ModifierSets getInstance()
    {
        if (singleton == null)
            singleton = new ModifierSets();
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap();
        defaultValues.put("Attachments", ProjectileAttachments.getInstance().getNullValue().getName());
        defaultValues.put("Barrels", Barrels.getInstance().getNullValue().getName());
        defaultValues.put("Bolts", Bolts.getInstance().getNullValue().getName());
        defaultValues.put("FireModes", FireModes.getInstance().getNullValue().getName());
        defaultValues.put("Magazines", Magazines.getInstance().getNullValue().getName());
        defaultValues.put("Sights", Sights.getInstance().getNullValue().getName());
        defaultValues.put("Stocks", Stocks.getInstance().getNullValue().getName());
        return defaultValues;
    }

    static private final String YML_NAME = "ModifierSets.yml";
    static private final String MODULE_NAME = "ModifierSets";
    static private final String[] NECESSARY_VALUES = new String[] { "Set Name" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private ModifierSets() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public ModifierSet buildModule(final int uniqueID, final ModifierMap values) {
        try {
            return new ModifierSet(
                    uniqueID,
                    values.getString("Set Name"),
                    ProjectileAttachments.getInstance().get(values.getStringList("Attachments"), true),
                    Barrels.getInstance().get(values.getStringList("Barrels"), true),
                    Bolts.getInstance().get(values.getStringList("Bolts"), true),
                    FireModes.getInstance().get(values.getStringList("FireModes"), false),
                    Magazines.getInstance().get(values.getStringList("FireModes"), false),
                    Sights.getInstance().get(values.getStringList("FireModes"), false),
                    Stocks.getInstance().get(values.getStringList("FireModes"), false)
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add bolt " + values.getString("Display Name"));
            return null;
        }
    }
    
    static public class ModifierSet extends ModifierValue
    {
        private final ArrayList<ProjectileAttachments.ProjectileAttachment> attachments;
        private final ArrayList<Barrels.Barrel> barrels;
        private final ArrayList<Bolts.Bolt> bolts;
        private final ArrayList<FireModes.FireMode> fireModes;
        private final ArrayList<Magazine> magazines;
        private final ArrayList<Sights.Sight> sights;
        private final ArrayList<Stocks.Stock> stocks;
        private final GunModifier[] modifiers;

        private ModifierSet(final int index,
                            final String name,
                            final ArrayList<ProjectileAttachments.ProjectileAttachment> attachments,
                            final ArrayList<Barrels.Barrel> barrels,
                            final ArrayList<Bolts.Bolt> bolts,
                            final ArrayList<FireModes.FireMode> fireModes,
                            final ArrayList<Magazine> magazines,
                            final ArrayList<Sights.Sight> sights,
                            final ArrayList<Stocks.Stock> stocks)
        {
            super(index, name);
            this.attachments = attachments;
            this.barrels = barrels;
            this.bolts = bolts;
            this.fireModes = fireModes;
            this.magazines = magazines;
            this.sights = sights;
            this.stocks = stocks;
            this.modifiers = constructGunModifierArray();
        }

        public ArrayList<ProjectileAttachments.ProjectileAttachment>  getAttachments()       { return attachments; }
        public ArrayList<Barrels.Barrel>      getBarrels()           { return barrels;      }
        public ArrayList<Bolts.Bolt>        getBolts()             { return bolts;        }
        public ArrayList<FireModes.FireMode>    getFireModes()         { return fireModes;    }
        public ArrayList<Magazine>    getMagazines()         { return magazines;    }
        public ArrayList<Sights.Sight> getSights()            { return sights;       }
        public ArrayList<Stocks.Stock>       getStocks()            { return stocks;       }
        public GunModifier[]          getModifiers()         { return modifiers;    }

        public int getCSCombinationCount()
        {
            return barrels.size() * bolts.size() * fireModes.size() * magazines.size() * sights.size();
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
            mods.addAll(sights);
            mods.addAll(stocks);

            return mods.toArray(new GunModifier[mods.size()]);
        }
    }
}
