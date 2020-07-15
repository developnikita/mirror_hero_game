package com.neolab.heroesGame.server;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neolab.heroesGame.aditional.SquareCoordinateKeyDeserializer;
import com.neolab.heroesGame.aditional.SquareCoordinateKeySerializer;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActionEffect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionEffect.class);

    @JsonProperty
    private final HeroActions action;
    @JsonSerialize(keyUsing = SquareCoordinateKeySerializer.class)
    @JsonDeserialize(keyUsing = SquareCoordinateKeyDeserializer.class)
    private final Map<SquareCoordinate, Integer> targetUnitsMap;
    @JsonProperty
    private final SquareCoordinate sourceUnit;

    @JsonCreator
    public ActionEffect(@JsonProperty("action") final HeroActions action,
                        @JsonProperty("sourceUnit") final SquareCoordinate sourceUnit,
                        @JsonProperty("targetUnitsMap") final Map<SquareCoordinate, Integer> targetUnitsMap) {
        this.action = action;
        this.targetUnitsMap = new HashMap<>(targetUnitsMap);
        this.sourceUnit = new SquareCoordinate(sourceUnit.getX(), sourceUnit.getY());
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActionEffect that = (ActionEffect) o;
        return action == that.action &&
                Objects.equals(targetUnitsMap, that.targetUnitsMap) &&
                Objects.equals(sourceUnit, that.sourceUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, targetUnitsMap, sourceUnit);
    }
}

