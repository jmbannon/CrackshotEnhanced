/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.modifiers;

/**
 *
 * @author jb
 */
public interface HeadshotAttributes extends ProjectileHitAttributes
{
    public double getHeadshotDamageModifier();
    public double getHeadshotDamageMultiplier();
}
