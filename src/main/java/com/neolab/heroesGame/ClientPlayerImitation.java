package com.neolab.heroesGame;

import com.neolab.heroesGame.arena.Army;
import com.neolab.heroesGame.client.ai.Player;
import com.neolab.heroesGame.client.ai.PlayerBot;
import com.neolab.heroesGame.client.ai.PlayerHuman;
import com.neolab.heroesGame.client.dto.ClientRequest;
import com.neolab.heroesGame.client.dto.ExtendedServerResponse;
import com.neolab.heroesGame.client.gui.IGraphics;
import com.neolab.heroesGame.client.gui.NullGraphics;
import com.neolab.heroesGame.client.gui.console.AsciiGraphics;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.answers.Answer;

import java.io.IOException;

public class ClientPlayerImitation {
    private final Player player;
    private final IGraphics gui;

    public ClientPlayerImitation(final int playerId, final String name) {
        player = new PlayerBot(playerId, name);
        gui = new NullGraphics();
    }

    public ClientPlayerImitation(final int playerId, final String name,
                                  final IGraphics gui) {
        player = new PlayerBot(playerId, name);
        this.gui = gui;
    }

    private ClientPlayerImitation(final Player player,
                                  final IGraphics gui) {
        this.player = player;
        this.gui = gui;
    }

    public static ClientPlayerImitation createPlayerWithAsciiGraphics(final int playerId,
                                                                      final String name) throws IOException {
        final IGraphics graphics = new AsciiGraphics(playerId);
        return new ClientPlayerImitation(playerId, name, graphics);
    }

    public static ClientPlayerImitation createHumanPlayerWithAsciiGraphics(final int playerId,
                                                                           final String name) throws IOException {
        final IGraphics graphics = new AsciiGraphics(playerId);
        final Player human = new PlayerHuman(playerId, name, graphics);
        return new ClientPlayerImitation(human, graphics);
    }

    public String getAnswer(final ExtendedServerResponse response) throws IOException, HeroExceptions {
        gui.showPosition(response, true);
        final Answer answer = player.getAnswer(response.arena);
        return new ClientRequest(answer).jsonAnswer;
    }

    public void sendInformation(final ExtendedServerResponse response) throws IOException {
        gui.showPosition(response, false);
    }

    public void endGame(final ExtendedServerResponse response) throws IOException {
        gui.endGame(response);
    }

    public String getArmyFirst(final int armySize) {
        return player.getStringArmyFirst(armySize);
    }

    public String getArmySecond(final int armySize, final Army army) {
        return player.getStringArmySecond(armySize, army);
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
