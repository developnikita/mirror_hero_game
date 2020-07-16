package com.neolab.heroesGame.client.networkService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;

public class Client {
    private final Player player;

    public Player getPlayer() {
        return player;
    }

    public Client(final int playerId, final String name) {
        this.player = new PlayerBot(playerId, name);
    }

    public Answer getClientAnswer(final BattleArena board) throws JsonProcessingException, HeroExceptions {
        //todo пока отключил Dto
        return player.getAnswer(board);
    }

}
