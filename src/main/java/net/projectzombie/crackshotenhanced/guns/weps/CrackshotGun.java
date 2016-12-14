package net.projectzombie.crackshotenhanced.guns.weps;

import net.projectzombie.crackshotenhanced.guns.attributes.Attributes;
import net.projectzombie.crackshotenhanced.guns.attributes.modifier.DamageOnHit;
import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.guns.crafting.GunModifierType;
import net.projectzombie.crackshotenhanced.guns.physical.components.GunModifierItemStack;
import net.projectzombie.crackshotenhanced.guns.qualities.Condition;
import net.projectzombie.crackshotenhanced.static_maps.Guns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by jb on 11/25/16.
 */
public class CrackshotGun extends GunSkeletons.GunSkeleton {
    private static final Random RAND = new Random();

    private final String uniqueID;
    private final String csUniqueID;
    private final GunID gunID;

    private final ProjectileAttachments.ProjectileAttachment[] attatchments;
    private final Barrels.Barrel barrel;
    private final Bolts.Bolt bolt;
    private final FireModes.FireMode firemode;
    private final Magazines.Magazine magazine;
    private final Sights.Sight sight;
    private final Stocks.Stock stock;
    private final Attributes attributes;


    public CrackshotGun(final GunSkeletons.GunSkeleton skeleton,
                        final ProjectileAttachments.ProjectileAttachment attachmentOne,
                        final ProjectileAttachments.ProjectileAttachment attachmentTwo,
                        final ProjectileAttachments.ProjectileAttachment attachmentThree,
                        final Barrels.Barrel barrel,
                        final Bolts.Bolt bolt,
                        final FireModes.FireMode firemodeType,
                        final Magazines.Magazine magazine,
                        final Sights.Sight sightType,
                        final Stocks.Stock stock) {
        super(skeleton);
        this.gunID = new GunID(skeleton, attachmentOne, attachmentTwo, attachmentThree, barrel, bolt, firemodeType, magazine, sightType, stock);

        final GunModifier[] modifiers = new GunModifier[]{attachmentOne, attachmentTwo, attachmentThree, barrel,
                bolt, firemodeType, magazine, sightType, stock};

        this.attatchments = new ProjectileAttachments.ProjectileAttachment[]{attachmentOne, attachmentTwo, attachmentThree};
        this.barrel = barrel;
        this.bolt = bolt;
        this.firemode = firemodeType;
        this.magazine = magazine;
        this.sight = sightType;
        this.stock = stock;

        this.uniqueID = gunID.getUniqueID();
        this.csUniqueID = skeleton.getFileName() + "_" + gunID.getCSUniqueID();
        this.attributes = new Attributes(skeleton, modifiers);
    }

    public CrackshotGun(final CrackshotGun gun) {
        this(gun, gun.getAttachmentOneMod(), gun.getAttachmentTwoMod(), gun.getAttachmentThreeMod(), gun.getBarrelMod(),
                gun.getBoltMod(), gun.getFireModeMod(), gun.getMagazineMod(), gun.getScopeMod(), gun.getStockMod());
    }

    public CrackshotGun(final GunID id) {
        this(id.getSkeleton(), id.getAttatchmentOne(), id.getAttatchmentTwo(), id.getAttatchmentThree(),
                id.getBarrel(), id.getBolt(), id.getFireMode(), id.getMagazine(), id.getScope(), id.getStock());
    }

    public GunID getGunID() {
        return gunID;
    }

    // -------------------------------------------------------------------------
    //  Modifiers
    // -------------------------------------------------------------------------

    public ProjectileAttachments.ProjectileAttachment getAttachmentOneMod() { return attatchments[0]; }
    public ProjectileAttachments.ProjectileAttachment getAttachmentTwoMod() { return attatchments[1]; }
    public ProjectileAttachments.ProjectileAttachment getAttachmentThreeMod() { return attatchments[2]; }
    public Bolts.Bolt getBoltMod() { return bolt; }
    public Barrels.Barrel getBarrelMod() { return barrel; }
    public FireModes.FireMode getFireModeMod() { return firemode; }
    public Magazines.Magazine getMagazineMod() { return magazine; }
    public Sights.Sight getScopeMod() { return sight; }
    public Stocks.Stock getStockMod() { return stock; }



