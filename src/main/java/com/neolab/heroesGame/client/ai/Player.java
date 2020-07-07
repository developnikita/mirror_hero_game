package com.neolab.heroesGame.client.ai;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.arena.SquareCoordinate;

public class Player {
    private final int id;

    public Player(int id) {
        this.id = id;
    }

    public Answer getAnswer(BattleArena board) {
        return null;
    }

    private SquareCoordinate chooseWarrior(BattleArena board) {
        return null;
    }

    private SquareCoordinate chooseHealer(BattleArena board) {
        return null;
    }


    private SquareCoordinate defineTargetForAttack(BattleArena board) {
        return null;
    }

    private SquareCoordinate defineTargetForHeal(BattleArena board) {
        return null;
    }

    private SquareCoordinate setDeffenceOfUnit(BattleArena board) {
        return null;
    }
}
