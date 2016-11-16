/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.yaml_gen;

import net.projectzombie.crackshotenhanced.cs.guns.weps.Guns;
import net.projectzombie.crackshotenhanced.cs.guns.components.modifiers.ProjectileSet;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.projectzombie.crackshotenhanced.cs.guns.skeleton.FirearmActions.FirearmAction;
import net.projectzombie.crackshotenhanced.cs.guns.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.cs.guns.skeleton.GunSkeletons.GunSkeleton;
import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class YAMLGenerator extends GunGenerator
{
    static private File weps;
    static private FileConfiguration wepsYAML;
    
    private YAMLGenerator(final Guns.CrackshotGun gun)
    {
        super(gun);
    }
    
    static
    public void generateDefaultWeapons()
    {
        int gunsWritten = 0;
        loadCrackshotWeaponFile();
        
        for (GunSkeleton skele : GunSkeletons.getInstance().getAll())
        {
            gunsWritten += skele.getGunBaseSet().length;
            for (Guns.CrackshotGun gun : skele.getGunBaseSet())
                writeWeapon(gun);
        }
        
        Main.getPlugin().getLogger().info("Wrote " + gunsWritten + " Crackshot guns.");
        saveWeapons();
    }
    
    static
    private void writeWeapon(final Guns.CrackshotGun gun)
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
        gunGen.writeHitEvents();
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
        wepsYAML.set(path + "Sounds_Acquired",   "BAT_TAKEOFF-1-1-0");
        wepsYAML.set(path + "Skip_Name_check",    false);
        wepsYAML.set(path + "Remove_Unused_Tag",  true);
    }
    
    private void writeCritical()
    {
        final String path = super.getCSWeaponName() + ".Critical_Hits.";
        final double criticalChance = super.getCritical().getChance();
        final double criticalStrike = super.getCritical().getCritStrikeDamage();
        
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
        final ProjectileSet projectileSet = super.getGunProjectile();
        
        wepsYAML.set(path + "Right_Click_To_Shoot",            true);
        wepsYAML.set(path + "Cancel_Left_Click_Block_Damage",  true);
        wepsYAML.set(path + "Cancel_Right_Click_Interactions", true);
        wepsYAML.set(path + "Delay_Between_Shots",             super.getDelayBetweenShots());
        wepsYAML.set(path + "Recoil_Amount",                   super.getRecoil());
        wepsYAML.set(path + "Projectile_Amount",               projectileSet.getTotalProjectileAmount());
        wepsYAML.set(path + "Projectile_Type",                 "snowball");
        wepsYAML.set(path + "Projectile_Speed",                projectileSet.getProjectileSpeed());
        wepsYAML.set(path + "Projectile_Damage",               super.getBaseDamage().getValue());
        wepsYAML.set(path + "Removal_Or_Drag_Delay",           super.getWeaponType().getRemovalDragDelay()); // NOTE: UPDATE THIS WITH PROJECTILE RANGE
        wepsYAML.set(path + "Bullet_Spread",                   super.getBulletSpread().getBulletSpread());
        wepsYAML.set(path + "Sounds_Shoot",                    super.getSoundsShoot());
    }
    
    private void writeScope()
    {
        if (super.getScopeMod().getZoomAmount() <= 0) return;
        
        final String path = super.getCSWeaponName() + ".Sight.";
        
        wepsYAML.set(path + "Enable",             true);
        wepsYAML.set(path + "Zoom_Amount",        super.getScopeMod().getZoomAmount());
        wepsYAML.set(path + "Zoom_Bullet_Spread", super.getSkeletonBulletSpread());
        wepsYAML.set(path + "Sounds_Toggle_Zoom", "NOTE_STICKS-1-2-0");
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
        wepsYAML.set(path + "Reload_Amount",               super.getGunMagazine().getMagazineSize());
        wepsYAML.set(path + "Reload_Duration",             super.getGunMagazine().getReloadDuration());
        wepsYAML.set(path + "Take_Ammo_On_Reload",         true);
        wepsYAML.set(path + "Reload_Bullets_Individually", super.reloadsBulletsIndividually());
        wepsYAML.set(path + "Sounds_Out_Of_Ammo",          "ITEM_BREAK-1-1-0");
        wepsYAML.set(path + "Sounds_Reloading",            super.getReloadSound());
    }
    
    private void writeFirearmAction()
    {
        final FirearmAction action = super.getWeaponType().getAction();
        final String path = super.getCSWeaponName() + ".Firearm_Action.";
        
        if (action == null || action.toString() == null) return;

        wepsYAML.set(path + "Type",              action.toString());
        wepsYAML.set(path + "Open_Duration",     super.getOpenDuration());
        wepsYAML.set(path + "Close_Duration",    super.getCloseDuration());
        wepsYAML.set(path + "Close_Shoot_Delay", super.getCloseShootDelay());
        wepsYAML.set(path + "Sound_Open",        action.getSoundOpen());
        wepsYAML.set(path + "Sound_Close",       action.getSoundClose());
    }
    

    private void writeHeadshot()
    {
        final String path = super.getCSWeaponName() + ".Headshot.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Bonus_Damage",  super.getSkeletonBaseDamage() * 2);
        wepsYAML.set(path + "Sounds_Victim", "VILLAGER_NO-1-1-0");
    }
    
    private void writeAmmo()
    {
        final String path = super.getCSWeaponName() + ".Ammo.";
        
        wepsYAML.set(path + "Enable",       true);
        wepsYAML.set(path + "Ammo_Item_ID", super.getAmmoID());
    }
    
    private void writeHitEvents()
    {
        final String path = super.getCSWeaponName() + ".Hit_Events.";
        
        wepsYAML.set(path + "Enable",        true);
        wepsYAML.set(path + "Sounds_Victim", "VILLAGER_IDLE-1-1-0");
    }
    
}
