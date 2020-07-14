package com.neolab.heroesGame.client.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.server.ActionEffect;

/**
 * класс который берет из запроса сервера новую доску и эффефект предыдущего ответа и преобразует в JSON
 */
public class ServerResponse {
    private static final ObjectMapper mapper = new ObjectMapper();
    private BattleArena board;
    private ActionEffect actionEffect;

    public ServerResponse(final String board, final String actionEffect) throws JsonProcessingException {
        setBoard(board);
        setPreviousActionEffect(actionEffect);
    }


    public BattleArena getBoard() {
        return board;
    }

    public ActionEffect getActionEffect() {
        return actionEffect;
    }

    public void setBoard(final String jsonString) throws JsonProcessingException {
        board = mapper.readValue(jsonString, BattleArena.class);
    }

    public void setPreviousActionEffect(final String jsonString) throws JsonProcessingException {
        actionEffect = mapper.readValue(jsonString, ActionEffect.class);
    }
}
