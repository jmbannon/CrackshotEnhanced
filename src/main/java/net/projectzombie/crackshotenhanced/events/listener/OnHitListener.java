package net.projectzombie.crackshotenhanced.events.listener;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import net.projectzombie.crackshotenhanced.guns.weps.CrackshotGun;
import net.projectzombie.crackshotenhanced.static_maps.ConnectedPlayers;
import net.projectzombie.crackshotenhanced.entities.CSEPlayer;
import net.projectzombie.crackshotenhanced.static_maps.Guns;
import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Created by jb on 11/22/16.
 */
public class OnHitListener implements Listener {

    public OnHitListener() { /* Do nothing. */ }
    /**
     * Testing
     * @param event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onHitEvent(WeaponDamageEntityEvent event)
    {
        final String gunID = event.getDamager().getCustomName();
        final CSEPlayer player = ConnectedPlayers.get(event.getPlayer());
        final CrackshotGun gun = Guns.get(gunID);

        if (gun != null && event.getVictim() instanceof LivingEntity)
        {
            final double damage = calculateDamageOnHit(player, gun, (LivingEntity)event.getVictim(), event.isHeadshot());
            Main.getPlugin().getLogger().info("Setting damage: " + damage);
            event.setDamage(damage);
        }
        else
        {
            Main.getPlugin().getLogger().info("Can not find gun with ID: " + gunID);
        }
    }

    /**
     * Calculates damage on hit.
     * TODO:
     * - damage over time
     * - check entities for armors.
     * @param damager Player
     * @param gun
     * @param victim
     * @param headshot
     * @return
     */
    private double calculateDamageOnHit(final CSEPlayer damager,
                                        final CrackshotGun gun,
                                        final LivingEntity victim,
                                        final boolean headshot)
    {
        double toReturn = gun.getTotalDamageOnHit();
        if (headshot) {
            toReturn += gun.getTotalHeadshotDamage();
        }
        if (gun.isCrit()) {
            toReturn += gun.getTotalDamageOnHit();
        }
        return toReturn;
    }
}
