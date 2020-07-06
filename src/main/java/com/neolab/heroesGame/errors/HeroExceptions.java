package com.neolab.heroesGame.errors;

import com.neolab.heroesGame.enumerations.HeroErrorCode;

public class HeroExceptions {
    private HeroErrorCode heroErrorCode;

    public HeroExceptions(HeroErrorCode heroErrorCode){
        this.heroErrorCode = heroErrorCode;
    }

    public HeroErrorCode getHeroErrorCode() {
        return heroErrorCode;
    }
}
