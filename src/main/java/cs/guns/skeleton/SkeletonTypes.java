/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.skeleton;

import java.util.ArrayList;
import csv.CSVReader;
import csv.CSVInput;
import csv.CSVValue;
import cs.guns.skeleton.FirearmActions.FirearmAction;
import cs.guns.skeleton.SkeletonTypes.SkeletonType;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * 
 */
public class SkeletonTypes extends CSVInput<SkeletonType>
{  
    static private SkeletonTypes singleton = null;
    static public SkeletonTypes getInstance()
    {
        if (singleton == null)
            singleton = new SkeletonTypes();
        return singleton;
    }
    
    static private final String WEAPON_TYPE_CSV_NAME = "WeaponTypes.csv";
    static private final String[] WEAPON_TYPE_VALUES = {
        "Display Name (STR)",
        "Firearm Action Name (STR)",
        "Ammo Name (STR)",
        "Ammo ID (INT)",
        "Ammo Data (INT)",
        "Projectile Amount (INT)",
        "Projectile Speed (INT)",
        "Projectile Range (INT)",
        "Inventory Control (STR)"
    }; 
    
    private SkeletonTypes()
    {
        super(WEAPON_TYPE_CSV_NAME, buildWeaponTypes(), WEAPON_TYPE_VALUES);
    }


    static private SkeletonType[] buildWeaponTypes()
    {
        final CSVReader csv = new CSVReader(WEAPON_TYPE_CSV_NAME, WEAPON_TYPE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final SkeletonType[] toReturn     = new SkeletonType[rowCount];
        final String[] displayNames       = csv.getColumnString(j++);
        final ArrayList<FirearmAction> firearmActions = FirearmActions.getInstance().get(csv.getColumnString(j++), false);
        final String[] ammoNames          = csv.getColumnString(j++);
        final int[]    ammoIDs            = csv.getColumnInt(j++);
        final int[]    ammoDatas          = csv.getColumnInt(j++);
        final int[]    projectileAmounts  = csv.getColumnInt(j++);
        final int[]    projectileSpeeds   = csv.getColumnInt(j++);
        final int[]    projectileRanges  = csv.getColumnInt(j++);
        final String[] inventoryControls  = csv.getColumnString(j++);
        
        if (firearmActions == null || firearmActions.size() != ammoNames.length)
            return null;

        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new SkeletonType(
                    i,
                    displayNames[i],
                    firearmActions.get(i),
                    ammoNames[i],
                    ammoIDs[i],
                    ammoDatas[i],
                    projectileAmounts[i],
                    projectileSpeeds[i],
                    projectileRanges[i],
                    inventoryControls[i]);
        }
        return toReturn;
    }

    static public class SkeletonType extends CSVValue
    {
        private static final double DRAG_DELAY_SCALAR = 10.3;
        
        private final FirearmAction firearmAction;
        private final String ammoName;
        private final int ammoID;
        private final int ammoData;
        private final int projectileAmount;
        private final int projectileSpeed;
        private final int projectileRange;
        private final String inventoryControl;
        private SkeletonType(final int index,
                        final String displayName,
                        final FirearmAction firearmAction,
                        final String ammoName,
                        final int ammoID,
                        final int ammoData,
                        final int projectileAmount,
                        final int projectileSpeed,
                        final int projectileRange,
                        final String inventoryControl)
        {
            super(index, displayName);
            this.firearmAction = firearmAction;
            this.ammoName = ammoName;
            this.ammoID = ammoID;
            this.ammoData = ammoData;
            this.projectileAmount = projectileAmount;
            this.projectileSpeed = projectileSpeed;
            this.projectileRange = projectileRange;
            this.inventoryControl = inventoryControl;
        }

        public FirearmAction getAction()             { return firearmAction;      }
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