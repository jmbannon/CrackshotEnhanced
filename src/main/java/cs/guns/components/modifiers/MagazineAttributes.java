/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.components.modifiers;

/**
 *
 * @author jesse
 */
public interface MagazineAttributes extends SkeletonAttributes
{
    public int getMagazineSizeModifier();
    public double getMagazineSizeMultiplier();
    
    public double getReloadSpeedMultiplier();
}
