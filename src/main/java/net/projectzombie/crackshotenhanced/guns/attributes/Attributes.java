package net.projectzombie.crackshotenhanced.guns.attributes;

import net.projectzombie.crackshotenhanced.guns.attributes.modifier.*;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.*;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;

import java.util.ArrayList;

import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.STAT_TYPE_SEPERATOR;
import static net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder.toTitle;

/**
 * Created by jb on 11/25/16.
 */
public class Attributes {

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
    private final SightSet sightSet;

    public Attributes(final GunSkeletons.GunSkeleton skeleton,
                      final GunModifier[] modifiers) {
        this.bulletSpread = new BulletSpreadSet(modifiers, skeleton.getSkeletonBulletSpread());
        this.baseDamage = new BaseDamageSet(modifiers, skeleton.getSkeletonBaseDamage());
        this.headshot = new HeadshotDamageSet(modifiers);
        this.shrapnel = new ShrapnelDamageSet(modifiers);
        this.fireDamage = new FireDamageSet(modifiers);
        this.bleedout = new BleedoutSet(modifiers, this.shrapnel.getTotal());
        this.crit = new CritSet(modifiers, this.baseDamage.getTotal());
        this.mag = new MagazineSet(modifiers, skeleton.getSkeletonReloadAmount(), skeleton.getSkeletonReloadDuration());
        this.boltSet = new BoltSet(modifiers, skeleton.getWeaponType().getAction().getActionDurationInTicks());
        this.projectile = new ProjectileSet(modifiers,
                skeleton.getWeaponType().getProjectileRange(),
                skeleton.getWeaponType().getProjectileSpeed(),
                skeleton.getWeaponType().getProjectileAmount());
        this.stun = new StunSet(modifiers);
        this.durability = new DurabilitySet(modifiers, skeleton.getSkeletonMaxDurability());
        this.motion = new MotionSet(modifiers,
                skeleton.getSkeletonRunningSpeedMultiplier(),
                skeleton.getSkeletonCrouchingBulletSpreadMultiplier(),
                skeleton.getSkeletonStandingBulletSpreadMultiplier(),
                skeleton.getSkeletonRunningBulletSpreadMultiplier());
        this.sightSet = new SightSet(modifiers);
    }

    public Attributes(final GunModifier modifiers) {
        this.bulletSpread = new BulletSpreadSet(modifiers);
        this.baseDamage = new BaseDamageSet(modifiers);
        this.headshot = new HeadshotDamageSet(modifiers);
        this.shrapnel = new ShrapnelDamageSet(modifiers);
        this.fireDamage = new FireDamageSet(modifiers);
        this.bleedout = new BleedoutSet(modifiers);
        this.crit = new CritSet(modifiers);
        this.mag = new MagazineSet(modifiers);
        this.boltSet = new BoltSet(modifiers);
        this.projectile = new ProjectileSet(modifiers);
        this.stun = new StunSet(modifiers);
        this.durability = new DurabilitySet(modifiers);
        this.motion = new MotionSet(modifiers);
        this.sightSet = new SightSet(modifiers);
    }

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
    public SightSet getSightSet()                 { return sightSet;      }

    public AttributeSet[] getAll() {
        return new AttributeSet[] {bulletSpread, baseDamage, headshot, bleedout, crit, shrapnel, fireDamage, mag,
                boltSet, projectile, stun, motion, durability, sightSet
        };
    }

    public ArrayList<String> getAllStatInfo(final String statDisplayName)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final AttributeSet modSets[] = getAll();
        ArrayList<String> tempStats;

        stats.add(toTitle(statDisplayName + " Stats"));
        for (AttributeSet modSet : modSets)
        {
            tempStats = modSet.getGunStats();
            if (modSet.hasStats() && !tempStats.isEmpty())
            {
                stats.addAll(tempStats);
            }
        }
        return stats;
    }

    public ArrayList<String> getIndividualStatInfo(final String statDisplayName)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final AttributeSet modSets[] = getAll();
        ArrayList<String> tempStats;

        stats.add(toTitle(statDisplayName + " Stats"));
        for (AttributeSet modSet : modSets)
        {
            tempStats = modSet.getIndividualStats();
            if (modSet.hasStats() && !tempStats.isEmpty())
            {
                stats.add(STAT_TYPE_SEPERATOR);
                stats.add(toTitle(modSet.getName()));
                stats.addAll(tempStats);
            }
        }
        return stats;
    }
}
