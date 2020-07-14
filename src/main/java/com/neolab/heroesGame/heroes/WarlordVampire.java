package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WarlordVampire extends Magician implements IWarlord {

    private float improveCoefficient = 0.05f;

    public WarlordVampire(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
    }


    @JsonCreator
    public WarlordVampire(@JsonProperty("hpDefault") final int hpDefault, @JsonProperty("hpMax") final int hpMax,
                          @JsonProperty("hp") final int hp, @JsonProperty("damageDefault") final int damageDefault,
                          @JsonProperty("damage") final int damage, @JsonProperty("precision") final float precision,
                          @JsonProperty("armor") final float armor, @JsonProperty("armorDefault") final float armorDefault,
                          @JsonProperty("defence") final boolean defence, @JsonProperty("improveCoefficient") final float improveCoefficient) {
        super(hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence);
        this.improveCoefficient = improveCoefficient;
    }

    public float getImproveCoefficient() {
        return improveCoefficient;
    }

    @Override
    public int getUnitId() {
        return super.getUnitId();
    }

    @Override
    public Map<SquareCoordinate, Integer> toAct(final SquareCoordinate position, final Army army) {
        final Map<SquareCoordinate, Integer> enemyHeroPosDamage = super.toAct(position, army);
        AtomicInteger heal = new AtomicInteger(this.getHp());
        enemyHeroPosDamage.values().forEach(heal::addAndGet);
        this.setHp(Math.min(heal.get(), this.getHpMax()));
        return enemyHeroPosDamage;
    }
}
