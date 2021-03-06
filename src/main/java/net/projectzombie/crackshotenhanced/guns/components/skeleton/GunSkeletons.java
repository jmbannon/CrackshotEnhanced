/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.skeleton;

import net.projectzombie.crackshotenhanced.guns.attributes.modifier.KnockbackSet;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.MotionSet;
import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.guns.utilities.Constants;
import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.resources.sounds.SoundAliases;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import net.projectzombie.crackshotenhanced.yaml.ModifierMap;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons.GunSkeleton;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/** Contains standard Crackshot guns with no modifiers (i.e. scopes) */
public class GunSkeletons extends ModifierConfig<GunSkeleton>
{
    static private GunSkeletons singleton = null;
    
    static public GunSkeletons getInstance()
    {
        if (singleton == null) {
            singleton = new GunSkeletons();
            singleton.postInitialize();
        }
        return singleton;
    }

    static private ModifierMap buildDefaultValues() {
        final ModifierMap defaultValues = new ModifierMap(MODULE_NAME);
        defaultValues.put("Price", 0);
        defaultValues.put("Color", "GREEN");
        defaultValues.put("Initial Bullet Spread", 1.5);
        defaultValues.put("Delay Between Shots", 4);
        defaultValues.put("Durability Max", 100);
        defaultValues.put("Damage", 10.0);
        defaultValues.put("Reload Amount", 30);
        defaultValues.put("Reload Duration", 40);
        defaultValues.put("Reload Bullets Individually", false);
        defaultValues.put("Recoil Amount", 0);
        defaultValues.put("Speed Running Multiplier", 0.0);
        defaultValues.put("Bullet Spread Crouching Multiplier", 0.0);
        defaultValues.put("Bullet Spread Standing Multiplier", 0.0);
        defaultValues.put("Bullet Spread Running Multiplier", 0.0);
        defaultValues.put("Knockback Multiplier", 0.0);
        defaultValues.put("Sound Silenced", "NULL");
        defaultValues.put("Particle Shoot", "NULL");
        return defaultValues;
    }

    static private final String YML_NAME = "GunSkeletons.yml";
    static private final String MODULE_NAME = "GunSkeletons";
    static private final String[] NECESSARY_VALUES = new String[] { "Display Name", "Quality", "Weapon Type", "Modifier Set", "Material ID", "Material Data", "Sound Shoot", "Sound Reload" };
    static private final ModifierMap DEFAULT_VALUES = buildDefaultValues();

    private GunSkeletons() { super(YML_NAME, MODULE_NAME, NECESSARY_VALUES, DEFAULT_VALUES); }

    public GunSkeleton buildModule(final String key, final int uniqueID, final ModifierMap values) {
        try {
            return new GunSkeleton(
                    key,
                    uniqueID,
                    values.getString("Display Name"),
                    values.getInt("Price"),
                    values.getString("Color"),
                    Qualities.getInstance().get(values.getString("Quality")),
                    SkeletonTypes.getInstance().get(values.getString("Weapon Type")),
                    ModifierSets.getInstance().get(values.getString("Modifier Set")),
                    values.getInt("Material ID"),
                    values.getInt("Material Data"),
                    values.getDouble("Initial Bullet Spread"),
                    values.getInt("Delay Between Shots"),
                    values.getInt("Durability Max"),
                    values.getDouble("Damage"),
                    values.getInt("Reload Amount"),
                    values.getInt("Reload Duration"),
                    values.getBoolean("Reload Bullets Individually"),
                    values.getInt("Recoil Amount"),
                    values.getDouble("Speed Running Multiplier"),
                    values.getDouble("Bullet Spread Crouching Multiplier"),
                    values.getDouble("Bullet Spread Standing Multiplier"),
                    values.getDouble("Bullet Spread Standing Multiplier"),
                    values.getDouble("Knockback Multiplier"),

                    SoundAliases.getInstance().get(values.getString("Sound Shoot")),
                    SoundAliases.getInstance().get(values.getString("Sound Silenced")),
                    values.getString("Particle Shoot"),
                    SoundAliases.getInstance().get(values.getString("Sound Reload"))
            );
        } catch (Exception e) {
            Main.getPlugin().getLogger().warning("Cannot add skeleton " + values.getString("Display Name") + e.toString());
            e.printStackTrace();
            return null;
        }
    }
    
