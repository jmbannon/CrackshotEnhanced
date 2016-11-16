/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.weps;

import net.projectzombie.crackshotenhanced.cs.guns.components.Sights;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.DurabilitySet;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.BleedoutSet;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.ShrapnelDamageSet;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.FireDamageSet;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.HeadshotDamageSet;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.CritSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.MagazineSet;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.BaseDamageSet;
import net.projectzombie.crackshotenhanced.cs.guns.qualities.Condition;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import net.projectzombie.crackshotenhanced.cs.guns.components.Attachments.Attachment;
import net.projectzombie.crackshotenhanced.cs.guns.components.Barrels.Barrel;
import net.projectzombie.crackshotenhanced.cs.guns.components.Bolts.Bolt;
import net.projectzombie.crackshotenhanced.cs.guns.components.FireModes.FireMode;
import net.projectzombie.crackshotenhanced.cs.guns.components.Stocks.Stock;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.components.Magazines.Magazine;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.STAT_TYPE_SEPERATOR;
import net.projectzombie.crackshotenhanced.cs.guns.skeleton.GunSkeletons.GunSkeleton;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.BulletSpreadSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifierType;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.STAT_SEPERATOR;
import static net.projectzombie.crackshotenhanced.cs.guns.components.ModifierLoreBuilder.toTitle;
import net.projectzombie.crackshotenhanced.cs.guns.modifiers.StunSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.BoltSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.MotionSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.ProjectileSet;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.SightSet;
import net.projectzombie.crackshotenhanced.cs.guns.physical.components.GunModifierItemStack;
import net.projectzombie.crackshotenhanced.cs.guns.qualities.Build;

import java.util.ArrayList;


/**
 *
 * @author jbannon
 */
public class Guns
{
    private static HashMap<String, CrackshotGun> guns = new HashMap<>();
    
    private Guns() { /* Do nothing. */ }
    
    static
    public int initialize()
    {
        return guns.size();
    }
    
    static
    public CrackshotGun get(final GunID gunID)
    {
        if (gunID.isValid())
        {
            if (guns.containsKey(gunID.getUniqueID()))
            {
                return guns.get(gunID.getUniqueID());
            }
            else
            {
                final CrackshotGun gun = new CrackshotGun(gunID);
                guns.put(gunID.getUniqueID(), gun);
                System.out.println("Total guns in hash map: " + guns.size());
                return gun;
            }
        }
        else
        {
            return null;
        }
    }
    
    static
    public CrackshotGun get(final String gunID)
    {
        return get(new GunID(gunID));
    }
    
//    /**
//     * TODO Check itemstack when retrieving id.
//     * @param item
//     * @return 
//     */
//    static
//    public CrackshotGun get(final ItemStack item)
//    {
//        //return get(new CrackshotGunLore(item.getItemMeta().getLore()).getGunID());
//    }
    
    static
    public Collection<CrackshotGun> getGuns()
    {
        return guns.values();
    }

    static public class CrackshotGun extends GunSkeleton
    {   
        private static final Random RAND = new Random();
        
        private final String uniqueID;
        private final String csUniqueID;
        private final GunID  gunID;
        
        private final Attachment[] attatchments;
        private final Barrel barrel;
        private final Bolt bolt;
        private final FireMode firemode;
        private final Magazine magazine;
        private final Sights.Sight sight;
        private final Stock stock;
        
        private final BulletSpreadSet bulletSpread;
        private final BaseDamageSet baseDamage;
        private final HeadshotDamageSet headshot;
        private final BleedoutSet bleedout;
        private final ShrapnelDamageSet shrapnel;
        private final FireDamageSet fireDamage;
        private final CritSet crit;
        private final MagazineSet mag;
        private final BoltSet boltSet;
        private final ProjectileSet projectile;
        private final StunSet stun;
        private final DurabilitySet durability;
        private final MotionSet motion;
        private final SightSet scopeSet;
        

