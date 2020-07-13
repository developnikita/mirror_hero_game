package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.util.*;

public class Magician extends Hero {

    public Magician(int hp, int damage, float precision, float armor, int unitId) {
        super(hp, damage, precision, armor, unitId);
    }

    @JsonCreator
    public Magician(@JsonProperty("hpDefault") final int hpDefault, @JsonProperty("hpMax") final int hpMax,
                @JsonProperty("hp") final int hp, @JsonProperty("damageDefault") final int damageDefault,
                @JsonProperty("damage") final int damage, @JsonProperty("precision") final float precision,
                @JsonProperty("armor") final float armor, @JsonProperty("armorDefault") final float armorDefault,
                @JsonProperty("defence") final boolean defence, @JsonProperty("unitId") final int unitId) {
        super(hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence, unitId);
    }

    @Override
    public Map<SquareCoordinate, Integer> toAttack(SquareCoordinate position, Army army) {
        Map<SquareCoordinate, Integer> enemyHeroPosDamage = new HashMap<>();

        army.getHeroes().keySet().removeIf(coord -> {
            Hero hero = army.getHero(coord).orElseThrow();
            int damageDone = 0;
            if (isHit(this.getPrecision())) {
                damageDone = calculateDamage(hero);
                hero.setHp(hero.getHp() - damageDone);
            }
            enemyHeroPosDamage.put(coord, damageDone);
            return army.removeHero(hero, army);
        });

        return enemyHeroPosDamage;
    }
}
