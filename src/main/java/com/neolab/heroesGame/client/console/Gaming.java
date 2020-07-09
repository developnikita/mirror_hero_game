package com.neolab.heroesGame.client.console;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.ActionEffect;

import java.io.PrintStream;

public class Gaming {
    private static final PrintStream out = System.out;

    static public void printLastAction(ActionEffect actionEffect) {

    }

    static public void printArena(BattleArena arena) {
        /*
        HashMap<Integer, Army> armies = arena.getArmies();
        Army leftArmy = armies.get(1);                       //шляпа, надо будет пройтись по всем функциям сделать норм
        Army rightArmy = armies.get(2);
        out.println(PrepearedString.HORIZONTAL_LINE);
        for(int i = 0; i < 3; i++){
            out.println(getTopStringFromLine(i, leftArmy, rightArmy));
            out.println(getMiddleStringFromLine(i, leftArmy, rightArmy));
            out.println(getBottomStringFromLine(i, leftArmy, rightArmy));
            out.println(PrepearedString.HORIZONTAL_LINE_WITH_SPACE);
        }
        */
    }

    private static String getBottomStringFromLine(int i, Army leftArmy, Army rightArmy) {
        StringBuilder stringBuilder = new StringBuilder("|");

        stringBuilder.append(addStatus(i, leftArmy));
        stringBuilder.append(PrepearedString.MIDDLE_SPACE);
        stringBuilder.append(addStatus(i, rightArmy));

        return stringBuilder.toString();
    }

    private static String getMiddleStringFromLine(int i, Army leftArmy, Army rightArmy) {
        StringBuilder stringBuilder = new StringBuilder("|");

        stringBuilder.append(addHpUnit(i, leftArmy));
        stringBuilder.append(PrepearedString.MIDDLE_SPACE);
        stringBuilder.append(addHpUnit(i, rightArmy));

        return stringBuilder.toString();
    }

    private static String getTopStringFromLine(int i, Army leftArmy, Army rightArmy) {
        StringBuilder stringBuilder = new StringBuilder("|");

        stringBuilder.append(addNameUnit(i, leftArmy));
        stringBuilder.append(PrepearedString.MIDDLE_SPACE);
        stringBuilder.append(addNameUnit(i, rightArmy));

        return stringBuilder.toString();
    }

    private static Hero findHeroByCoord(Army army, SquareCoordinate coordinate) {

        return null;
    }

    private static String addNameUnit(int i, Army army) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < 2; j++) {
            Hero hero = findHeroByCoord(army, new SquareCoordinate(i, j));

            if (hero != null) {
                if (true) {
                    stringBuilder.append(String.format("%6s", PrepearedString.CAN_ACTION));
                } else {
                    stringBuilder.append(String.format("%6s", PrepearedString.CANT_ACTION));
                }
                stringBuilder.append(String.format("%4s|", hero.isDefence() ? PrepearedString.DEFENCE : ""));
            } else {
                stringBuilder.append(PrepearedString.MIDDLE_SPACE);
            }
        }

        return stringBuilder.toString();
    }

    private static String addHpUnit(int i, Army army) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < 2; j++) {
            Hero hero = findHeroByCoord(army, new SquareCoordinate(i, j));
            if (hero != null) {
                stringBuilder.append(String.format("HP %7d|", hero.getHp()));
            } else {
                stringBuilder.append(PrepearedString.MIDDLE_SPACE);
            }
        }
        return stringBuilder.toString();
    }

    private static String addStatus(int i, Army army) {
        StringBuilder stringBuilder = new StringBuilder();

        return stringBuilder.toString();
    }
}
