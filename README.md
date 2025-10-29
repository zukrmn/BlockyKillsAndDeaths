# BlockyKillsAndDeaths

Plugin para o Scoreboard do BlockyCRAFT que registra estatísticas de PvP, PvE e mortes naturais de jogadores e mobs.

---

## Funcionalidade

- **Player Kills (PvP):** Registra sempre que um jogador elimina outro jogador.
- **Mob Kills:** Registra toda vez que um jogador mata qualquer mob (zumbi, porco, vaca, etc).
- **Mob Deaths:** Registra quando um jogador é morto por um mob hostil.
- **Natural Deaths:** Registra qualquer morte "natural" (queda, fogo, afogamento, explosão, void, sufocamento, etc).

---

## Estrutura de banco (SQLite, arquivo `killsanddeaths.db`)

- **kills:** PvP (player matou player)
- **mob_kills:** PvE ativo (player matou mob)
- **mob_deaths:** PvE passivo (player morreu para mob)
- **natural_deaths:** Player morreu por evento ambiental/natural

## Reportar bugs ou requisitar features

Reporte bugs ou sugestões na seção [Issues](https://github.com/andradecore/BlockyKillsAndDeaths/issues) do projeto. do projeto.

## Contato

- Discord: https://discord.gg/tthPMHrP