/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.utilities;

import net.projectzombie.crackshotenhanced.guns.physical.weps.CrackshotGunItemStack;
import net.projectzombie.crackshotenhanced.guns.weps.Guns;
import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.DEFAULT_PLAYER_SPEED;

/**
 *
 * @author jb
 */
public class CSEPlayer
{
    private final Player player;
    public CSEPlayer(final Player player) {
        this.player = player;
    }

    public ArrayList<Guns.CrackshotGun> getWeaponsInHotbar() {
        final ArrayList<Guns.CrackshotGun> toRet = new ArrayList<>();
        final PlayerInventory inv = player.getInventory();
        CrackshotGunItemStack tmpGunItemStack;

        for (int i = 0; i < 9; i++) {
            tmpGunItemStack = new CrackshotGunItemStack(inv.getItem(i));
            if (tmpGunItemStack.isValid()) {
                toRet.add(tmpGunItemStack.getGun());
            }
        }
        return toRet;
    }

    /**
     * Checks to see which weapons are in the player's hotbar. Will adjust the speed by multiplying MC's default speed
     * with the gun containing the LOWEST speed multiplier.
     */
    public void adjustRunningSpeed() {
        double lowestSpeed = Double.MAX_VALUE;
        for (Guns.CrackshotGun gun : getWeaponsInHotbar()) {
            lowestSpeed = Math.min(lowestSpeed, gun.getMotionSet().getTotalRunningSpeedMultiplier());
        }
        lowestSpeed = (lowestSpeed == Double.MAX_VALUE) ? DEFAULT_PLAYER_SPEED : lowestSpeed * DEFAULT_PLAYER_SPEED;
        player.setWalkSpeed((float)lowestSpeed);
    }
}
