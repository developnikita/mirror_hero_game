package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.errors.HeroExceptions;

public class Magician extends Hero {

    public Magician(int hp, int damage, float precision, float armor, int armyId) {
        super(hp, damage, precision, armor, armyId);
    }

    @Override
    public void toAttack(SquareCoordinate position, Army army) {
        army.getHeroes().values().forEach(hero -> {
            if(isHit(this.getPrecision())){
                hero.setHp(hero.getHp() - calculateDamage(hero));
                removeTarget(hero, position, army);
            }
        });
    }
}
