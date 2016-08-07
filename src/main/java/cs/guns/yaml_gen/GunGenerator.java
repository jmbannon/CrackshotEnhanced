/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.yaml_gen;

import cs.guns.components.FireModes.FireMode;
import cs.guns.skeleton.FirearmActions.FirearmAction;
import cs.guns.skeleton.SkeletonTypes.SkeletonType;
import cs.guns.weps.Guns.CrackshotGun;
import org.bukkit.ChatColor;
import cs.guns.physical.weps.CrackshotGunLore;
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
        final SkeletonType weaponType = super.getWeaponType();
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
     * Gets the open duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Open duration in ticks after multipliers taken into account.
     */
    public int getOpenDuration()
    {
        final FirearmAction action = super.getWeaponType().getAction();
        if (action != null)
        {
            final int openDuration = action.getOpenDuration();
            double durationMultiplier = super.getGunBolt().getBoltDurationMultiplier();
            
            int modifiedOpenDuration = (int)Math.round(openDuration * durationMultiplier);
            return Math.min(1, modifiedOpenDuration);
        }
        else
            return 0;
    }
    
    /**
     * Gets the close duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Close duration in ticks after multipliers taken into account.
     */
    public int getCloseDuration()
    {
        final FirearmAction action = super.getWeaponType().getAction();
        if (action != null)
        {
            final int closeDuration = action.getCloseDuration();
            final int modifiedCloseDuration
                    = (int)Math.round(closeDuration * super.getBoltMod().getBoltDurationMultiplier());
            
            return Math.min(1, modifiedCloseDuration);
        }
        else
            return 0;
    }
    
    /**
     * Gets the close shoot delay duration in ticks. Takes the action's duration and multiplies
     * it by the sum of all duration multipliers.
     * @return Close shoot delay in ticks after multipliers taken into account.
     */
    public int getCloseShootDelay()
    {
        final FirearmAction action = super.getWeaponType().getAction();
        if (action != null)
        {
            final int closeShootDelay = action.getCloseShootDelay();
            final int modifiedCloseShootDelay = (int)Math.round(closeShootDelay * super.getBoltMod().getBoltDurationMultiplier());
            
            return Math.min(1, modifiedCloseShootDelay);
        }
        else
            return 0;
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
        final List<String> lore = new CrackshotGunLore(super.getGunID())
                                        .toPreShotLore()
                                        .getLore();
        
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
        return (super.getBarrelMod().isSilencer()) ? 
                super.getSilencedSound() : super.getShootSound();
    }
    
    public int getDelayBetweenShots()
    {
        final int shootDelay = super.getShootDelay();
        final FireMode fireMode = super.getFireModeMod();
        final SkeletonType weaponType = super.getWeaponType();
        
        if (shootDelay == 0 || fireMode.isAutomatic())
            return 0;
        
        else if (fireMode.isBurstFire())
            return 9 + shootDelay;
        else
            return shootDelay;
    }
}
