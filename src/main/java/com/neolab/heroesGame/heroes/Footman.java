package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Footman extends Hero {

    protected Footman(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
    }

    @JsonCreator
    protected Footman(@JsonProperty("hpDefault") final int hpDefault, @JsonProperty("hpMax") final int hpMax,
                      @JsonProperty("hp") final int hp, @JsonProperty("damageDefault") final int damageDefault,
                      @JsonProperty("damage") final int damage, @JsonProperty("precision") final float precision,
                      @JsonProperty("armor") final float armor, @JsonProperty("armorDefault") final float armorDefault,
                      @JsonProperty("defence") final boolean defence) {
        super(hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence);
    }

    public static Hero createInstance() {
        final int hp = 170;
        final int damage = 50;
        final float precision = 0.8f;
        final float armor = 0.1f;
        return new Footman(hp, damage, precision, armor);
    }
}