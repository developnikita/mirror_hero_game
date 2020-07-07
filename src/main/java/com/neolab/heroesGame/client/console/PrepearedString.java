package com.neolab.heroesGame.client.console;

public enum PrepearedString {
    HORIZONTAL_LINE("________________________________________________________"),
    HORIZONTAL_LINE_WITH_SPACE("|__________|__________|          |__________|__________|"),
    MIDDLE_SPACE("          |"),
    CAN_ACTION("CA"),
    CANT_ACTION("W"),
    DEFENCE("D");
    private String display;

    PrepearedString(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
