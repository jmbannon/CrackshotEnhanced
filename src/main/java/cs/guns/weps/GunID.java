/*
 * Copyright (C) 2016 jesse
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cs.guns.weps;

import cs.guns.components.Attachments;
import cs.guns.components.Attachments.Attachment;
import cs.guns.skeleton.GunSkeletons;
import cs.guns.components.ProjectileAttachments.ProjectileAttachment;
import cs.guns.components.Barrels;
import cs.guns.components.Barrels.Barrel;
import cs.guns.components.Bolts;
import cs.guns.components.Bolts.Bolt;
import cs.guns.components.FireModes;
import cs.guns.components.FireModes.FireMode;
import cs.guns.components.GunModifier;
import cs.guns.components.GunModifierType;
import static cs.guns.components.GunModifierType.*;
import cs.guns.components.Magazines;
import cs.guns.components.Magazines.Magazine;
import cs.guns.components.Sights;
import cs.guns.components.Sights.Scope;
import cs.guns.components.Stocks;
import cs.guns.components.Stocks.Stock;
import cs.guns.skeleton.GunSkeletons.GunSkeleton;
import cs.guns.weps.Guns.CrackshotGun;

/**
 *
 * @author jesse
 */
public class GunID
{
    
    
    private static final String GUN_MOD_ID_SEP = "_";
    private static final String CS_ID_SEP = "X";
    
    private final String uniqueID;
    private final int skeletonIndex;
    private final int attatchmentOneIndex;
    private final int attatchmentTwoIndex;
    private final int attatchmentThreeIndex;
    private final int barrelIndex;
    private final int boltIndex;
    private final int firemodeTypeIndex;
    private final int magazineIndex;
    private final int scopeIndex;
    private final int stockIndex;
    
    public GunID(final GunSkeleton skeleton,
                final Attachment attatchmentOne,
                final Attachment attatchmentTwo,
                final Attachment attatchmentThree,
                final Barrel barrel,
                final Bolt bolt,
                final FireMode firemodeType,
                final Magazine magazine,
                final Scope scopeType,
                final Stock stock)
    {
        this.skeletonIndex = skeleton.getIndex();
        this.attatchmentOneIndex = getGunModifierID(attatchmentOne);
        this.attatchmentTwoIndex = getGunModifierID(attatchmentTwo);
        this.attatchmentThreeIndex = getGunModifierID(attatchmentThree);
        this.barrelIndex = getGunModifierID(barrel);
        this.boltIndex = getGunModifierID(bolt);
        this.firemodeTypeIndex = getGunModifierID(firemodeType);
        this.magazineIndex = getGunModifierID(magazine);
        this.scopeIndex = getGunModifierID(scopeType);
        this.stockIndex = getGunModifierID(stock);
        
        final StringBuilder stb = new StringBuilder();

        stb.append(skeletonIndex);         stb.append(GUN_MOD_ID_SEP);
        stb.append(barrelIndex);           stb.append(GUN_MOD_ID_SEP);
        stb.append(boltIndex);             stb.append(GUN_MOD_ID_SEP);
        stb.append(firemodeTypeIndex);     stb.append(GUN_MOD_ID_SEP);
        stb.append(magazineIndex);         stb.append(GUN_MOD_ID_SEP);
        stb.append(scopeIndex);
                    
        stb.append(CS_ID_SEP);

        stb.append(attatchmentOneIndex);   stb.append(GUN_MOD_ID_SEP);
        stb.append(attatchmentTwoIndex);   stb.append(GUN_MOD_ID_SEP);
        stb.append(attatchmentThreeIndex); stb.append(GUN_MOD_ID_SEP);
        stb.append(stockIndex);            

        if (isValidID(stb.toString()))
            this.uniqueID = stb.toString();
        else
            this.uniqueID = null;
    }
    
    public GunID(final CrackshotGun gun,
                 final GunModifier modifier,
                 final GunModifierType type)
    {
        this(gun,
            type.equals(SLOT_ONE_ATTACHMENT)   ? (ProjectileAttachment)modifier : gun.getAttachmentOneMod(), 
            type.equals(SLOT_TWO_ATTATCHMENT)   ? (ProjectileAttachment)modifier : gun.getAttachmentTwoMod(),  
            type.equals(SLOT_THREE_ATTATCHMENT) ? (ProjectileAttachment)modifier : gun.getAttachmentThreeMod(), 
            type.equals(BARREL)                 ? (Barrel)modifier      : gun.getBarrelMod(), 
            type.equals(BOLT)                   ? (Bolt)modifier        : gun.getBoltMod(), 
            type.equals(FIREMODE)               ? (FireMode)modifier    : gun.getFireModeMod(), 
            type.equals(MAGAZINE)               ? (Magazine)modifier    : gun.getMagazineMod(), 
            type.equals(SIGHT)                  ? (Scope)modifier       : gun.getScopeMod(), 
            type.equals(STOCK)                  ? (Stock)modifier       : gun.getStockMod());
    }
    
