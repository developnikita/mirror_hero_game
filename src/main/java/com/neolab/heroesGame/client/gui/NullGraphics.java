package com.neolab.heroesGame.client.gui;

import com.neolab.heroesGame.client.dto.ExtendedServerResponse;

import java.util.List;

public class NullGraphics implements IGraphics {

    public NullGraphics() {

    }

    @Override
    public void showPosition(final ExtendedServerResponse response, final boolean isYourTurn) {

    }

    @Override
    public void endGame(final ExtendedServerResponse response) {

    }

    @Override
    public int getChoose(final List<String> strings) {
        return 0;
    }
}
