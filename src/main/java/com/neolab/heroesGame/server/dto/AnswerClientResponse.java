package com.neolab.heroesGame.server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neolab.heroesGame.server.answers.Answer;

public class AnswerClientResponse {
    private static final ObjectMapper mapper = new ObjectMapper();
    private Answer answer;

    public AnswerClientResponse(String jsonString) {
        setAnswer(jsonString);
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(String jsonString) {
        try {
            this.answer = mapper.readValue(jsonString, Answer.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
