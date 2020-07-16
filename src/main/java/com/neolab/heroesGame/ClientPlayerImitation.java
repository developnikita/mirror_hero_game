package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.client.dto.ClientRequest;
import com.neolab.heroesGame.client.dto.ServerResponse;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.client.gui.NullGraphics;
import com.neolab.heroesGame.client.gui.console.AsciiGraphics;
import com.neolab.heroesGame.server.ActionEffect;
import com.neolab.heroesGame.server.answers.Answer;

public class ClientPlayerImitation {
    private final Player player;
    private final IGraphics gui;

    public ClientPlayerImitation(final int playerId, final String name) {
        player = new PlayerBot(playerId, name);
        gui = new NullGraphics();
    }

    public ClientPlayerImitation(final int playerId, final String name,
                                 final boolean useAsciiGraphics) throws Exception {
        player = new PlayerBot(playerId, name);
        if (useAsciiGraphics) {
            gui = new AsciiGraphics();
        } else {
            gui = new AsciiGraphics();
        }
    }

    public String getAnswer(final String jsonBattleArena, final String jsonEffect) throws Exception {
        final ServerResponse response = new ServerResponse(jsonBattleArena, jsonEffect);
        final BattleArena arena = response.board;
        final ActionEffect effect = response.actionEffect;
        gui.showPosition(arena, effect, player.getId(), true);
        Answer answer = player.getAnswer(response.board);
        return new ClientRequest(answer).jsonAnswer;
    }

    public void sendInformation(String jsonBattleArena, String jsonEffect) throws Exception {
        ServerResponse response = new ServerResponse(jsonBattleArena, jsonEffect);
        gui.showPosition(response.board, response.actionEffect, player.getId(), false);
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerId() {
        return player.getId();
    }
}