    static public class GunSkeleton extends QualityGunModifier
        implements MotionSet.MotionAttributes,
            KnockbackSet.KnockbackAttributes
    {   
        private final SkeletonTypes.SkeletonType weaponType;
        private final double bulletSpread;
        private final double damage;
        private final ModifierSets.ModifierSet modSet;
        private final String particleShoot;
        private final boolean reloadBulletsIndividually;
        
        private final double runningSpeedMultiplier;
        private final double crouchingBulletSpreadMultiplier;
        private final double standingBulletSpreadMultiplier;
        private final double runningBulletSpreadMultiplier;
        private final double knockbackMultiplier;

        private final int
                itemID, 
                itemData, 
                shootDelay, 
                maxDurability,  
                recoilAmount, 
                reloadAmount, 
                reloadDuration;

        private final SoundAliases.SoundAlias
                soundShoot,
                soundSilenced,
                soundReload;

        private GunSkeleton(final String key,
                            final int uniqueID,
                            final String skeletonName,
                            final int price,
                            final String color,
                            final Qualities.Quality quality,
                            final SkeletonTypes.SkeletonType weaponType,
                            final ModifierSets.ModifierSet set,
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
                            final double crouchingBulletSpreadMultiplier,
                            final double standingBulletSpreadMultiplier,
                            final double runningBulletSpreadMultiplier,
                            final double knockbackMultiplier,

                            final SoundAliases.SoundAlias sounds_shoot,
                            final SoundAliases.SoundAlias sounds_silenced,
                            final String particle_shoot,
                            final SoundAliases.SoundAlias sounds_reloading)
        {
            super(key, uniqueID, skeletonName, price, color, quality, CraftableType.SKELETON);
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
            this.crouchingBulletSpreadMultiplier = crouchingBulletSpreadMultiplier;
            this.standingBulletSpreadMultiplier = standingBulletSpreadMultiplier;
            this.runningBulletSpreadMultiplier = runningBulletSpreadMultiplier;
            this.knockbackMultiplier = knockbackMultiplier;
        }
        
        public GunSkeleton(final GunSkeleton skele)
        {
            super(skele.getKey(),
                  skele.getIndex(),
                  skele.getName(),
                  skele.price(),
                  skele.getColor().name(),
                  skele.getQuality(),
                  CraftableType.SKELETON);
            this.weaponType = skele.weaponType;
            this.itemID = skele.itemID;
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
            this.crouchingBulletSpreadMultiplier = skele.crouchingBulletSpreadMultiplier;
            this.standingBulletSpreadMultiplier = skele.standingBulletSpreadMultiplier;
            this.runningBulletSpreadMultiplier = skele.runningBulletSpreadMultiplier;
            this.knockbackMultiplier = skele.knockbackMultiplier;
        }

        public String        getFileName()       { return super.getName().toLowerCase(); }
        public SkeletonTypes.SkeletonType getWeaponType()     { return weaponType;     }
        public double        getSkeletonBulletSpread()   { return bulletSpread;   }
        public int           getItemID()         { return itemID;         }
        public int           getItemData()       { return itemData;       }
        public Material      getMaterial()       { return Material.getMaterial(itemID); }
        public MaterialData  getMaterialData()   { return new MaterialData(itemID, (byte)itemData); }
        public int           getShootDelay()     { return shootDelay;     }
        public int           getSkeletonMaxDurability()  { return maxDurability;  }
        public double        getSkeletonBaseDamage() { return damage;         }
        public int           getRecoil()         { return recoilAmount;   }

        public String        getShootParticle()  { return particleShoot; }
        public int           getSkeletonReloadAmount()   { return reloadAmount;   }
        public int           getSkeletonReloadDuration() { return reloadDuration; }

        @Override public double getSpeedMultiplier() { return runningSpeedMultiplier; }
        @Override public double getCrouchingBulletSpreadMultiplier() { return crouchingBulletSpreadMultiplier; }
        @Override public double getStandingBulletSpreadMultiplier() { return standingBulletSpreadMultiplier; }
        @Override public double getRunningBulletSpreadMultiplier() { return runningBulletSpreadMultiplier; }
        @Override public double getKnockbackMultiplier()           { return knockbackMultiplier; }


        public ItemStack     getBareItemStack()  { return new ItemStack(itemID, 1, (short)itemData); }
        public ModifierSets.ModifierSet getModifierSet()    { return modSet; }
        public boolean       reloadsBulletsIndividually() { return reloadBulletsIndividually; }
        public GunModifier[] getModifiers()      { return modSet.getModifiers(); }

        public SoundAliases.SoundAlias       getShootSound()     { return soundShoot;    }
        public SoundAliases.SoundAlias       getSilencedSound()  { return soundSilenced; }
        public SoundAliases.SoundAlias       getReloadSound()    { return soundReload;   }

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
                return new CrackshotGun[0];

            CrackshotGun guns[] = new CrackshotGun[combinationCount];
            final ProjectileAttachments.ProjectileAttachment nullAtt = ProjectileAttachments.getSlotOneInstance().getNullValue();
            final Stocks.Stock nullStock = Stocks.getInstance().getNullValue();

            for (Barrels.Barrel barrel : modSet.getBarrels())
            {
                for (Bolts.Bolt bolt : modSet.getBolts())
                {
                    for (FireModes.FireMode fireMode : modSet.getFireModes())
                    {
                        for (Magazines.Magazine magazine : modSet.getMagazines())
                        {
                            for (Sights.Sight sight : modSet.getSights())
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
                                        sight,
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

        /**
         * Creates a CrackshotGun ItemStack by randomly choosing one of this GunSkeleton's FireModes and having
         * the other GunModifiers be their null values.
         * @return Bare CrackshotGun ItemStack with a random FireMode.
         */
        @Override
        public ItemStack toItemStack() {
            final int randomFireModeIndex = Constants.RANDOM.nextInt(modSet.getFireModes().size());
            final FireModes.FireMode randomFireMode = modSet.getFireModes().get(randomFireModeIndex);
            System.out.println(randomFireMode != null);
            return new CrackshotGun(this,
                    ProjectileAttachments.getSlotOneInstance().getNullValue(),
                    ProjectileAttachments.getSlotTwoInstance().getNullValue(),
                    ProjectileAttachments.getSlotThreeInstance().getNullValue(),
                    Barrels.getInstance().getNullValue(),
                    Bolts.getInstance().getNullValue(),
                    randomFireMode,
                    Magazines.getInstance().getNullValue(),
                    Sights.getInstance().getNullValue(),
                    Stocks.getInstance().getNullValue())
                .toItemStack();
        }

        public GunSkeleton getNullModifier() {
            return null;
        }
    }
}
