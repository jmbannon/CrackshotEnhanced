/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.entities;

import net.projectzombie.crackshotenhanced.guns.physical.gun.CrackshotGunItemStack;
import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

import static net.projectzombie.crackshotenhanced.guns.utilities.Constants.DEFAULT_PLAYER_SPEED;

/**
 *
 * @author jb
 */
public class CSEPlayer extends CSELivingEntity<Player>
{
    /**
     * Total amount of squared distance in a PlayerMoveEvent to determine whether a player is standing or moving.
     * Approximately .0006 less than the squared distance when crouching and moving at full in a PlayerMoveEvent.
     */
    static private final double CROUCH_MOVE_SQUARED_DISTANCE = .004098;

    /** Total amount of time in ms to determine whether a player is standing or moving. */
    static private final long STANDING_IDLE_MS = 120;

    private boolean isZoomed = false;

    private double movementDistanceSquared = 0.0;
    private long movementTimestamp = 0;

    public CSEPlayer(final Player player) {
        super(player);
    }

    /** @return ArrayList of CrackshotGuns within the player's Hotbar. */
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
        double lowestSpeedMultiplier = Double.MAX_VALUE;
        for (CrackshotGun gun : getWeaponsInHotbar()) {
            lowestSpeedMultiplier = Math.min(lowestSpeedMultiplier, gun.getAttributes().getMotionSet().getTotalRunningSpeedMultiplier());
        }
        final double speedMultiplier = (lowestSpeedMultiplier == Double.MAX_VALUE) ? 1.0 : lowestSpeedMultiplier;
        this.setSpeed(speedMultiplier * DEFAULT_PLAYER_SPEED);
    }

    public void setZoomed(final boolean isZoomed) {
        this.isZoomed = isZoomed;
    }

    /** Update the player's movement speed. */
    public void onPlayerMove(PlayerMoveEvent event) {
        this.movementTimestamp = System.currentTimeMillis();
        this.movementDistanceSquared =  event.getTo().toVector().distanceSquared(event.getFrom().toVector());
    }

    /** @return True if the player is zoomed in with a CrackshotGun. False otherwise. */
    public boolean isZoomed() { return isZoomed; }

    /** @return True if the player is crouched (sneaking). False otherwise. */
    public boolean isCrouching() { return super.toBukkit().isSneaking(); }

    /** @return True if the player is standing idle. False otherwise. */
    public boolean isStanding() { return !isMoving(); }

    /** @return True if the player is moving (!isStanding()). False otherwise. */
    public boolean isMoving() {
        final long lastMovement = System.currentTimeMillis() - this.movementTimestamp;
        return this.movementDistanceSquared > CROUCH_MOVE_SQUARED_DISTANCE && lastMovement < STANDING_IDLE_MS;
    }
}
