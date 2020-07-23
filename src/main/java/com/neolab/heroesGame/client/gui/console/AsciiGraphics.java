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
    private static final int TERMINAL_WIDTH = 70;
    private static final int TERMINAL_HEIGHT = 35;
    private static final int ARMY_WIDTH = 40;
    private static final int ENEMY_ARMY_ROW_START_AT = 0;
    private static final int YOUR_ARMY_ROW_START_AT = 20;
    private static final SquareCoordinate IS_AOE_EFFECT = new SquareCoordinate(-1, -1);
    private final Terminal term;
    private final TextGraphics textGraphics;
    private int leftOffset;
    private final int playerId;
    private final int infoString = 22;

    public AsciiGraphics(final int playerId) throws IOException {

        term = new DefaultTerminalFactory().setInitialTerminalSize(
                new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT)).createTerminal();
        textGraphics = term.newTextGraphics();
        leftOffset = (term.getTerminalSize().getColumns() - ARMY_WIDTH) / 2;
        this.playerId = playerId;
    }

    @Override
    public void showPosition(final ExtendedServerResponse response, final boolean isYourTurn) throws IOException {
        final ActionEffect effect = Optional.ofNullable(response.effect).orElse(ActionEffect.defaultActionEffect());
        term.clearScreen();
        printBattleArena(response.arena, effect);
        printTurn(isYourTurn);
        if (!effect.getSourceUnit().equals(IS_AOE_EFFECT)) {
            printEffect(effect, isYourTurn);
        }
        term.flush();
        term.readInput();
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
        leftOffset = (term.getTerminalSize().getColumns() - ARMY_WIDTH) / 2;
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.putString(leftOffset, ENEMY_ARMY_ROW_START_AT, "------------Армия противника------------");
        printArmy(arena.getEnemyArmy(playerId), effect, false);
        printArmy(arena.getArmy(playerId), effect, true);
        textGraphics.putString(leftOffset, YOUR_ARMY_ROW_START_AT, "---------------Ваша армия---------------");
    }

    /**
     * С помощью булевой переменной определяем на какую строку выводить армию, в каком порядке выводить линии армии,
     * эта или нет армия действовала в прошлом ходу
     * Проходим в цикле по всем доступным координатам, выбираем для юнита цвет,
     * если юнит только что умер, отрисовываем смерть. Иначе рисуем юнита
     * Если юнита нет, то поле будет пустым
     * Для определения умер ли юнит только что проверяем чей последний ход и обращаемся к функции isUnitDiedRightNow
     * для того, чтобы определить наносился ли этому юниту в прошлом ходу урон
     */
    private void printArmy(final Army yours, final ActionEffect effect, final boolean isItYourArmy) {
        int topString = isItYourArmy ? 12 : 2;
        final boolean isLastMoveMakeThisArmy = (effect.getLastMovedPlayerId() == playerId) == isItYourArmy;
        for (int i = 0; i < 2; i++) {
            final int y = isItYourArmy ? 1 - i : i;
            for (int x = 0; x < 3; x++) {
                final SquareCoordinate coordinate = new SquareCoordinate(x, y);
                final Optional<Hero> hero = yours.getHero(coordinate);
                final TextColor textColor = chooseColorForHero(coordinate, effect, isLastMoveMakeThisArmy);
                int step = 13;
                if (!isLastMoveMakeThisArmy && hero.isEmpty() && isUnitDiedRightNow(effect, coordinate)) {
                    printDeadUnit(topString, leftOffset + 1 + x * step);
                } else {
                    printHero(hero, yours, topString, leftOffset + 1 + x * step, textColor);
                }
            }
            topString += 4;
        }
    }

    private void printTurn(final boolean isYourTurn) {
        if (isYourTurn) {
            textGraphics.putString(leftOffset, infoString, "Сейчас ваш ход", SGR.BOLD);
        } else {
            textGraphics.putString(leftOffset, infoString, "Сейчас ход вашего оппонента", SGR.BOLD);
        }

    }

    private void printEffect(final ActionEffect effect, final boolean isNowYourTurn) throws IOException {
        final int offsetForEffect = 23;
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Во время прошлого хода ");
        stringBuilder.append(isNowYourTurn ? "вражеский " : "ваш ");
        stringBuilder.append(effect.toString());

        if (effect.getAction() == HeroActions.ATTACK) {
            printAttackEffect(stringBuilder, offsetForEffect);
        } else if (effect.getAction() == HeroActions.DEFENCE) {
            final int offset = (term.getTerminalSize().getColumns() - stringBuilder.length()) / 2;
            textGraphics.putString(offset, offsetForEffect, stringBuilder.toString());
        } else {
            printHealEffect(stringBuilder, offsetForEffect);
        }
    }

    private void printHealEffect(final StringBuilder stringBuilder, int currentStringNumber) {
        final int offset = 10;
        final String[] strings = stringBuilder.toString().split("восстановил");
        textGraphics.putString(0, currentStringNumber++, strings[0] + ":");
        for (int i = 1; i < strings.length; i++) {
            textGraphics.putString(offset, currentStringNumber++, "восстановил" + strings[i]);
        }
    }

    private void printAttackEffect(final StringBuilder stringBuilder, int currentStringNumber) {
        final int offset = 10;
        final String[] strings = stringBuilder.toString().split("нанес|промахнулся");
        textGraphics.putString(0, currentStringNumber++, strings[0] + ":");
        for (int i = 1; i < strings.length; i++) {
            final String additional = strings[i].contains("урона") ? "-нанес" : "-промахнулся";
            textGraphics.putString(offset, currentStringNumber++, additional + strings[i]);
        }
    }

    private boolean isUnitDiedRightNow(final ActionEffect effect, final SquareCoordinate coordinate) {
        return effect.getTargetUnitsMap().containsKey(coordinate)
                && effect.getAction() == HeroActions.ATTACK;

    }

    /**
     * Если последний ход был этой армии, то юнит может быть циан (если он действовал) или зеленным (если его лечили)
     * если лекарь лечил сам себя, то он циан; если юнит встал в защиту, то он циан
     * Если последний ход делала вражеская армия, то юнит может быть красным (если ему нанесли урон)
     * или желтым (если по нему не попали
     * Если в прошлом ходу юнит никак не участвовал, то он остается белым
     */
    private TextColor chooseColorForHero(final SquareCoordinate coordinate, final ActionEffect effect,
                                         final boolean isLastTurnMakeYou) {
        if (isLastTurnMakeYou) {
            if (effect.getSourceUnit().equals(coordinate)) {
                return TextColor.ANSI.CYAN;
            }
            if (effect.getAction() == HeroActions.HEAL && effect.getTargetUnitsMap().containsKey(coordinate)) {
                return TextColor.ANSI.GREEN;
            }
        } else if (effect.getTargetUnitsMap().containsKey(coordinate) && effect.getAction() == HeroActions.ATTACK) {
            if (effect.getTargetUnitsMap().get(coordinate) != 0) {
                return TextColor.ANSI.RED;
            }
            return TextColor.ANSI.YELLOW;
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
        textGraphics.putString(widthOffset, topString++, "            ");
        textGraphics.putString(widthOffset, topString++, "    DEAD    ", SGR.BLINK);
        textGraphics.putString(widthOffset, topString, "            ");
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }

}
