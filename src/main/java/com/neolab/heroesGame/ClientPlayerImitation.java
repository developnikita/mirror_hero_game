package com.neolab.heroesGame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.client.dto.ClientRequest;
import com.neolab.heroesGame.client.dto.ExtendedServerResponse;
import com.neolab.heroesGame.client.dto.ServerResponse;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.client.gui.NullGraphics;
import com.neolab.heroesGame.client.gui.console.AsciiGraphics;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.ActionEffect;
import com.neolab.heroesGame.server.answers.Answer;
import com.neolab.heroesGame.server.dto.ExtendedServerRequest;

import java.io.IOException;

public class ClientPlayerImitation {
    private final Player player;
    private final IGraphics gui;

    public ClientPlayerImitation(final int playerId, final String name) {
        player = new PlayerBot(playerId, name);
        gui = new NullGraphics();
    }

    public ClientPlayerImitation(final int playerId, final String name,
                                 final boolean useAsciiGraphics) throws IOException {
        player = new PlayerBot(playerId, name);
        if (useAsciiGraphics) {
            gui = new AsciiGraphics(playerId);
        } else {
            gui = new NullGraphics();
        }
    }

    public String getAnswer(final String jsonRequest) throws IOException, HeroExceptions {
        ExtendedServerResponse response = ExtendedServerResponse.getResponseFromString(jsonRequest);
        gui.showPosition(response, true);
        Answer answer = player.getAnswer(response.arena);
        return new ClientRequest(answer).jsonAnswer;
    }

    public void sendInformation(String jsonRequest) throws IOException {
        ExtendedServerResponse response = ExtendedServerResponse.getResponseFromString(jsonRequest);
        gui.showPosition(response, false);
    }

    public void endGame(String jsonRequest) throws IOException {
        ExtendedServerResponse response = ExtendedServerResponse.getResponseFromString(jsonRequest);
        gui.endGame(response);
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerId() {
        return player.getId();
    }

    public String getPlayerName() {
        return player.getName();
    }
}
