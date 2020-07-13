package com.neolab.heroesGame.errors;

import com.neolab.heroesGame.enumerations.HeroErrorCode;

import java.util.function.Supplier;

public class HeroExceptions extends Exception {
    private final HeroErrorCode heroErrorCode;

    public HeroExceptions(HeroErrorCode heroErrorCode) {
        this.heroErrorCode = heroErrorCode;
    }

    public HeroErrorCode getHeroErrorCode() {
        return heroErrorCode;
    }
}
