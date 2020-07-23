package com.neolab.heroesGame.enumerations;

public enum GameEvent {
    NEW_ROUND_STARTED(""),
    NEW_GAME_STARTED(""),
    GAME_END_WITH_A_TIE("Draw"),
    YOU_WIN_GAME("Win"),
    YOU_LOSE_GAME("Lose"),
    NOTHING_HAPPEN("");
    private final String description;

    GameEvent(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
