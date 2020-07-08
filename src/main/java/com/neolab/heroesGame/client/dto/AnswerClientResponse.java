package com.neolab.heroesGame.client.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.server.answers.Answer;

public class AnswerClientResponse {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String jsonAnswer;

    public AnswerClientResponse(Answer answer) {
        setAnswer(answer);
    }

    public void setAnswer(Answer answer) {
        try {
            jsonAnswer = mapper.writeValueAsString(answer);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public String getAnswerJson() {
        return jsonAnswer;
    }
}
