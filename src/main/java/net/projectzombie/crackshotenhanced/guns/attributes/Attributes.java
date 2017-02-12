package net.projectzombie.crackshotenhanced.guns.attributes;

import net.projectzombie.crackshotenhanced.guns.attributes.modifier.*;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.*;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;

import java.util.ArrayList;

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
    private final IncendiaryDamageSet incendiaryDamage;
    private final IgniteSet igniteSet;
    private final CritSet crit;
    private final MagazineSet mag;
    private final BoltSet boltSet;
    private final ProjectileSet projectile;
    private final StunSet stun;
    private final DurabilitySet durability;
    private final MotionSet motion;
    private final SightSet sightSet;
    private final FireModeSet fireModeSet;
    private final SilencerSet silencerSet;
    private final KnockbackSet knockbackSet;

    public Attributes(final GunSkeletons.GunSkeleton skeleton,
                      final GunModifier[] modifiers) {
        this.bulletSpread = new BulletSpreadSet(modifiers, skeleton.getSkeletonBulletSpread());
        this.baseDamage = new BaseDamageSet(modifiers, skeleton.getSkeletonBaseDamage());
        this.headshot = new HeadshotDamageSet(modifiers);
        this.shrapnel = new ShrapnelDamageSet(modifiers);
        this.incendiaryDamage = new IncendiaryDamageSet(modifiers);
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
        this.motion = new MotionSet(modifiers);
        this.sightSet = new SightSet(modifiers);
        this.fireModeSet = new FireModeSet(modifiers);
        this.silencerSet = new SilencerSet(modifiers);
        this.igniteSet = new IgniteSet(modifiers, incendiaryDamage.getTotal());
        this.knockbackSet = new KnockbackSet(modifiers);
    }

    public Attributes(final GunModifier modifier) {
        this.bulletSpread = new BulletSpreadSet(modifier);
        this.baseDamage = new BaseDamageSet(modifier);
        this.headshot = new HeadshotDamageSet(modifier);
        this.shrapnel = new ShrapnelDamageSet(modifier);
        this.incendiaryDamage = new IncendiaryDamageSet(modifier);
        this.bleedout = new BleedoutSet(modifier);
        this.crit = new CritSet(modifier);
        this.mag = new MagazineSet(modifier);
        this.boltSet = new BoltSet(modifier);
        this.projectile = new ProjectileSet(modifier);
        this.stun = new StunSet(modifier);
        this.durability = new DurabilitySet(modifier);
        this.motion = new MotionSet(modifier);
        this.sightSet = new SightSet(modifier);
        this.fireModeSet = new FireModeSet(modifier);
        this.silencerSet = new SilencerSet(modifier);
        this.igniteSet = new IgniteSet(modifier);
        this.knockbackSet = new KnockbackSet(modifier);
    }

    public BulletSpreadSet getBulletSpread()      { return bulletSpread;  }
    public BaseDamageSet getBaseDamage()          { return baseDamage;    }
    public HeadshotDamageSet getHeadshotDamage()  { return headshot;      }
    public BleedoutSet getBleedout()              { return bleedout;      }
    public CritSet getCritical()                  { return crit;          }
    public ShrapnelDamageSet getShrapnel()        { return shrapnel;      }
    public IncendiaryDamageSet getIncendiarySet() { return incendiaryDamage;    }
    public IgniteSet getIgniteSet()               { return igniteSet;     }
    public MagazineSet getGunMagazine()           { return mag;           }
    public BoltSet getGunBolt()                   { return boltSet;       }
    public ProjectileSet getGunProjectile()       { return projectile;    }
    public StunSet getStunSet()                   { return stun;          }
    public MotionSet getMotionSet()               { return motion;        }
    public DurabilitySet getDurabilitySet()       { return durability;    }
    public SightSet getSightSet()                 { return sightSet;      }
    public FireModeSet getFireModeSet()           { return fireModeSet;   }
    public SilencerSet getSilencerSet()           { return silencerSet;   }
    public KnockbackSet getKnockbackSet()         { return knockbackSet;  }

    public AttributeSet[] getAll() {
        return new AttributeSet[] {fireModeSet, silencerSet, bulletSpread, baseDamage, headshot, bleedout, crit,
                shrapnel, incendiaryDamage, mag, boltSet, projectile, stun, motion, durability, sightSet, knockbackSet
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

    public ArrayList<String> getIndividualStatInfo(final String statDisplayName,
                                                   final int statNameIndentation,
                                                   final int statIndentation)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final AttributeSet modSets[] = getAll();
        ArrayList<String> tempStats;

        stats.add(toTitle(statDisplayName + " Stats"));
        for (AttributeSet modSet : modSets)
        {
            tempStats = modSet.getIndividualStats(statIndentation);
            if (modSet.hasStats() && !tempStats.isEmpty())
            {
                stats.add("  " + toTitle(modSet.getName()));
                stats.addAll(tempStats);
            }
        }
        return stats;
    }

    public ArrayList<String> getIndividualStatInfo(final String statDisplayName)
    {
        return getIndividualStatInfo(statDisplayName, 0, 0);
    }
}
