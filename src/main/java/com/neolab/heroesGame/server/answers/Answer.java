package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;

public class Answer {
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
        log.append(String.format("Игрок <%d> запросии действие %s юнитом на позиции (%d, %d) ",
                playerId, action, activeHero.getX(), activeHero.getY()));
        if (action != HeroActions.DEFENCE) {
            log.append(String.format(" на юнита на позиции (%d, %d)",
                    targetUnit.getX(), targetUnit.getY()
            ));
        }
        return log.toString();
    }
}