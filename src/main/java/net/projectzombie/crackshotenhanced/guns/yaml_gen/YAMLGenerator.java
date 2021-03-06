/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.yaml_gen;

import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.ProjectileSet;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.FirearmActions;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.resources.sounds.GlobalConfiguration;
import net.projectzombie.crackshotenhanced.resources.sounds.SoundAliases;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author jbannon
 */
public class YAMLGenerator extends GunGenerator
{
    static private File weps;
    static private FileConfiguration wepsYAML;
    
    private YAMLGenerator(final CrackshotGun gun)
    {
        super(gun);
    }
    
    static
    public void generateDefaultWeapons()
    {
        int gunsWritten = 0;
        loadCrackshotWeaponFile();
        CrackshotGun[] skeleBase;
        
        for (GunSkeletons.GunSkeleton skele : GunSkeletons.getInstance().getAll())
        {
            skeleBase = skele.getGunBaseSet();
            if (skeleBase.length <= 0) {
                Main.warning(skele.getName() + " has a base set of 0");
                continue;
            }
            gunsWritten += skeleBase.length;
            Arrays.stream(skeleBase).forEach(YAMLGenerator::writeWeapon);
        }
        
        Main.info("Wrote " + gunsWritten + " Crackshot guns.");
        saveWeapons();
    }
    
    static
    private void writeWeapon(final CrackshotGun gun)
    {
        YAMLGenerator gunGen = new YAMLGenerator(gun);
        gunGen.writeItemInformation();
        gunGen.writeShooting();
        gunGen.writeScope();
        gunGen.writeBurstfire();
        gunGen.writeFullyAutomatic();
        gunGen.writeReload();
        gunGen.writeFirearmAction();
        gunGen.writeHeadshot();
        gunGen.writeAmmo();
        //gunGen.writeHitEvents();
    }
    
    static
    private void loadCrackshotWeaponFile()
    {
        final String defaultWeaponsPath = Main.getPlugin().getDataFolder().getParent() + "/CrackShot/weapons/";
        
        if (weps == null)
            weps = new File(defaultWeaponsPath, "defaultWeapons.yml");
        
        wepsYAML = new YamlConfiguration();
        wepsYAML = YamlConfiguration.loadConfiguration(weps);
        saveWeapons();
    }
    
