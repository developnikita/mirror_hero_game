package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class WarlordMagician extends Magician implements IWarlord {

    private float improveCoefficient = 0.05f;

    protected WarlordMagician(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
    }

    @JsonCreator
    protected WarlordMagician(@JsonProperty("unitId") final int unitId, @JsonProperty("hpDefault") final int hpDefault,
                              @JsonProperty("hpMax") final int hpMax, @JsonProperty("hp") final int hp,
                              @JsonProperty("damageDefault") final int damageDefault,
                              @JsonProperty("damage") final int damage, @JsonProperty("precision") final float precision,
                              @JsonProperty("armor") final float armor,
                              @JsonProperty("armorDefault") final float armorDefault,
                              @JsonProperty("defence") final boolean defence,
                              @JsonProperty("improveCoefficient") final float improveCoefficient) {
        super(unitId, hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence);
        this.improveCoefficient = improveCoefficient;
    }

    public float getImproveCoefficient() {
        return improveCoefficient;
    }

    @Override
    public int getUnitId() {
        return super.getUnitId();
    }

    public static Hero createInstance() {
        final int hp = 90;
        final int damage = 40;
        final float precision = 0.75f;
        final float armor = 0.05f;
        return new WarlordMagician(hp, damage, precision, armor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WarlordMagician that = (WarlordMagician) o;
        return Float.compare(that.improveCoefficient, improveCoefficient) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), improveCoefficient);
    }
}
