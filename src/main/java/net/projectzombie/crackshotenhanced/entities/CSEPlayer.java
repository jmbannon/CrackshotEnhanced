/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.entities;

import net.projectzombie.crackshotenhanced.guns.physical.weps.CrackshotGunItemStack;
import net.projectzombie.crackshotenhanced.guns.weps.CrackshotGun;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.DEFAULT_PLAYER_SPEED;

/**
 *
 * @author jb
 */
public class CSEPlayer extends CSELivingEntity<Player>
{
    public CSEPlayer(final Player player) {
        super(player);
    }

    public ArrayList<CrackshotGun> getWeaponsInHotbar() {
        final ArrayList<CrackshotGun> toRet = new ArrayList<>();
        final PlayerInventory inv = super.toBukkit().getInventory();
        CrackshotGunItemStack tmpGunItemStack;

        for (int i = 0; i < 9; i++) {
            tmpGunItemStack = new CrackshotGunItemStack(inv.getItem(i));
            if (tmpGunItemStack.isValid()) {
                toRet.add(tmpGunItemStack.getGun());
            }
        }
        return toRet;
    }

    @Override public double getDefaultSpeed() { return DEFAULT_PLAYER_SPEED; }
    @Override public double getSpeed() { return super.toBukkit().getWalkSpeed(); }
    @Override public void setSpeed(final double speed) { super.toBukkit().setWalkSpeed((float)speed); }

    /**
     * Checks to see which weapons are in the player's hotbar. Will adjust the speed by multiplying MC's default speed
     * with the gun containing the LOWEST speed multiplier.
     */
    public void adjustRunningSpeed() {
        double lowestSpeed = Double.MAX_VALUE;
        for (CrackshotGun gun : getWeaponsInHotbar()) {
            lowestSpeed = Math.min(lowestSpeed, gun.getAttributes().getMotionSet().getTotalRunningSpeedMultiplier());
        }
        lowestSpeed = (lowestSpeed == Double.MAX_VALUE) ? getDefaultSpeed() : lowestSpeed * getDefaultSpeed();
        this.setSpeed(lowestSpeed);
    }

    public boolean isCrouching() {
        return super.toBukkit().isSneaking();
    }

    public boolean isStanding() {
        return super.toBukkit().getVelocity().equals(new Vector().zero());
    }

    public boolean isMoving() {
        return !isStanding();
    }
}
