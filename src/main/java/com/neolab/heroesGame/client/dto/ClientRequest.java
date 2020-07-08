package com.neolab.heroesGame.client.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.server.answers.Answer;

/**
 * класс который берет ответ пользоватля и преобразует в JSON
 */
public class ClientRequest {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String jsonAnswer;

    public ClientRequest(Answer answer) throws JsonProcessingException {
        setAnswer(answer);
    }

    public void setAnswer(Answer answer) throws JsonProcessingException {
        jsonAnswer = mapper.writeValueAsString(answer);
    }

    public String getAnswerJson() {
        return jsonAnswer;
    }
}
