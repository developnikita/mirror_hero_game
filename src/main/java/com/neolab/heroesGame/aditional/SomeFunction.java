package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SomeFunction {
    public static Army getEnemyArmy(BattleArena board, Army thisBotArmy) throws HeroExceptions {
        ArrayList<Army> armies = (ArrayList<Army>) board.getArmies().values();
        armies.remove(thisBotArmy);
        if (armies.size() != 1) {
            throw new HeroExceptions(HeroErrorCode.ERROR_ON_BATTLE_ARENA);
        }
        return armies.get(0);
    }

    public static Army getCurrentPlayerArmy(BattleArena board, Integer playerId) throws HeroExceptions {
        return Optional.of(board.getArmy(playerId)).orElseThrow(
                new HeroExceptions(HeroErrorCode.ERROR_ON_BATTLE_ARENA));
    }


    public static boolean isUnitMagician(Hero hero) {
        return hero.getClass() == Magician.class
                || hero.getClass() == WarlordMagician.class
                || hero.getClass() == WarlordVampire.class;
    }

    public static boolean isUnitArcher(Hero hero) {
        return hero.getClass() == Archer.class;
    }

    public static boolean isUnitHealer(Hero hero) {
        return hero.getClass() == Healer.class;
    }

    public static Set<SquareCoordinate> getCorrectTargetForFootman(SquareCoordinate activeUnit, Army enemyArmy) {
        HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        for (int x = 1; x >= 0; x--) {
            if (enemyArmy.getHero(new SquareCoordinate(x, activeUnit.getY())).isPresent()) {
                validateTarget.add(new SquareCoordinate(x, activeUnit.getY()));
            }
            if (enemyArmy.getHero(new SquareCoordinate(x, 1)).isPresent()) {
                validateTarget.add(new SquareCoordinate(x, 1));
            }
            if (!validateTarget.isEmpty()) {
                break;
            }
            int y = activeUnit.getY() == 2 ? 0 : 2;
            if (enemyArmy.getHero(new SquareCoordinate(x, y)).isPresent()) {
                validateTarget.add(new SquareCoordinate(x, y));
                break;
            }
        }
        return validateTarget;
    }
}
