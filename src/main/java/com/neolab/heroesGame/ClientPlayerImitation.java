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
    private final Player player;
    private final IGraphics gui;

    public ClientPlayerImitation(final int playerId, final String name) {
        player = new PlayerBot(playerId, name);
        gui = new AsciiGraphics();
    }

    public String getAnswer(final String jsonBattleArena, final String jsonEffect) throws Exception {
        final ServerResponse response = new ServerResponse(jsonBattleArena, jsonEffect);
        final BattleArena arena = response.board;
        final ActionEffect effect = response.actionEffect;
        gui.showPosition(arena, effect, player.getId());
        final Answer answer = player.getAnswer(arena);
        return new ClientRequest(answer).jsonAnswer;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerId() {
        return player.getId();
    }
}
