package com.neolab.heroesGame.client.gui;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.dto.ExtendedServerResponse;
import com.neolab.heroesGame.server.ActionEffect;

import java.io.IOException;

public interface IGraphics {

    void showPosition(ExtendedServerResponse response, boolean isYourTurn) throws IOException;

    void endGame(ExtendedServerResponse response) throws IOException;
}
