/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.skeleton;

import java.util.ArrayList;
import csv.CSVInput;
import csv.CSVReader;
import csv.CSVValue;
import cs.guns.components.Attachments;
import cs.guns.components.Attachments.Attachment;
import cs.guns.components.Barrels.Barrel;
import cs.guns.components.Bolts.Bolt;
import cs.guns.components.FireModes.FireMode;
import cs.guns.components.Magazines.Magazine;
import cs.guns.components.ModifierSets;
import cs.guns.components.ModifierSets.ModifierSet;
import cs.guns.components.Sights.Scope;
import cs.guns.components.Stocks.Stock;
import cs.guns.components.GunModifier;
import cs.guns.components.Stocks;
import cs.guns.skeleton.SkeletonTypes.SkeletonType;
import cs.guns.skeleton.GunSkeletons.GunSkeleton;
import cs.guns.weps.Guns.CrackshotGun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 * Contains standard Crackshot guns with no modifiers (i.e. scopes, 
 * 
 */
public class GunSkeletons extends CSVInput<GunSkeleton>
{
    static private GunSkeletons singleton = null;
    
    static public GunSkeletons getInstance()
    {
        if (singleton == null)
            singleton = new GunSkeletons();
        return singleton;
    }
    
    static private final String SKELETONS_CSV_NAME = "GunSkeletons.csv";
    static private final String[] SKELETON_VALUES = {
        "Skeleton Name (STR)",
        "Weapon Type (STR)",
        "Modifier Set (STR)",
        "Material ID (INT)",
        "Material Data (INT)",
        "Initial Bullet Spread (DBL)",
        "Delay Between Shots (INT)",
        "Max Durability (INT)",
        "Damage (DBL)",
        "Reload Amount (INT)",
        "Reload Duration (INT)",
        "Reload Bullets Individually (T/F)",
        "Recoil Amount (INT)",
        
        // UPDATE
        "Running Speed Multiplier (DBL)", 
        "Sprinting Speed Multiplier (DBL)",
        "Crouching Bullet Spread Multiplier (DBL)",
        "Standing Bullet Spread Multiplier (DBL)",
        "Running Bullet Spread Multiplier (DBL)",
        "Sprinting Bullet Spread Multiplier (DBL)",
        // END-UPDATE
        
        "Shoot Sound (STR)",
        "Silenced Sound (STR)",
        "Particle Shoot (STR)",
        "Reload Sound (STR)",
    };
    
    public GunSkeletons()
    {
        super(SKELETONS_CSV_NAME, buildSkeletons(), SKELETON_VALUES);
    }
    
    static private GunSkeleton[] buildSkeletons()
    {
        final CSVReader csv = new CSVReader(SKELETONS_CSV_NAME, SKELETON_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final GunSkeleton[] toReturn       = new GunSkeleton[rowCount];
        final String[] skeletonNames        = csv.getColumnString(j++);
        final ArrayList<SkeletonType> wepTypes = SkeletonTypes.getInstance().get(csv.getColumnString(j++), false);
        final ArrayList<ModifierSet> modifierSets = ModifierSets.getInstance().get(csv.getColumnString(j++), false);
        final int[]    materialIDs          = csv.getColumnInt(j++);
        final int[]    materialData         = csv.getColumnInt(j++);
        final double[] initialBulletSpreads = csv.getColumnDouble(j++);
        final int[]    delayBetweenShots    = csv.getColumnInt(j++);
        final int[]    maxDurabilities      = csv.getColumnInt(j++);
        final double[] damages              = csv.getColumnDouble(j++);
        final int[]    reloadAmounts        = csv.getColumnInt(j++);
        final int[]    reloadDurations      = csv.getColumnInt(j++);
        final boolean[] reloadIndividually  = csv.getColumnBoolean(j++);
        final int[]    recoilAmounts        = csv.getColumnInt(j++);
        final double[]  runningSpeedMultiplier = csv.getColumnDouble(j++);
        final double[] sprintingSpeedMultiplier = csv.getColumnDouble(j++);
        final double[] crouchingBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] standingBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] runningBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] sprintingBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final String[] shootSounds          = csv.getColumnString(j++);
        final String[] silencedSounds       = csv.getColumnString(j++);
        final String[] reloadSounds         = csv.getColumnString(j++);
        final String[] particleShoots       = csv.getColumnString(j++);
        
        if (modifierSets.isEmpty() || wepTypes.isEmpty())
            return null;
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new GunSkeleton(
                    i,
                    skeletonNames[i],
                    wepTypes.get(i),
                    modifierSets.get(i),
                    materialIDs[i],
                    materialData[i],
                    initialBulletSpreads[i],
                    delayBetweenShots[i],
                    maxDurabilities[i],
                    damages[i],
                    reloadAmounts[i],
                    reloadDurations[i],
                    reloadIndividually[i],
                    recoilAmounts[i],
                    runningSpeedMultiplier[i],
                    sprintingSpeedMultiplier[i],
                    crouchingBulletSpreadMultiplier[i],
                    standingBulletSpreadMultiplier[i],
                    runningBulletSpreadMultiplier[i],
                    sprintingBulletSpreadMultiplier[i],
                    shootSounds[i],
                    silencedSounds[i],
                    particleShoots[i],
                    reloadSounds[i]);
        }
        
        return toReturn;
    }
    
    static public class GunSkeleton extends CSVValue
    {   
        private final SkeletonType weaponType;
        private final double bulletSpread;
        private final double damage;
        private final ModifierSet modSet;
        private final String particleShoot;
        private final boolean reloadBulletsIndividually;
        
        private final double runningSpeedMultiplier;
        private final double sprintingSpeedMultiplier;
        private final double crouchingBulletSpreadMultiplier;
        private final double standingBulletSpreadMultiplier;
        private final double runningBulletSpreadMultiplier;
        private final double sprintingBulletSpreadMultiplier;
        
        private final int
                itemID, 
                itemData, 
                shootDelay, 
                maxDurability,  
                recoilAmount, 
                reloadAmount, 
                reloadDuration;

        private final String
                soundShoot,
                soundSilenced,
                soundReload;

        private GunSkeleton(final int uniqueID,
                            final String skeletonName,
                            final SkeletonType weaponType,
                            final ModifierSet set,
                            final int materialID,
                            final int materialData,
                            final double initialBulletSpread,
                            final int delay_between_shots,
                            final int maxDurability,
                            final double damage,
                            final int reload_amount,
                            final int reload_duration,
                            final boolean reloadBulletsIndividually,
                            final int recoilAmount,
                            final double runningSpeedMultiplier,
                            final double sprintingSpeedMultiplier,
                            final double crouchingBulletSpreadMultiplier,
                            final double standingBulletSpreadMultiplier,
                            final double runningBulletSpreadMultiplier,
                            final double sprintingBulletSpreadMultiplier,
                            
                            final String sounds_shoot,
                            final String sounds_silenced,
                            final String particle_shoot,
                            final String sounds_reloading)
        {
            super(uniqueID, skeletonName);
            this.weaponType = weaponType;
            this.itemID = materialID;
            this.itemData = materialData;
            this.shootDelay = delay_between_shots;
            this.maxDurability = maxDurability;
            this.bulletSpread = initialBulletSpread;
            this.damage = damage;
            this.recoilAmount = recoilAmount;    
            this.soundShoot = sounds_shoot;
            this.soundSilenced = sounds_silenced;
            this.particleShoot = particle_shoot;
            this.reloadAmount = reload_amount;
            this.reloadDuration = reload_duration;
            this.soundReload = sounds_reloading;
            this.modSet = set;
            this.reloadBulletsIndividually = reloadBulletsIndividually;
            this.runningSpeedMultiplier = runningSpeedMultiplier;
            this.sprintingSpeedMultiplier = sprintingSpeedMultiplier;
            this.crouchingBulletSpreadMultiplier = crouchingBulletSpreadMultiplier;
            this.standingBulletSpreadMultiplier = standingBulletSpreadMultiplier;
            this.runningBulletSpreadMultiplier = runningBulletSpreadMultiplier;
            this.sprintingBulletSpreadMultiplier = sprintingBulletSpreadMultiplier;
        }
        
        public GunSkeleton(final GunSkeleton skele)
        {
            super(skele.getIndex(), skele.getName());
            this.weaponType = skele.weaponType;
            this.itemID = skele.itemData;
            this.itemData = skele.itemData;
            this.shootDelay = skele.shootDelay;
            this.maxDurability = skele.maxDurability;
            this.bulletSpread = skele.bulletSpread;
            this.damage = skele.damage;
            this.recoilAmount = skele.recoilAmount;    
            this.soundShoot = skele.soundShoot;
            this.soundSilenced = skele.soundSilenced;
            this.particleShoot = skele.particleShoot;
            this.reloadAmount = skele.reloadAmount;
            this.reloadDuration = skele.reloadDuration;
            this.soundReload = skele.soundReload;
            this.modSet = skele.modSet;
            this.reloadBulletsIndividually = skele.reloadBulletsIndividually;
            this.runningSpeedMultiplier = skele.runningSpeedMultiplier;
            this.sprintingSpeedMultiplier = skele.sprintingSpeedMultiplier;
            this.crouchingBulletSpreadMultiplier = skele.crouchingBulletSpreadMultiplier;
            this.standingBulletSpreadMultiplier = skele.standingBulletSpreadMultiplier;
            this.runningBulletSpreadMultiplier = skele.runningBulletSpreadMultiplier;
            this.sprintingBulletSpreadMultiplier = skele.sprintingBulletSpreadMultiplier;
        }

        public String        getFileName()       { return super.getName().toLowerCase(); }
        public SkeletonType  getWeaponType()     { return weaponType;     }
        public double        getSkeletonBulletSpread()   { return bulletSpread;   }
        public int           getItemID()         { return itemID;         }
        public int           getItemData()       { return itemData;       }
        public Material      getMaterial()       { return Material.getMaterial(itemID); }
        public MaterialData  getMaterialData()   { return new MaterialData(itemID, (byte)itemData); }
        public int           getShootDelay()     { return shootDelay;     }
        public int           getSkeletonMaxDurability()  { return maxDurability;  }
        public double        getSkeletonBaseDamage() { return damage;         }
        public int           getRecoil()         { return recoilAmount;   }
        public String        getShootSound()     { return soundShoot;    }
        public String        getSilencedSound()  { return soundSilenced; }
        public String        getShootParticle()  { return particleShoot; }
        public int           getSkeletonReloadAmount()   { return reloadAmount;   }
        public int           getSkeletonReloadDuration() { return reloadDuration; }
        public double        getSkeletonRunningSpeedMultiplier()          { return runningSpeedMultiplier;  }
        public double        getSkeletonSprintingSpeedMultiplier()        { return sprintingSpeedMultiplier;  }
        public double        getSkeletonCrouchingBulletSpreadMultiplier() { return crouchingBulletSpreadMultiplier;  }
        public double        getSkeletonStandingBulletSpreadMultiplier()  { return standingBulletSpreadMultiplier;  }
        public double        getSkeletonRunningBulletSpreadMultiplier()   { return runningBulletSpreadMultiplier;  }
        public double        getSkeletonSprintingBulletSpreadMultiplier() { return sprintingBulletSpreadMultiplier;  }
        public String        getReloadSound()    { return soundReload;   }
        public ItemStack     getBareItemStack()  { return new ItemStack(itemID, 1, (short)itemData); }
        public ModifierSet   getModifierSet()    { return modSet; }
        public boolean       reloadsBulletsIndividually() { return reloadBulletsIndividually; }
        public GunModifier[] getModifiers()      { return modSet.getModifiers(); }

        /**
         * Builds the set of guns that contain no Attatchments or Stock. This
         * method is to be used for generating the YAML for Crackshot guns.
         * @return 
         */
        public CrackshotGun[] getGunBaseSet()
        {
            final int combinationCount = modSet.getCSCombinationCount();
            int i = 0;
            
            if (combinationCount <= 0)
                return null;

            CrackshotGun guns[] = new CrackshotGun[combinationCount];
            final Attachment nullAtt = Attachments.getInstance().getNullValue();
            final Stock nullStock = Stocks.getInstance().getNullValue();

            for (Barrel barrel : modSet.getBarrels())
            {
                for (Bolt bolt : modSet.getBolts())
                {
                    for (FireMode fireMode : modSet.getFireModes())
                    {
                        for (Magazine magazine : modSet.getMagazines())
                        {
                            for (Scope scope : modSet.getScopes())
                            {
                                guns[i++] = new CrackshotGun(
                                        this,
                                        nullAtt,
                                        nullAtt, 
                                        nullAtt,
                                        barrel,
                                        bolt,
                                        fireMode,
                                        magazine,
                                        scope,
                                        nullStock);
                            }
                        }
                    }
                }
            }
            return guns;
        }

        private boolean containsMod(final GunModifier modifier)
        {
            boolean containsMod = false;
            for (GunModifier mod : getModifiers())
            {
                if (mod.equals(modifier))
                    containsMod = true;
            }

            return containsMod;
        }
    }
}
