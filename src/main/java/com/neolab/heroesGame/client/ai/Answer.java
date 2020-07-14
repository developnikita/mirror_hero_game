package com.neolab.heroesGame.client.ai;

import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;

public class Answer {

    private SquareCoordinate activeHero;
    private HeroActions action;
    private SquareCoordinate targetUnit;
    private final int playerId;

    public Answer(final SquareCoordinate activeHero, final HeroActions action, final SquareCoordinate targetUnit, final int playerId) {
        this.playerId = playerId;
        setAction(action);
        setActiveHero(activeHero);
        setTargetUnit(targetUnit);
    }

    public SquareCoordinate getActiveHero() {
        return activeHero;
    }

    public void setActiveHero(final SquareCoordinate activeHero) {
        this.activeHero = activeHero;
    }

    public HeroActions getAction() {
        return action;
    }

    public void setAction(final HeroActions action) {
        this.action = action;
    }

    public SquareCoordinate getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(final SquareCoordinate targetUnit) {
        this.targetUnit = targetUnit;
    }

    public int getPlayerId() {
        return playerId;
    }
}
