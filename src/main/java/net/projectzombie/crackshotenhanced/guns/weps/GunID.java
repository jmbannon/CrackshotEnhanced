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
package net.projectzombie.crackshotenhanced.guns.weps;

import net.projectzombie.crackshotenhanced.guns.crafting.GunModifierType;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.main.Main;

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
    
    public GunID(final GunSkeletons.GunSkeleton skeleton,
                final ProjectileAttachments.ProjectileAttachment attatchmentOne,
                final ProjectileAttachments.ProjectileAttachment attatchmentTwo,
                final ProjectileAttachments.ProjectileAttachment attatchmentThree,
                final Barrels.Barrel barrel,
                final Bolts.Bolt bolt,
                final FireModes.FireMode firemodeType,
                final Magazines.Magazine magazine,
                final Sights.Sight sightType,
                final Stocks.Stock stock)
    {
        this.skeletonIndex = skeleton.getIndex();
        this.attatchmentOneIndex = getGunModifierID(attatchmentOne);
        this.attatchmentTwoIndex = getGunModifierID(attatchmentTwo);
        this.attatchmentThreeIndex = getGunModifierID(attatchmentThree);
        this.barrelIndex = getGunModifierID(barrel);
        this.boltIndex = getGunModifierID(bolt);
        this.firemodeTypeIndex = getGunModifierID(firemodeType);
        this.magazineIndex = getGunModifierID(magazine);
        this.scopeIndex = getGunModifierID(sightType);
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
            type.equals(GunModifierType.SLOT_ONE_ATTACHMENT)   ? (ProjectileAttachments.ProjectileAttachment)modifier : gun.getAttachmentOneMod(),
            type.equals(GunModifierType.SLOT_TWO_ATTATCHMENT)   ? (ProjectileAttachments.ProjectileAttachment)modifier : gun.getAttachmentTwoMod(),
            type.equals(GunModifierType.SLOT_THREE_ATTATCHMENT) ? (ProjectileAttachments.ProjectileAttachment)modifier : gun.getAttachmentThreeMod(),
            type.equals(GunModifierType.BARREL)                 ? (Barrels.Barrel)modifier      : gun.getBarrelMod(),
            type.equals(GunModifierType.BOLT)                   ? (Bolts.Bolt)modifier        : gun.getBoltMod(),
            type.equals(GunModifierType.FIREMODE)               ? (FireModes.FireMode)modifier    : gun.getFireModeMod(),
            type.equals(GunModifierType.MAGAZINE)               ? (Magazines.Magazine)modifier    : gun.getMagazineMod(),
            type.equals(GunModifierType.SIGHT)                  ? (Sights.Sight)modifier       : gun.getScopeMod(),
            type.equals(GunModifierType.STOCK)                  ? (Stocks.Stock)modifier       : gun.getStockMod());
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
    
    public String getUniqueID() { return uniqueID; }
    public String getCSUniqueID() { return (uniqueID != null) ? uniqueID.split(CS_ID_SEP)[0] : null; }
    public boolean isValid() { return uniqueID != null; }
    @Override public String toString() { return this.uniqueID; }
    
    public GunSkeletons.GunSkeleton getSkeleton()       { return GunSkeletons.getInstance().get(skeletonIndex); }
    public ProjectileAttachments.ProjectileAttachment getAttatchmentOne()   { return ProjectileAttachments.getInstance().get(attatchmentOneIndex); }
    public ProjectileAttachments.ProjectileAttachment getAttatchmentTwo()   { return ProjectileAttachments.getInstance().get(attatchmentTwoIndex); }
    public ProjectileAttachments.ProjectileAttachment getAttatchmentThree() { return ProjectileAttachments.getInstance().get(attatchmentThreeIndex); }
    public Barrels.Barrel getBarrel()                { return Barrels.getInstance().get(barrelIndex); }
    public Bolts.Bolt getBolt()                    { return Bolts.getInstance().get(boltIndex); }
    public FireModes.FireMode getFireMode()            { return FireModes.getInstance().get(firemodeTypeIndex); }
    public Magazines.Magazine getMagazine()            { return Magazines.getInstance().get(magazineIndex); }
    public Sights.Sight getScope()                  { return Sights.getInstance().get(scopeIndex); }
    public Stocks.Stock getStock()                  { return Stocks.getInstance().get(stockIndex); }
    
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
        
        return csIDXs[0] < GunSkeletons.getInstance().size()
                && csIDXs[1] < Barrels.getInstance().size()
                && csIDXs[2] < Bolts.getInstance().size()
                && csIDXs[3] < FireModes.getInstance().size()
                && csIDXs[4] < Magazines.getInstance().size()
                && csIDXs[5] < Sights.getInstance().size()
                && attIDXs[0] < ProjectileAttachments.getInstance().size()
                && attIDXs[1] < ProjectileAttachments.getInstance().size()
                && attIDXs[2] < ProjectileAttachments.getInstance().size()
                && attIDXs[3] < Stocks.getInstance().size();
    }
    
    private static int getGunModifierID(final GunModifier mod) { return mod != null ? mod.getIndex() : -1; }
}
