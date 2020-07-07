package com.neolab.heroesGame.heroes;

public class WarlordFootman extends Footman {

    public WarlordFootman(int hp, int damage, float precision, float armor, int armyId) {
        super(hp, damage, precision, armor, armyId);
    }

    public float getImproveCoefficient() { return (float) 0.1; }
}