    static
    private void saveWeapons()
    {
        if (weps == null || wepsYAML == null) return;
        
        try   { wepsYAML.save(weps); }
        catch (IOException ex)
        {
            Logger.getLogger(YAMLGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void writeItemInformation()
    {
        final String path = super.getCSWeaponName() + ".Item_Information.";
        
        wepsYAML.set(path + "Item_Name",         super.getItemName());
        wepsYAML.set(path + "Item_Type",         super.getItemType());
        wepsYAML.set(path + "Item_Lore",         super.getItemLore());
        wepsYAML.set(path + "Inventory_Control", super.getInventoryControl());
        wepsYAML.set(path + "Sounds_Acquired",   GlobalConfiguration.config().getSoundAcquired().toString());
        wepsYAML.set(path + "Skip_Name_check",    false);
        wepsYAML.set(path + "Remove_Unused_Tag",  true);
    }
    
    private void writeCritical()
    {
        final String path = super.getCSWeaponName() + ".Critical_Hits.";
        final double criticalChance = super.getAttributes().getCritical().getChance();
        final double criticalStrike = super.getAttributes().getCritical().getTotalDamageOnCrit();
        
        if (criticalChance > 0 && criticalStrike > 0)
        {
            wepsYAML.set(path + "Enable", true);
            wepsYAML.set(path + "Bonus_Damage", criticalStrike);
            wepsYAML.set(path + "Chance", criticalChance);
        }
    }
    
    private void writeShooting()
    {
        final String path = super.getCSWeaponName() + ".Shooting.";
        final ProjectileSet projectileSet = super.getAttributes().getGunProjectile();
        
        wepsYAML.set(path + "Right_Click_To_Shoot",            true);
        wepsYAML.set(path + "Cancel_Left_Click_Block_Damage",  true);
        wepsYAML.set(path + "Cancel_Right_Click_Interactions", true);
        wepsYAML.set(path + "Delay_Between_Shots",             super.getDelayBetweenShots());
        wepsYAML.set(path + "Recoil_Amount",                   super.getRecoil());
        wepsYAML.set(path + "Projectile_Amount",               projectileSet.getTotalProjectileAmount());
        wepsYAML.set(path + "Projectile_Type",                 GlobalConfiguration.config().getProjectileType());
        wepsYAML.set(path + "Projectile_Speed",                projectileSet.getProjectileSpeed());
        wepsYAML.set(path + "Projectile_Damage",               super.getAttributes().getBaseDamage().getValue());
        wepsYAML.set(path + "Removal_Or_Drag_Delay",           super.getWeaponType().getRemovalDragDelay()); // NOTE: UPDATE THIS WITH PROJECTILE RANGE
        wepsYAML.set(path + "Bullet_Spread",                   super.getAttributes().getBulletSpread().getBulletSpread());
        wepsYAML.set(path + "Sounds_Shoot",                    super.getSoundsShoot());
    }
    
    private void writeScope()
    {
        if (super.getScopeMod().getZoomAmount() <= 0) return;
        
        final String path = super.getCSWeaponName() + ".Scope.";
        
        wepsYAML.set(path + "Enable",             true);
        wepsYAML.set(path + "Zoom_Amount",        super.getAttributes().getSightSet().getZoomAmount());
        wepsYAML.set(path + "Zoom_Bullet_Spread", super.getSkeletonBulletSpread()); /* TODO: FIX. */
        wepsYAML.set(path + "Sounds_Toggle_Zoom", GlobalConfiguration.config().getSoundToggleZoom().toString());
    }
    
    private void writeBurstfire()
    {
        if (!super.getFireModeMod().isBurstFire()) return;
        
        final String path = super.getCSWeaponName() + ".Burstfire.";
        
        wepsYAML.set(path + "Enable",                       true);
        wepsYAML.set(path + "Shots_Per_Burst",              super.getFireModeMod().getShotsPerBurst());
        wepsYAML.set(path + "Delay_Between_Shots_In_Burst", 3);
    }
    
    
    private void writeFullyAutomatic()
    {
        if (!super.getFireModeMod().isAutomatic()) return;
        final String path = super.getCSWeaponName() + ".Fully_Automatic.";
        
        wepsYAML.set(path + "Enable",    true);
        wepsYAML.set(path + "Fire_Rate", 3);
    }
    
    private void writeReload()
    {
        final String path = super.getCSWeaponName() + ".Reload.";
        
        wepsYAML.set(path + "Enable",                      true);
        wepsYAML.set(path + "Reload_Amount",               super.getAttributes().getGunMagazine().getTotalMagazineSize());
        wepsYAML.set(path + "Reload_Duration",             super.getAttributes().getGunMagazine().getTotalReloadDurationInTicks());
        wepsYAML.set(path + "Take_Ammo_On_Reload",         true);
        wepsYAML.set(path + "Reload_Bullets_Individually", super.reloadsBulletsIndividually());
        wepsYAML.set(path + "Sounds_Out_Of_Ammo",          GlobalConfiguration.config().getSoundOutOfAmmo().toString());
        wepsYAML.set(path + "Sounds_Reloading",            super.getReloadSound().getCrackShotConfigString());
    }
    
    private void writeFirearmAction()
    {
        final FirearmActions.FirearmAction action = super.getWeaponType().getAction();
        final String path = super.getCSWeaponName() + ".Firearm_Action.";
        
        if (action == null || action.toString() == null) return;

        wepsYAML.set(path + "Type",              action.getFirearmActionType());
        wepsYAML.set(path + "Open_Duration",     super.getOpenDuration());
        wepsYAML.set(path + "Close_Duration",    super.getCloseDuration());
        wepsYAML.set(path + "Close_Shoot_Delay", super.getCloseShootDelay());

        final SoundAliases.SoundAlias soundOpen = action.getSoundOpen();
        final SoundAliases.SoundAlias soundClose = action.getSoundClose();

        if (soundOpen != null)
            wepsYAML.set(path + "Sound_Open", action.getSoundOpen().toString());
        if (soundClose != null)
            wepsYAML.set(path + "Sound_Close", action.getSoundClose().toString());
    }
    

    private void writeHeadshot()
    {
        final String path = super.getCSWeaponName() + ".Headshot.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Bonus_Damage",  super.getSkeletonBaseDamage() * 2); // TODO: Fix
        //wepsYAML.set(path + "Sounds_Victim", "VILLAGER_NO-1-1-0");             // TODO: Make headshot sound only on death
    }
    
    private void writeAmmo()
    {
        final String path = super.getCSWeaponName() + ".Ammo.";
        
        wepsYAML.set(path + "Enable",       true);
        wepsYAML.set(path + "Ammo_Item_ID", super.getAmmoID());
    }
    
//    private void writeHitEvents()
//    {
//        final String path = super.getCSWeaponName() + ".Hit_Events.";
//
//        wepsYAML.set(path + "Enable",        true);
//        wepsYAML.set(path + "Sounds_Victim", "VILLAGER_IDLE-1-1-0");
//    }
    
}
