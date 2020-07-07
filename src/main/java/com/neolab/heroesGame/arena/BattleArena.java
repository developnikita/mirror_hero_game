package com.neolab.heroesGame.arena;

import java.util.Map;

public class BattleArena {
    private Map<int, Army> armies;

    public BattleArena(Map<int, Army> armies) {
        this.armies = armies;
    }

    public boolean isArmyDied(int playerId) {
        return armies.get(playerId).getHeroes().size() == 0;
    }

    public boolean isWarlordAlive(int playerId) {
        return armies.get(playerId).isWarlordAlive();
    }
}
