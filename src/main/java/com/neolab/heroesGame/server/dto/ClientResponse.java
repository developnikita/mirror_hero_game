package com.neolab.heroesGame.server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.server.answers.Answer;


/**
 * класс который принимает ответ клиента в формате JSON и преобразует в объект
 */
public class ClientResponse {
    private static final ObjectMapper mapper = new ObjectMapper();
    private Answer answer;

    public ClientResponse(final String jsonString) throws JsonProcessingException {
        setAnswer(jsonString);
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(final String jsonString) throws JsonProcessingException {
        this.answer = mapper.readValue(jsonString, Answer.class);
    }

}
