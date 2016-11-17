/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.physical.components;

import net.projectzombie.crackshotenhanced.guns.utilities.HiddenLoreInfo;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.crafting.GunModifierType;

/**
 *
 * @author jb
 */
public class HiddenGunModifierInfo extends HiddenLoreInfo
{  
    private final int TYPE_IDX = 1;
    private final int ID_IDX = 2;
    private final int LENGTH = 3;
    
    /**
     * 
     * @param type
     * @param id Index in CSV
     */
    public HiddenGunModifierInfo(final GunModifierType type,
                                 final int id)
    {
        super(new String[]
        {
            type.name(),
            String.valueOf(id)
        });
    }
    
    public HiddenGunModifierInfo(final String encodedString)
    {
        super(encodedString);
    }
    
    public GunModifierType getGunModifierType()
    {
        try
        {
            return GunModifierType.valueOf(super.getInfoStr(TYPE_IDX));
        } 
        catch (IllegalArgumentException ex)
        {
            return null;
        }
    }
    
    public int getID()
    {
        return super.getInfoInt(ID_IDX);
    }
    
    public GunModifier getGunModifier()
    {
        if (isValid())
            return getGunModifierType().getGunModifier(getID());
        else
            return null;
    }
    
    @Override
    public boolean isValid()
    {
        return super.isValid()
            && super.getLength() == LENGTH
            && getGunModifierType() != null
            && getGunModifierType().getGunModifier(getID()) != null;
    }
}
