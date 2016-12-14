/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.physical.weps;

import net.projectzombie.crackshotenhanced.guns.utilities.HiddenLoreInfo;
import net.projectzombie.crackshotenhanced.guns.weps.CrackshotGun;
import net.projectzombie.crackshotenhanced.guns.weps.GunID;
import net.projectzombie.crackshotenhanced.static_maps.Guns;

/**
 *
 * @author Jesse Bannon
 * 
 * This class hides critical information within the Lore of an 
 * CrackshotGun ItemStack.
 * 
 * It converts text to hex which then each hex char
 * is prepended by a COLOR_CHAR which tricks Minecraft into
 * thinking it's a color, therefore no actual text is displayed.
 * 
 * A decrypted HiddenGunInfo is in the form of
 *      PZ`gunID`durability`BuildType
 * 
 * where PZ is a HiddenLore verifier.
 */
public class HiddenGunInfo extends HiddenLoreInfo
{ 
    /** Index of the Gun ID. */
    private static final int GUN_ID_IDX = 1;
    
    /** Index of the current durability. */
    private static final int DUR_IDX = 2;
    
    /** Total length of the elements within the HiddenInfo. */
    private static final int INFO_LEN = 3;
    
    /** Magic number that is set to a pre-shot gun's durability. Metal af \m/ */
    private static final int PRESHOT_VER_DUR = -666;

    /**
     * Creates HiddenGunInfo from an existing hidden string.
     * @param hiddenLoreInfo Existing hidden string that contains gun info.
     */
    public HiddenGunInfo(final String hiddenLoreInfo) { super(hiddenLoreInfo); }

    /**
     * Creates HiddenGunInfo from a GunID. Gun is assumed to be pre-shot.
     * @param id GunID of the gun.
     */
    public HiddenGunInfo(final GunID id)
    {
        super(new String[] {
            String.valueOf(id.toString()),
            String.valueOf(PRESHOT_VER_DUR),
        });
    }
    
    /** @return The GunID hidden in the lore as a String. */
    public String getGunIDStr()
    {
        if (super.isValid())
            return super.getInfoStr(GUN_ID_IDX);
        else
            return null;
    }
    
    /** @return The GunID hidden in the lore. */
    public GunID getGunID() { return new GunID(getGunIDStr()); }
    
    /**
     * Gets the CrackshotGun by parsing the GunID in the hidden lore.
     * @return CrackshotGun affiliated with the GunID.
     */
    public CrackshotGun getGun() { return Guns.get(super.getInfoStr(GUN_ID_IDX)); }

    /** @return Current durability.*/
    public int getDurability() { return super.getInfoInt(DUR_IDX); }
    
    /** @return Returns true if the gun is post-shot and the durability is >= 0. False otherwise. */
    public boolean isBroken() { return this.isPostShot() && this.getDurability() < 0; }

    /** @param newID GunID String to set. */
    public void setGunID(final String newID) { super.setInfoStr(GUN_ID_IDX, newID); }
    
    /** @param newDurability New durability to set. */
    public void setDurability(final int newDurability) { super.setInfoInt(DUR_IDX, newDurability); }
    
    /**
     * @return Returns true if hidden gun info is pre-shot by checking magic values
     * set for both build and durability. False otherwise.
     */
    public boolean isPreShot() { return this.isValid() && this.getDurability() == PRESHOT_VER_DUR; }
    
    /** @return Returns true if the HiddenGunInfo is valid and it's not pre-shot. */
    public boolean isPostShot() { return this.isValid() && !this.isPreShot(); }
    
    /**
     * @return Returns true if the HiddenGunInfo's length is correct and
     * the GunID is valid. Returns false otherwise.
     */
    @Override
    public boolean isValid()
    {
        return super.isValid()
            && super.getLength() == INFO_LEN
            && GunID.isValidID(getGunIDStr());
    }
}
