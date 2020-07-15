package com.neolab.heroesGame.client.gui.console;

import com.neolab.heroesGame.aditional.CommonFunction;
import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.server.ActionEffect;

public class AsciiGraphics implements IGraphics {

    public AsciiGraphics() {

    }

    @Override
    public void showPosition(BattleArena arena, ActionEffect effect, Integer playerId) throws Exception {
        clearConsole();
        System.out.println("------------Армия противника------------");
        System.out.println(arenaToString(arena, playerId));
        System.out.println("---------------Ваша армия---------------");
        if (effect != null) {
            System.out.println(effect.toString());
        }
        System.out.print("Для продолжения нажмите Enter: ");
        System.in.read();
    }

    private String arenaToString(BattleArena arena, Integer playerId) {
        final StringBuilder stringBuilder = new StringBuilder();
        Army yourArmy = arena.getArmy(playerId);
        Army enemyArmy = arena.getEnemyArmy(playerId);
        stringBuilder.append(CommonFunction.printArmy(enemyArmy));
        stringBuilder.append(CommonFunction.printInvertLinesArmy(yourArmy));
        return stringBuilder.toString();
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
