package com.neolab.heroesGame.client.ai;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;

public abstract class Player {
    private final int id;

    public Player(int id) {
        this.id = id;
    }

    abstract public Answer getAnswer(BattleArena board) throws HeroExceptions;

    public int getId() {
        return id;
    }
}
