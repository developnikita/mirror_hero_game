package com.neolab.heroesGame.server.answers;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.Player;

public final class AnswerProcessor {
    private static Player serverBot;
    private static Player player;
    private static AnswerProcessor instance;
    private static Player activePlayer;
    private static Answer answer;
    private static BattleArena board;

    public static Answer askNewPlayer(){
        return null;
    }

    public static void playingProcess(){

    }
}
