package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Footman extends Hero {

    public Footman(int hp, int damage, float precision, float armor, int unitId) {
        super(hp, damage, precision, armor, unitId);
    }

    @JsonCreator
    public Footman(@JsonProperty("hpDefault") final int hpDefault, @JsonProperty("hpMax") final int hpMax,
                @JsonProperty("hp") final int hp, @JsonProperty("damageDefault") final int damageDefault,
                @JsonProperty("damage") final int damage, @JsonProperty("precision") final float precision,
                @JsonProperty("armor") final float armor, @JsonProperty("armorDefault") final float armorDefault,
                @JsonProperty("defence") final boolean defence, @JsonProperty("unitId") final int unitId) {
        super(hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence, unitId);
    }
}