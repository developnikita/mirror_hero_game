package com.neolab.heroesGame.client.gui;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.dto.ExtendedServerResponse;
import com.neolab.heroesGame.server.ActionEffect;

public class NullGraphics implements IGraphics {

    public NullGraphics() {

    }

    @Override
    public void showPosition(ExtendedServerResponse response, boolean isYourTurn) {

    }

    @Override
    public void endGame(ExtendedServerResponse response) {

    }
}
