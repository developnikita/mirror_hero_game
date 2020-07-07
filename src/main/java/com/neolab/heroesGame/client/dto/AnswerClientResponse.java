package com.neolab.heroesGame.client.dto;

import com.neolab.heroesGame.client.ai.Answer;

public class AnswerClientResponse {
    private String jsonAnswer;

    public AnswerClientResponse(Answer answer) {
        setAnswer(answer);
    }

    public void setAnswer(Answer answer) {
        jsonAnswer = "";
    }

    public Answer getAnswer() {
        return null;
    }
}
