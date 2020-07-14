package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.client.dto.ClientRequest;
import com.neolab.heroesGame.client.dto.ServerResponse;
import com.neolab.heroesGame.server.answers.Answer;

public class ClientPlayerImitation {
    private Player player;

    public ClientPlayerImitation(int playerId) {
        player = new PlayerBot(playerId);
    }

    public String getAnswer(String jsonBattleArena) throws Exception {
        BattleArena arena = new ServerResponse(jsonBattleArena).board;
        Answer answer = player.getAnswer(arena);
        return new ClientRequest(answer).jsonAnswer;
    }

    public Player getPlayer() {
        return player;
    }
}
