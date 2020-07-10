package com.neolab.heroesGame.arena;

import com.neolab.heroesGame.TemplateServer;
import com.neolab.heroesGame.aditional.SomeFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BattleArena {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleArena.class);
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

    public Army getArmy(int playerId) {
        return armies.get(playerId);
    }

    public String toLog() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Integer key : armies.keySet()) {
            stringBuilder.append(String.format("Армия игрока <%d>: \n", key));
            stringBuilder.append(SomeFunction.printArmy(armies.get(key)));
        }
        LOGGER.info(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
