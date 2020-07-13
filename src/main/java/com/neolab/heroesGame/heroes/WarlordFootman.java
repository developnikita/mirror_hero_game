package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WarlordFootman extends Footman implements IWarlord {

    private float improveCoefficient = 0.1f;

    public WarlordFootman(int hp, int damage, float precision, float armor, int unitId) {
        super(hp, damage, precision, armor, unitId);
    }

    @JsonCreator
    public WarlordFootman(@JsonProperty("hpDefault") final int hpDefault, @JsonProperty("hpMax") final int hpMax,
                @JsonProperty("hp") final int hp, @JsonProperty("damageDefault") final int damageDefault,
                @JsonProperty("damage") final int damage, @JsonProperty("precision") final float precision,
                @JsonProperty("armor") final float armor, @JsonProperty("armorDefault") final float armorDefault,
                @JsonProperty("defence") final boolean defence, @JsonProperty("improveCoefficient") final float improveCoefficient,
                          @JsonProperty("unitId") final int unitId) {
        super(hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence, unitId);
        this.improveCoefficient = improveCoefficient;
    }

    public float getImproveCoefficient() {
        return improveCoefficient;
    }

    @Override
    public int getUnitId() {
        return super.getUnitId();
    }
}
