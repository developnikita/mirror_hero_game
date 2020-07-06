package com.neolab.heroesGame.heroes;

public class WarlordMagician extends Magician{

    private final float improveCoefficient;

    public WarlordMagician(int hp, int damage, float precision, float armor, int armyId, float improveCoefficient) {
        super(hp, damage, precision, armor, armyId);
        this.improveCoefficient = improveCoefficient;
    }
}
