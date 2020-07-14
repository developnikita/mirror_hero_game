package com.neolab.heroesGame.server;

import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ActionEffect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionEffect.class);
    private final HeroActions action;
    private final Map<SquareCoordinate, Integer> targetUnitsMap;
    private final SquareCoordinate sourceUnit;

    public ActionEffect(final HeroActions action, final SquareCoordinate sourceUnit, final Map<SquareCoordinate, Integer> targetUnitsMap) {
        this.action = action;
        this.targetUnitsMap = new HashMap<>(targetUnitsMap);
        this.sourceUnit = new SquareCoordinate(sourceUnit.getX(), sourceUnit.getY());
    }

    public HeroActions getAction() {
        return action;
    }

    public SquareCoordinate getSourceUnit() {
        return sourceUnit;
    }

    public Map<SquareCoordinate, Integer> getTargetUnitsMap() {
        return targetUnitsMap;
    }

    public void setSourceUnit(final SquareCoordinate sourceUnit) {

    }

    public void toLog() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Юнит на позиции (%d, %d) ", sourceUnit.getX(), sourceUnit.getY()));
        if (action == HeroActions.DEFENCE) {
            stringBuilder.append("встал в защиту");
        } else {
            final String stringAction = (action == HeroActions.HEAL) ? "восстановил" : "нанес";
            final String effectName = (action == HeroActions.HEAL) ? "" : " урона";
            for (final SquareCoordinate key : targetUnitsMap.keySet()) {
                if (targetUnitsMap.get(key) <= 0) {
                    stringBuilder.append(String.format("промахнулся по юниту на позиции (%d, %d)", key.getX(), key.getY()));
                } else {
                    stringBuilder.append(String.format("%s юниту на позиции (%d, %d) %d HP%s ",
                            stringAction, key.getX(), key.getY(), targetUnitsMap.get(key), effectName));
                }
            }
        }
        stringBuilder.append("\n");
        LOGGER.info(stringBuilder.toString());
    }
}

