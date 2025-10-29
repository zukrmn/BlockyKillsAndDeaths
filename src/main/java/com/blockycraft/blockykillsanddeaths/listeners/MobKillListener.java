package com.blockycraft.blockykillsanddeaths.listeners;

import com.blockycraft.blockykillsanddeaths.BlockyKillsAndDeaths;
import com.blockycraft.blockykillsanddeaths.database.DatabaseManagerKillsAndDeaths;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobKillListener implements Listener {
    private final BlockyKillsAndDeaths plugin;

    public MobKillListener(BlockyKillsAndDeaths plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        // Só processa se NÃO é um player morto (PvP é outro listener)
        if (!(entity instanceof Player)) {
            // Tenta detectar se foi kill por jogador
            EntityDamageByEntityEvent lastDamageEvent = null;
            if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                lastDamageEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
            }
            if (lastDamageEvent != null && lastDamageEvent.getDamager() instanceof Player) {
                Player killer = (Player) lastDamageEvent.getDamager();
                String killerUUID = killer.getUniqueId().toString();
                String killerUsername = killer.getName();

                // ID do mob (classe simples)
                String mobName = entity.getClass().getSimpleName(); // ex: Zombie, Cow, Sheep, Pig, etc.
                String mobId = mobName.toLowerCase();

                long killedAt = System.currentTimeMillis() / 1000L;

                DatabaseManagerKillsAndDeaths db = plugin.getDatabaseManagerKillsAndDeaths();
                db.logMobKill(killerUUID, killerUsername, mobName, mobId, killedAt);
            }
        }
    }
}
