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

    public String toLog() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Юнит на позиции (%d, %d) ", sourceUnit.getX(), sourceUnit.getY()));
        if (action == HeroActions.DEFENCE) {
            stringBuilder.append("встал в защиту");
        } else {
            String stringAction = (action == HeroActions.HEAL) ? "восстановил" : "нанес";
            String effectName = (action == HeroActions.HEAL) ? "" : " урона";
            for (SquareCoordinate key : targetUnitsMap.keySet()) {
                if (targetUnitsMap.get(key) <= 0) {
                    stringBuilder.append("промахнулся по юниту на позиции (%d, %d)", key.getX(), key.getY());
                } else {
                    stringBuilder.append(String.format("%s юниту на позиции (%d, %d) %d HP%s ",
                            stringAction, key.getX(), key.getY(), targetUnitsMap.get(key), effectName));
                }
            }
        }
        stringBuilder.append("/n");
        return stringBuilder.toString();
    }
}

