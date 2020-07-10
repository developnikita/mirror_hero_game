package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.util.*;

public class Magician extends Hero {

    public Magician(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
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
            return army.removeHero(hero);
        });

        return enemyHeroPosDamage;
    }
}
