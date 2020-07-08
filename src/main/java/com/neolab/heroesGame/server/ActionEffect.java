package com.neolab.heroesGame.server;

import com.neolab.heroesGame.arena.SquareCoordinate;

public class ActionEffect {
    private final String action;
    private final Integer value;
    private final SquareCoordinate sourceUnit;
    private final SquareCoordinate targetUnit;

    public ActionEffect(String action, Integer value, SquareCoordinate sourceUnit, SquareCoordinate targetUnit) {
        this.action = action;
        this.value = value;
        this.sourceUnit = sourceUnit;
        this.targetUnit = targetUnit;
    }

    public String getAction() {
        return action;
    }

    public Integer getValue() {
        return value;
    }

    public SquareCoordinate getSourceUnit() {
        return sourceUnit;
    }

    public SquareCoordinate getTargetUnit() {
        return targetUnit;
    }
}

