package com.neolab.heroesGame.client.dto;

import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.ActionEffect;

public class AnswerServerResponse {
    private BattleArena board;
    private ActionEffect previousActionEffect;

    public AnswerServerResponse(String jsonString) {
        setBoard(jsonString);
        setPreviousActionEffect(jsonString);
    }

    public void setBoard(String jsonString) {
        board = null;
    }

    public BattleArena getBoard() {
        return board;
    }

    public ActionEffect getPreviousActionEffect() {
        return previousActionEffect;
    }

    public void setPreviousActionEffect(String jsonString) {
        previousActionEffect = null;
    }
}
