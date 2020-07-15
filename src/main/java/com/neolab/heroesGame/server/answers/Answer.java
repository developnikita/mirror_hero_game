package com.neolab.heroesGame.server.answers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Answer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Answer.class);
    private final SquareCoordinate activeHero;
    private final HeroActions action;
    private final SquareCoordinate targetUnit;
    private final int playerId;

    @JsonCreator
    public Answer(@JsonProperty("activeHero") final SquareCoordinate activeHero,
                  @JsonProperty("action") final HeroActions action,
                  @JsonProperty("targetUnit") final SquareCoordinate targetUnit,
                  @JsonProperty("playerId") final int playerId) {
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
        return log.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Answer answer = (Answer) o;
        return playerId == answer.playerId &&
                Objects.equals(activeHero, answer.activeHero) &&
                action == answer.action &&
                Objects.equals(targetUnit, answer.targetUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeHero, action, targetUnit, playerId);
    }

    public void toLog() {
        LOGGER.info(this.toString());
    }
}