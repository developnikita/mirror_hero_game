package com.neolab.heroesGame.server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.client.ai.ActionEffect;

/**
 * класс который берет обновелнную доску и еффект от предыдущего ответа пользоватля и преобразует в JSON
 */
public class ServerRequest {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String boardJson;
    private String actionEffect;

    public ServerRequest(BattleArena board, ActionEffect actionEffect) throws JsonProcessingException {
        setBoard(board);
        setActionEffect(actionEffect);
    }

    public String getActionEffectJson() {
        return actionEffect;
    }

    public String getBoardJson() {
        return boardJson;
    }

    public void setActionEffect(ActionEffect actionEffect) throws JsonProcessingException {
        this.actionEffect = mapper.writeValueAsString(actionEffect);
    }

    public void setBoard(BattleArena board) throws JsonProcessingException {
        this.boardJson = mapper.writeValueAsString(board);
    }

}