        public CrackshotGun(final GunSkeleton skeleton,
                             final Attachment attachmentOne,
                             final Attachment attachmentTwo,
                             final Attachment attachmentThree,
                             final Barrel barrel,
                             final Bolt bolt,
                             final FireMode firemodeType,
                             final Magazine magazine,
                             final Sights.Sight sightType,
                             final Stock stock)
        {
            super(skeleton);
            this.gunID = new GunID(skeleton, attachmentOne, attachmentTwo, attachmentThree, barrel, bolt, firemodeType, magazine, sightType, stock);
            
            final GunModifier[] modifiers = new GunModifier[]
            { 
                attachmentOne,
                attachmentTwo,
                attachmentThree,
                barrel,
                bolt,
                firemodeType,
                magazine,
                    sightType,
                stock
            };
            
            this.attatchments = new Attachment[]
            {
                attachmentOne,
                attachmentTwo,
                attachmentThree
            };
            this.barrel = barrel;
            this.bolt = bolt;
            this.firemode = firemodeType;
            this.magazine = magazine;
            this.sight = sightType;
            this.stock = stock;
            
            this.uniqueID = gunID.getUniqueID();
            this.csUniqueID = skeleton.getFileName() + "_" + gunID.getCSUniqueID();
            
            this.bulletSpread = new BulletSpreadSet(modifiers, skeleton.getSkeletonBulletSpread());
            this.baseDamage = new BaseDamageSet(modifiers, skeleton.getSkeletonBaseDamage());
            this.headshot = new HeadshotDamageSet(modifiers);
            this.shrapnel = new ShrapnelDamageSet(modifiers);
            this.fireDamage = new FireDamageSet(modifiers);
            this.bleedout = new BleedoutSet(modifiers, this.shrapnel.getTotal());
            this.crit = new CritSet(modifiers, this.baseDamage.getTotal());
            this.mag = new MagazineSet(modifiers, super.getSkeletonReloadAmount(), super.getSkeletonReloadDuration());
            this.boltSet = new BoltSet(modifiers, super.getWeaponType().getAction().getActionDurationInTicks());
            this.projectile = new ProjectileSet(modifiers, 
                    skeleton.getWeaponType().getProjectileRange(), 
                    skeleton.getWeaponType().getProjectileSpeed(), 
                    skeleton.getWeaponType().getProjectileAmount());
            this.stun = new StunSet(modifiers);
            this.durability = new DurabilitySet(modifiers, super.getSkeletonMaxDurability());
            this.motion = new MotionSet(modifiers,
                    super.getSkeletonRunningSpeedMultiplier(),
                    super.getSkeletonSprintingSpeedMultiplier(),
                    super.getSkeletonCrouchingBulletSpreadMultiplier(),
                    super.getSkeletonStandingBulletSpreadMultiplier(),
                    super.getSkeletonRunningBulletSpreadMultiplier(),
                    super.getSkeletonSprintingBulletSpreadMultiplier());
            this.scopeSet = new SightSet(modifiers);
        }
        
        public CrackshotGun(final CrackshotGun gun)
        {
            this(gun,
                 gun.getAttachmentOneMod(),
                 gun.getAttachmentTwoMod(),
                 gun.getAttachmentThreeMod(),
                 gun.getBarrelMod(),
                 gun.getBoltMod(),
                 gun.getFireModeMod(),
                 gun.getMagazineMod(),
                 gun.getScopeMod(),
                 gun.getStockMod());
        }
        
        private CrackshotGun(final GunID id)
        {
            this(id.getSkeleton(),
                 id.getAttatchmentOne(),
                 id.getAttatchmentTwo(),
                 id.getAttatchmentThree(),
                 id.getBarrel(),
                 id.getBolt(),
                 id.getFireMode(),
                 id.getMagazine(),
                 id.getScope(),
                 id.getStock());
        }

        public GunID        getGunID()               { return gunID;           }
        public Attachment   getAttachmentOneMod()    { return attatchments[0]; }
        public Attachment   getAttachmentTwoMod()    { return attatchments[1]; }
        public Attachment   getAttachmentThreeMod()  { return attatchments[2]; }
        public Bolt         getBoltMod()             { return bolt;            }
        public Barrel       getBarrelMod()           { return barrel;          }
        public FireMode     getFireModeMod()         { return firemode;        }
        public Magazine     getMagazineMod()         { return magazine;        }
        public Sights.Sight getScopeMod()            { return sight;           }
        public Stock        getStockMod()            { return stock;           }
        public String       getCSWeaponName()        { return csUniqueID;      }
        public String       getUniqueID()            { return uniqueID;        }
        
        public BulletSpreadSet getBulletSpread()      { return bulletSpread;  }
        public BaseDamageSet getBaseDamage()          { return baseDamage;    }
        public HeadshotDamageSet getHeadshotDamage()  { return headshot;      }
        public BleedoutSet getBleedout()              { return bleedout;      }
        public CritSet getCritical()                  { return crit;          }
        public ShrapnelDamageSet getShrapnel()        { return shrapnel;      }
        public FireDamageSet getFireDamage()          { return fireDamage;    }
        public MagazineSet getGunMagazine()           { return mag;           }
        public BoltSet getGunBolt()                   { return boltSet;       }
        public ProjectileSet getGunProjectile()       { return projectile;    }
        public StunSet getGunStun()                   { return stun;          }
        public MotionSet getMotionSet()               { return motion;        }
        public DurabilitySet getDurabilitySet()       { return durability;    }
        public GunModifierSet[] getSets()
        {
            return new GunModifierSet[]
            {
                bulletSpread,
                baseDamage,
                headshot,
                bleedout,
                crit,
                shrapnel,
                fireDamage,
                mag,
                boltSet,
                projectile,
                stun,
                motion,
                durability,
                scopeSet
            };
        }
        
