package com.neolab.heroesGame.client.ai;

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
}
