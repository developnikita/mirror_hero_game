package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Answer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Answer.class);
    private final SquareCoordinate activeHero;
    private final HeroActions action;
    private final SquareCoordinate targetUnit;
    private final int playerId;

    public Answer(final SquareCoordinate activeHero, final HeroActions action, final SquareCoordinate targetUnit, final int playerId) {
        this.playerId = playerId;
        this.action = action;
        this.activeHero = activeHero;
        this.targetUnit = targetUnit;
    }

    public SquareCoordinate getActiveHero() {
        return activeHero;
    }

    public HeroActions getAction() {
        return action;
    }

    public SquareCoordinate getTargetUnit() {
        return targetUnit;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        final StringBuilder log = new StringBuilder();
        log.append(String.format("Игрок <%d> запросил действие %s юнитом на позиции (%d, %d)",
                playerId, action, activeHero.getX(), activeHero.getY()));
        if (action != HeroActions.DEFENCE) {
            log.append(String.format(" на юнита на позиции (%d, %d)", targetUnit.getX(), targetUnit.getY()));
        }
        LOGGER.info(log.toString());
        return log.toString();
    }
}