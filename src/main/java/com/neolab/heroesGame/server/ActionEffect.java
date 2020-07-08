package com.neolab.heroesGame.server;

import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;

import java.util.HashMap;
import java.util.Map;

public class ActionEffect {
    private HeroActions action;
    private Map<SquareCoordinate, Integer> targetUnitsMap;
    private SquareCoordinate sourceUnit;


    public HeroActions getAction() {
        return action;
    }

    public SquareCoordinate getSourceUnit() {
        return sourceUnit;
    }

    public Map<SquareCoordinate, Integer> getTargetUnitsMap() {
        return targetUnitsMap;
    }

    public void setTargetUnitsMap(Map<SquareCoordinate, Integer> targetUnitsMap) {
        this.targetUnitsMap = new HashMap<>();
        this.targetUnitsMap.putAll(targetUnitsMap);
    }

    public void setSourceUnit(SquareCoordinate sourceUnit) {
        this.sourceUnit = new SquareCoordinate(sourceUnit.getX(), sourceUnit.getY());
    }

    public void setAction(HeroActions action) {
        this.action = action;
    }
}

