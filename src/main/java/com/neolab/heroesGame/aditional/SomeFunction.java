package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;

import java.util.*;

public class SomeFunction {
    public static Army getEnemyArmy(BattleArena board, Army thisBotArmy) throws HeroExceptions {
        ArrayList<Army> armies = new ArrayList<>(board.getArmies().values());
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
        Map<SquareCoordinate, Hero> heroes = enemyArmy.getHeroes();
        HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        for (int y = 1; y >= 0; y--) {
            if (heroes.get(new SquareCoordinate(activeUnit.getX(), y)) != null) {
                validateTarget.add(new SquareCoordinate(activeUnit.getX(), y));
            }
            if (heroes.get(new SquareCoordinate(1, y)) != null) {
                validateTarget.add(new SquareCoordinate(1, y));
            }
            if (!validateTarget.isEmpty()) {
                break;
            }
            int x = activeUnit.getX() == 2 ? 0 : 2;
            if (heroes.get(new SquareCoordinate(x, y)) != null) {
                validateTarget.add(new SquareCoordinate(x, y));
                break;
            }
        }
        return validateTarget;
    }
}
