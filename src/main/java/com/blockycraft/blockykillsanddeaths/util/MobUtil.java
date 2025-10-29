package com.blockycraft.blockykillsanddeaths.util;

import org.bukkit.entity.Entity;

public class MobUtil {
    /**
     * Retorna nome amigável para exibir o nome do mob/entity (site, ranking).
     */
    public static String getMobName(Entity entity) {
        if (entity == null) return "UNKNOWN";
        String mobName = entity.getClass().getSimpleName(); // Ex: Pig, Zombie, Skeleton, etc
        // Capitaliza a primeira letra (restante minúsculo)
        if (mobName.length() == 0) return "Mob";
        return mobName.substring(0, 1).toUpperCase() + mobName.substring(1).toLowerCase();
    }

    /**
     * Retorna um "ID" (ex: "zombie", "cow", "pigzombie") para salvar no banco etc.
     */
    public static String getMobId(Entity entity) {
        if (entity == null) return "unknown";
        String mobName = entity.getClass().getSimpleName();
        return mobName.toLowerCase();
    }
}
