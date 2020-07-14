package com.neolab.heroesGame;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.neolab.heroesGame.errors.HeroExceptions;
import com.neolab.heroesGame.server.networkServiceServer.Server;

public class ClientServerGame {
    public static void main(final String[] args) throws JsonProcessingException, HeroExceptions {
        final Server server = new Server();
        server.playingProcess();
    }
}
