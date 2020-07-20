package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Archer extends Hero {

    public Archer(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
    }

    @JsonCreator
    protected Archer(@JsonProperty("unitId") final int unitId, @JsonProperty("hpDefault") final int hpDefault,
                     @JsonProperty("hpMax") final int hpMax, @JsonProperty("hp") final int hp,
                     @JsonProperty("damageDefault") final int damageDefault, @JsonProperty("damage") final int damage,
                     @JsonProperty("precision") final float precision, @JsonProperty("armor") final float armor,
                     @JsonProperty("armorDefault") final float armorDefault,
                     @JsonProperty("defence") final boolean defence) {
        super(unitId, hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence);
    }

    public static Hero createInstance() {
        final int hp = 90;
        final int damage = 40;
        final float precision = 0.85f;
        final float armor = 0;
        return new Archer(hp, damage, precision, armor);
    }
}
