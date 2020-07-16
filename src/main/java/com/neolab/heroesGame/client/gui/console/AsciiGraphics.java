package com.neolab.heroesGame.client.gui.console;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.neolab.heroesGame.aditional.CommonFunction;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.ActionEffect;

import java.util.Optional;


public class AsciiGraphics implements IGraphics {
    private final Terminal term;
    private final TextGraphics textGraphics;
    private final int step = 13;
    private int leftOffset;

    public AsciiGraphics() throws Exception {
        term = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(70, 30)).createTerminal();
        textGraphics = term.newTextGraphics();
        leftOffset = (term.getTerminalSize().getColumns() - 40) / 2;
    }

    @Override
    public void showPosition(final BattleArena arena, final ActionEffect effect,
                             final Integer playerId, final boolean isYourTurn) throws Exception {
        term.clearScreen();
        leftOffset = (term.getTerminalSize().getColumns() - 40) / 2;

        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(leftOffset, 0, "------------Армия противника------------");
        drawArena(arena, effect, playerId, isYourTurn);
        textGraphics.putString(leftOffset, 20, "---------------Ваша армия---------------");
        if (effect != null) {
            showEffect(effect, isYourTurn);
        }
        term.flush();
        term.readInput();
    }

    private void showEffect(final ActionEffect effect, final boolean isNowYourTurn) throws Exception {
        final int offsetForEffect = 22;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Во время прошлого хода ");
        stringBuilder.append(isNowYourTurn ? "вражеский " : "ваш ");
        stringBuilder.append(effect.toString());

        if (effect.getAction() == HeroActions.ATTACK) {
            printAttackEffect(stringBuilder, offsetForEffect);
        } else if (effect.getAction() == HeroActions.DEFENCE) {
            int offset = (term.getTerminalSize().getColumns() - stringBuilder.length()) / 2;
            textGraphics.putString(offset, offsetForEffect, stringBuilder.toString());
        } else {
            printHealEffect(stringBuilder, offsetForEffect);
        }
    }

    private void printHealEffect(final StringBuilder stringBuilder, int currentStringNumber) {
        final int offset = 10;
        String[] strings = stringBuilder.toString().split("восстановил");
        textGraphics.putString(0, currentStringNumber++, strings[0] + ":");
        for (int i = 1; i < strings.length; i++) {
            textGraphics.putString(offset, currentStringNumber++, "восстановил" + strings[i]);
        }
    }

    private void printAttackEffect(final StringBuilder stringBuilder, int currentStringNumber) {
        int offset = 10;
        String[] strings = stringBuilder.toString().split("нанес|промахнулся");
        textGraphics.putString(0, currentStringNumber++, strings[0] + ":");
        for (int i = 1; i < strings.length; i++) {
            String additional = strings[i].contains("урона") ? "-нанес" : "-промахнулся";
            textGraphics.putString(offset, currentStringNumber++, additional + strings[i]);
        }
    }

    private void drawArena(final BattleArena arena, final ActionEffect effect,
                           final Integer playerId, final boolean isNowYourTurn) throws Exception {
        final Army enemy = arena.getEnemyArmy(playerId);
        drawEnemyArmy(enemy, effect, isNowYourTurn);
        final Army yours = arena.getArmy(playerId);
        drawYourArmy(yours, effect, isNowYourTurn);
    }

    private void drawEnemyArmy(final Army enemy, final ActionEffect effect,
                               final boolean isNowYourTurn) {
        int topString = 2;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++) {
                final SquareCoordinate coordinate = new SquareCoordinate(x, y);
                final Optional<Hero> hero = enemy.getHero(coordinate);
                TextColor textColor = chooseColorForEnemy(coordinate, effect, isNowYourTurn);
                if (!isNowYourTurn && isUnitDiedRightNow(hero, effect, coordinate)) {
                    printDeadUnit(topString, leftOffset + 1 + x * step);
                } else {
                    printHero(hero, enemy, topString, leftOffset + 1 + x * step, textColor);
                }
            }
            topString += 4;
        }
    }

    private void drawYourArmy(final Army yours, final ActionEffect effect,
                              final boolean isNowYourTurn) {
        int topString = 12;
        for (int y = 1; y >= 0; y--) {
            for (int x = 0; x < 3; x++) {
                final SquareCoordinate coordinate = new SquareCoordinate(x, y);
                final Optional<Hero> hero = yours.getHero(coordinate);
                TextColor textColor = chooseColorForYour(coordinate, effect, isNowYourTurn);
                if (isNowYourTurn && isUnitDiedRightNow(hero, effect, coordinate)) {
                    printDeadUnit(topString, leftOffset + 1 + x * step);
                } else {
                    printHero(hero, yours, topString, leftOffset + 1 + x * step, textColor);
                }
            }
            topString += 4;
        }
    }

    private boolean isUnitDiedRightNow(Optional<Hero> hero, ActionEffect effect, SquareCoordinate coordinate) {
        return hero.isEmpty()
                && effect.getTargetUnitsMap().containsKey(coordinate)
                && effect.getAction() == HeroActions.ATTACK;

    }

    private TextColor chooseColorForYour(final SquareCoordinate coordinate, final ActionEffect effect,
                                         final boolean isNowYourTurn) {
        if (effect == null) {
            return TextColor.ANSI.WHITE;
        }
        if (isNowYourTurn) {
            if (effect.getTargetUnitsMap().containsKey(coordinate)) {
                if (effect.getAction() == HeroActions.ATTACK) {
                    if (effect.getTargetUnitsMap().get(coordinate) != 0) {
                        return TextColor.ANSI.RED;
                    }
                    return TextColor.ANSI.YELLOW;
                }
            }
        } else {
            if (effect.getSourceUnit().equals(coordinate)) {
                return TextColor.ANSI.CYAN;
            }
            if (effect.getAction() == HeroActions.HEAL && effect.getTargetUnitsMap().containsKey(coordinate)) {
                return TextColor.ANSI.GREEN;
            }
        }
        return TextColor.ANSI.WHITE;
    }

    private TextColor chooseColorForEnemy(final SquareCoordinate coordinate, final ActionEffect effect,
                                          final boolean isNowYourTurn) {
        if (effect == null) {
            return TextColor.ANSI.WHITE;
        }
        if (isNowYourTurn) {
            if (effect.getSourceUnit().equals(coordinate)) {
                return TextColor.ANSI.CYAN;
            }
            if (effect.getAction() == HeroActions.HEAL && effect.getTargetUnitsMap().containsKey(coordinate)) {
                return TextColor.ANSI.GREEN;
            }
        } else if (effect.getTargetUnitsMap().containsKey(coordinate)) {
            if (effect.getAction() == HeroActions.ATTACK) {
                if (effect.getTargetUnitsMap().get(coordinate) != 0) {
                    return TextColor.ANSI.RED;
                }
                return TextColor.ANSI.YELLOW;
            }
        }
        return TextColor.ANSI.WHITE;
    }

    private void printHero(final Optional<Hero> hero, final Army army, int heightOffset,
                           final int widthOffset, final TextColor textColor) {
        textGraphics.setBackgroundColor(textColor);
        textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
        textGraphics.putString(widthOffset, heightOffset++, CommonFunction.classToString(hero));
        textGraphics.putString(widthOffset, heightOffset++, CommonFunction.hpToString(hero));
        textGraphics.putString(widthOffset, heightOffset, CommonFunction.statusToString(hero, army));
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }

    private void printDeadUnit(int topString, final int widthOffset) {
        textGraphics.setBackgroundColor(TextColor.ANSI.RED);
        textGraphics.setForegroundColor(TextColor.ANSI.BLACK);
        textGraphics.putString(widthOffset, topString++, "            |");
        textGraphics.putString(widthOffset, topString++, "    DEAD    |");
        textGraphics.putString(widthOffset, topString, "            |");
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }

}
