# BlockyKillsAndDeaths

Plugin para o Scoreboard do BlockyCRAFT que registra estatísticas de PvP, PvE e mortes naturais de jogadores e mobs.

## Funcionalidade

- **Player Kills (PvP):** Registra sempre que um jogador elimina outro jogador.
- **Mob Kills:** Registra toda vez que um jogador mata qualquer mob (zumbi, porco, vaca, etc).
- **Mob Deaths:** Registra quando um jogador é morto por um mob hostil.
- **Natural Deaths:** Registra qualquer morte "natural" (queda, fogo, afogamento, explosão, void, sufocamento, etc).

## Estrutura de banco (SQLite, arquivo `killsanddeaths.db`)

- **kills:** PvP (player matou player)
- **mob_kills:** PvE ativo (player matou mob)
- **mob_deaths:** PvE passivo (player morreu para mob)
- **natural_deaths:** Player morreu por evento ambiental/natural

```CREATE TABLE IF NOT EXISTS kills (
id INTEGER PRIMARY KEY AUTOINCREMENT,
killer_uuid TEXT NOT NULL,
victim_uuid TEXT NOT NULL,
killer_username TEXT NOT NULL,
victim_username TEXT NOT NULL,
killed_at INTEGER NOT NULL
);
```

```CREATE TABLE IF NOT EXISTS mob_kills (
id INTEGER PRIMARY KEY AUTOINCREMENT,
killer_uuid TEXT NOT NULL,
killer_username TEXT NOT NULL,
mob_name TEXT NOT NULL,
mob_id TEXT NOT NULL,
killed_at INTEGER NOT NULL
);
```

```CREATE TABLE IF NOT EXISTS mob_deaths (
id INTEGER PRIMARY KEY AUTOINCREMENT,
victim_uuid TEXT NOT NULL,
victim_username TEXT NOT NULL,
mob_name TEXT NOT NULL,
mob_id TEXT NOT NULL,
killed_at INTEGER NOT NULL
);
```

```CREATE TABLE IF NOT EXISTS natural_deaths (
id INTEGER PRIMARY KEY AUTOINCREMENT,
victim_uuid TEXT NOT NULL,
victim_username TEXT NOT NULL,
reason TEXT NOT NULL,
killed_at INTEGER NOT NULL
);
```
## Consulta para Site/Stats

Exemplos:
- **Kills PvP:**  
  `SELECT killer_username, COUNT(*) as total FROM kills GROUP BY killer_username ORDER BY total DESC;`
- **Mob Kills:**  
  `SELECT killer_username, mob_name, COUNT(*) as total FROM mob_kills GROUP BY killer_username, mob_name ORDER BY total DESC;`
- **Mortes naturais:**  
  `SELECT victim_username, reason, COUNT(*) as total FROM natural_deaths GROUP BY victim_username, reason ORDER BY total DESC;`

## Reportar bugs ou requisitar features

Reporte bugs ou sugestões na seção [Issues](https://github.com/andradecore/BlockyKillsAndDeaths/issues) do projeto. do projeto.

## Contato

- Discord: https://discord.gg/tthPMHrP