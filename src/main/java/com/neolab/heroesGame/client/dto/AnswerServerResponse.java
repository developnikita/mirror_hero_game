package com.neolab.heroesGame.client.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.server.ActionEffect;

public class AnswerServerResponse {
    private static final ObjectMapper mapper = new ObjectMapper();
    private BattleArena board;
    private ActionEffect previousActionEffect;

    public AnswerServerResponse(String jsonString) {
        setBoard(jsonString);
        setPreviousActionEffect(jsonString);
    }

    public void setBoard(String jsonString) {
        try {
            board = mapper.readValue(jsonString, BattleArena.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public BattleArena getBoard() {
        return board;
    }

    public ActionEffect getPreviousActionEffect() {
        return previousActionEffect;
    }

    public void setPreviousActionEffect(String jsonString) {
        try {
            previousActionEffect = mapper.readValue(jsonString, ActionEffect.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