    public Attributes getAttributes() { return attributes; }
    public String getCSWeaponName() { return csUniqueID; }
    public String getUniqueID() { return uniqueID; }

    public GunModifier[] getGunModifiers() { return new GunModifier[]{attatchments[0], attatchments[1], attatchments[2],
            barrel, bolt, firemode, magazine, sight, stock}; }

    public double getTotalDamageOnHit() {
        return Arrays.stream(attributes.getAll())
                .filter(set -> set instanceof DamageOnHit)
                .map(set -> (DamageOnHit)set)
                .mapToDouble(DamageOnHit::getTotal)
                .sum();
    }

    // -------------------------------------------------------------------------
    //  Chance modifiers
    // -------------------------------------------------------------------------

    public boolean isIgnite() { return attributes.getIgniteSet().rollDice(); }
    public boolean isStun() { return attributes.getStunSet().rollDice(); }
    public boolean isCrit() { return attributes.getCritical().rollDice(); }

    // -------------------------------------------------------------------------
    //  Chance modifier Effects
    // -------------------------------------------------------------------------

    /** @return Total headshot damage on hit. */
    public double getTotalHeadshotDamage() { return attributes.getHeadshotDamage().getTotal(); }

    /** @return Total critical damage on hit. */
    public double getTotalCritDamage() { return attributes.getCritical().getTotalDamageOnCrit(); }


    @Override
    public String toString() {
        return uniqueID;
    }

    public double getDPS() {
        return 0;
    }

    // -------------------------------------------------------------------------
    //  Bullet Spread
    // -------------------------------------------------------------------------

    /** @return BulletSpread * multiplier */
    private double getBulletSpread(final double multiplier) {
        return attributes.getBulletSpread().getBulletSpread() * multiplier;
    }

    /** @return BulletSpread. */
    private double getBulletSpread() { return getBulletSpread(1.0); }

    /** @return BulletSpread when zoomed. */
    public double getZoomedBulletSpread() {
        final double zoomMultiplier = attributes.getSightSet().getZoomBulletSpreadMultiplier();
        return this.getBulletSpread(zoomMultiplier);
    }

    /** @return BulletSpread when standing. */
    public double getStandingBulletSpread() {
        final double standingMultiplier = attributes.getMotionSet().getTotalStandingBulletSpreadMultiplier();
        return this.getBulletSpread(standingMultiplier);
    }

    /** @return BulletSpread when running. */
    public double getRunningBulletSpread() {
        final double runningMultiplier = attributes.getMotionSet().getTotalMovingBulletSpreadMultiplier();
        return this.getBulletSpread(runningMultiplier);
    }

    /** @return BulletSpread when crouching. */
    public double getCrouchingBulletSpread() {
        final double crouchingMultiplier = attributes.getMotionSet().getTotalCrouchingBulletSpreadMultiplier();
        return this.getBulletSpread(crouchingMultiplier);
    }

    public int getInitialDurability() {
        final int maxDurability = this.getSkeletonMaxDurability();
        return maxDurability - RAND.nextInt(maxDurability / 2);
    }

    /**
     * Returns the integer of the gun condition.
     *
     * @param durability Current durability of the gun.
     * @return Gun tier integer.
     */
    public Condition getCondition(final int durability) {
        return Condition.getCondition(durability, attributes.getDurabilitySet().getMaxDurability());
    }

    public CrackshotGun getModifiedGun(final GunModifierItemStack item) {
        return Guns.get(new GunID(this, item.getGunModifier(), item.getGunModifierType()));
    }

    public ArrayList<String> getStats() {
        return attributes.getAllStatInfo(super.getName());
    }

    public ArrayList<String> getComponentStats() {
        final ArrayList<String> stats = new ArrayList<>();
        for (GunModifier gunMod : getGunModifiers()) {
            if (!gunMod.isNull())
                stats.addAll(new Attributes(gunMod).getIndividualStatInfo(gunMod.getDisplayName(false), 2, 4));
        }
        return stats;
    }

    public boolean canZoom() { return attributes.getSightSet().getZoomAmount() > 0; }
}
