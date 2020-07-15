package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.client.dto.ClientRequest;
import com.neolab.heroesGame.client.dto.ServerResponse;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.client.gui.console.AsciiGraphics;
import com.neolab.heroesGame.server.ActionEffect;
import com.neolab.heroesGame.server.answers.Answer;

public class ClientPlayerImitation {
    private Player player;
    private final IGraphics gui;

    public ClientPlayerImitation(int playerId) {
        player = new PlayerBot(playerId);
        gui = new AsciiGraphics();
    }

    public String getAnswer(String jsonBattleArena, String jsonEffect) throws Exception {
        ServerResponse response = new ServerResponse(jsonBattleArena, jsonEffect);
        BattleArena arena = response.board;
        ActionEffect effect = response.actionEffect;
        gui.showPosition(arena, effect, player.getId());
        Answer answer = player.getAnswer(arena);
        return new ClientRequest(answer).jsonAnswer;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerId() {
        return player.getId();
    }
}
