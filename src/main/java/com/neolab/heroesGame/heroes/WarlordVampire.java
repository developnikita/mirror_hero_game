package com.neolab.heroesGame.heroes;

public class WarlordVampire extends Magician implements IWarlord {

    public WarlordVampire(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
    }

    public float getImproveCoefficient() {
        return 0.05f;
    }
}
