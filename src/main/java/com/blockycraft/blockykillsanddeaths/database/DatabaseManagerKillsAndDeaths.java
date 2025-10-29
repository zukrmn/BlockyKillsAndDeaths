package com.blockycraft.blockykillsanddeaths.database;

import java.sql.*;

public class DatabaseManagerKillsAndDeaths {
    private Connection connection;

    public DatabaseManagerKillsAndDeaths(String dbPath) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        createTables();
    }

    private void createTables() {
        String killsSQL = "CREATE TABLE IF NOT EXISTS kills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "killer_uuid TEXT NOT NULL," +
                "victim_uuid TEXT NOT NULL," +
                "killer_username TEXT NOT NULL," +
                "victim_username TEXT NOT NULL," +
                "killed_at INTEGER NOT NULL" +
                ");";
        String mobKillsSQL = "CREATE TABLE IF NOT EXISTS mob_kills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "killer_uuid TEXT NOT NULL," +
                "killer_username TEXT NOT NULL," +
                "mob_name TEXT NOT NULL," +
                "mob_id TEXT NOT NULL," +
                "killed_at INTEGER NOT NULL" +
                ");";
        String mobDeathsSQL = "CREATE TABLE IF NOT EXISTS mob_deaths (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "victim_uuid TEXT NOT NULL," +
                "victim_username TEXT NOT NULL," +
                "mob_name TEXT NOT NULL," +
                "mob_id TEXT NOT NULL," +
                "killed_at INTEGER NOT NULL" +
                ");";
        String naturalDeathsSQL = "CREATE TABLE IF NOT EXISTS natural_deaths (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "victim_uuid TEXT NOT NULL," +
                "victim_username TEXT NOT NULL," +
                "reason TEXT NOT NULL," +
                "killed_at INTEGER NOT NULL" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(killsSQL);
            stmt.execute(mobKillsSQL);
            stmt.execute(mobDeathsSQL);
            stmt.execute(naturalDeathsSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logPlayerKill(String killerUUID, String killerUsername, String victimUUID, String victimUsername, long killedAt) {
        String sql = "INSERT INTO kills(killer_uuid, killer_username, victim_uuid, victim_username, killed_at) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, killerUUID);
            pstmt.setString(2, killerUsername);
            pstmt.setString(3, victimUUID);
            pstmt.setString(4, victimUsername);
            pstmt.setLong(5, killedAt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logMobKill(String killerUUID, String killerUsername, String mobName, String mobId, long killedAt) {
        String sql = "INSERT INTO mob_kills(killer_uuid, killer_username, mob_name, mob_id, killed_at) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, killerUUID);
            pstmt.setString(2, killerUsername);
            pstmt.setString(3, mobName);
            pstmt.setString(4, mobId);
            pstmt.setLong(5, killedAt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logMobDeath(String victimUUID, String victimUsername, String mobName, String mobId, long killedAt) {
        String sql = "INSERT INTO mob_deaths(victim_uuid, victim_username, mob_name, mob_id, killed_at) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, victimUUID);
            pstmt.setString(2, victimUsername);
            pstmt.setString(3, mobName);
            pstmt.setString(4, mobId);
            pstmt.setLong(5, killedAt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logNaturalDeath(String victimUUID, String victimUsername, String reason, long killedAt) {
        String sql = "INSERT INTO natural_deaths(victim_uuid, victim_username, reason, killed_at) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, victimUUID);
            pstmt.setString(2, victimUsername);
            pstmt.setString(3, reason);
            pstmt.setLong(4, killedAt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
