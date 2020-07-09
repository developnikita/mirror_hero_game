package com.neolab.heroesGame.heroes;

public class WarlordFootman extends Footman implements IWarlord {

    public WarlordFootman(int hp, int damage, float precision, float armor) {
        super(hp, damage, precision, armor);
    }

    public float getImproveCoefficient() {
        return 0.1f;
    }
}
