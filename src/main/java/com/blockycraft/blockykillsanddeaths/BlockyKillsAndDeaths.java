package com.blockycraft.blockykillsanddeaths;

import com.blockycraft.blockykillsanddeaths.database.DatabaseManagerKillsAndDeaths;
import com.blockycraft.blockykillsanddeaths.listeners.PlayerKillListener;
import com.blockycraft.blockykillsanddeaths.listeners.PlayerDeathListener;
import com.blockycraft.blockykillsanddeaths.listeners.MobKillListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class BlockyKillsAndDeaths extends JavaPlugin {
    private static BlockyKillsAndDeaths instance;
    private DatabaseManagerKillsAndDeaths databaseManagerKillsAndDeaths;
    public static final Logger log = Logger.getLogger("Minecraft"); // Compatível versão antiga

    @Override
    public void onEnable() {
        instance = this;
        try {
            File dbFile = new File(getDataFolder(), "killsanddeaths.db");
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            databaseManagerKillsAndDeaths = new DatabaseManagerKillsAndDeaths(dbFile.getAbsolutePath());
            log.info("[BlockyKillsAndDeaths] Banco de dados inicializado.");
        } catch (Exception e) {
            log.severe("[BlockyKillsAndDeaths] Erro ao inicializar banco!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new PlayerKillListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new MobKillListener(this), this);
        log.info("[BlockyKillsAndDeaths] Listeners registrados.");
        log.info("[BlockyKillsAndDeaths] Plugin ativado!");
    }

    @Override
    public void onDisable() {
        if (databaseManagerKillsAndDeaths != null) {
            databaseManagerKillsAndDeaths.closeConnection();
            log.info("[BlockyKillsAndDeaths] Banco de dados fechado.");
        }
        log.info("[BlockyKillsAndDeaths] Plugin desativado.");
    }

    public static BlockyKillsAndDeaths getInstance() {
        return instance;
    }

    public DatabaseManagerKillsAndDeaths getDatabaseManagerKillsAndDeaths() {
        return databaseManagerKillsAndDeaths;
    }
}
