package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;

public class Healer extends Hero {

    public Healer(int hp, int damage, float precision, float armor, int armyId) {
        super(hp, damage, precision, armor, armyId);
    };

    @Override
    public void toAttack(SquareCoordinate position, Army army){
        throw new UnsupportedOperationException();
    };

    public void toHeal(SquareCoordinate position, Army army){
        throw new UnsupportedOperationException();
    };
}