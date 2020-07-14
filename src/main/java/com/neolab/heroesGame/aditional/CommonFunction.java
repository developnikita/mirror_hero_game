package com.neolab.heroesGame.aditional;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.heroes.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommonFunction {

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
        final HashSet<SquareCoordinate> validateTarget = new HashSet<>();
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
     */
    private static Set<SquareCoordinate> getTargetForFlankUnit(final int activeUnitX, final Army enemyArmy, final int y) {
        final HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        if (enemyArmy.getHero(new SquareCoordinate(1, y)).isPresent()) {
            validateTarget.add(new SquareCoordinate(1, y));
        }
        if (enemyArmy.getHero(new SquareCoordinate(activeUnitX, y)).isPresent()) {
            validateTarget.add(new SquareCoordinate(activeUnitX, y));
        }
        if (validateTarget.isEmpty()) {
            final int x = activeUnitX == 2 ? 0 : 2;
            if (enemyArmy.getHero(new SquareCoordinate(x, y)).isPresent()) {
                validateTarget.add(new SquareCoordinate(x, y));
            }
        }
        return validateTarget;
    }

    /**
     * Проверяем всю линию на наличие юнитов в армии противника
     */
    private static Set<SquareCoordinate> getTargetForCentralUnit(final Army enemyArmy, final Integer line) {
        final HashSet<SquareCoordinate> validateTarget = new HashSet<>();
        for (int x = 0; x < 3; x++) {
            final SquareCoordinate coordinate = new SquareCoordinate(x, line);
            final Optional<Hero> hero = enemyArmy.getHero(coordinate);
            if (hero.isPresent()) {
                validateTarget.add(coordinate);
            }
        }
        return validateTarget;
    }

    public static String printArmy(final Army army) {
        final StringBuilder stringBuilder = new StringBuilder();
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
    private static String getLineUnit(final Army army, final int y) {
        final StringBuilder stringBuilder = new StringBuilder();
        final Map<Integer, Optional<Hero>> heroes = new HashMap<>();
        for (int x = 0; x < 3; x++) {
            heroes.put(x, army.getHero(new SquareCoordinate(x, y)));
        }
        for (int x = 0; x < 3; x++) {
            stringBuilder.append(classToString(heroes.get(x)));
        }
        stringBuilder.append("\n");
        for (int x = 0; x < 3; x++) {
            stringBuilder.append(hpToString(heroes.get(x)));
        }
        stringBuilder.append("\n");
        for (int x = 0; x < 3; x++) {
            stringBuilder.append(statusToString(heroes.get(x), army));
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private static String statusToString(final Optional<Hero> optionalHero, final Army army) {
        final StringBuilder result = new StringBuilder();
        if (optionalHero.isEmpty()) {
            result.append(String.format("%12s|", ""));
        } else {
            final Hero hero = optionalHero.get();
            if (hero.isDefence()) {
                result.append("   D  ");
            } else {
                result.append("      ");
            }
            if (army.getAvailableHero().containsValue(hero)) {
                result.append("  CA  |");
            } else {
                result.append("   W  |");
            }
        }
        return result.toString();
    }

    private static String hpToString(final Optional<Hero> optionalHero) {
        final String result;
        if (optionalHero.isEmpty()) {
            result = String.format("%12s|", "");
        } else {
            final Hero hero = optionalHero.get();
            result = String.format("  HP%3d/%3d |", hero.getHp(), hero.getHpMax());
        }
        return result;
    }

    public static String classToString(final Optional<Hero> optionalHero) {
        final String result;
        if (optionalHero.isEmpty()) {
            return String.format("%12s|", "");
        }
        Hero hero = optionalHero.get();
        if (hero.getClass() == Magician.class) {
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

    /**
     * Для генеариия id героев
     */
    private static class MyInt {
        public static int i = 0;
    }

    public interface IdGeneration {
        int getNextId();
    }

    public static IdGeneration idGeneration = () -> ++MyInt.i;
}