    public GunID(final String ID)
    {
        if (isValidID(ID))
        {
            final String[] split = ID.split(CS_ID_SEP);
            final String[] csInfo = split[0].split(GUN_MOD_ID_SEP);
            final String[] attInfo = split[1].split(GUN_MOD_ID_SEP);
            this.skeletonIndex = Integer.valueOf(csInfo[0]);
            this.barrelIndex = Integer.valueOf(csInfo[1]);
            this.boltIndex = Integer.valueOf(csInfo[2]);
            this.firemodeTypeIndex = Integer.valueOf(csInfo[3]);
            this.magazineIndex = Integer.valueOf(csInfo[4]);
            this.scopeIndex = Integer.valueOf(csInfo[5]);
            
            
            this.attatchmentOneIndex = Integer.valueOf(attInfo[0]);
            this.attatchmentTwoIndex = Integer.valueOf(attInfo[1]);
            this.attatchmentThreeIndex = Integer.valueOf(attInfo[2]);
            this.stockIndex = Integer.valueOf(attInfo[3]);
            
            this.uniqueID = ID;
        }
        else
        {
            this.uniqueID = null;
            this.attatchmentOneIndex = -1;
            this.attatchmentTwoIndex = -1;
            this.attatchmentThreeIndex = -1;
            this.boltIndex = -1;
            this.firemodeTypeIndex = -1;
            this.magazineIndex = -1;
            this.scopeIndex = -1;
            this.stockIndex = -1;
            this.skeletonIndex = -1;
            this.barrelIndex = -1;
        }
    }
    
    public String getUniqueID()
    {
        return uniqueID;
    }
    
    public String getCSUniqueID()
    {
        if (uniqueID == null)
            return null;
        else
            return uniqueID.split(CS_ID_SEP)[0];
    }
    
    public boolean isValid() 
    {
        return uniqueID != null;
    }
    
    @Override public String toString()
    {
        return this.uniqueID;
    }
    
    public GunSkeleton getSkeleton()       { return GunSkeletons.getInstance().get(skeletonIndex); }
    public Attachment getAttatchmentOne()   { return Attachments.getInstance().get(attatchmentOneIndex); }
    public Attachment getAttatchmentTwo()   { return Attachments.getInstance().get(attatchmentTwoIndex); }
    public Attachment getAttatchmentThree() { return Attachments.getInstance().get(attatchmentThreeIndex); }
    public Barrel getBarrel()                { return Barrels.getInstance().get(barrelIndex); }
    public Bolt getBolt()                    { return Bolts.getInstance().get(boltIndex); }
    public FireMode getFireMode()            { return FireModes.getInstance().get(firemodeTypeIndex); }
    public Magazine getMagazine()            { return Magazines.getInstance().get(magazineIndex); }
    public Scope getScope()                  { return Sights.getInstance().get(scopeIndex); }
    public Stock getStock()                  { return Stocks.getInstance().get(stockIndex); }
    
    /**
     * Checks to see if the ID is valid by checking length and comparing Skeleton
     * and GunModifier values to see if they're in range of their instance arrays.
     * @param ID ID to be checked.
     * @return True if the ID is valid. False otherwise.
     */
    static public boolean isValidID(final String ID)
    {
        final String[] split = ID.split(CS_ID_SEP);
        if (split.length != 2)
            return false;
        
        final String[] csInfo = split[0].split(GUN_MOD_ID_SEP);
        final String[] attInfo = split[1].split(GUN_MOD_ID_SEP);
     
        if (csInfo.length != 6 && attInfo.length != 4)
            return false;
        
        final int[] csIDXs = new int[csInfo.length];
        final int[] attIDXs = new int[attInfo.length];
        
        for (int i = 0; i < csInfo.length; i++)
            csIDXs[i] = Integer.valueOf(csInfo[i]);
        for (int i = 0; i < attInfo.length; i++)
            attIDXs[i] = Integer.valueOf(attInfo[i]);
        
        return csIDXs[0] < GunSkeletons.getInstance().initialize()
                && csIDXs[1] < Barrels.getInstance().initialize()
                && csIDXs[2] < Bolts.getInstance().initialize()
                && csIDXs[3] < FireModes.getInstance().initialize()
                && csIDXs[4] < Magazines.getInstance().initialize()
                && csIDXs[5] < Sights.getInstance().initialize()
                && attIDXs[0] < Attachments.getInstance().initialize()
                && attIDXs[1] < Attachments.getInstance().initialize()
                && attIDXs[2] < Attachments.getInstance().initialize()
                && attIDXs[3] < Stocks.getInstance().initialize();
    }
    
    private static int getGunModifierID(final GunModifier mod)
    {
        if (mod != null)
            return mod.getIndex();
        else
            return -1;
    }
}
