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
public interface BleedoutAttributes extends ProjectileHitAttributes
{
    public double getBleedoutDurationValue();
    public double getBleedoutDurationMultiplier();
    
    public double getBleedoutDamageValuePerSecond();
    public double getBleedoutDamageMultiplerFromShrapnel();
}
