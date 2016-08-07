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
public interface ProjectileAttributes extends SkeletonAttributes
{
    public int getProjectileAmount();
    public double getProjectileSpeedMultiplier();
    
    public int getProjectileRangeValue();
    public double getProjectileRangeMultiplier();
}
