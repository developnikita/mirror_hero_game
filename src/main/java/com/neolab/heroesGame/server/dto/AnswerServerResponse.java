package com.neolab.heroesGame.server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.arena.BattleArena;

public class AnswerServerResponse {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String jsonAnswer;

    public AnswerServerResponse(BattleArena board) {
        setBoard(board);
    }

    public void setBoard(BattleArena board) {
        try {
            jsonAnswer = mapper.writeValueAsString(board);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public String getBoardJson() {
        return jsonAnswer;
    }
}

