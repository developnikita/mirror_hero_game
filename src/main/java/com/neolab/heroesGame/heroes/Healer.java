package com.neolab.heroesGame.heroes;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.errors.HeroExceptions;


public class Healer extends Hero {

    public Healer(int hp, int healing, float precision, float armor, int armyId) {
        super(hp, healing, precision, armor, armyId);
    }
    /**
     * Невозможно превысить максимальный запас здоровья
     * @param position позиция героя
     * @param army армия союзников
     * @throws HeroExceptions
     */
    public void toHeal(SquareCoordinate position, Army army) throws HeroExceptions {
        Hero targetHeal = searchTarget(position, army);
        int healing = targetHeal.getHp() + this.getDamage();
        targetHeal.setHp(Math.min(healing, targetHeal.getHpMax()));
    }
}