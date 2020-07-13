package com.neolab.heroesGame.client.console;

public enum PreparedString {
    HORIZONTAL_LINE("________________________________________________________"),
    HORIZONTAL_LINE_WITH_SPACE("|__________|__________|          |__________|__________|"),
    MIDDLE_SPACE("          |"),
    CAN_ACTION("CA"),
    CANT_ACTION("W"),
    DEFENCE("D");
    private final String display;

    PreparedString(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
}
