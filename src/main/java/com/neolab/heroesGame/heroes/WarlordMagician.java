package com.neolab.heroesGame.heroes;

public class WarlordMagician extends Magician implements IWarlord {

    public WarlordMagician(int hp, int damage, float precision, float armor, int armyId) {
        super(hp, damage, precision, armor, armyId);
    }

    public float getImproveCoefficient() {
        return 0.05f;
    }
}
