package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.enumerations.HeroErrorCode;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.heroes.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommonFunction {
    public static Army getEnemyArmy(final BattleArena board, final Army thisBotArmy) throws HeroExceptions {
        ArrayList<Army> armies = new ArrayList<>(board.getArmies().values());
        armies.remove(thisBotArmy);
        if (armies.size() != 1) {
            throw new HeroExceptions(HeroErrorCode.ERROR_ON_BATTLE_ARENA);
        }
        return armies.get(0);
    }

    public static Army getCurrentPlayerArmy(final BattleArena board, final Integer playerId) throws HeroExceptions {
        return board.getArmy(playerId);
    }

    public static boolean isUnitMagician(final Hero hero) {
        return hero instanceof Magician;
    }

    public static boolean isUnitArcher(final Hero hero) {
        return hero instanceof Archer;
    }

    public static boolean isUnitHealer(final Hero hero) {
        return hero instanceof Healer;
    }

    /**
     * Ищем корректные цели для милишников. В цикле проверяем по рядам наличие целей. Если в первом ряду есть юниты,
     * то задний ряд не проверяем. Сперва смотрим это центральный юнит или фланговый, вызываем соответствующие функции
     * для соответствующего случая
     */
    public static @NotNull Set<SquareCoordinate> getCorrectTargetForFootman(final @NotNull SquareCoordinate activeUnit,
                                                                            final @NotNull Army enemyArmy) {
        HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        for (int y = 1; y >= 0; y--) {
            if (activeUnit.getX() == 1) {
                validateTarget.addAll(getTargetForCentralUnit(enemyArmy, y));
            } else {
                validateTarget.addAll(getTargetForFlankUnit(activeUnit.getX(), enemyArmy, y));
            }
            if (!validateTarget.isEmpty()) {
                break;
            }
        }
        return validateTarget;
    }

    /**
     * Сперва проверяем, наличие центрального юнита в армии врага, потом юнита на том же фланге. Если юнитов в армии
     * противника все еще не встретилось, то проверяем второй фланг
     *
     * @param activeUnitX
     * @param enemyArmy
     * @param y
     * @return
     */
    private static Set<SquareCoordinate> getTargetForFlankUnit(int activeUnitX, Army enemyArmy, int y) {
        HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        if (enemyArmy.getHero(new SquareCoordinate(1, y)).isPresent()) {
            validateTarget.add(new SquareCoordinate(1, y));
        }
        if (enemyArmy.getHero(new SquareCoordinate(activeUnitX, y)).isPresent()) {
            validateTarget.add(new SquareCoordinate(activeUnitX, y));
        }
        if (validateTarget.isEmpty()) {
            int x = activeUnitX == 2 ? 0 : 2;
            if (enemyArmy.getHero(new SquareCoordinate(x, y)).isPresent()) {
                validateTarget.add(new SquareCoordinate(x, y));
            }
        }
        return validateTarget;
    }

    /**
     * Проверяем всю линию на наличие юнитов в армии противника
     */
    private static Set<SquareCoordinate> getTargetForCentralUnit(Army enemyArmy, Integer line) {
        HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        for (int x = 0; x < 3; x++) {
            SquareCoordinate coordinate = new SquareCoordinate(x, line);
            Optional<Hero> hero = enemyArmy.getHero(coordinate);
            if (hero.isPresent()) {
                validateTarget.add(coordinate);
            }
        }
        return validateTarget;
    }

    public static String printArmy(final Army army) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < 2; y++) {
            stringBuilder.append(getLineUnit(army, y));
            stringBuilder.append("____________|____________|____________|\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    /**
     * Формируем 3 строки - первая с названием класса, вторая с текущим/маскимальным хп, третья со статусом действия
     */
    private static String getLineUnit(final Army army, int y) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < 3; x++) {
            Hero hero = getHero(army, new SquareCoordinate(x, y));
            stringBuilder.append(classToString(hero));
        }
        stringBuilder.append("\n");
        for (int x = 0; x < 3; x++) {
            Hero hero = getHero(army, new SquareCoordinate(x, y));
            stringBuilder.append(hpToString(hero));
        }
        stringBuilder.append("\n");
        for (int x = 0; x < 3; x++) {
            Hero hero = getHero(army, new SquareCoordinate(x, y));
            stringBuilder.append(statusToString(hero, army));
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private static String statusToString(final Hero hero, Army army) {
        StringBuilder result = new StringBuilder();
        if (hero == null) {
            result.append(String.format("%12s|", ""));
        } else {
            if (hero.isDefence()) {
                result.append(String.format("   D  "));
            } else {
                result.append(String.format("      "));
            }
            if (army.getAvailableHero().containsValue(hero)) {
                result.append(String.format("  CA  |"));
            } else {
                result.append(String.format("   W  |"));
            }
        }
        return result.toString();
    }

    private static String hpToString(final Hero hero) {
        String result;
        if (hero == null) {
            result = String.format("%12s|", "");
        } else {
            result = String.format("  HP%3d/%3d |", hero.getHp(), hero.getHpMax());
        }
        return result;
    }

    private static String classToString(final Hero hero) {
        String result;
        if (hero == null) {
            result = String.format("%12s|", "");
        } else if (hero.getClass() == Magician.class) {
            result = String.format("%12s|", "Маг");
        } else if (hero instanceof WarlordMagician) {
            result = String.format("%12s|", "Архимаг");
        } else if (hero instanceof WarlordVampire) {
            result = String.format("%12s|", "Вампир");
        } else if (hero instanceof Archer) {
            result = String.format("%12s|", "Лучник");
        } else if (hero instanceof Healer) {
            result = String.format("%12s|", "Лекарь");
        } else if (hero.getClass() == Footman.class) {
            result = String.format("%12s|", "Мечник");
        } else if (hero instanceof WarlordFootman) {
            result = String.format("%12s|", "Генерал");
        } else {
            result = String.format("%12s|", "Unknown");
        }
        return result;
    }

    private static Hero getHero(final Army army, final SquareCoordinate coord) {
        return army.getHeroes().get(coord);
    }

    /**
     * Для генеариия id героев
     */
    private static class MyInt {
        public static int i = 0;
    }

    public interface IdGeneration{
        int getNextId();
    }

    public static IdGeneration idGeneration = () -> ++MyInt.i;
}
