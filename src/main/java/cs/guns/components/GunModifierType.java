/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components;

import cs.guns.utilities.GunUtils;
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
public enum GunModifierType
{   
    // TODO: CHANGE MATERIAL TYPE
    SLOT_ONE_ATTACHMENT(0, "PRISMARINE_SHARD", 0),
    SLOT_TWO_ATTATCHMENT(6, "PRISMARINE_CRYSTALS", 0),
    SLOT_THREE_ATTATCHMENT(8, "RABBIT_HIDE", 0),
    BARREL(3, "QUARTZ", 0),
    BOLT(2, "SUGAR", 0),
    FIREMODE(2, "EGG", 0),
    MAGAZINE(7, "LEATHER", 0),
    SIGHT(1, "COAL", 1),
    STOCK(5, "BLAZE_ROD", 0);
 
    private final int matrixIndex;
    private final Material material;
    private final int materialData;
 
    private GunModifierType(final int matrixIndex,
                            final String material,
                            final int materialData)
    {
        this.matrixIndex = matrixIndex;
        this.material = GunUtils.matchMaterial(material);
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
        if (this.equals(SLOT_ONE_ATTACHMENT)
            || this.equals(SLOT_TWO_ATTATCHMENT)
            || this.equals(SLOT_THREE_ATTATCHMENT))
        {
            return Attachments.getInstance().get(uniqueID);
        }
        else if (this.equals(BARREL)) return Barrels.getInstance().get(uniqueID);
        else if (this.equals(BOLT)) return Bolts.getInstance().get(uniqueID);
        else if (this.equals(FIREMODE)) return FireModes.getInstance().get(uniqueID);
        else if (this.equals(MAGAZINE)) return Magazines.getInstance().get(uniqueID);
        else if (this.equals(SIGHT)) return Sights.getInstance().get(uniqueID);
        else if (this.equals(STOCK)) return Stocks.getInstance().get(uniqueID);
        else return null;
    }
}