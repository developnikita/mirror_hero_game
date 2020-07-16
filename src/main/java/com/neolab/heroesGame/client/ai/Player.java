package com.neolab.heroesGame.client.ai;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;

public abstract class Player {

    private final int id;

    private final String name;

    public Player(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public abstract Answer getAnswer(BattleArena board) throws HeroExceptions;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