        public HashMap<String, GunModifierSet> getHashedSets()
        {
            final HashMap<String, GunModifierSet> hash = new HashMap<>();
            for (GunModifierSet modSet : getSets())
            {
                hash.put(modSet.getName(), modSet);
            }
            return hash;
        }
        
        @Override public String toString()        { return uniqueID;  }
        
        public double getDamageOnEntityHit(final boolean headshot)
        {
            return 0;
        }
        
        public double getDPS()
        {
            return 0;
        }
        
        public double getZoomedBulletSpread()
        {
            return 0;
        }
        
        public double getRunningBulletSpread()
        {
            return 0;
        }

        public int getInitialDurability()
        {
            final int maxDurability = this.getSkeletonMaxDurability();
            return maxDurability - RAND.nextInt(maxDurability/2);
        }

        /**
         * Returns the integer of the gun condition.
         * @param durability Current durability of the gun.
         * @return Gun tier integer.
         */
        public Condition getCondition(final int durability)
        {
            return Condition.getCondition(durability, this.durability.getMaxDurability());
        }
        
        public CrackshotGun getModifiedGun(final GunModifierItemStack item)
        {
            if (item.isValid())
                return getModifiedGun(item.getGunModifier(), item.getGunModifierType());
            else
                return null;
        }

        public CrackshotGun getModifiedGun(final GunModifier modifier,
                                           final GunModifierType type)
        {
            return Guns.get(new GunID(this, modifier, type));
        }
        
        // TODO
        public double getEventBulletSpread(final double eventBulletSpread,
                                           final int durability,
                                           final Build build)
        {
            return eventBulletSpread;
        }
        
        public ArrayList<String> getAllStatInfo()
        {
            final ArrayList<String> stats = new ArrayList<>();
            final GunModifierSet modSets[] = getSets();
            
            stats.add(toTitle(super.getName() + " Stats"));
            for (GunModifierSet modSet : modSets)
            {
                if (modSet.hasStats())
                {
                    stats.add(STAT_TYPE_SEPERATOR);
                    stats.add(toTitle(modSet.getName()));
                    stats.addAll(modSet.getStats());
                }
            }
            return stats;
        }
        
        /**
         * Returns stats of specified modifier names.
         * Combines all args, removes spaces, replaces all characters other than a-z with ,
         * @param cmdArgs String array args from a player command.
         * @return 
         */
        public ArrayList<String> getSpecificStats(final String[] cmdArgs)
        {
            final StringBuilder stb = new StringBuilder();
            for (String s : cmdArgs) stb.append(s);
            
            final String args = stb.toString();
            final ArrayList<String> stats = new ArrayList<>();
            final ArrayList<String> unrecognizedNames = new ArrayList();
            
            final HashMap<String, GunModifierSet> hash = getHashedSets();
            final String[] modSetNames = args.toLowerCase().replace(" ", "").replaceAll("[^a-z]", ",").split(",");
            GunModifierSet tmp;
            
            for (String modSetName : modSetNames)
            {
                if (hash.containsKey(modSetName))
                {
                    tmp = hash.get(modSetName);
                    stats.add(STAT_TYPE_SEPERATOR);
                    stats.add(toTitle(tmp.getName()));
                    stats.addAll(tmp.getStats());
                }
                else
                {
                    unrecognizedNames.add(modSetName);
                }
            }
            
            if (!unrecognizedNames.isEmpty())
            {
                stats.add(STAT_SEPERATOR);
                stats.add("Could not recognize the following stats:");
                for (String unrec : unrecognizedNames)
                {
                    stats.add(unrec);
                }
            }
                
            return stats;
        }
        
        public ArrayList<String> getEntireStatList()
        {
            final ArrayList<String> statList = new ArrayList<>();
            
            statList.add(toTitle("Full Stat List"));
            for (GunModifierSet modSet : getSets())
            {
                statList.add(toTitle(modSet.getName()));
            }
            statList.add("/gun stats <name>,<name>,...,<name> for ordered list of stats");
            return statList;
        }
        
        public ArrayList<String> getUsefulStatList()
        {
            final ArrayList<String> statList = new ArrayList<>();
            
            statList.add(toTitle("Stat List"));
            for (GunModifierSet modSet : getSets())
            {
                if (modSet.hasStats())
                {
                    statList.add(toTitle(modSet.getName()));
                }
            }
            statList.add("/gun stats <name>,<name>,...,<name> for ordered list of stats");
            return statList;
        }
    }
}
