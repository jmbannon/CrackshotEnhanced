/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.modifiers;

/**
 *
 * @author jesse
 */
public interface BaseDamageAttributes extends ProjectileHitAttributes
{
    /**
     * Value - increase or decrease to base damage.
     * @return Double to be added to damage.
     */
    public double getDamageValue();
    
    /**
     * Multiplier - percentage to modify base damage.
     * @return Percentage to be multiplied to base damage (0, inf).
     */
    public double getDamageMultiplier();
}
