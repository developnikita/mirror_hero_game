package com.neolab.heroesGame.arena;

import java.util.Map;

public class BattleArena {
    private final Map<Integer, Army> armies;

    public BattleArena(Map<Integer, Army> armies) {
        this.armies = armies;
    }

    public Map<Integer, Army> getArmies() {
        return armies;
    }

    public boolean isArmyDied(int playerId) {
        return armies.get(playerId).getHeroes().size() == 0;
    }

    public boolean isWarlordAlive(int playerId) {
        return armies.get(playerId).isWarlordAlive();
    }

    public Army getArmy(int playerId) {
        return armies.get(playerId);
    }


}
