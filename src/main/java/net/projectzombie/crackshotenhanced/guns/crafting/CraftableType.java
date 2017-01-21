/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.crafting;

import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * Assigns each GunModifier a matrix index within the crafting inventory.
 * 
 *     [,0] [,1] [,2]
 * [0,]  0    1    2
 * [1,]  3    4    5    =   9
 * [2,]  6    7    8
 * 
 */
public enum CraftableType
{   
    // TODO: CHANGE MATERIAL TYPE
    SLOT_ONE_ATTACHMENT(0, Material.PRISMARINE_SHARD, 0),
    SIGHT(1, Material.COAL, 1),
    BOLT(2, Material.SUGAR, 0),
    FIREMODE(2, Material.EGG, 0),
    BARREL(3, Material.QUARTZ, 0),
    SKELETON(4, null, 0),
    STOCK(5, Material.BLAZE_ROD, 0),
    SLOT_TWO_ATTACHMENT(6, Material.PRISMARINE_CRYSTALS, 0),
    MAGAZINE(7, Material.LEATHER, 0),
    SLOT_THREE_ATTACHMENT(8, Material.RABBIT_HIDE, 0);

    private final int matrixIndex;
    private final Material material;
    private final int materialData;
 
    CraftableType(final int matrixIndex,
                  final Material material,
                  final int materialData)
    {
        this.matrixIndex = matrixIndex;
        this.material = material;
        this.materialData = materialData;
    }
 
    public int getMatrixIndex() { return matrixIndex; }
 
    public Material getMaterial()
    {
        return material;
    }
    
    public short getMaterialDataShort()
    {
        return (short)materialData;
    }
    
    public MaterialData getMaterialData()
    {
        if (material != null)
            return new MaterialData(material, (byte)materialData);
        else
            return null;
    }
    
    public GunModifier getGunModifier(final int uniqueID)
    {
        switch(this) {
            case SLOT_ONE_ATTACHMENT: return ProjectileAttachments.getSlotOneInstance().get(uniqueID);
            case SLOT_TWO_ATTACHMENT: return ProjectileAttachments.getSlotOneInstance().get(uniqueID);
            case SLOT_THREE_ATTACHMENT: return ProjectileAttachments.getSlotOneInstance().get(uniqueID);
            case BARREL: return Barrels.getInstance().get(uniqueID);
            case BOLT: return Bolts.getInstance().get(uniqueID);
            case FIREMODE: return FireModes.getInstance().get(uniqueID);
            case MAGAZINE: return Magazines.getInstance().get(uniqueID);
            case SIGHT: return Sights.getInstance().get(uniqueID);
            case STOCK: return Stocks.getInstance().get(uniqueID);
            case SKELETON: return GunSkeletons.getInstance().get(uniqueID);
            default: return null;
        }
    }
}