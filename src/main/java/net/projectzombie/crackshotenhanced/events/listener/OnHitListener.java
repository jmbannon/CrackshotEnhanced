package net.projectzombie.crackshotenhanced.events.listener;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import net.projectzombie.crackshotenhanced.entities.CSELivingEntity;
import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import net.projectzombie.crackshotenhanced.static_maps.ConnectedPlayers;
import net.projectzombie.crackshotenhanced.entities.CSEPlayer;
import net.projectzombie.crackshotenhanced.static_maps.EntityTimedEvents;
import net.projectzombie.crackshotenhanced.static_maps.Guns;
import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class OnHitListener implements Listener {

    public OnHitListener() { /* Do nothing. */ }
    /**
     * Calculates the damage the victim will receive from the player's CrackshotGun.
     * @see CrackshotGun
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHitEvent(WeaponDamageEntityEvent event)
    {
        if (!(event.getVictim() instanceof LivingEntity)) {
            return;
        }

        final LivingEntity victim = (LivingEntity)event.getVictim();

        final String gunID = event.getDamager().getCustomName();
        final CSEPlayer player = ConnectedPlayers.get(event.getPlayer());
        final CSELivingEntity<LivingEntity> entity = new CSELivingEntity<>(victim);

        final CrackshotGun gun = Guns.get(gunID);
        if (gun == null) {
            Main.severe("Can not find gun with ID: " + gunID);
            return;
        }

        // Cancel the event if you are shooting yourself
        if (player.getUniqueId().equals(entity.getUniqueId())) {
            event.setCancelled(true);
        } else {
            if (event.getDamager() instanceof Projectile) {
                event.setCancelled(true);
                final double damage = calculateProjectileDamageOnHit(player, gun, entity, event.isHeadshot());
                if (player.isValid()) {
                    victim.damage(damage, event.getPlayer());
                } else {
                    victim.damage(damage);
                }
            } else {
                victim.damage(event.getDamage());
            }

        }
    }

    /**
     * Calculates damage on hit and applies all effects to the victim.
     * <p>
     * The event's following this event will be cancelled, negating any default Minecraft effect the projectile
     * has on the victim.
     * TODO:
     * - damage over time
     * - check entities for armors.
     * @param damager Player
     * @param gun
     * @param victim
     * @param headshot
     * @return
     */
    private double calculateProjectileDamageOnHit(final CSEPlayer damager,
                                                  final CrackshotGun gun,
                                                  final CSELivingEntity<LivingEntity> victim,
                                                  final boolean headshot)
    {
        this.setKnockback(damager, victim, gun);

        double toReturn = gun.getTotalDamageOnHit();
        if (headshot) {
            toReturn += gun.getTotalHeadshotDamage();
        }
        if (gun.isCrit()) {
            toReturn += gun.getTotalDamageOnHit();
        }
        if (gun.isStun()) {
            Main.info("IS STUN");
            EntityTimedEvents.add(victim, gun.getAttributes().getStunSet());
        }
        if (gun.isIgnite()) {
            Main.info("IS IGNITE");
            EntityTimedEvents.add(victim, gun.getAttributes().getIgniteSet());
        }
        EntityTimedEvents.add(victim, gun.getAttributes().getBleedout());
        return toReturn;
    }

    private void setKnockback(final CSEPlayer damager,
                              CSELivingEntity<LivingEntity> victim,
                              final CrackshotGun gun) {
        final double scalar = gun.getAttributes().getKnockbackSet().getKnockbackVelocityVectorScalar();

        // If the player is online
        if (damager.isValid()) {
            Location damagerLocation = damager.toBukkit().getLocation();
            Location victimLocation = victim.toBukkit().getLocation();

            Location velocityDirection = victimLocation.subtract(damagerLocation);
            Vector velocityVector = velocityDirection.toVector().normalize().multiply(scalar);
            victim.toBukkit().setVelocity(velocityVector);
        }
    }
}
