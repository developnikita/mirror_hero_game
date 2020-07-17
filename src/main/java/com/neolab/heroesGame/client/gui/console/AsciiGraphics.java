package com.neolab.heroesGame.client.gui.console;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.neolab.heroesGame.aditional.CommonFunction;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;
import com.neolab.heroesGame.client.dto.ExtendedServerResponse;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.enumerations.GameEvent;
import com.neolab.heroesGame.enumerations.HeroActions;
import com.neolab.heroesGame.heroes.Hero;
import com.neolab.heroesGame.server.ActionEffect;

import java.io.IOException;
import java.util.Optional;


public class AsciiGraphics implements IGraphics {
    private final Terminal term;
    private final TextGraphics textGraphics;
    private final int step = 13;
    private int leftOffset;
    private final int playerId;
    private final int infoString = 22;

    public AsciiGraphics(final int playerId) throws IOException {
        term = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(70, 35)).createTerminal();
        textGraphics = term.newTextGraphics();
        leftOffset = (term.getTerminalSize().getColumns() - 40) / 2;
        this.playerId = playerId;
    }

    @Override
    public void showPosition(final BattleArena arena, final ActionEffect effect,
                             final boolean isYourTurn) throws IOException {
        term.clearScreen();
        printBattleArena(arena, effect);
        printTurn(isYourTurn);
        if (effect != null) {
            printEffect(effect, isYourTurn);
        }
        term.flush();
        term.readInput();
    }

    @Override
    public void showPosition(final ExtendedServerResponse response, boolean isYourTurn) throws IOException {
        showPosition(response.arena, response.effect, isYourTurn);
    }

    @Override
    public void endGame(final ExtendedServerResponse response) throws IOException {
        term.clearScreen();
        printBattleArena(response.arena, response.effect);
        printGameResult(response.event);
        term.flush();
        term.readInput();
        term.close();
    }

    private void printGameResult(final GameEvent event) {
        if (event == GameEvent.YOU_WIN_GAME) {
            textGraphics.putString(leftOffset, infoString, "Поздравляем, вы одержали великую победу", SGR.BOLD);
        } else if (event == GameEvent.YOU_LOSE_GAME) {
            textGraphics.putString(leftOffset, infoString, "Ты недостоен своей жизни, умри!", SGR.BOLD);
        } else {
            textGraphics.putString(leftOffset, infoString, "Никто не смог получить преимущества. Ничья", SGR.BOLD);
        }
    }

    private void printBattleArena(final BattleArena arena, final ActionEffect effect) throws IOException {
        leftOffset = (term.getTerminalSize().getColumns() - 40) / 2;
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(leftOffset, 0, "------------Армия противника------------");
        printArena(arena, effect, playerId);
        textGraphics.putString(leftOffset, 20, "---------------Ваша армия---------------");
    }

    private void printTurn(boolean isYourTurn) {
        if (isYourTurn) {
            textGraphics.putString(leftOffset, infoString, "Сейчас ваш ход", SGR.BOLD);
        } else {
            textGraphics.putString(leftOffset, infoString, "Сейчас ход вашего оппонента", SGR.BOLD);
        }

    }

    private void printEffect(final ActionEffect effect, final boolean isNowYourTurn) throws IOException {
        final int offsetForEffect = 23;
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

    private void printArena(final BattleArena arena, final ActionEffect effect,
                            final Integer playerId) {
        final Army enemy = arena.getEnemyArmy(playerId);
        printEnemyArmy(enemy, effect);
        final Army yours = arena.getArmy(playerId);
        printYourArmy(yours, effect);
    }

    private void printEnemyArmy(final Army enemy, final ActionEffect effect) {
        int topString = 2;
        final boolean isLastMoveMakeEnemy = effect != null && effect.getLastMovedPlayerId() != playerId;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++) {
                final SquareCoordinate coordinate = new SquareCoordinate(x, y);
                final Optional<Hero> hero = enemy.getHero(coordinate);
                TextColor textColor = chooseColorForHero(coordinate, effect, isLastMoveMakeEnemy);
                if (!isLastMoveMakeEnemy && isUnitDiedRightNow(hero, effect, coordinate)) {
                    printDeadUnit(topString, leftOffset + 1 + x * step);
                } else {
                    printHero(hero, enemy, topString, leftOffset + 1 + x * step, textColor);
                }
            }
            topString += 4;
        }
    }

    private void printYourArmy(final Army yours, final ActionEffect effect) {
        int topString = 12;
        final boolean isLastMoveMakeYou = effect != null && effect.getLastMovedPlayerId() == playerId;
        for (int y = 1; y >= 0; y--) {
            for (int x = 0; x < 3; x++) {
                final SquareCoordinate coordinate = new SquareCoordinate(x, y);
                final Optional<Hero> hero = yours.getHero(coordinate);
                TextColor textColor = chooseColorForHero(coordinate, effect, isLastMoveMakeYou);
                if (!isLastMoveMakeYou && isUnitDiedRightNow(hero, effect, coordinate)) {
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

    private TextColor chooseColorForHero(final SquareCoordinate coordinate, final ActionEffect effect,
                                         final boolean isLastTurnMakeYou) {
        if (effect == null) {
            return TextColor.ANSI.WHITE;
        }
        if (isLastTurnMakeYou) {
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
