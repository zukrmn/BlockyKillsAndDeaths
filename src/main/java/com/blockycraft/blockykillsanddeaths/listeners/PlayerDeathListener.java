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

public class PlayerDeathListener implements Listener {
    private final BlockyKillsAndDeaths plugin;

    public PlayerDeathListener(BlockyKillsAndDeaths plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        // Só processa se foi um jogador morto
        if (entity instanceof Player) {
            Player victim = (Player) entity;
            DatabaseManagerKillsAndDeaths db = plugin.getDatabaseManagerKillsAndDeaths();
            long killedAt = System.currentTimeMillis() / 1000L;

            // Causa PvP ou PvE? Checa se há um "agressor"
            EntityDamageEvent lastDamageEvent = victim.getLastDamageCause();
            if (lastDamageEvent instanceof EntityDamageByEntityEvent) {
                Entity damager = ((EntityDamageByEntityEvent) lastDamageEvent).getDamager();
                if (damager instanceof Player) {
                    // PvP - mas não devemos registrar aqui (PlayerKillListener já faz isso)
                    // Nada faz aqui para evitar redundância
                    return;
                } else {
                    // PvE: Morto por mob hostil
                    String mobName = damager.getClass().getSimpleName(); // Ex: Zombie, Skeleton, etc
                    String mobId = mobName.toLowerCase();
                    db.logMobDeath(victim.getUniqueId().toString(), victim.getName(), mobName, mobId, killedAt);
                    return;
                }
            } else {
                // Morte natural (queda, fogo, sufocamento, etc)
                String reason = (lastDamageEvent != null && lastDamageEvent.getCause() != null)
                        ? lastDamageEvent.getCause().toString()
                        : "UNKNOWN";
                db.logNaturalDeath(victim.getUniqueId().toString(), victim.getName(), reason, killedAt);
            }
        }
    }
}
