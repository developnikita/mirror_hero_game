package com.neolab.heroesGame.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.util.HashMap;
import java.util.Map;

public class Magician extends Hero {

    protected Magician(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
    }

    @JsonCreator
    protected Magician(@JsonProperty("unitId") final int unitId, @JsonProperty("hpDefault") final int hpDefault,
                       @JsonProperty("hpMax") final int hpMax, @JsonProperty("hp") final int hp,
                       @JsonProperty("damageDefault") final int damageDefault, @JsonProperty("damage") final int damage,
                       @JsonProperty("precision") final float precision, @JsonProperty("armor") final float armor,
                       @JsonProperty("armorDefault") final float armorDefault,
                       @JsonProperty("defence") final boolean defence) {
        super(unitId, hpDefault, hpMax, hp, damageDefault, damage, precision, armor, armorDefault, defence);
    }

    @Override
    public Map<SquareCoordinate, Integer> toAct(final SquareCoordinate position, final Army army) {
        final Map<SquareCoordinate, Integer> enemyHeroPosDamage = new HashMap<>();

        army.getHeroes().keySet().removeIf(coordinate -> {
            final Hero hero;
            hero = army.getHero(coordinate).orElseThrow();
            int damageDone = 0;
            if (isHit(this.getPrecision())) {
                damageDone = calculateDamage(hero);
                hero.setHp(hero.getHp() - damageDone);
            }
            enemyHeroPosDamage.put(coordinate, damageDone);
            return army.removeHero(hero, army);
        });

        return enemyHeroPosDamage;
    }

    public static Hero createInstance() {
        final int hp = 75;
        final int damage = 30;
        final float precision = 0.8f;
        final float armor = 0;
        return new Magician(hp, damage, precision, armor);
    }
}
