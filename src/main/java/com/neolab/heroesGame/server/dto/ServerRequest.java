package com.neolab.heroesGame.server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.arena.BattleArena;
import com.neolab.heroesGame.server.ActionEffect;

/**
 * класс который берет обновелнную доску и еффект от предыдущего ответа пользоватля и преобразует в JSON
 */
public class ServerRequest {
    public final String boardJson;
    public final String actionEffect;

    public ServerRequest(final BattleArena board, final ActionEffect actionEffect) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        this.actionEffect = mapper.writeValueAsString(actionEffect);
        this.boardJson = mapper.writeValueAsString(board);
    }


}

