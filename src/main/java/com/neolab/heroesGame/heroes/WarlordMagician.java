package com.neolab.heroesGame.heroes;

public class WarlordMagician extends Magician{

    public WarlordMagician(int hp, int damage, float precision, float armor, int armyId) {
        super(hp, damage, precision, armor, armyId);
    }

    public float getImproveCoefficient() { return (float) 0.05; }
}
