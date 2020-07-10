package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.TemplateServer;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Answer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateServer.class);
    private SquareCoordinate activeHero;
    private HeroActions action;
    private SquareCoordinate targetUnit;
    private final int playerId;

    public Answer(SquareCoordinate activeHero, HeroActions action, SquareCoordinate targetUnit, int playerId) {
        this.playerId = playerId;
        setAction(action);
        setActiveHero(activeHero);
        setTargetUnit(targetUnit);
    }

    public SquareCoordinate getActiveHero() {
        return activeHero;
    }

    public void setActiveHero(SquareCoordinate activeHero) {
        this.activeHero = activeHero;
    }

    public HeroActions getAction() {
        return action;
    }

    public void setAction(HeroActions action) {
        this.action = action;
    }

    public SquareCoordinate getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(SquareCoordinate targetUnit) {
        this.targetUnit = targetUnit;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String toLog() {
        StringBuilder log = new StringBuilder();
        log.append(String.format("Игрок <%d> запросил действие %s юнитом на позиции (%d, %d)",
                playerId, action, activeHero.getX(), activeHero.getY()));
        if (action != HeroActions.DEFENCE) {
            log.append(String.format(" на юнита на позиции (%d, %d)", targetUnit.getX(), targetUnit.getY()));
        }
        log.append("\n");
        LOGGER.info(log.toString());
        return log.toString();
    }
}