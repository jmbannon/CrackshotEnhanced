/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.skeleton;

import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.SkeletonTypes.SkeletonType;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * 
 */
public class SkeletonTypes extends ModifierConfig<SkeletonType>
{  
    static private SkeletonTypes singleton = null;
    static public SkeletonTypes getInstance()
    {
        if (singleton == null) {
            singleton = new SkeletonTypes();
            singleton.postInitialize();
        }
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Projectile Amount", 1);
        defaultValues.put("Projectile Speed", 100);
        defaultValues.put("Projectile Range", 100);
        defaultValues.put("Inventory Control", null);
        defaultValues.put("Firearm Action Name", null);
        return defaultValues;
    }

    static private final String YML_NAME = "WeaponTypes.yml";
    static private final String MODULE_NAME = "WeaponTypes";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Ammo Name", "Ammo ID", "Ammo Data"};
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private SkeletonTypes() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    @Override public SkeletonType getNullValue() { return null; }

    public SkeletonType buildModule(final String key, final int uniqueID, final ModifierMap values) {
        try {
            return new SkeletonType(
                    key,
                    uniqueID,
                    values.getString("Display Name"),
                    FirearmActions.getInstance().get(values.getString("Firearm Action Name")),
                    values.getString("Ammo Name"),
                    values.getInt("Ammo ID"),
                    values.getInt("Ammo Data"),
                    values.getInt("Projectile Amount"),
                    values.getInt("Projectile Speed"),
                    values.getInt("Projectile Range"),
                    values.getString("Inventory Control")
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add WeaponType " + values.getString("Display Name") + ": " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    static public class SkeletonType extends ModifierValue
    {
        private static final double DRAG_DELAY_SCALAR = 10.3;
        
        private final FirearmActions.FirearmAction firearmAction;
        private final String ammoName;
        private final int ammoID;
        private final int ammoData;
        private final int projectileAmount;
        private final int projectileSpeed;
        private final int projectileRange;
        private final String inventoryControl;
        private SkeletonType(final String key,
                             final int index,
                        final String displayName,
                        final FirearmActions.FirearmAction firearmAction,
                        final String ammoName,
                        final int ammoID,
                        final int ammoData,
                        final int projectileAmount,
                        final int projectileSpeed,
                        final int projectileRange,
                        final String inventoryControl)
        {
            super(key, index, displayName);
            this.firearmAction = firearmAction;
            this.ammoName = ammoName;
            this.ammoID = ammoID;
            this.ammoData = ammoData;
            this.projectileAmount = projectileAmount;
            this.projectileSpeed = projectileSpeed;
            this.projectileRange = projectileRange;
            this.inventoryControl = inventoryControl;
        }

        public FirearmActions.FirearmAction getAction()             { return firearmAction;      }
        public String        getAmmoName()           { return ammoName;           }
        public int           getAmmoID()             { return ammoID;             }
        public int           getAmmoData()           { return ammoData;           }
        public int           getProjectileAmount()   { return projectileAmount;   }
        public int           getProjectileSpeed()    { return projectileSpeed;    }
        public int           getProjectileRange()    { return projectileRange;    }
        public String        getInventoryControl()   { return inventoryControl;   }
        @Override public String toString()           { return ammoName;           }

        /**
         * Calculates how many ticks the projectile should live based on 
         * range and ticks. The formula is (10.3 * range) / speed = ticks
         * @return Drag delay needed for Crackshot YAML.
         */
        public String getRemovalDragDelay() 
        {
            int ticksToLive = (int)Math.ceil(DRAG_DELAY_SCALAR * (double)projectileRange / (double)projectileSpeed);
            if (ticksToLive < 1)
                ticksToLive = 1;
            
            return String.valueOf(ticksToLive) + "-true";
        }
    }
}