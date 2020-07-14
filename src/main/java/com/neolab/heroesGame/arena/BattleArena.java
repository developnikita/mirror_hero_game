package com.neolab.heroesGame.arena;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neolab.heroesGame.aditional.CommonFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class BattleArena {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleArena.class);
    private final Map<Integer, Army> armies;

    @JsonCreator
    public BattleArena(@JsonProperty("armies") final Map<Integer, Army> armies) {
        this.armies = armies;
    }

    public Map<Integer, Army> getArmies() {
        return armies;
    }

    public boolean isArmyDied(final int playerId) {
        return armies.get(playerId).getHeroes().isEmpty();
    }

    public Army getArmy(final int playerId) {
        return armies.get(playerId);
    }

    public boolean haveAvailableHeroByArmyId(final Integer id){
        return armies.get(id).getAvailableHero().isEmpty();
    }

    public void removeUsedHeroesById(final int heroId, final int armyId){
        armies.get(armyId).removeAvailableHeroById(heroId);
    }

    public void endRound(){
        armies.values().forEach(Army::roundIsOver);
    }

    public Army getCurrentPlayerArmy(final Integer playerId) {
        return armies.get(playerId);
    }

    public Army getEnemyArmy(final int playerId){

        Integer botArmyId = armies.keySet().stream()
                .filter(id -> id != playerId).findFirst().get();

        return armies.get(botArmyId);
    }

    public void toLog() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (final Integer key : armies.keySet()) {
            stringBuilder.append(String.format("Армия игрока <%d>: \n", key));
            stringBuilder.append(CommonFunction.printArmy(armies.get(key)));
        }
        LOGGER.info(stringBuilder.toString());
    }
}
