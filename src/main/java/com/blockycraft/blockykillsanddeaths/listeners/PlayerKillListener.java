package com.blockycraft.blockykillsanddeaths.listeners;

import com.blockycraft.blockykillsanddeaths.BlockyKillsAndDeaths;
import com.blockycraft.blockykillsanddeaths.database.DatabaseManagerKillsAndDeaths;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerKillListener implements Listener {
    private final BlockyKillsAndDeaths plugin;

    public PlayerKillListener(BlockyKillsAndDeaths plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        // Confirma se quem morreu é jogador
        if (entity instanceof Player) {
            Player victim = (Player) entity;

            EntityDamageEvent lastDamageEvent = victim.getLastDamageCause();
            if (lastDamageEvent instanceof EntityDamageByEntityEvent) {
                Entity damager = ((EntityDamageByEntityEvent) lastDamageEvent).getDamager();

                // Agora garante que foi PvP
                if (damager instanceof Player && !damager.equals(victim)) {
                    Player killer = (Player) damager;
                    String killerUUID = killer.getUniqueId().toString();
                    String killerUsername = killer.getName();
                    String victimUUID = victim.getUniqueId().toString();
                    String victimUsername = victim.getName();
                    long killedAt = System.currentTimeMillis() / 1000L;
                    DatabaseManagerKillsAndDeaths db = plugin.getDatabaseManagerKillsAndDeaths();
                    db.logPlayerKill(killerUUID, killerUsername, victimUUID, victimUsername, killedAt);
                }
            }
        }
    }
}
