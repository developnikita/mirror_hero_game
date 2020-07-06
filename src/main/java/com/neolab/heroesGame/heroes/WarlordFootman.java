package com.neolab.heroesGame.heroes;

public class WarlordFootman extends Footman {

    private final float improveCoefficient;

    public WarlordFootman(int hp, int damage, float precision, float armor, int armyId, float improveCoefficient) {
        super(hp, damage, precision, armor, armyId);
        this.improveCoefficient = improveCoefficient;
    }


}
