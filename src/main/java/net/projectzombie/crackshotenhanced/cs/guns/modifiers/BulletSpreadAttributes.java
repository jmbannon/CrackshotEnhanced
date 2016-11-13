/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.modifiers;

/**
 *
 * @author jesse
 */
public interface BulletSpreadAttributes extends ProjectileHitAttributes
{
    /**
     * Multiplier - percentage to modifiy the bullet spread.
     * @return Percentage to modifiy the bullet spread (0, inf).
     */
    public double getBulletSpreadMultiplier();
    
}
