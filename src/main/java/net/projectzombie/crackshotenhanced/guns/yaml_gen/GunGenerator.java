/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.yaml_gen;

import net.projectzombie.crackshotenhanced.guns.components.modifier.FireModes;
import net.projectzombie.crackshotenhanced.guns.physical.gun.CrackshotGunLore;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.FirearmActions;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.SkeletonTypes;
import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import net.projectzombie.crackshotenhanced.resources.sounds.SoundAliases;
import org.bukkit.ChatColor;

import java.util.List;

/**
 *
 * @author jesse
 */
public class GunGenerator extends CrackshotGun
{
    private static final char CRACKSHOT_COLOR_CHAR = '&';
    private static final char CRACKSHOT_LORE_NEW_LINE= '|';
    public static final ChatColor ITEM_COLOR  = ChatColor.YELLOW;
    
    public GunGenerator(final CrackshotGun gun)
    {
        super(gun);
    }

    
    public String getAmmoID()
    {
        final SkeletonTypes.SkeletonType weaponType = super.getWeaponType();
        final int ammoData = weaponType.getAmmoData();
        
        if (ammoData != 0)
        {
            return String.valueOf(weaponType.getAmmoID() + "~" + ammoData);
        }
        else
        {
            return String.valueOf(weaponType.getAmmoID());
        }
    }

    /**
     * Multiplies the duration by the boltDurationMultiplier
     * @param duration Duration of FirearmAction action
     * @return Duration of the action
     */
    private int getFirearmActionDuration(final double duration) {
        double durationMultiplier = super.getAttributes().getGunBolt().getBoltDurationMultiplier();

        int modifiedOpenDuration = (int)Math.round(duration * durationMultiplier);
        return Math.max(1, modifiedOpenDuration);
    }
    
    /**
     * Gets the open duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Open duration in ticks after multipliers taken into account.
     */
    public int getOpenDuration()
    {
        final FirearmActions.FirearmAction action = super.getWeaponType().getAction();
        return (action != null) ? getFirearmActionDuration(action.getOpenDuration()) : 0;
    }
    
    /**
     * Gets the close duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Close duration in ticks after multipliers taken into account.
     */
    public int getCloseDuration()
    {
        final FirearmActions.FirearmAction action = super.getWeaponType().getAction();
        return (action != null) ? getFirearmActionDuration(action.getCloseDuration()) : 0;
    }
    
    /**
     * Gets the close shoot delay duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Close shoot delay in ticks after multipliers taken into account.
     */
    public int getCloseShootDelay()
    {
        final FirearmActions.FirearmAction action = super.getWeaponType().getAction();
        return (action != null) ? getFirearmActionDuration(action.getCloseShootDelay()): 0;
    }
    
    public String getItemName()
    {
        final String ID = String.valueOf(super.getUniqueID());
        final StringBuilder stb = new StringBuilder();
        
        stb.append(ITEM_COLOR);
        for (char c : ID.toCharArray())
        {
            stb.append('&');
            stb.append(c);
        }
        stb.append(ITEM_COLOR);
        stb.append(super.getName());
        return stb.toString().replace(ChatColor.COLOR_CHAR, '&');
    }
    
    public String getItemType()
    {
        final int materialData = super.getItemData();
        final StringBuilder stb = new StringBuilder();
        
        stb.append(super.getItemID());
        if (materialData != 0)
        {
            stb.append('~');
            stb.append(materialData);
        }
        return stb.toString();
    }
    
    public String getItemLore()
    {
        final List<String> lore = new CrackshotGunLore(super.getGunID()).generateLore();
        final StringBuilder stb = new StringBuilder();
        
        for (int i = 0; i < lore.size() - 1; i++)
        {
            stb.append(lore.get(i));
            stb.append(CRACKSHOT_LORE_NEW_LINE);
        }
        stb.append(lore.get(lore.size() - 1));
        
        return stb.toString().replace(ChatColor.COLOR_CHAR, CRACKSHOT_COLOR_CHAR);
    }
    
    public String getInventoryControl()
    {
        return super.getWeaponType().getInventoryControl();
    }
    
    public String getSoundsShoot()
    {
        final SoundAliases.SoundAlias silencedSound = super.getSilencedSound();
        if (super.getBarrelMod().isSilencer() && silencedSound != null) {
            return silencedSound.getCrackShotConfigString();
        } else {
            return super.getShootSound().getCrackShotConfigString();
        }
    }
    
    public int getDelayBetweenShots()
    {
        final int shootDelay = super.getShootDelay();
        final FireModes.FireMode fireMode = super.getFireModeMod();
        final SkeletonTypes.SkeletonType weaponType = super.getWeaponType();
        
        if (shootDelay == 0 || fireMode.isAutomatic())
            return 0;
        
        else if (fireMode.isBurstFire())
            return 9 + shootDelay;
        else
            return shootDelay;
    }
}
