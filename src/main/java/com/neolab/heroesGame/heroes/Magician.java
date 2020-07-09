package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.util.HashMap;
import java.util.Map;

public class Magician extends Hero {

    public Magician(int hp, int damage, float precision, float armor, int armyId) {
        super(hp, damage, precision, armor, armyId);
    }

    @Override
    public Map<SquareCoordinate, Integer> toAttack(SquareCoordinate position, Army army) {
        Map<SquareCoordinate, Integer> enemyHeroPosDamage = new HashMap<>();

        army.getHeroes().forEach((key, value) -> {
            int damageDone = 0;
            if (isHit(this.getPrecision())) {
                damageDone = calculateDamage(value);
                value.setHp(value.getHp() - damageDone);
                removeTarget(value, army);
            }
            enemyHeroPosDamage.put(key, damageDone);
        });
        return enemyHeroPosDamage;
    }
}
