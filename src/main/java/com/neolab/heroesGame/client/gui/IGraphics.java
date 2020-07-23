package com.neolab.heroesGame.client.gui;

import com.neolab.heroesGame.client.dto.ExtendedServerResponse;

import java.io.IOException;
import java.util.List;

public interface IGraphics {

    void showPosition(final ExtendedServerResponse response, boolean isYourTurn) throws IOException;

    void endGame(final ExtendedServerResponse response) throws IOException;

    int getChoose(final List<String> strings) throws IOException;
}
